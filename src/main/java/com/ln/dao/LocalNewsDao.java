package com.ln.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.geoNear;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.lookup;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.ln.domain.Coordinates;
import com.ln.domain.NewsMediaAgg;
import com.ln.domain.NewsUserAgg;
import com.ln.entity.DraftNews;
import com.ln.entity.PublishNews;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

@Repository
public class LocalNewsDao {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private GridFS gridFS;

	public void saveDraftNews(DraftNews dn) {
		mongoTemplate.save(dn);
	}

	public void updateDraftNews(DraftNews draftNews) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(draftNews.getId()));

		Update update = new Update();
		update.set("title", draftNews.getTitle());
		update.set("description", draftNews.getDescription());
		update.set("refLink", draftNews.getRefLink());
		update.set("location", draftNews.getLocation());
		update.set("newsDate", draftNews.getNewsDate());
		update.set("editorId", draftNews.getEditorId());
		update.set("updateDate", draftNews.getUpdateDate());
		update.set("channel", draftNews.getChannel());
		update.set("language", draftNews.getLanguage());
		update.set("currentLocation", draftNews.getCurrentLocation());

		mongoTemplate.updateFirst(query, update, DraftNews.class);
	}

	public DraftNews getDraftNews(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, DraftNews.class);
	}

	public List<NewsUserAgg> getDraftNewsByEditor(ObjectId editorId) {

		Aggregation agg = newAggregation(match(Criteria.where("editorId").is(editorId)),
				lookup("channels", "channel", "_id", "channelData"), sort(Sort.Direction.DESC, "updateDate"));

		AggregationResults<NewsUserAgg> groupResults = mongoTemplate.aggregate(agg, DraftNews.class, NewsUserAgg.class);
		List<NewsUserAgg> result = groupResults.getMappedResults();

		return result;

	}

	public void removeDraftNews(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		mongoTemplate.remove(query, DraftNews.class);
	}

	public String savePublishNews(PublishNews publishNews) {
		mongoTemplate.save(publishNews);
		return publishNews.getId();
	}

	public PublishNews getPublishNews(String id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(id));
		return mongoTemplate.findOne(query, PublishNews.class);
	}

	public void updatePublishNews(PublishNews publishNews) {
		Query query = new Query();
		query.addCriteria(Criteria.where("id").is(publishNews.getId()));

		Update update = new Update();
		update.set("title", publishNews.getTitle());
		update.set("description", publishNews.getDescription());
		update.set("refLink", publishNews.getRefLink());
		update.set("location", publishNews.getLocation());
		update.set("newsDate", publishNews.getNewsDate());
		update.set("reviewerId", publishNews.getReviewerId());
		update.set("reviewDate", publishNews.getReviewDate());
		update.set("status", publishNews.getStatus());
		update.set("updateDate", publishNews.getUpdateDate());
		update.set("channel", publishNews.getChannel());
		update.set("language", publishNews.getLanguage());
		update.set("currentLocation", publishNews.getCurrentLocation());

		mongoTemplate.updateFirst(query, update, PublishNews.class);
	}

	public List<NewsUserAgg> getReviewNews(ObjectId editorId, String newsDate, List<ObjectId> channels) {

		Criteria criteria = Criteria.where("status").is(null);
		if (editorId != null)
			criteria.and("editorId").is(editorId);
		if (StringUtils.isNotBlank(newsDate))
			criteria.and("newsDate").is(newsDate);
		if (channels != null && channels.size() > 0)
			criteria.and("channel").in(channels);

		Aggregation agg = newAggregation(match(criteria), lookup("user_details", "editorId", "_id", "editors"),
				lookup("channels", "channel", "_id", "channelData"), sort(Sort.Direction.DESC, "updateDate"));

		AggregationResults<NewsUserAgg> groupResults = mongoTemplate.aggregate(agg, PublishNews.class,
				NewsUserAgg.class);
		List<NewsUserAgg> result = groupResults.getMappedResults();

		return result;
	}

	public List<NewsUserAgg> getEditorPublishNews(ObjectId editorId) {
		Aggregation agg = newAggregation(match(Criteria.where("editorId").is(editorId)),
				lookup("channels", "channel", "_id", "channelData"), sort(Sort.Direction.DESC, "updateDate"));

		AggregationResults<NewsUserAgg> groupResults = mongoTemplate.aggregate(agg, PublishNews.class,
				NewsUserAgg.class);
		List<NewsUserAgg> result = groupResults.getMappedResults();

		return result;
	}

	public List<NewsUserAgg> getPublicNews(String newsId, List<String> languages, String channelId) {
		Criteria criteria = Criteria.where("status").is("A");
		if (StringUtils.isNotBlank(newsId))
			criteria.and("id").is(new ObjectId(newsId));
		if (StringUtils.isNotBlank(channelId))
			criteria.and("channel").is(new ObjectId(channelId));
		if (languages != null && languages.size() > 0)
			criteria.and("language").in(languages);

		Aggregation agg = newAggregation(match(criteria), lookup("user_details", "editorId", "_id", "editors"),
				lookup("channels", "channel", "_id", "channelData"), sort(Sort.Direction.DESC, "updateDate"));

		AggregationResults<NewsUserAgg> groupResults = mongoTemplate.aggregate(agg, PublishNews.class,
				NewsUserAgg.class);
		List<NewsUserAgg> result = groupResults.getMappedResults();

		return result;
	}

	public List<NewsUserAgg> getPublicNewsByLoc(Object[] locations, List<String> languages) {

		Set<NewsUserAgg> result = new LinkedHashSet<>();
		for (int i = 0; i < locations.length; i++) {
			Criteria criteria = Criteria.where("status").is("A");
			if (languages != null && languages.size() > 0)
				criteria.and("language").in(languages);
			Coordinates coord = (Coordinates) locations[i];
			Aggregation agg = newAggregation(
					geoNear(NearQuery.near(new Point(coord.getLat(), coord.getLng()), Metrics.KILOMETERS).maxDistance(5)
							.spherical(false), "distance"),
					match(criteria), lookup("user_details", "editorId", "_id", "editors"),
					lookup("channels", "channel", "_id", "channelData"), sort(Sort.Direction.DESC, "updateDate"));

			AggregationResults<NewsUserAgg> groupResults = mongoTemplate.aggregate(agg, PublishNews.class,
					NewsUserAgg.class);
			result.addAll(groupResults.getMappedResults());
		}
		return new ArrayList<NewsUserAgg>(result);
	}

	public void saveImage(String newsId, InputStream newsImage) {
		GridFSInputFile gfs = gridFS.createFile(newsImage);
		gfs.setId(new ObjectId(newsId));
		gfs.save();
	}

	public void removeImage(String newsId) {
		gridFS.remove(new ObjectId(newsId));
	}

	public NewsMediaAgg getNewsMedia(ObjectId newsId) {
		Criteria criteria = Criteria.where("_id").is(newsId);

		Aggregation agg = newAggregation(match(criteria), lookup("fs.files", "_id", "_id", "imageFiles"),
				lookup("fs.chunks", "imageFiles._id", "files_id", "imageChunks"));

		AggregationResults<NewsMediaAgg> groupResults = mongoTemplate.aggregate(agg, PublishNews.class,
				NewsMediaAgg.class);

		List<NewsMediaAgg> result = groupResults.getMappedResults();
		if (result != null && result.size() > 0)
			return result.get(0);
		return null;
	}

}

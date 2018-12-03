package com.ln.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ln.dao.ChannelDao;
import com.ln.dao.LocalNewsDao;
import com.ln.domain.NewsMediaAgg;
import com.ln.domain.NewsUserAgg;
import com.ln.domain.PostNews;
import com.ln.domain.PublicNews;
import com.ln.domain.ReviewNews;
import com.ln.entity.Channel;
import com.ln.entity.DraftNews;
import com.ln.entity.PublishNews;

@Service
public class LocalNewsService {

	@Autowired
	private LocalNewsDao localNewsDao;
	
	@Autowired
	private ChannelDao channelDao;

	public void saveDraftNews(PostNews news) {
		DraftNews draftNews = mapDraftNews(news);
		localNewsDao.saveDraftNews(draftNews);
	}
	
	private DraftNews mapDraftNews(PostNews news) {
		DraftNews draftNews = new DraftNews();
		draftNews.setTitle(news.getTitle());
		draftNews.setDescription(news.getDescription());
		draftNews.setLocation(news.getLocation());
		draftNews.setNewsDate(news.getNewsDate());
		draftNews.setRefLink(news.getRefLink());
		draftNews.setEditorId(new ObjectId(news.getEditorId()));
		draftNews.setChannel(new ObjectId(news.getChannel()));
		draftNews.setCurrentLocation(news.getCurrentLocation());
		draftNews.setLanguage(news.getLanguage());
		return draftNews;
	}

	public void updateDraftNews(PostNews news) {
		DraftNews draftNews = mapDraftNews(news);
		draftNews.setId(news.getId());
		draftNews.setUpdateDate(new Date());
		localNewsDao.updateDraftNews(draftNews);
	}

	public DraftNews getDraftNews(String id) {
		return localNewsDao.getDraftNews(id);
	}

	public List<PostNews> getDraftNewsByEditor(String editorId) {
		List<NewsUserAgg> listDb = localNewsDao.getDraftNewsByEditor(new ObjectId(editorId));
		List<PostNews> retList = new ArrayList<>();
		if (listDb != null)
			listDb.stream().forEach(nua -> {
				PostNews pn = new PostNews();
				pn.setDescription(nua.getDescription());
				pn.setId(nua.getId());
				pn.setEditorId(nua.getEditorId());
				pn.setLocation(nua.getLocation());
				pn.setNewsDate(nua.getNewsDate());
				pn.setRefLink(nua.getRefLink());
				pn.setTitle(nua.getTitle());
				pn.setChannelData(nua.getChannelData());
				pn.setChannel(nua.getChannel());
				pn.setCurrentLocation(nua.getCurrentLocation());
				pn.setLanguage(nua.getLanguage());
				retList.add(pn);
			});
		return retList;
	}

	public void removeDraftNews(String id) {
		localNewsDao.removeDraftNews(id);
	}

	public String savePublishNews(PostNews news) {
		PublishNews publishNews = mapPublishNews(news);
		return localNewsDao.savePublishNews(publishNews);
	}
	
	private PublishNews mapPublishNews(PostNews news) {
		PublishNews publishNews = new PublishNews();
		publishNews.setDescription(news.getDescription());
		publishNews.setEditorId(new ObjectId(news.getEditorId()));
		publishNews.setLocation(news.getLocation());
		publishNews.setNewsDate(news.getNewsDate());
		publishNews.setRefLink(news.getRefLink());
		publishNews.setTitle(news.getTitle());
		publishNews.setChannel(new ObjectId(news.getChannel()));
		publishNews.setCurrentLocation(news.getCurrentLocation());
		publishNews.setLanguage(news.getLanguage());
		return publishNews;
	}

	public PublishNews getPublishNews(String id) {
		return localNewsDao.getPublishNews(id);
	}

	public void updatePublishNews(ReviewNews news) {
		PublishNews publishNews = mapPublishNews(news);
		publishNews.setId(news.getId());
		publishNews.setReviewDate(new Date());
		publishNews.setReviewerId(new ObjectId(news.getReviewerId()));
		publishNews.setStatus(news.getStatus());
		publishNews.setUpdateDate(new Date());
		localNewsDao.updatePublishNews(publishNews);
	}

	public List<ReviewNews> getReviewNews(String editorId, String newsDate, List<ObjectId> channels, String reviewerId, boolean adminInd) {
		ObjectId edtrId = StringUtils.isNotBlank(editorId) ? new ObjectId(editorId) : null;
		if(CollectionUtils.isEmpty(channels)) {
			List<Channel> chnls = channelDao.getChannelsForReview(new ObjectId(reviewerId), adminInd);
			channels = chnls.stream().map(chnl -> new ObjectId(chnl.getId())).collect(Collectors.toList());
		}
		List<NewsUserAgg> listDb = localNewsDao.getReviewNews(edtrId, newsDate, channels);
		List<ReviewNews> list = new ArrayList<>();
		if (listDb != null) {
			listDb.parallelStream().forEach(nua -> {
				ReviewNews rn = mapReviewNews(nua);
				rn.setPublishedBy(nua.getEditors().get(0).getName());
				list.add(rn);
			});
		}
		return list;
	}

	public List<ReviewNews> getEditorPublishNews(String editorId) {
		List<NewsUserAgg> listDb = localNewsDao.getEditorPublishNews(new ObjectId(editorId));
		List<ReviewNews> list = new ArrayList<>();
		if (listDb != null) {
			listDb.parallelStream().forEach(nua -> {
				list.add(mapReviewNews(nua));
			});
		}
		return list;
	}

	private ReviewNews mapReviewNews(NewsUserAgg nua) {
		ReviewNews rn = new ReviewNews();
		rn.setId(nua.getId());
		rn.setDescription(nua.getDescription());
		rn.setEditorId(nua.getEditorId());
		rn.setLocation(nua.getLocation());
		rn.setNewsDate(nua.getNewsDate());
		rn.setRefLink(nua.getRefLink());
		rn.setStatus(nua.getStatus());
		rn.setTitle(nua.getTitle());
		rn.setChannelData(nua.getChannelData());
		rn.setChannel(nua.getChannel());
		rn.setCurrentLocation(nua.getCurrentLocation());
		rn.setLanguage(nua.getLanguage());
		return rn;
	}

	public List<PublicNews> getPublicNews(String newsId, Object[] locations, List<String> languages, String channelId) {
		List<NewsUserAgg> listDb = localNewsDao.getPublicNews(newsId, locations, languages, channelId);
		List<PublicNews> list = new ArrayList<>();
		if (listDb != null) {
			listDb.parallelStream().forEach(nua -> {
				PublicNews pn = new PublicNews();
				pn.setId(nua.getId());
				pn.setDescription(nua.getDescription());
				pn.setLocation(nua.getLocation());
				pn.setNewsDate(nua.getNewsDate());
				pn.setPublishedBy(nua.getEditors().get(0).getName());
				pn.setRefLink(nua.getRefLink());
				pn.setTitle(nua.getTitle());
				pn.setDislikesCount(nua.getDislikesCount());
				pn.setLikesCount(nua.getLikesCount());
				pn.setMylike(nua.getMyLike());
				pn.setChannelData(nua.getChannelData());
				pn.setChannel(nua.getChannel());
				pn.setCurrentLocation(nua.getCurrentLocation());
				pn.setLanguage(nua.getLanguage());
				list.add(pn);
			});
		}
		return list;
	}

	public void saveImage(String newsId, InputStream newsImage) {
		localNewsDao.saveImage(newsId, newsImage);
	}

	public void removeImage(String newsId) {
		localNewsDao.removeImage(newsId);
	}

	public NewsMediaAgg getNewsMedia(String newsId) {
		return localNewsDao.getNewsMedia(new ObjectId(newsId));
	}

}

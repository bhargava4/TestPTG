package com.ln.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ln.dao.LocalNewsDao;
import com.ln.domain.NewsUserAgg;
import com.ln.domain.PostNews;
import com.ln.domain.PublicNews;
import com.ln.domain.ReviewNews;
import com.ln.entity.DraftNews;
import com.ln.entity.PublishNews;

@Service
public class LocalNewsService {

	@Autowired
	private LocalNewsDao localNewsDao;

	public void saveDraftNews(PostNews news) {
		DraftNews draftNews = mapDraftNews(news);
		localNewsDao.saveDraftNews(draftNews);
	}
	
	private DraftNews mapDraftNews(PostNews news) {
		DraftNews draftNews = new DraftNews();
		draftNews.setTitle(news.getTitle());
		draftNews.setDescription(news.getDescription());
		draftNews.setLocation(Arrays.stream(news.getLocation()).map(loc -> new ObjectId(loc)).toArray(ObjectId[]::new));
		draftNews.setNewsDate(news.getNewsDate());
		draftNews.setRefLink(news.getRefLink());
		draftNews.setEditorId(news.getEditorId());
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
		List<NewsUserAgg> listDb = localNewsDao.getDraftNewsByEditor(editorId);
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
				pn.setLocationData(nua.getLocationData());
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
		publishNews.setEditorId(news.getEditorId());
		publishNews.setLocation(Arrays.stream(news.getLocation()).map(loc -> new ObjectId(loc)).toArray(ObjectId[]::new));
		publishNews.setNewsDate(news.getNewsDate());
		publishNews.setRefLink(news.getRefLink());
		publishNews.setTitle(news.getTitle());
		return publishNews;
	}

	public PublishNews getPublishNews(String id) {
		return localNewsDao.getPublishNews(id);
	}

	public void updatePublishNews(ReviewNews news) {
		PublishNews publishNews = mapPublishNews(news);
		publishNews.setId(news.getId());
		publishNews.setReviewDate(new Date());
		publishNews.setReviewerId(news.getReviewerId());
		publishNews.setStatus(news.getStatus());
		publishNews.setUpdateDate(new Date());
		localNewsDao.updatePublishNews(publishNews);
	}

	public List<ReviewNews> getReviewNews(String editorId, String newsDate, List<ObjectId> locList) {
		List<NewsUserAgg> listDb = localNewsDao.getReviewNews(editorId, newsDate, locList);
		List<ReviewNews> list = new ArrayList<>();
		if (listDb != null) {
			listDb.parallelStream().forEach(nua -> {
				ReviewNews rn = new ReviewNews();
				rn.setId(nua.getId());
				rn.setDescription(nua.getDescription());
				rn.setEditorId(nua.getEditorId());
				rn.setLocation(nua.getLocation());
				rn.setNewsDate(nua.getNewsDate());
				rn.setPublishedBy(nua.getEditors().get(0).getName());
				rn.setRefLink(nua.getRefLink());
				rn.setStatus(nua.getStatus());
				rn.setTitle(nua.getTitle());
				rn.setImageFiles(nua.getImageFiles());
				rn.setImageChunks(nua.getImageChunks());
				rn.setLocationData(nua.getLocationData());
				list.add(rn);
			});
		}
		return list;
	}

	public List<ReviewNews> getEditorPublishNews(String editorId) {
		List<NewsUserAgg> listDb = localNewsDao.getEditorPublishNews(editorId);
		List<ReviewNews> list = new ArrayList<>();
		if (listDb != null) {
			listDb.parallelStream().forEach(nua -> {
				ReviewNews rn = new ReviewNews();
				rn.setId(nua.getId());
				rn.setDescription(nua.getDescription());
				rn.setEditorId(nua.getEditorId());
				rn.setLocation(nua.getLocation());
				rn.setNewsDate(nua.getNewsDate());
				rn.setRefLink(nua.getRefLink());
				rn.setStatus(nua.getStatus());
				rn.setTitle(nua.getTitle());
				rn.setLocationData(nua.getLocationData());
				list.add(rn);
			});
		}
		return list;
	}

	public List<PublicNews> getPublicNews(String newsId, List<ObjectId> locList) {
		List<NewsUserAgg> listDb = localNewsDao.getPublicNews(newsId, locList);
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
				pn.setImageFiles(nua.getImageFiles());
				pn.setImageChunks(nua.getImageChunks());
				pn.setLocationData(nua.getLocationData());
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

}

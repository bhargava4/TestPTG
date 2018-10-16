package com.ln.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ln.domain.PostNews;
import com.ln.domain.PublicNews;
import com.ln.domain.ReviewNews;
import com.ln.entity.DraftNews;
import com.ln.entity.PublishNews;
import com.ln.service.LocalNewsService;

@RestController
public class LocalNewsController {

	@Autowired
	private LocalNewsService localNewsService;

	@RequestMapping(value = "/user/draft-news", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity saveDraftNews(@Valid @RequestBody PostNews news) {
		localNewsService.saveDraftNews(news);
		return ResponseEntity.ok("Post saved successfully");
	}

	@RequestMapping(value = "/user/draft-news", method = RequestMethod.PUT, consumes = "application/json")
	public ResponseEntity updateDraftNews(@Valid @RequestBody PostNews news) {

		if (StringUtils.isBlank(news.getId()))
			ResponseEntity.badRequest().body("Id is mandatory to update");

		DraftNews draftNewsDb = localNewsService.getDraftNews(news.getId());
		if (draftNewsDb == null)
			ResponseEntity.badRequest().body("News id does not exist to update");

		localNewsService.updateDraftNews(news);
		return ResponseEntity.ok("Post updated successfully");
	}

	@RequestMapping(value = "/user/draft-news/{editorId}", method = RequestMethod.GET)
	public ResponseEntity getDraftNews(@PathVariable String editorId) {
		List<PostNews> list = localNewsService.getDraftNewsByEditor(editorId);
		if (list == null || list.size() == 0)
			return ResponseEntity.badRequest().body("No records");
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/user/draft-news/{newsId}", method = RequestMethod.DELETE)
	public ResponseEntity deleteDraftNews(@PathVariable String newsId) {
		localNewsService.removeDraftNews(newsId);
		return ResponseEntity.ok("Deleted successfully");
	}
	
	@RequestMapping(value = "/user/publish-news", method = RequestMethod.POST)
	public ResponseEntity publishNews(@Valid @RequestPart(name = "newsDetails") PostNews news,
			@RequestPart(name = "newsImage", required = false) MultipartFile newsImage) throws IOException {

		if (StringUtils.isNotBlank(news.getId()))
			localNewsService.removeDraftNews(news.getId());

		String newsId = localNewsService.savePublishNews(news);

		if (newsImage != null)
			localNewsService.saveImage(newsId, newsImage.getInputStream());

		return ResponseEntity.ok("Post published for review successfully");
	}

	@RequestMapping(value = "/user/publish-news/{editorId}", method = RequestMethod.GET)
	public ResponseEntity getEditorPublishNews(@PathVariable String editorId) {
		List<ReviewNews> list = localNewsService.getEditorPublishNews(editorId);
		if (list == null || list.size() == 0)
			return ResponseEntity.badRequest().body("No records");
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/admin/review-news", method = RequestMethod.GET)
	public ResponseEntity getReviewNews(@RequestParam(required = false) String editorId,
			@RequestParam(required = false) String newsDate, @RequestParam(required = false) String locations) {
		List<ObjectId> locList = null;
		if (StringUtils.isNotBlank(locations)) {
			locList = Arrays.stream(locations.split(",")).map(loc -> new ObjectId(loc)).collect(Collectors.toList());
		}
		List<ReviewNews> list = localNewsService.getReviewNews(editorId, newsDate, locList);
		if (list == null || list.size() == 0)
			return ResponseEntity.badRequest().body("No records");
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/admin/review-news", method = RequestMethod.PUT)
	public ResponseEntity reviewNews(@Valid @RequestPart(name = "newsDetails") ReviewNews news,
			@RequestPart(name = "newsImage", required = false) MultipartFile newsImage) throws IOException {

		PublishNews publishNewsDb = localNewsService.getPublishNews(news.getId());
		if (publishNewsDb == null)
			ResponseEntity.badRequest().body("News id does not exist to update");

		// validate if reviewer has permissions

		localNewsService.updatePublishNews(news);

		if (newsImage != null) {
			localNewsService.removeImage(news.getId());
			localNewsService.saveImage(news.getId(), newsImage.getInputStream());
		}

		return ResponseEntity.ok("Post reviewed successfully");
	}

	@RequestMapping(value = "/public/news", method = RequestMethod.GET)
	public ResponseEntity getPublicNews(@RequestParam(required = false) String newsId,
			@RequestParam(required = false) String locations) {
		List<ObjectId> locList = null;
		if (StringUtils.isNotBlank(locations)) {
			locList = Arrays.stream(locations.split(",")).map(loc -> new ObjectId(loc)).collect(Collectors.toList());
		}
		List<PublicNews> list = localNewsService.getPublicNews(newsId, locList);
		if (list == null || list.size() == 0)
			return ResponseEntity.badRequest().body("News does not exist");
		return ResponseEntity.ok().body(list);
	}

}

package com.ln.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ln.SecurityUtils;
import com.ln.domain.Coordinates;
import com.ln.domain.NewsMediaAgg;
import com.ln.domain.PostNews;
import com.ln.domain.PublicNews;
import com.ln.domain.ReturnResponse;
import com.ln.domain.ReviewNews;
import com.ln.entity.CustomUserDetails;
import com.ln.entity.DraftNews;
import com.ln.entity.PublishNews;
import com.ln.service.LocalNewsService;

@RestController
public class LocalNewsController {

	@Autowired
	private LocalNewsService localNewsService;

	@RequestMapping(value = "/user/draft-news", method = RequestMethod.POST, consumes = "application/json")
	public ReturnResponse<?> saveDraftNews(@Valid @RequestBody PostNews news) {
		news.setEditorId(SecurityUtils.getUserId());
		localNewsService.saveDraftNews(news);
		return ReturnResponse.getHttpStatusResponse("Post saved successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/draft-news", method = RequestMethod.PUT, consumes = "application/json")
	public Object updateDraftNews(@Valid @RequestBody PostNews news) {

		if (StringUtils.isBlank(news.getId()))
			return ResponseEntity.badRequest().body("Id is mandatory to update");

		DraftNews draftNewsDb = localNewsService.getDraftNews(news.getId());
		if (draftNewsDb == null)
			return ResponseEntity.badRequest().body("News id does not exist to update");

		news.setEditorId(SecurityUtils.getUserId());
		localNewsService.updateDraftNews(news);
		return ReturnResponse.getHttpStatusResponse("Post updated successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/draft-news", method = RequestMethod.GET)
	public Object getDraftNews() {
		List<PostNews> list = localNewsService.getDraftNewsByEditor(SecurityUtils.getUserId());
		if (list == null || list.size() == 0)
			return ResponseEntity.notFound().build();
		return ReturnResponse.getHttpStatusResponse("Draft news retrieved successfully", HttpStatus.OK, list);
	}

	@RequestMapping(value = "/user/draft-news/{newsId}", method = RequestMethod.DELETE)
	public ReturnResponse<?> deleteDraftNews(@PathVariable String newsId) {
		localNewsService.removeDraftNews(newsId);
		return ReturnResponse.getHttpStatusResponse("Deleted successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/publish-news", method = RequestMethod.POST)
	public ReturnResponse<?> publishNews(@Valid @RequestPart(name = "newsDetails") PostNews news,
			@RequestPart(name = "newsImage", required = false) MultipartFile newsImage) throws IOException {

		if (StringUtils.isNotBlank(news.getId()))
			localNewsService.removeDraftNews(news.getId());
		news.setEditorId(SecurityUtils.getUserId());
		String newsId = localNewsService.savePublishNews(news);

		if (newsImage != null)
			localNewsService.saveImage(newsId, newsImage.getInputStream());

		return ReturnResponse.getHttpStatusResponse("Post published for review successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/user/publish-news", method = RequestMethod.GET)
	public Object getEditorPublishNews() {
		List<ReviewNews> list = localNewsService.getEditorPublishNews(SecurityUtils.getUserId());
		if (list == null || list.size() == 0)
			return ResponseEntity.notFound().build();
		return ReturnResponse.getHttpStatusResponse("Published news retrieved successfully", HttpStatus.OK, list);
	}

	@RequestMapping(value = "/admin/review-news", method = RequestMethod.GET)
	public Object getReviewNews(@RequestParam(required = false) String editorId,
			@RequestParam(required = false) String newsDate, @RequestParam(required = false) String channels) {
		List<ObjectId> chnl = null;
		if (StringUtils.isNotBlank(channels))
			chnl = Arrays.stream(channels.split(",")).map(loc -> new ObjectId(loc)).collect(Collectors.toList());

		CustomUserDetails loggedInUser = SecurityUtils.getUser();
		boolean admin = false;
		if (loggedInUser.getRoles().contains("ADMIN"))
			admin = true;

		List<ReviewNews> list = localNewsService.getReviewNews(editorId, newsDate, chnl, loggedInUser.getId(), admin);
		if (list == null || list.size() == 0)
			return ResponseEntity.notFound().build();
		return ReturnResponse.getHttpStatusResponse("Review news retrieved successfully", HttpStatus.OK, list);
	}

	@RequestMapping(value = "/admin/review-news", method = RequestMethod.PUT)
	public Object reviewNews(@Valid @RequestPart(name = "newsDetails") ReviewNews news,
			@RequestPart(name = "newsImage", required = false) MultipartFile newsImage) throws IOException {

		PublishNews publishNewsDb = localNewsService.getPublishNews(news.getId());
		if (publishNewsDb == null)
			return ResponseEntity.badRequest().body("News id does not exist to update");

		news.setReviewerId(SecurityUtils.getUserId());

		// validate if reviewer has permissions

		localNewsService.updatePublishNews(news);

		if (newsImage != null) {
			localNewsService.removeImage(news.getId());
			localNewsService.saveImage(news.getId(), newsImage.getInputStream());
		}

		return ReturnResponse.getHttpStatusResponse("Post reviewed successfully", HttpStatus.OK, null);
	}

	@RequestMapping(value = "/public/news", method = RequestMethod.GET)
	public Object getPublicNews(@RequestParam(required = false) String newsId,
			@RequestParam(required = false) String locations, @RequestParam(required = false) String languages)
			throws Exception {
		List<String> lang = null;
		Object[] locArray = null;
		if (StringUtils.isNotBlank(languages))
			lang = Arrays.stream(languages.split(",")).collect(Collectors.toList());
		if (StringUtils.isNotBlank(locations)) {
			ObjectMapper mapper = new ObjectMapper();
			locArray = mapper.readValue(locations, Coordinates[].class);
		}
		List<PublicNews> list = localNewsService.getPublicNews(newsId, locArray, lang, null);
		if (list == null || list.size() == 0)
			return ResponseEntity.notFound().build();
		return ReturnResponse.getHttpStatusResponse("Public news retrieved successfully", HttpStatus.OK, list);
	}

	@RequestMapping(value = "/public/channel/{channelId}/news", method = RequestMethod.GET)
	public Object getChannelPublicNews(@PathVariable String channelId,
			@RequestParam(required = false) String languages) throws Exception {
		List<String> lang = null;
		if (StringUtils.isNotBlank(languages))
			lang = Arrays.stream(languages.split(",")).collect(Collectors.toList());
		List<PublicNews> list = localNewsService.getPublicNews(null, null, lang, channelId);
		if (list == null || list.size() == 0) {
			return ResponseEntity.notFound().build();
		}
		return ReturnResponse.getHttpStatusResponse("Channel news retrieved successfully", HttpStatus.OK, list);
	}

	@RequestMapping(value = "/public/news/{newsId}/media", method = RequestMethod.GET)
	public Object getNewsMedia(@PathVariable String newsId) {
		NewsMediaAgg media = localNewsService.getNewsMedia(newsId);
		if (media == null || media.getImageFiles() == null || media.getImageFiles().size() == 0)
			return ResponseEntity.notFound().build();
		return ReturnResponse.getHttpStatusResponse("Public news media retrieved successfully", HttpStatus.OK, media);
	}

}

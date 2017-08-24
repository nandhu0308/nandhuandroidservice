package com.limitless.services.payment.PaymentService.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.AlbumVideoRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.CategoryRequestBean;
import com.limitless.services.engage.entertainment.ChannelRequestBean;
import com.limitless.services.engage.entertainment.ChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.engage.entertainment.VideoRequestBean;
import com.limitless.services.engage.entertainment.dao.BroadcasterManager;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.dao.SocialEntityManager;
import com.limitless.services.socialentity.dao.SocialEntityType;

@Path("/broadcast")
public class BroadcasterResource {
	final static Logger logger = Logger.getLogger(BroadcasterResource.class);

	@Path("/channel/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BroadcasterChannelResponseBean getChannel(BroadcasterChannelRequestBean requestBean) throws Exception {
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getBroadcasterChannel(requestBean, true);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@Path("/channel/get/{broadcasterId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BroadcasterChannelResponseBean getChannel(@PathParam("broadcasterId") int broadcasterId) throws Exception {
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getBroadcasterChannel(broadcasterId, 100018, true);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}

	@Path("/album/category/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAlbums(BroadcasterAlbumCategoryRequestBean requestBean) throws Exception {
		List<BroadcasterAlbumCategoryResponseBean> responseBean = new ArrayList<BroadcasterAlbumCategoryResponseBean>();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getAllBroadcasterAlbumCategoryList(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		if (responseBean != null && !(responseBean.isEmpty())) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/video/list/{albumId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AlbumBean getAlbumVideoList(@PathParam("albumId") int albumId) throws Exception {
		AlbumBean albumBean = new AlbumBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			albumBean = manager.getBroadcasterAlbumVideoList(albumId, -1, 0, false);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return albumBean;
	}

	@Path("/video/pagelist")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AlbumBean getAlbumVideoListPost(AlbumVideoRequestBean requestBean) throws Exception {
		AlbumBean albumBean = new AlbumBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			albumBean = manager.getBroadcasterAlbumVideoList(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return albumBean;
	}

	@Path("/video/pagelist/{albumId}/{videoId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AlbumBean getAlbumVideoList(@PathParam("albumId") int albumId, @PathParam("videoId") int videoId)
			throws Exception {
		AlbumBean albumBean = new AlbumBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			albumBean = manager.getBroadcasterAlbumVideoList(albumId, videoId, 0, false);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return albumBean;
	}

	@Path("/video/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VideoBean getVideo(VideoRequestBean requestBean) throws Exception {
		VideoBean videoBean = new VideoBean();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			videoBean = manager.getVideoById(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return videoBean;
	}

	@Path("/video/stop/{vtId}/{videoId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response stopVideo(@PathParam("vtId") int vtId, @PathParam("videoId") int videoId) throws Exception {
		try {
			BroadcasterManager manager = new BroadcasterManager();
			manager.markVideoStopped(vtId, videoId);
		} catch (Exception e) {
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return Response.status(200).build();
	}

	@Path("/channel/category/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories() throws Exception {
		List<BroadcasterChannelCategoryResponseBean> responseBean = new ArrayList<BroadcasterChannelCategoryResponseBean>();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getAllBroadcasters(-1, 0, false);
		} catch (Exception e) {
			logger.error("API Error", e);
			responseBean.clear();
		}
		if (responseBean != null && !(responseBean.isEmpty())) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/broadcaster/categories")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategories(CategoryRequestBean requestBean) throws Exception {
		List<BroadcasterChannelCategoryResponseBean> responseBean = new ArrayList<BroadcasterChannelCategoryResponseBean>();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getAllBroadcasters(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			responseBean.clear();
		}
		if (responseBean != null && !(responseBean.isEmpty())) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/channel/category/get/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryByFilter(@PathParam("id") int id) throws Exception {
		List<BroadcasterChannelCategoryResponseBean> responseBean = new ArrayList<BroadcasterChannelCategoryResponseBean>();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getAllBroadcasters(id, 0, false);
		} catch (Exception e) {
			logger.error("API Error", e);
			responseBean.clear();
		}
		if (responseBean != null && !(responseBean.isEmpty()) && responseBean.size() > 0) {
			return Response.status(200).entity(responseBean.get(0)).build();
		}
		return Response.status(404).build();

	}

	@Path("/channels/search")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchChannels(ChannelRequestBean requestBean) throws Exception {
		List<ChannelResponseBean> responseBean = new ArrayList<ChannelResponseBean>();
		try {
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.searchChannels(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
			responseBean.clear();
		}
		if (responseBean != null && !(responseBean.isEmpty())) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

}

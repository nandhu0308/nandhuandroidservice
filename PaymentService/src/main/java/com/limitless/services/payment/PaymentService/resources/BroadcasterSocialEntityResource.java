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

import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.dao.BroadcasterManager;
import com.limitless.services.engage.entertainment.dao.BroadcasterSocialEntityManager;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.SocialEntityResultBean;
import com.limitless.services.socialentity.dao.SocialEntityType;

@Path("/broadcast/socialentity")
public class BroadcasterSocialEntityResource {
	final static Logger logger = Logger.getLogger(BroadcasterSocialEntityResource.class);

	@Path("/broadcaster/like")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeBroadcaster(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.B.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.like(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/broadcaster/share")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shareBroadcaster(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.B.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.share(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/broadcaster/follow")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response followBroadcaster(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.B.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.follow(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}
	
	@Path("/channel/like")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeChannel(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.C.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.like(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/channel/share")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shareChannel(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.C.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.share(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/channel/follow")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response followChannel(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.C.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.follow(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}
	
	
	@Path("/video/like")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response likeVideo(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.V.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.like(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/video/share")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response shareVideo(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.V.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.share(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}

	@Path("/video/follow")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response followVideo(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.V.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.follow(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}
	
	

	@Path("/video/view")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewVideo(SocialEntityRequestBean requestBean) throws Exception {
		SocialEntityResultBean responseBean = null;
		try {
			requestBean.setEntityType(SocialEntityType.V.toString());
			BroadcasterSocialEntityManager manager = new BroadcasterSocialEntityManager();
			responseBean = manager.processView(requestBean);
		} catch (Exception e) {
			logger.error("API Error", e);
		}
		if (responseBean != null) {
			return Response.status(200).entity(responseBean).build();
		}
		return Response.status(404).build();
	}
}

package com.limitless.services.payment.PaymentService.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.engage.entertainment.dao.BroadcasterManager;

@Path("/broadcast")
public class BroadcasterResource {
	final static Logger logger = Logger.getLogger(BroadcasterResource.class);
	
	@Path("/channel/get")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public BroadcasterChannelResponseBean getChannel(BroadcasterChannelRequestBean requestBean) throws Exception{
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		try{
			BroadcasterManager manager = new BroadcasterManager();
			responseBean = manager.getBroadcasterChannel(requestBean);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
	
	@Path("/video/list/{albumId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AlbumBean getAlbumVideoList(@PathParam("albumId") int albumId) throws Exception{
		AlbumBean albumBean = new AlbumBean();
		try{
			BroadcasterManager manager = new BroadcasterManager();
			albumBean = manager.getBroadcasterAlbumVideoList(albumId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return albumBean;
	}
	
	@Path("/video/get/{videoId}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public VideoBean getVideo(@PathParam("videoId") int videoId) throws Exception{
		VideoBean videoBean = new VideoBean();
		try{
			BroadcasterManager manager = new BroadcasterManager();
			videoBean = manager.getVideoById(videoId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return videoBean;
	}
	
}

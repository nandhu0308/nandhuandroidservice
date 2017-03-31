package com.limitless.services.engage.entertainment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;

public class BroadcasterManager {
	private static final Log log = LogFactory.getLog(BroadcasterManager.class);
	Client client = RestClientUtil.createClient();
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public BroadcasterChannelResponseBean getBroadcasterChannel(BroadcasterChannelRequestBean requestBean){
		log.debug("getting channles");
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Broadcaster.class);
			Criterion bcName = Restrictions.eq("broadcasterName", requestBean.getBroadcasterName());
			Criterion channelName = Restrictions.eq("broadcasterChannelName", requestBean.getBroadcasterName());
			LogicalExpression logExp = Restrictions.or(bcName, channelName);
			criteria.add(logExp);
			List<Broadcaster> broadcasterList = criteria.list();
			log.debug("broadcaster size : " + broadcasterList.size());
			if(broadcasterList.size()>0){
				for(Broadcaster broadcaster : broadcasterList){
					responseBean.setBroadcasterId(broadcaster.getBroadcasterId());
					responseBean.setBroadcasterName(broadcaster.getBroadcasterName());
					responseBean.setChannelName(broadcaster.getBroadcasterChannelName());
					responseBean.setBroadcasterDescription(broadcaster.getBroadcasterDescription());
					responseBean.setTotalVideos(broadcaster.getBroadcasterTotalVideos());
					responseBean.setMessage("Success");
					List<AlbumBean> albumList = new ArrayList<AlbumBean>();
					Criteria criteria2 = session.createCriteria(BroadcasterAlbum.class);
					criteria2.add(Restrictions.eq("broadcasterId", broadcaster.getBroadcasterId()));
					List<BroadcasterAlbum> albumsList = criteria2.list();
					log.debug("album size : " + albumsList.size());
					if(albumsList.size()>0){
						for(BroadcasterAlbum album : albumsList){
							AlbumBean bean = new AlbumBean();
							bean.setAlbumId(album.getAlbumId());
							bean.setAlbumName(album.getAlbumName());
							bean.setAlbumDescription(album.getAlbumDescription());
							bean.setAlbumVideoCount(album.getAlbumVideos());
							albumList.add(bean);
							bean=null;
						}
						responseBean.setAlbumList(albumList);
					}
				}
			}
			else if(broadcasterList.isEmpty()){
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting channels failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public AlbumBean getBroadcasterAlbumVideoList(int albumId){
		log.debug("getting album video list");
		AlbumBean albumBean = new AlbumBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			BroadcasterAlbum album = (BroadcasterAlbum) session
					.get("com.limitless.services.engage.entertainment.dao.BroadcasterAlbum", albumId);
			if(album!=null){
				albumBean.setMessage("Success");
				albumBean.setAlbumId(albumId);
				albumBean.setAlbumName(album.getAlbumName());
				albumBean.setAlbumDescription(album.getAlbumDescription());
				albumBean.setAlbumThumbnail(album.getAlbumThumbnail());
				albumBean.setAlbumVideoCount(album.getAlbumVideos());
				List<VideoBean> videoList = new ArrayList<VideoBean>();				
				Criteria criteria = session.createCriteria(BroadcasterVideo.class);
				criteria.add(Restrictions.eq("albumId", albumId));
				List<BroadcasterVideo> videosList = criteria.list();
				log.debug("video size : " + videosList.size());
				if(videosList.size()>0){
					for(BroadcasterVideo video : videosList){
						VideoBean videoBean = new VideoBean();
						videoBean.setVideoId(video.getVideosId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getAlbumId());
						videoBean.setAlbumName(album.getAlbumDescription());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						videoList.add(videoBean);
						videoBean = null;
					}
					albumBean.setVideoList(videoList);
				}
			}
			else{
				albumBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting album failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return albumBean;
	}
	
	public VideoBean getVideoById(int videoId){
		log.debug("getting video");
		VideoBean videoBean = new VideoBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			BroadcasterVideo video = (BroadcasterVideo) session
					.get("com.limitless.services.engage.entertainment.dao.BroadcasterVideo", videoId);
			if(video!=null){
				videoBean.setVideoId(videoId);
				videoBean.setVideoName(video.getVideoName());
				videoBean.setVideoDescription(video.getVideoDescription());
				videoBean.setVideoThumbnail(video.getVideoThumbnail());
				videoBean.setVideoUrl(video.getVideoUrl());
				videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
				videoBean.setMessage("Success");
			}
			else{
				videoBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting video failed : " + re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return videoBean;
	}
	
}

package com.limitless.services.engage.entertainment.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.entertainment.AdRollBean;
import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.engage.entertainment.VideoRequestBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;

public class BroadcasterManager {
	private static final Log log = LogFactory.getLog(BroadcasterManager.class);
	Client client = RestClientUtil.createClient();
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public BroadcasterChannelResponseBean getBroadcasterChannel(BroadcasterChannelRequestBean requestBean) {
		log.debug("getting channles");
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Broadcaster.class);
			Criterion bcName = Restrictions.eq("broadcasterName", requestBean.getBroadcasterName());
			Criterion channelName = Restrictions.eq("broadcasterChannelName", requestBean.getBroadcasterName());
			LogicalExpression logExp = Restrictions.or(bcName, channelName);
			criteria.add(logExp);
			List<Broadcaster> broadcasterList = criteria.list();
			log.debug("broadcaster size : " + broadcasterList.size());
			if (broadcasterList.size() > 0) {
				for (Broadcaster broadcaster : broadcasterList) {
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
					int count = 0;
					if (albumsList.size() > 0) {
						for (BroadcasterAlbum album : albumsList) {
							if (count > 10)
								break;
							AlbumBean bean = new AlbumBean();
							bean.setAlbumId(album.getAlbumId());
							bean.setAlbumName(album.getAlbumName());
							bean.setAlbumDescription(album.getAlbumDescription());
							bean.setAlbumVideoCount(album.getAlbumVideos());
							bean.setAlbumThumbnail(album.getAlbumThumbnail());
							albumList.add(bean);
							count++;
							bean = null;
						}
						responseBean.setAlbumList(albumList);
					}
				}
			} else if (broadcasterList.isEmpty()) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting channels failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public List<BroadcasterChannelCategoryResponseBean> getAllBroadcasters() {
		log.debug("getting categories and channels");
		List<BroadcasterChannelCategoryResponseBean> categories = new ArrayList<BroadcasterChannelCategoryResponseBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(BroadcasterCategory.class);
			criteria.addOrder(Order.asc("rank"));
			criteria.addOrder(Order.asc("name"));
			List<BroadcasterCategory> businessCategories = criteria.list();
			if (businessCategories.size() > 0) {
				List<BroadcasterChannelResponseBean> channels = null;
				for (BroadcasterCategory category : businessCategories) {
					Criteria broadcasterCriteria = session.createCriteria(Broadcaster.class);
					broadcasterCriteria.add(Restrictions.eq("categoryId", category.getId()));
					broadcasterCriteria.add(Restrictions.eq("isActive", true));
					broadcasterCriteria.addOrder(Order.asc("rank"));
					broadcasterCriteria.addOrder(Order.asc("broadcasterChannelName"));
					List<Broadcaster> broadcasters = broadcasterCriteria.list();
					if (broadcasters.size() > 0) {
						channels = new ArrayList<BroadcasterChannelResponseBean>();
						for (Broadcaster broadcaster : broadcasters) {
							BroadcasterChannelResponseBean bean = new BroadcasterChannelResponseBean();
							bean.setBroadcasterDescription(broadcaster.getBroadcasterDescription());
							bean.setBroadcasterId(broadcaster.getBroadcasterId());
							bean.setBroadcasterName(broadcaster.getBroadcasterName());
							bean.setChannelName(broadcaster.getBroadcasterChannelName());
							bean.setTotalVideos(broadcaster.getBroadcasterTotalVideos());
							bean.setSellerId(broadcaster.getSellerId());
							bean.setBroadcasterImage(broadcaster.getBroadcasterImage());
							channels.add(bean);
							bean = null;
						}
						BroadcasterChannelCategoryResponseBean responseBean = new BroadcasterChannelCategoryResponseBean();
						responseBean.setCategoryName(category.getName());
						responseBean.setChannelList(channels);
						categories.add(responseBean);
					}
				}
			}

			transaction.commit();

		} catch (

		RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting channels failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return categories;

	}

	public List<BroadcasterAlbumCategoryResponseBean> getAllBroadcasterAlbumCategoryList(
			BroadcasterAlbumCategoryRequestBean requestBean) {
		log.debug("getting categories and Albums");
		List<BroadcasterAlbumCategoryResponseBean> categories = new ArrayList<BroadcasterAlbumCategoryResponseBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(BroadcasterAlbumCategory.class);
			criteria.addOrder(Order.asc("rank"));
			criteria.addOrder(Order.asc("name"));
			List<BroadcasterAlbumCategory> albumCategories = criteria.list();
			if (albumCategories.size() > 0) {
				List<AlbumBean> albumsBean = null;
				for (BroadcasterAlbumCategory category : albumCategories) {
					Criteria albumCriteria = session.createCriteria(BroadcasterAlbum.class);
					albumCriteria.add(Restrictions.eq("broadcasterAlbumCategoryId", category.getId()));
					albumCriteria.add(Restrictions.eq("broadcasterId", requestBean.getBroadcasterId()));
					albumCriteria.add(Restrictions.eq("isActive", true));
					albumCriteria.addOrder(Order.asc("rank"));
					albumCriteria.addOrder(Order.asc("albumName"));
					List<BroadcasterAlbum> albums = albumCriteria.list();
					if (albums.size() > 0) {
						albumsBean = new ArrayList<AlbumBean>();
						for (BroadcasterAlbum album : albums) {
							AlbumBean bean = new AlbumBean();
							bean.setAlbumId(album.getAlbumId());
							bean.setAlbumName(album.getAlbumName());
							bean.setAlbumDescription(album.getAlbumDescription());
							bean.setAlbumVideoCount(album.getAlbumVideos());
							bean.setAlbumThumbnail(album.getAlbumThumbnail());
							albumsBean.add(bean);
							bean = null;
						}
						BroadcasterAlbumCategoryResponseBean responseBean = new BroadcasterAlbumCategoryResponseBean();
						responseBean.setCategoryName(category.getName());
						responseBean.setChannelList(albumsBean);
						categories.add(responseBean);
					}
				}
			}

			transaction.commit();

		} catch (

		RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting albums failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return categories;
	}

	public AlbumBean getBroadcasterAlbumVideoList(int albumId, int beginingVideoId) {
		log.debug("getting album video list");
		AlbumBean albumBean = new AlbumBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			BroadcasterAlbum album = (BroadcasterAlbum) session
					.get("com.limitless.services.engage.entertainment.dao.BroadcasterAlbum", albumId);
			if (album != null) {
				albumBean.setMessage("Success");
				albumBean.setAlbumId(albumId);
				albumBean.setAlbumName(album.getAlbumName());
				albumBean.setAlbumDescription(album.getAlbumDescription());
				albumBean.setAlbumThumbnail(album.getAlbumThumbnail());
				albumBean.setAlbumVideoCount(album.getAlbumVideos());
				List<VideoBean> videoList = new ArrayList<VideoBean>();
				Criteria criteria = session.createCriteria(BroadcasterVideo.class);
				criteria.add(Restrictions.eq("albumId", albumId));
				criteria.add(Restrictions.eq("isActive", true));
				if (beginingVideoId > -1) {
					criteria.add(Restrictions.gt("videosId", beginingVideoId));
					criteria.setMaxResults(15);
				}
				criteria.addOrder(Order.asc("rank"));

				List<BroadcasterVideo> videosList = criteria.list();
				log.debug("video size : " + videosList.size());
				if (videosList.size() > 0) {
					for (BroadcasterVideo video : videosList) {
						VideoBean videoBean = new VideoBean();
						videoBean.setVideoId(video.getVideosId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getAlbumId());
						videoBean.setAlbumName(album.getAlbumDescription());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setYoutube(video.isYoutube());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						Criteria criteria2 = session.createCriteria(ViewersTrack.class);
						criteria2.add(Restrictions.eq("videoId", video.getVideosId()));
						ProjectionList projectionList = Projections.projectionList();
						projectionList.add(Projections.groupProperty("viewDate"));
						projectionList.add(Projections.groupProperty("customerId"));
						criteria2.setProjection(projectionList);
						List<ViewersTrack> trackList2 = criteria2.list();
						videoBean.setTotalViewCount(trackList2.size());
						List<VideoAds> videoAds = new ArrayList<VideoAds>();
						fillAds(session, video, videoBean);
						videoList.add(videoBean);
						videoBean = null;
					}
					albumBean.setVideoList(videoList);
				}
			} else {
				albumBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting album failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return albumBean;
	}

	private void fillAds(Session session, BroadcasterVideo video, VideoBean videoBean) {
		List<VideoAds> videoAds;
		Criteria criteriaVideoAds = session.createCriteria(VideoAds.class);
		criteriaVideoAds.add(Restrictions.eq("videoId", video.getVideosId()));
		videoAds = criteriaVideoAds.list();
		List<AdRollBean> midRolls = new ArrayList<AdRollBean>();
		for (VideoAds videoAd : videoAds) {
			AdRollBean adRoll = new AdRollBean();
			if (videoAd.getPreRollUrl() != null && !videoAd.getPreRollUrl().isEmpty()) {
				adRoll.setUrl(videoAd.getPreRollUrl());
				adRoll.setShopCode(videoAd.getPreRollCode());
				videoBean.setPreRoll(adRoll);
				adRoll = null;
			}
			if (videoAd.getMidRollUrl_1() != null && !videoAd.getMidRollUrl_1().isEmpty()) {
				AdRollBean m1 = new AdRollBean();
				m1.setUrl(videoAd.getMidRollUrl_1());
				m1.setShopCode(videoAd.getMidRollUrl_1_code());
				midRolls.add(m1);
				m1 = null;
			}

			if (videoAd.getMidRollUrl_2() != null && !videoAd.getMidRollUrl_2().isEmpty()) {
				AdRollBean m2 = new AdRollBean();
				m2.setUrl(videoAd.getMidRollUrl_2());
				m2.setShopCode(videoAd.getMidRollUrl_2_code());
				midRolls.add(m2);
				m2 = null;
			}

			if (videoAd.getMidRollUrl_3() != null && !videoAd.getMidRollUrl_3().isEmpty()) {
				AdRollBean m3 = new AdRollBean();
				m3.setUrl(videoAd.getMidRollUrl_3());
				m3.setShopCode(videoAd.getMidRollUrl_3_code());
				midRolls.add(m3);
				m3 = null;
			}

			if (videoAd.getMidRollUrl_4() != null && !videoAd.getMidRollUrl_4().isEmpty()) {
				AdRollBean m4 = new AdRollBean();
				m4.setUrl(videoAd.getMidRollUrl_4());
				m4.setShopCode(videoAd.getMidRollUrl_4_code());
				midRolls.add(m4);
				m4 = null;
			}

			if (videoAd.getMidRollUrl_5() != null && !videoAd.getMidRollUrl_5().isEmpty()) {
				AdRollBean m5 = new AdRollBean();
				m5.setUrl(videoAd.getMidRollUrl_5());
				m5.setShopCode(videoAd.getMidRollUrl_5_code());
				midRolls.add(m5);
				m5 = null;
			}

			if (videoAd.getMidRollUrl_6() != null && !videoAd.getMidRollUrl_6().isEmpty()) {
				AdRollBean m6 = new AdRollBean();
				m6.setUrl(videoAd.getMidRollUrl_6());
				m6.setShopCode(videoAd.getMidRollUrl_6_code());
				midRolls.add(m6);
				m6 = null;
			}

			if (videoAd.getMidRollUrl_7() != null && !videoAd.getMidRollUrl_7().isEmpty()) {
				AdRollBean m7 = new AdRollBean();
				m7.setUrl(videoAd.getMidRollUrl_7());
				m7.setShopCode(videoAd.getMidRollUrl_7_code());
				midRolls.add(m7);
				m7 = null;
			}

			if (videoAd.getMidRollUrl_8() != null && !videoAd.getMidRollUrl_8().isEmpty()) {
				AdRollBean m8 = new AdRollBean();
				m8.setUrl(videoAd.getMidRollUrl_8());
				m8.setShopCode(videoAd.getMidRollUrl_8_code());
				midRolls.add(m8);
				m8 = null;
			}

			if (videoAd.getMidRollUrl_9() != null && !videoAd.getMidRollUrl_9().isEmpty()) {
				AdRollBean m9 = new AdRollBean();
				m9.setUrl(videoAd.getMidRollUrl_9());
				m9.setShopCode(videoAd.getMidRollUrl_9_code());
				midRolls.add(m9);
				m9 = null;
			}

			if (videoAd.getMidRollUrl_10() != null && !videoAd.getMidRollUrl_10().isEmpty()) {
				AdRollBean m10 = new AdRollBean();
				m10.setUrl(videoAd.getMidRollUrl_10());
				m10.setShopCode(videoAd.getMidRollUrl_10_code());
				midRolls.add(m10);
				m10 = null;
			}
			videoBean.setMidRolls(midRolls);
			midRolls = null;

		}
	}

	public VideoBean getVideoById(VideoRequestBean requestBean) {
		log.debug("getting video");
		VideoBean videoBean = new VideoBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			BroadcasterVideo video = (BroadcasterVideo) session
					.get("com.limitless.services.engage.entertainment.dao.BroadcasterVideo", requestBean.getVideoId());
			if (video != null) {
				videoBean.setVideoId(requestBean.getVideoId());
				videoBean.setVideoName(video.getVideoName());
				videoBean.setVideoDescription(video.getVideoDescription());
				videoBean.setVideoThumbnail(video.getVideoThumbnail());
				videoBean.setVideoUrl(video.getVideoUrl());
				videoBean.setYoutube(video.isYoutube());
				videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
				videoBean.setMessage("Success");

				int customerId = 0;
				if (requestBean.getCustomerId() > 0) {
					customerId = requestBean.getCustomerId();
				} else if (requestBean.getGuestId() > 0) {
					customerId = requestBean.getGuestId();
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String viewDate = sdf.format(date);

				ViewersTrack track = new ViewersTrack();
				track.setVideoId(requestBean.getVideoId());
				track.setCustomerId(customerId);
				track.setViewDate(viewDate);
				track.setIsWatching(1);
				session.persist(track);
				log.debug("vt id : " + track.getVtId());
				videoBean.setVtId(track.getVtId());

				Criteria criteria = session.createCriteria(ViewersTrack.class);
				Criterion vidCriterion = Restrictions.eq("videoId", requestBean.getVideoId());
				Criterion iwCriterion = Restrictions.eq("isWatching", 1);
				LogicalExpression logExp = Restrictions.and(vidCriterion, iwCriterion);

				criteria.add(logExp);
				List<ViewersTrack> trackList = criteria.list();
				log.debug("live view count : " + trackList.size());
				videoBean.setLiveViewCount(trackList.size());

				Criteria criteria2 = session.createCriteria(ViewersTrack.class);
				criteria2.add(Restrictions.eq("videoId", requestBean.getVideoId()));
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("viewDate"));
				projectionList.add(Projections.groupProperty("customerId"));
				criteria2.setProjection(projectionList);
				List<ViewersTrack> trackList2 = criteria2.list();
				log.debug("total view count :" + trackList2.size());
				videoBean.setTotalViewCount(trackList2.size());
				fillAds(session, video, videoBean);
			} else {
				videoBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting video failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return videoBean;
	}

	public void markVideoStopped(int vtId, int videoId) {
		log.debug("marking video");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			ViewersTrack track = (ViewersTrack) session
					.get("com.limitless.services.engage.entertainment.dao.ViewersTrack", vtId);
			if (track != null) {
				if (track.getVideoId() == videoId) {
					track.setIsWatching(0);
					session.update(track);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("marking video failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

}

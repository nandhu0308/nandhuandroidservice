package com.limitless.services.engage.entertainment.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.dao.EngageSellerManager;
import com.limitless.services.engage.entertainment.AdRollBean;
import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.AlbumVideoRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterAlbumCategoryRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelCategoryResponseBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelRequestBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.CategoryRequestBean;
import com.limitless.services.engage.entertainment.ChannelFilterResponseBean;
import com.limitless.services.engage.entertainment.ChannelRequestBean;
import com.limitless.services.engage.entertainment.ChannelResponseBean;
import com.limitless.services.engage.entertainment.FilterResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.engage.entertainment.VideoRequestBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.dao.SocialEntityManager;
import com.limitless.services.socialentity.dao.SocialEntityType;
import com.sun.jersey.api.client.Client;

public class BroadcasterManager {
	private static final Log log = LogFactory.getLog(BroadcasterManager.class);
	Client client = RestClientUtil.createClient();
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	private final int broadcasterMaxResults = 5;
	private final int videoMaxResults = 10;
	private final int channelMaxResults = 10;

	public BroadcasterChannelResponseBean getBroadcasterChannel(BroadcasterChannelRequestBean requestBean,
			boolean addSocialEntity) {
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
					if (addSocialEntity)
						SocialEntityManager.setSocialEntity(responseBean, session, requestBean.getCustomerId(),
								requestBean.getIsLoggedIn());
					responseBean.setMessage("Success");
					List<AlbumBean> albumList = new ArrayList<AlbumBean>();
					Criteria criteria2 = session.createCriteria(BroadcasterChannel.class);
					criteria2.add(Restrictions.eq("broadcasterId", broadcaster.getBroadcasterId()));
					List<BroadcasterChannel> albumsList = criteria2.list();
					log.debug("album size : " + albumsList.size());
					int count = 0;
					if (albumsList.size() > 0) {
						for (BroadcasterChannel album : albumsList) {
							if (count > 10)
								break;
							AlbumBean bean = new AlbumBean();
							bean.setAlbumId(album.getChannelId());
							bean.setAlbumName(album.getChannelName());
							bean.setAlbumDescription(album.getChannelDescription());
							bean.setAlbumVideoCount(0);
							bean.setAlbumThumbnail(album.getChannelThumbnail());
							if (addSocialEntity)
								SocialEntityManager.setSocialEntity(bean, requestBean.getCustomerId(), session);
							albumList.add(bean);
							count++;
							bean = null;
						}
						responseBean.setAlbumList(albumList);
					}
					if (addSocialEntity)
						SocialEntityManager.setSocialEntity(responseBean, session, requestBean.getCustomerId(),
								requestBean.getIsLoggedIn());
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

	public BroadcasterChannelResponseBean getBroadcasterChannel(int broadcasterId, int customerId, boolean isLoggedIn) {
		log.debug("getting channles");
		BroadcasterChannelResponseBean responseBean = new BroadcasterChannelResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(Broadcaster.class);
			Criterion bcId = Restrictions.eq("broadcasterId", broadcasterId);
			criteria.add(bcId);
			List<Broadcaster> broadcasterList = criteria.list();
			log.debug("broadcaster size : " + broadcasterList.size());
			if (broadcasterList.size() > 0) {
				for (Broadcaster broadcaster : broadcasterList) {
					responseBean.setBroadcasterId(broadcaster.getBroadcasterId());
					responseBean.setBroadcasterName(broadcaster.getBroadcasterName());
					responseBean.setChannelName(broadcaster.getBroadcasterChannelName());
					responseBean.setBroadcasterDescription(broadcaster.getBroadcasterDescription());
					responseBean.setTotalVideos(broadcaster.getBroadcasterTotalVideos());
					SocialEntityManager.setSocialEntity(responseBean, session, customerId, isLoggedIn);
					responseBean.setMessage("Success");
					List<AlbumBean> albumBeanList = new ArrayList<AlbumBean>();
					Criteria criteria2 = session.createCriteria(BroadcasterChannel.class);
					criteria2.add(Restrictions.eq("broadcasterId", broadcaster.getBroadcasterId()));
					List<BroadcasterChannel> albumsList = criteria2.list();
					log.debug("album size : " + albumsList.size());
					if (albumsList.size() > 0) {
						for (BroadcasterChannel album : albumsList) {
							AlbumBean albumBean = new AlbumBean();
							albumBean.setMessage("Success");
							albumBean.setAlbumId(album.getChannelId());
							albumBean.setAlbumName(album.getChannelName());
							albumBean.setAlbumDescription(album.getChannelDescription());
							albumBean.setAlbumThumbnail(album.getChannelThumbnail());
							albumBean.setAlbumVideoCount(0);
							SocialEntityManager.setSocialEntity(albumBean, customerId, session);
							List<VideoBean> videoList = new ArrayList<VideoBean>();
							Criteria vCriteria = session.createCriteria(BroadcasterVideo.class);
							vCriteria.add(Restrictions.eq("Id", album.getChannelId()));
							vCriteria.add(Restrictions.eq("isActive", true));
							vCriteria.add(Restrictions.gt("videosId", 0));
							vCriteria.setMaxResults(15);
							vCriteria.addOrder(Order.asc("rank"));
							List<BroadcasterVideo> videosList = vCriteria.list();
							log.debug("video size : " + videosList.size());
							if (videosList.size() > 0) {
								for (BroadcasterVideo video : videosList) {
									VideoBean videoBean = new VideoBean();
									videoBean.setVideoId(video.getId());
									videoBean.setVideoName(video.getVideoName());
									videoBean.setAlbumId(video.getChannelId());
									videoBean.setAlbumName(album.getChannelDescription());
									videoBean.setVideoDescription(video.getVideoDescription());
									videoBean.setVideoThumbnail(video.getVideoThumbnail());
									videoBean.setVideoUrl(video.getVideoUrl());
									videoBean.setYoutube(video.isYoutube());
									videoBean.setLive(video.isLive());
									videoBean.setUrl(video.getUrl());
									videoBean.setVideoType(video.getVideoType());
									videoBean.setP160(video.isP160());
									videoBean.setP360(video.isP360());
									videoBean.setP720(video.isP720());
									videoBean.setP1080(video.isP1080());
									videoBean.setpUhd(video.ispUhd());
									videoBean.setDuration(video.getDuration());
									videoBean.setLiveAds(video.isLiveAds());
									videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
									Criteria vtCriteria = session.createCriteria(ViewersTrack.class);
									vtCriteria.add(Restrictions.eq("videoId", video.getId()));
									ProjectionList projectionList = Projections.projectionList();
									projectionList.add(Projections.groupProperty("viewDate"));
									projectionList.add(Projections.groupProperty("customerId"));
									vtCriteria.setProjection(projectionList);
									List<ViewersTrack> trackList2 = vtCriteria.list();
									videoBean.setTotalViewCount(trackList2.size());
									List<VideoAds> videoAds = new ArrayList<VideoAds>();
									fillAds(session, video, videoBean);
									SocialEntityManager.setSocialEntity(videoBean, session, customerId, isLoggedIn);
									videoList.add(videoBean);
									videoBean = null;
								}
								albumBean.setVideoList(videoList);
								if (albumBean != null)
									albumBeanList.add(albumBean);
								albumBean = null;
							}
						}
					}
					responseBean.setAlbumList(albumBeanList);
				}
			} else {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			responseBean.setMessage("Failed");
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

	public List<BroadcasterChannelCategoryResponseBean> getAllBroadcasters(int categoryId, int customerId,
			boolean isLoggedIn) {
		CategoryRequestBean requestBean = new CategoryRequestBean();
		requestBean.setBroadcasterId(0);
		requestBean.setCategoryId(categoryId);
		requestBean.setCustomerId(customerId);
		requestBean.setIsLoggedIn(isLoggedIn);
		return getAllBroadcasters(requestBean);
	}

	public List<BroadcasterChannelCategoryResponseBean> getAllBroadcasters(CategoryRequestBean requestBean) {
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
			if (requestBean.isEnablePage()) {
				if (requestBean.getIsVerticalPage()) {
					criteria.setFirstResult(requestBean.getVerticalIndex());
					criteria.setMaxResults(broadcasterMaxResults);
				} else {
					criteria.add(Restrictions.eq("id", requestBean.getCategoryId()));
				}
			} else {
				if (requestBean.getCategoryId() > 0)
					criteria.add(Restrictions.eq("id", requestBean.getCategoryId()));
			}

			List<BroadcasterCategory> businessCategories = criteria.list();
			if (businessCategories.size() > 0) {
				List<BroadcasterChannelResponseBean> channels = null;
				for (BroadcasterCategory category : businessCategories) {
					Criteria broadcasterCriteria = session.createCriteria(Broadcaster.class);
					broadcasterCriteria.add(Restrictions.eq("categoryId", category.getId()));
					broadcasterCriteria.add(Restrictions.eq("isActive", true));
					if (requestBean.isEnablePage()) {
						broadcasterCriteria.setFirstResult(requestBean.getHorizontalIndex());
						broadcasterCriteria.setMaxResults(broadcasterMaxResults);

					}
					broadcasterCriteria.addOrder(Order.asc("rank"));
					broadcasterCriteria.addOrder(Order.asc("broadcasterChannelName"));
					List<Broadcaster> broadcasters = broadcasterCriteria.list();
					BroadcasterChannelCategoryResponseBean responseBean = new BroadcasterChannelCategoryResponseBean();
					responseBean.setCategoryName(category.getName());
					responseBean.setId(category.getId());
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
							SocialEntityManager.setSocialEntity(bean, session, requestBean.getCustomerId(),
									requestBean.getIsLoggedIn());
							channels.add(bean);
							bean = null;
						}

						responseBean.setChannelList(channels);
						categories.add(responseBean);
						responseBean = null;
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
			Criteria criteria = session.createCriteria(BroadcasterChannelCategory.class);
			criteria.addOrder(Order.asc("rank"));
			criteria.addOrder(Order.asc("name"));
			List<BroadcasterChannelCategory> albumCategories = criteria.list();
			if (albumCategories.size() > 0) {
				List<AlbumBean> albumsBean = null;
				for (BroadcasterChannelCategory category : albumCategories) {
					Criteria albumCriteria = session.createCriteria(BroadcasterChannel.class);
					albumCriteria.add(Restrictions.eq("broadcasterChannelCategoryId", category.getId()));
					albumCriteria.add(Restrictions.eq("broadcasterId", requestBean.getBroadcasterId()));
					albumCriteria.add(Restrictions.eq("isActive", true));
					albumCriteria.addOrder(Order.asc("rank"));
					albumCriteria.addOrder(Order.asc("channelName"));
					List<BroadcasterChannel> albums = albumCriteria.list();
					if (albums.size() > 0) {
						albumsBean = new ArrayList<AlbumBean>();
						for (BroadcasterChannel album : albums) {
							AlbumBean bean = new AlbumBean();
							bean.setAlbumId(album.getChannelId());
							bean.setAlbumName(album.getChannelName());
							bean.setAlbumDescription(album.getChannelDescription());
							bean.setAlbumVideoCount(0);
							bean.setAlbumThumbnail(album.getChannelThumbnail());
							SocialEntityManager.setSocialEntity(bean, requestBean.getCustomerId(), session);
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

	@Deprecated
	public AlbumBean getBroadcasterAlbumVideoList(AlbumVideoRequestBean requestBean) {
		log.debug("getting album video list");
		AlbumBean albumBean = new AlbumBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			BroadcasterChannel album = (BroadcasterChannel) session.get(
					"com.limitless.services.engage.entertainment.dao.BroadcasterChannel", requestBean.getAlbumId());
			if (album != null) {
				albumBean.setMessage("Success");
				albumBean.setAlbumId(album.getChannelId());
				albumBean.setAlbumName(album.getChannelName());
				albumBean.setAlbumDescription(album.getChannelDescription());
				albumBean.setAlbumThumbnail(album.getChannelThumbnail());
				albumBean.setAlbumVideoCount(0);
				SocialEntityManager.setSocialEntity(albumBean, requestBean.getCustomerId(), session);
				List<VideoBean> videoList = new ArrayList<VideoBean>();
				Criteria criteria = session.createCriteria(BroadcasterVideo.class);
				criteria.add(Restrictions.eq("channelId", album.getChannelId()));
				criteria.add(Restrictions.eq("isActive", true));
				criteria.setFirstResult(requestBean.getVideoIndex());
				criteria.setMaxResults(videoMaxResults);
				criteria.addOrder(Order.asc("rank"));

				List<BroadcasterVideo> videosList = criteria.list();
				log.debug("video size : " + videosList.size());
				if (videosList.size() > 0) {
					for (BroadcasterVideo video : videosList) {
						VideoBean videoBean = new VideoBean();
						videoBean.setVideoId(video.getId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getChannelId());
						videoBean.setAlbumName(album.getChannelDescription());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setYoutube(video.isYoutube());
						videoBean.setLive(video.isLive());
						videoBean.setUrl(video.getUrl());
						videoBean.setVideoType(video.getVideoType());
						videoBean.setP160(video.isP160());
						videoBean.setP360(video.isP360());
						videoBean.setP720(video.isP720());
						videoBean.setP1080(video.isP1080());
						videoBean.setpUhd(video.ispUhd());
						videoBean.setDuration(video.getDuration());
						videoBean.setLiveAds(video.isLiveAds());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						SocialEntityManager.setSocialEntity(videoBean, session, requestBean.getCustomerId(),
								requestBean.getIsLoggedIn());
						Criteria criteria2 = session.createCriteria(ViewersTrack.class);
						criteria2.add(Restrictions.eq("videoId", video.getId()));
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

	@Deprecated
	public AlbumBean getBroadcasterAlbumVideoList(int albumId, int beginingVideoId, int customerId,
			boolean isLoggedIn) {
		log.debug("getting album video list");
		AlbumBean albumBean = new AlbumBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			BroadcasterChannel album = (BroadcasterChannel) session
					.get("com.limitless.services.engage.entertainment.dao.BroadcasterChannel", albumId);
			if (album != null) {
				albumBean.setMessage("Success");
				albumBean.setAlbumId(albumId);
				albumBean.setAlbumName(album.getChannelName());
				albumBean.setAlbumDescription(album.getChannelDescription());
				albumBean.setAlbumThumbnail(album.getChannelThumbnail());
				albumBean.setAlbumVideoCount(0);
				SocialEntityManager.setSocialEntity(albumBean, customerId, session);
				List<VideoBean> videoList = new ArrayList<VideoBean>();
				Criteria criteria = session.createCriteria(BroadcasterVideo.class);
				criteria.add(Restrictions.eq("channelId", albumId));
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
						videoBean.setVideoId(video.getId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getChannelId());
						videoBean.setAlbumName(album.getChannelDescription());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setYoutube(video.isYoutube());
						videoBean.setLive(video.isLive());
						videoBean.setUrl(video.getUrl());
						videoBean.setVideoType(video.getVideoType());
						videoBean.setP160(video.isP160());
						videoBean.setP360(video.isP360());
						videoBean.setP720(video.isP720());
						videoBean.setP1080(video.isP1080());
						videoBean.setpUhd(video.ispUhd());
						videoBean.setDuration(video.getDuration());
						videoBean.setLiveAds(video.isLiveAds());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						SocialEntityManager.setSocialEntity(videoBean, session, customerId, isLoggedIn);
						Criteria criteria2 = session.createCriteria(ViewersTrack.class);
						criteria2.add(Restrictions.eq("videoId", video.getId()));
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

	private void setAdRollAdditionalDetails(Session session, AdRollBean adrollBean) {
		String searchString = adrollBean.getShopCode();
		if (searchString != null && !searchString.trim().isEmpty()) {
			int sellerId = 0;
			if (EngageSellerManager.isInteger(searchString) && searchString.length() <= 7)
				sellerId = Integer.parseInt(searchString);
			Criteria criteriaSeller = session.createCriteria(EngageSeller.class);
			Junction condition1 = Restrictions.disjunction().add(Restrictions.eq("sellerMobileNumber", searchString))
					.add(Restrictions.eq("mobileAlias", searchString)).add(Restrictions.eq("sellerName", searchString))
					.add(Restrictions.eq("sellerShopName", searchString)).add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.eq("sellerEmail99", searchString)).add(Restrictions.like("tag", searchString));
			Junction condition2 = Restrictions.conjunction().add(condition1).add(Restrictions.ne("isDeleted", 1));
			criteriaSeller.add(condition2);
			List<EngageSeller> sellerList = criteriaSeller.list();
			log.debug("Size : " + sellerList.size());
			if (sellerList.size() >= 1) {
				EngageSeller seller = sellerList.get(0);
				adrollBean.setContactNumber(seller.getSellerMobileNumber());
				adrollBean.setShopName(seller.getSellerShopName());
				Criteria cVideoBrandPromotion = session.createCriteria(VideoBrandPromotion.class);
				cVideoBrandPromotion.add(Restrictions.eq("sellerId", seller.getSellerId()));
				List<VideoBrandPromotion> listVbp = cVideoBrandPromotion.list();
				if (listVbp.size() >= 1) {
					adrollBean.setBannerUrl(listVbp.get(0).getAdUrl());
				}
			} else {
				adrollBean.setContactNumber(adrollBean.getShopCode());
			}
		}
	}

	private void fillAds(Session session, BroadcasterVideo video, VideoBean videoBean) {
		List<VideoAds> videoAds;
		Criteria criteriaVideoAds = session.createCriteria(VideoAds.class);
		criteriaVideoAds.add(Restrictions.eq("videoId", video.getId()));
		videoAds = criteriaVideoAds.list();
		List<AdRollBean> midRolls = new ArrayList<AdRollBean>();
		for (VideoAds videoAd : videoAds) {
			AdRollBean adRoll = new AdRollBean();
			if (videoAd.getPreRollUrl() != null && !videoAd.getPreRollUrl().isEmpty()) {
				adRoll.setUrl(videoAd.getPreRollUrl());
				adRoll.setShopCode(videoAd.getPreRollCode());
				setAdRollAdditionalDetails(session, adRoll);
				videoBean.setPreRoll(adRoll);
				adRoll = null;
			}
			if (videoAd.getMidRollUrl_1() != null && !videoAd.getMidRollUrl_1().isEmpty()) {
				AdRollBean m1 = new AdRollBean();
				m1.setUrl(videoAd.getMidRollUrl_1());
				m1.setShopCode(videoAd.getMidRollUrl_1_code());
				setAdRollAdditionalDetails(session, m1);
				midRolls.add(m1);
				m1 = null;
			}

			if (videoAd.getMidRollUrl_2() != null && !videoAd.getMidRollUrl_2().isEmpty()) {
				AdRollBean m2 = new AdRollBean();
				m2.setUrl(videoAd.getMidRollUrl_2());
				m2.setShopCode(videoAd.getMidRollUrl_2_code());
				setAdRollAdditionalDetails(session, m2);
				midRolls.add(m2);
				m2 = null;
			}

			if (videoAd.getMidRollUrl_3() != null && !videoAd.getMidRollUrl_3().isEmpty()) {
				AdRollBean m3 = new AdRollBean();
				m3.setUrl(videoAd.getMidRollUrl_3());
				m3.setShopCode(videoAd.getMidRollUrl_3_code());
				setAdRollAdditionalDetails(session, m3);
				midRolls.add(m3);
				m3 = null;
			}

			if (videoAd.getMidRollUrl_4() != null && !videoAd.getMidRollUrl_4().isEmpty()) {
				AdRollBean m4 = new AdRollBean();
				m4.setUrl(videoAd.getMidRollUrl_4());
				m4.setShopCode(videoAd.getMidRollUrl_4_code());
				setAdRollAdditionalDetails(session, m4);
				midRolls.add(m4);
				m4 = null;
			}

			if (videoAd.getMidRollUrl_5() != null && !videoAd.getMidRollUrl_5().isEmpty()) {
				AdRollBean m5 = new AdRollBean();
				m5.setUrl(videoAd.getMidRollUrl_5());
				m5.setShopCode(videoAd.getMidRollUrl_5_code());
				setAdRollAdditionalDetails(session, m5);
				midRolls.add(m5);
				m5 = null;
			}

			if (videoAd.getMidRollUrl_6() != null && !videoAd.getMidRollUrl_6().isEmpty()) {
				AdRollBean m6 = new AdRollBean();
				m6.setUrl(videoAd.getMidRollUrl_6());
				m6.setShopCode(videoAd.getMidRollUrl_6_code());
				setAdRollAdditionalDetails(session, m6);
				midRolls.add(m6);
				m6 = null;
			}

			if (videoAd.getMidRollUrl_7() != null && !videoAd.getMidRollUrl_7().isEmpty()) {
				AdRollBean m7 = new AdRollBean();
				m7.setUrl(videoAd.getMidRollUrl_7());
				m7.setShopCode(videoAd.getMidRollUrl_7_code());
				setAdRollAdditionalDetails(session, m7);
				midRolls.add(m7);
				m7 = null;
			}

			if (videoAd.getMidRollUrl_8() != null && !videoAd.getMidRollUrl_8().isEmpty()) {
				AdRollBean m8 = new AdRollBean();
				m8.setUrl(videoAd.getMidRollUrl_8());
				m8.setShopCode(videoAd.getMidRollUrl_8_code());
				setAdRollAdditionalDetails(session, m8);
				midRolls.add(m8);
				m8 = null;
			}

			if (videoAd.getMidRollUrl_9() != null && !videoAd.getMidRollUrl_9().isEmpty()) {
				AdRollBean m9 = new AdRollBean();
				m9.setUrl(videoAd.getMidRollUrl_9());
				m9.setShopCode(videoAd.getMidRollUrl_9_code());
				setAdRollAdditionalDetails(session, m9);
				midRolls.add(m9);
				m9 = null;
			}

			if (videoAd.getMidRollUrl_10() != null && !videoAd.getMidRollUrl_10().isEmpty()) {
				AdRollBean m10 = new AdRollBean();
				m10.setUrl(videoAd.getMidRollUrl_10());
				m10.setShopCode(videoAd.getMidRollUrl_10_code());
				setAdRollAdditionalDetails(session, m10);
				midRolls.add(m10);
				m10 = null;
			}
			videoBean.setMidRolls(midRolls);
			midRolls = null;

		}
	}

	private void fillAds(Session session, ChannelVideo video, VideoBean videoBean) {
		List<VideoAds> videoAds;
		Criteria criteriaVideoAds = session.createCriteria(VideoAds.class);
		criteriaVideoAds.add(Restrictions.eq("videoId", video.getId()));
		videoAds = criteriaVideoAds.list();
		List<AdRollBean> midRolls = new ArrayList<AdRollBean>();
		for (VideoAds videoAd : videoAds) {
			AdRollBean adRoll = new AdRollBean();
			if (videoAd.getPreRollUrl() != null && !videoAd.getPreRollUrl().isEmpty()) {
				adRoll.setUrl(videoAd.getPreRollUrl());
				adRoll.setShopCode(videoAd.getPreRollCode());
				setAdRollAdditionalDetails(session, adRoll);
				videoBean.setPreRoll(adRoll);
				adRoll = null;
			}
			if (videoAd.getMidRollUrl_1() != null && !videoAd.getMidRollUrl_1().isEmpty()) {
				AdRollBean m1 = new AdRollBean();
				m1.setUrl(videoAd.getMidRollUrl_1());
				m1.setShopCode(videoAd.getMidRollUrl_1_code());
				setAdRollAdditionalDetails(session, m1);
				midRolls.add(m1);
				m1 = null;
			}

			if (videoAd.getMidRollUrl_2() != null && !videoAd.getMidRollUrl_2().isEmpty()) {
				AdRollBean m2 = new AdRollBean();
				m2.setUrl(videoAd.getMidRollUrl_2());
				m2.setShopCode(videoAd.getMidRollUrl_2_code());
				setAdRollAdditionalDetails(session, m2);
				midRolls.add(m2);
				m2 = null;
			}

			if (videoAd.getMidRollUrl_3() != null && !videoAd.getMidRollUrl_3().isEmpty()) {
				AdRollBean m3 = new AdRollBean();
				m3.setUrl(videoAd.getMidRollUrl_3());
				m3.setShopCode(videoAd.getMidRollUrl_3_code());
				setAdRollAdditionalDetails(session, m3);
				midRolls.add(m3);
				m3 = null;
			}

			if (videoAd.getMidRollUrl_4() != null && !videoAd.getMidRollUrl_4().isEmpty()) {
				AdRollBean m4 = new AdRollBean();
				m4.setUrl(videoAd.getMidRollUrl_4());
				m4.setShopCode(videoAd.getMidRollUrl_4_code());
				setAdRollAdditionalDetails(session, m4);
				midRolls.add(m4);
				m4 = null;
			}

			if (videoAd.getMidRollUrl_5() != null && !videoAd.getMidRollUrl_5().isEmpty()) {
				AdRollBean m5 = new AdRollBean();
				m5.setUrl(videoAd.getMidRollUrl_5());
				m5.setShopCode(videoAd.getMidRollUrl_5_code());
				setAdRollAdditionalDetails(session, m5);
				midRolls.add(m5);
				m5 = null;
			}

			if (videoAd.getMidRollUrl_6() != null && !videoAd.getMidRollUrl_6().isEmpty()) {
				AdRollBean m6 = new AdRollBean();
				m6.setUrl(videoAd.getMidRollUrl_6());
				m6.setShopCode(videoAd.getMidRollUrl_6_code());
				setAdRollAdditionalDetails(session, m6);
				midRolls.add(m6);
				m6 = null;
			}

			if (videoAd.getMidRollUrl_7() != null && !videoAd.getMidRollUrl_7().isEmpty()) {
				AdRollBean m7 = new AdRollBean();
				m7.setUrl(videoAd.getMidRollUrl_7());
				m7.setShopCode(videoAd.getMidRollUrl_7_code());
				setAdRollAdditionalDetails(session, m7);
				midRolls.add(m7);
				m7 = null;
			}

			if (videoAd.getMidRollUrl_8() != null && !videoAd.getMidRollUrl_8().isEmpty()) {
				AdRollBean m8 = new AdRollBean();
				m8.setUrl(videoAd.getMidRollUrl_8());
				m8.setShopCode(videoAd.getMidRollUrl_8_code());
				setAdRollAdditionalDetails(session, m8);
				midRolls.add(m8);
				m8 = null;
			}

			if (videoAd.getMidRollUrl_9() != null && !videoAd.getMidRollUrl_9().isEmpty()) {
				AdRollBean m9 = new AdRollBean();
				m9.setUrl(videoAd.getMidRollUrl_9());
				m9.setShopCode(videoAd.getMidRollUrl_9_code());
				setAdRollAdditionalDetails(session, m9);
				midRolls.add(m9);
				m9 = null;
			}

			if (videoAd.getMidRollUrl_10() != null && !videoAd.getMidRollUrl_10().isEmpty()) {
				AdRollBean m10 = new AdRollBean();
				m10.setUrl(videoAd.getMidRollUrl_10());
				m10.setShopCode(videoAd.getMidRollUrl_10_code());
				setAdRollAdditionalDetails(session, m10);
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
				videoBean.setLive(video.isLive());
				videoBean.setUrl(video.getUrl());
				videoBean.setVideoType(video.getVideoType());
				videoBean.setP160(video.isP160());
				videoBean.setP360(video.isP360());
				videoBean.setP720(video.isP720());
				videoBean.setP1080(video.isP1080());
				videoBean.setpUhd(video.ispUhd());
				videoBean.setDuration(video.getDuration());
				videoBean.setLiveAds(video.isLiveAds());
				videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
				videoBean.setMessage("Success");

				int customerId = 0;
				if (requestBean.getCustomerId() > 0) {
					customerId = requestBean.getCustomerId();
					SocialEntityManager.setSocialEntity(videoBean, session, customerId, true);
				} else if (requestBean.getGuestId() > 0) {
					customerId = requestBean.getGuestId();
					SocialEntityManager.setSocialEntity(videoBean, session, customerId, false);
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

	public List<ChannelResponseBean> searchChannels(ChannelRequestBean requestBean) {
		log.debug("getting channles");
		List<ChannelResponseBean> responseBean = new ArrayList<ChannelResponseBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(Channel.class);
			if (requestBean.getBroadcasterId() > 0)
				criteria.add(Restrictions.eq("broadcasterId", requestBean.getBroadcasterId()));
			if (requestBean.getCategoryId() > 0)
				criteria.add(Restrictions.eq("categoryId", requestBean.getCategoryId()));
			if (requestBean.getChannelId() > 0)
				criteria.add(Restrictions.eq("channelId", requestBean.getChannelId()));
			if (requestBean.getLanguageId() > 0)
				criteria.add(Restrictions.eq("languageId", requestBean.getLanguageId()));
			if (requestBean.getChannelName() != null && !requestBean.getChannelName().isEmpty()) {
				criteria.add(Restrictions.like("channelName", requestBean.getChannelName()));
			}
			criteria.add(Restrictions.eq("deprecated", false));
			criteria.add(Restrictions.eq("isActive", true));
			criteria.addOrder(Order.asc("rank"));
			criteria.addOrder(Order.asc("channelName"));
			criteria.setFirstResult(requestBean.getStartIndex());
			criteria.setMaxResults(channelMaxResults);
			List<Channel> channelList = criteria.list();
			log.debug("Channel size : " + channelList.size());
			if (channelList.size() > 0) {
				for (Channel channel : channelList) {
					ChannelResponseBean channelBean = new ChannelResponseBean();
					channelBean.setChannelId(channel.getChannelId());
					channelBean.setBroadcasterId(channel.getBroadcasterId());
					channelBean.setCategoryId(channel.getBroadcasterChannelCategoryId());
					channelBean.setLanguageId(channel.getLanguageId());
					channelBean.setChannelName(channel.getChannelName());
					channelBean.setChannelDescription(channel.getChannelDescription());
					channelBean.setChannelThumbnail(channel.getHaChannelThumbnail());
					channelBean.setHd(channel.isHd());
					if (channel.getVideos() != null && channel.getVideos().size() > 0) {
						// log.info("Video Size: "+ channel.getVideos().size());
						ChannelVideo video = channel.getVideos().get(0);
						VideoBean videoBean = new VideoBean();
						videoBean.setVideoId(video.getId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getChannelId());
						videoBean.setAlbumName(channel.getChannelName());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setYoutube(video.isYoutube());
						videoBean.setLive(video.isLive());
						videoBean.setUrl(video.getUrl());
						videoBean.setVideoType(video.getVideoType());
						videoBean.setP160(video.isP160());
						videoBean.setP360(video.isP360());
						videoBean.setP720(video.isP720());
						videoBean.setP1080(video.isP1080());
						videoBean.setpUhd(video.ispUhd());
						videoBean.setDuration(video.getDuration());
						videoBean.setLiveAds(video.isLiveAds());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						SocialEntityManager.setSocialEntity(videoBean, session, requestBean.getCustomerId(),
								requestBean.getIsLoggedIn());
						channelBean.setLiveVideo(videoBean);
					}
					SocialEntityManager.setSocialEntity(channelBean, session, requestBean.getCustomerId(),
							requestBean.getIsLoggedIn());
					responseBean.add(channelBean);
					channelBean = null;
				}
				transaction.commit();
			}
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

	public AlbumBean getBroadcasterChannelVideoList(AlbumVideoRequestBean requestBean) {
		log.debug("getting channel video list");
		AlbumBean albumBean = new AlbumBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Channel album = (Channel) session.get("com.limitless.services.engage.entertainment.dao.Channel",
					requestBean.getAlbumId());
			if (album != null) {
				albumBean.setMessage("Success");
				albumBean.setAlbumId(album.getChannelId());
				albumBean.setAlbumName(album.getChannelName());
				albumBean.setAlbumDescription(album.getChannelDescription());
				albumBean.setAlbumThumbnail(album.getChannelThumbnail());
				albumBean.setAlbumVideoCount(0);
				SocialEntityManager.setSocialEntity(albumBean, requestBean.getCustomerId(), session);

			} else if (requestBean.getVideoId() > 0) {
				albumBean = new AlbumBean();
			} else {
				albumBean = null;
			}

			if (albumBean != null) {
				List<VideoBean> videoList = new ArrayList<VideoBean>();
				Criteria criteria = session.createCriteria(ChannelVideo.class);
				criteria.add(Restrictions.eq("channelId", album.getChannelId()));
				criteria.add(Restrictions.eq("isActive", true));
				criteria.setFirstResult(requestBean.getVideoIndex());
				criteria.setMaxResults(videoMaxResults);
				criteria.addOrder(Order.asc("rank"));

				List<ChannelVideo> videosList = criteria.list();
				log.debug("video size : " + videosList.size());
				if (videosList.size() > 0) {
					for (ChannelVideo video : videosList) {
						VideoBean videoBean = new VideoBean();
						videoBean.setVideoId(video.getId());
						videoBean.setVideoName(video.getVideoName());
						videoBean.setAlbumId(video.getChannelId());
						videoBean.setAlbumName(album.getChannelDescription());
						videoBean.setVideoDescription(video.getVideoDescription());
						videoBean.setVideoThumbnail(video.getVideoThumbnail());
						videoBean.setVideoUrl(video.getVideoUrl());
						videoBean.setYoutube(video.isYoutube());
						videoBean.setLive(video.isLive());
						videoBean.setUrl(video.getUrl());
						videoBean.setVideoType(video.getVideoType());
						videoBean.setP160(video.isP160());
						videoBean.setP360(video.isP360());
						videoBean.setP720(video.isP720());
						videoBean.setP1080(video.isP1080());
						videoBean.setpUhd(video.ispUhd());
						videoBean.setDuration(video.getDuration());
						videoBean.setLiveAds(video.isLiveAds());
						videoBean.setVideoCreated(video.getVideoCreatedTime().toString());
						SocialEntityManager.setSocialEntity(videoBean, session, requestBean.getCustomerId(),
								requestBean.getIsLoggedIn());
						List<VideoAds> videoAds = new ArrayList<VideoAds>();
						fillAds(session, video, videoBean);

						videoList.add(videoBean);
						videoBean = null;
					}
					albumBean.setVideoList(videoList);
				}
			} else {
				albumBean = new AlbumBean();
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

	public ChannelFilterResponseBean getChannelFilters() {
		log.debug("getting channles");
		ChannelFilterResponseBean responseBean = new ChannelFilterResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(ChannelCategory.class);
			criteria.addOrder(Order.asc("rank"));
			criteria.addOrder(Order.asc("name"));
			List<ChannelCategory> categories = criteria.list();
			if (categories != null && categories.size() > 0) {
				for (ChannelCategory cat : categories) {
					FilterResponseBean bean = new FilterResponseBean();
					bean.setId(cat.getId());
					bean.setName(cat.getName());
					responseBean.getCategories().add(bean);
					bean = null;
				}
			}

			Criteria criteriaLang = session.createCriteria(ChannelLanguage.class);
			criteriaLang.addOrder(Order.asc("lang_name"));
			List<ChannelLanguage> languages = criteriaLang.list();
			if (languages != null && languages.size() > 0) {
				for (ChannelLanguage lan : languages) {
					FilterResponseBean bean = new FilterResponseBean();
					bean.setId(lan.getId());
					bean.setName(lan.getLang_name());
					responseBean.getLanguages().add(bean);
					bean = null;
				}
			}

			Criteria criteriaBc = session.createCriteria(Broadcaster.class);
			criteriaBc.addOrder(Order.asc("broadcasterName"));
			List<Broadcaster> broadcasters = criteriaBc.list();
			if (broadcasters != null && broadcasters.size() > 0) {
				for (Broadcaster bc : broadcasters) {
					FilterResponseBean bean = new FilterResponseBean();
					bean.setId(bc.getBroadcasterId());
					bean.setName(bc.getBroadcasterName());
					bean.setImageUrl(bc.getBroadcasterImage());
					responseBean.getBroadcasters().add(bean);
					bean = null;
				}
			}
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
		return responseBean;

	}

}

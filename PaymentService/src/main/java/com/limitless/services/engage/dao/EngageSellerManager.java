 package com.limitless.services.engage.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

// Generated Oct 14, 2016 12:16:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.context.spi.CurrentSessionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;

import com.limitless.services.engage.AliasCheckResponseBean;
import com.limitless.services.engage.AmbassadorResponseBean;
import com.limitless.services.engage.CoordinatesResponseBean;
import com.limitless.services.engage.CustomerCoordsBean;
import com.limitless.services.engage.CustomerDataBean;
import com.limitless.services.engage.CustomerFcmRequestBean;
import com.limitless.services.engage.CustomerNotifyBean;
import com.limitless.services.engage.CustomerNotifyListBean;
import com.limitless.services.engage.CustomerNotifyRequestBean;
import com.limitless.services.engage.CustomerNotifyResponseBean;
import com.limitless.services.engage.CustomerNotifyUpdateResponseBean;
import com.limitless.services.engage.MerchantDeviceIdRequestBean;
import com.limitless.services.engage.MerchantDeviceIdResponseBean;
import com.limitless.services.engage.MerchantLogoutRequestBean;
import com.limitless.services.engage.MerchantLogoutResponseBean;
import com.limitless.services.engage.MerchantRequestCountBean;
import com.limitless.services.engage.MerchantRequestListBean;
import com.limitless.services.engage.NewMerchantsRequestBean;
import com.limitless.services.engage.NewPromoCodeRequestBean;
import com.limitless.services.engage.NewPromoCodeResponseBean;
import com.limitless.services.engage.PhoneNumber;
import com.limitless.services.engage.PromoCodeRequestBean;
import com.limitless.services.engage.PromoCodeResponseBean;
import com.limitless.services.engage.SellerAdBean;
import com.limitless.services.engage.SellerAdsListBean;
import com.limitless.services.engage.SellerBrandPromotionBean;
import com.limitless.services.engage.SellerBrandPromotionListBean;
import com.limitless.services.engage.SellerBusinessCategoryBean;
import com.limitless.services.engage.SellerBusinessCategoryListBean;
import com.limitless.services.engage.SellerContactsRequestBean;
import com.limitless.services.engage.SellerContactsResponseBean;
import com.limitless.services.engage.SellerLoginRequestBean;
import com.limitless.services.engage.SellerLoginResponseBean;
import com.limitless.services.engage.SellerMinBean;
import com.limitless.services.engage.SellerPasswdRequestBean;
import com.limitless.services.engage.SellerPasswdResponseBean;
import com.limitless.services.engage.SellerRestaurantListBean;
import com.limitless.services.engage.SellerRestaurantsBean;
import com.limitless.services.engage.SellerUpdateRequestBean;
import com.limitless.services.engage.SellerUpdateResponseBean;
import com.limitless.services.engage.restaurants.dao.Restaurants;
import com.limitless.services.engage.sellers.SubMerchantBean;
import com.limitless.services.engage.sellers.SubMerchantListResponseBean;
import com.limitless.services.engage.sellers.dao.SellerVersion;
import com.limitless.services.engage.sellers.product.dao.ProductCategory;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Home object for domain model class EngageSeller.
 * 
 * @see com.limitless.EngageSeller
 * @author Hibernate Tools
 */
public class EngageSellerManager {

	private static final Log log = LogFactory.getLog(EngageSellerManager.class);
	Client client = RestClientUtil.createClient();

	/*
	 * private final SessionFactory sessionFactory = getSessionFactory();
	 * 
	 * protected SessionFactory getSessionFactory() { try { return
	 * (SessionFactory) new InitialContext() .lookup("SessionFactory"); } catch
	 * (Exception e) { log.error("Could not locate SessionFactory in JNDI", e);
	 * throw new IllegalStateException(
	 * "Could not locate SessionFactory in JNDI"); } }
	 */

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(EngageSeller transientInstance) {
		log.debug("persisting EngageSeller instance");
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			log.debug("persist successful");
			tx.commit();
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("persist failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public void attachDirty(EngageSeller instance) {
		log.debug("attaching dirty EngageSeller instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EngageSeller instance) {
		log.debug("attaching clean EngageSeller instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EngageSeller persistentInstance) {
		log.debug("deleting EngageSeller instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EngageSeller merge(EngageSeller detachedInstance) {
		log.debug("merging EngageSeller instance");
		try {
			EngageSeller result = (EngageSeller) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EngageSeller findById(int id) {
		log.debug("getting EngageSeller instance with id: " + id);
		Transaction tx = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			EngageSeller instance = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			tx.commit();
			return instance;
		} catch (RuntimeException re) {
			if (tx != null) {
				tx.rollback();
			}
			log.error("get failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public List findByExample(EngageSeller instance) {
		log.debug("finding EngageSeller instance by example");
		try {
			List results = sessionFactory.getCurrentSession().createCriteria("com.limitless.EngageSeller")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public boolean checkDuplicateEmail(String sellerEmail) {
		log.debug("Checking Email whether already exist");
		Transaction transaction = null;
		Session session = null;
		try {
			boolean emailExist = false;
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageSeller where sellerEmail99 = :sellerEmail");
			query.setParameter("sellerEmail", sellerEmail);
			List<EngageSeller> seller = query.list();
			if (seller != null && seller.size() > 0) {
				emailExist = true;
			}
			transaction.commit();
			return emailExist;
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Checking Email failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public boolean checkDuplicateMobile(String sellerMobile) {
		log.debug("Checking Mobile whether already exist");
		Transaction transaction = null;
		Session session = null;
		try {
			boolean mobileExist = false;
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageSeller where sellerMobileNumber = :sellerMobile");
			query.setParameter("sellerMobile", sellerMobile);
			List<EngageSeller> seller = query.list();
			if (seller != null && seller.size() > 0) {
				mobileExist = true;
			}
			transaction.commit();
			return mobileExist;
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Checking mobile failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public SellerLoginResponseBean sellerLogin(SellerLoginRequestBean reqBean) throws Exception {
		log.debug("Logging in seller");
		SellerLoginResponseBean respBean = new SellerLoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageSeller.class);
			Junction conditionGroup = Restrictions.conjunction()
					.add(Restrictions.eq("sellerEmail99", reqBean.getEmailId()))
					.add(Restrictions.eq("sellerPasswd99", reqBean.getPasswd())).add(Restrictions.eq("isActive", 1));
			criteria.add(conditionGroup);
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Size: " + sellerList.size());
			if (sellerList != null && sellerList.size() == 1) {
				int sellerId = 0;
				for (EngageSeller seller : sellerList) {
					respBean.setSellerId(seller.getSellerId());
					sellerId = seller.getSellerId();
					respBean.setSellerName(seller.getSellerShopName());
					respBean.setMobileNumber(seller.getSellerMobileNumber());
					respBean.setSellerAddress(seller.getSellerAddress());
					respBean.setSellerCity(seller.getSellerCity());
					respBean.setSellerType(seller.getSellerType());
					respBean.setSellerRole(seller.getSellerRole());
					respBean.setBusinessType(seller.getBusinessType());
					respBean.setBusinessCategory(seller.getBusinessCategory());
					respBean.setMapMarkerName(seller.getMapMarkerName());
					respBean.setCobranding(seller.isCobranding());
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					log.debug("configs size : " + configList.size());
					if (configList.size() > 0) {
						for (SellerPayamentsConfiguration config : configList) {
							respBean.setCitrusSellerId(config.getCitrusSellerId());
							if (config.getPayOnDelivery() == 1) {
								respBean.setPodAvailable(true);
							} else if (config.getPayOnDelivery() == 0) {
								respBean.setPodAvailable(false);
							}
							respBean.setDeliveryMinAmount(config.getDevliveryMInAmt());
							respBean.setDeliveryFee(config.getDeliveryFee());
							respBean.setDeliveryRadius(config.getDeliveryRadius());
							if (config.getConvenienceFee() == 1) {
								respBean.setConvenienceFee(true);
							} else if (config.getConvenienceFee() == 0) {
								respBean.setConvenienceFee(false);
							}
						}
					}
					respBean.setMessage("Success");
					respBean.setStatus(1);

					SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(new Date());
					calendar.add(Calendar.MINUTE, 5);
					String validDate = sdf1.format(calendar.getTime());
					log.debug("Date String : " + validDate);
					Date validTill = sdf1.parse(validDate);
					log.debug("Date String : " + validTill.toString());

					JSONObject sessionKeyJson = new JSONObject();
					sessionKeyJson.put("role", "merchant");
					sessionKeyJson.put("key", seller.getSellerId());
					sessionKeyJson.put("value", seller.getSellerPasswd99());

					SessionKeys sessionKeys = new SessionKeys();
					sessionKeys.setUserId(seller.getSellerId());
					sessionKeys.setSessionKey(sessionKeyJson.toString());
					sessionKeys.setKeyAlive(1);

					session.persist(sessionKeys);

					int sesssionKeyId = sessionKeys.getSessionId();

					String sessionKeyString = sesssionKeyId + "." + sessionKeyJson.toString();
					String sessionKeyB64 = Base64.getEncoder().encodeToString(sessionKeyString.getBytes());
					log.debug("Session Key : " + sessionKeyB64);

					respBean.setSessionKey(sessionKeyB64);
					respBean.setSessionId(sesssionKeyId);
				}
				EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						sellerId);
				seller.setSellerDeviceId(reqBean.getSellerDeviceId());
				session.update(seller);

				Criteria criteria2 = session.createCriteria(SellerDeviceIdMapper.class);
				criteria2.add(Restrictions.eq("sellerDeviceId", reqBean.getSellerDeviceId()));
				List<SellerDeviceIdMapper> mapperList = criteria2.list();
				log.debug("sdm list size : " + mapperList.size());
				if (mapperList.isEmpty()) {
					SellerDeviceIdMapper mapper = new SellerDeviceIdMapper();
					mapper.setSellerId(respBean.getSellerId());
					mapper.setSellerDeviceId(reqBean.getSellerDeviceId());
					mapper.setDeviceActive(1);
					session.persist(mapper);
				} else if (mapperList.size() > 0) {
					for (SellerDeviceIdMapper sdMapper : mapperList) {
						SellerDeviceIdMapper instance = (SellerDeviceIdMapper) session
								.get("com.limitless.services.engage.dao.SellerDeviceIdMapper", sdMapper.getSdmId());
						if (instance != null) {
							instance.setSellerId(sellerId);
							instance.setDeviceActive(1);
							session.update(instance);
						}
					}
				}

			} else {
				respBean.setMessage("Login Failed/Account Not Activated");
				respBean.setStatus(-1);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Login failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return respBean;
	}

	public SellerPasswdResponseBean changeSellerPasswd(SellerPasswdRequestBean reqBean) {
		log.debug("Changing seller passwd");
		SellerPasswdResponseBean respBean = new SellerPasswdResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			int sellerId = reqBean.getSellerId();
			EngageSeller instance = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (instance != null) {
				instance.setSellerPasswd99(reqBean.getNewPasswd());
				session.update(instance);
				EngageSeller newInstance = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
						sellerId);
				respBean.setSellerId(sellerId);
				respBean.setPasswd(newInstance.getSellerPasswd99());
				respBean.setMessage("Success");
				respBean.setStatus(1);
			} else {
				respBean.setMessage("Failed");
				respBean.setStatus(-1);
				respBean.setSellerId(sellerId);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Passwd change failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return respBean;
	}

	public List<CoordinatesResponseBean> sellerCoordinates() {
		log.debug("Getting seller coordinates");
		Transaction transaction = null;
		Session session = null;
		List<CoordinatesResponseBean> coords = new ArrayList<CoordinatesResponseBean>();
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageSeller ES");
			List<EngageSeller> sellers = query.list();
			for (EngageSeller seller : sellers) {
				CoordinatesResponseBean bean = new CoordinatesResponseBean();
				bean.setSellerId(seller.getSellerId());
				bean.setSellerName(seller.getSellerName());
				bean.setShopName(seller.getSellerShopName());
				bean.setSellerMobile(seller.getSellerMobileNumber());
				bean.setLatitude(seller.getSellerLocationLatitude());
				bean.setLongitude(seller.getSellerLocationLongitude());
				bean.setBusinessCategory(seller.getBusinessCategory());
				bean.setMapMarkerName(seller.getMapMarkerName());
				coords.add(bean);
				bean = null;
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Passwd change failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return coords;
	}

	public String findSellerDeviceId(int sellerId) {
		log.debug("Getting seller deviceId");
		Transaction transaction = null;
		Session session = null;
		String sellerDeviceId = "";
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			sellerDeviceId = seller.getSellerDeviceId();
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Finding deviceId failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return sellerDeviceId;
	}

	public SellerLoginResponseBean getSellerByMobile(String sellerMobileNumber) {
		log.debug("Getting seller details by mobile");
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageSeller.class);
			Junction condition1 = Restrictions.disjunction()
					.add(Restrictions.eq("sellerMobileNumber", sellerMobileNumber))
					.add(Restrictions.eq("mobileAlias", sellerMobileNumber));
			Junction condition2 = Restrictions.conjunction().add(condition1).add(Restrictions.ne("isDeleted", 1));
			criteria.add(condition2);
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Size : " + sellerList.size());
			if (sellerList.size() == 1) {
				for (EngageSeller seller : sellerList) {
					responseBean.setSellerId(seller.getSellerId());
					responseBean.setSellerName(seller.getSellerShopName());
					responseBean.setSellerType(seller.getSellerType());
					responseBean.setBrandingUrl(seller.getBranding_url());
					responseBean.setBusinessType(seller.getBusinessType());
					responseBean.setBusinessCategory(seller.getBusinessCategory());
					responseBean.setMapMarkerName(seller.getMapMarkerName());
					responseBean.setAboutSeller(seller.getAboutSeller());
					responseBean.setCobranding(seller.isCobranding());
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					log.debug("configs size : " + configList.size());
					if (configList.size() > 0) {
						for (SellerPayamentsConfiguration config : configList) {
							responseBean.setCitrusSellerId(config.getCitrusSellerId());
							if (config.getPayOnDelivery() == 1) {
								responseBean.setPodAvailable(true);
							} else if (config.getPayOnDelivery() == 0) {
								responseBean.setPodAvailable(false);
							}
							responseBean.setDeliveryMinAmount(config.getDevliveryMInAmt());
							responseBean.setDeliveryFee(config.getDeliveryFee());
							responseBean.setDeliveryRadius(config.getDeliveryRadius());
							if (config.getConvenienceFee() == 1) {
								responseBean.setConvenienceFee(true);
							} else if (config.getConvenienceFee() == 0) {
								responseBean.setConvenienceFee(false);
							}
						}
					}
					responseBean.setMessage("Success");
				}
			} else {
				responseBean.setMessage("Mobile Number Not Registered");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting seller details by mobile failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public static boolean isInteger(String s) {
		return isInteger(s, 10);
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty())
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0)
				return false;
		}
		return true;
	}

	public SellerLoginResponseBean getSellerBySearchString(String searchString) {
		log.debug("Getting seller details by mobile");
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			if (searchString != null) {
				searchString = searchString.trim();
			}
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			int sellerId = 0;
			if (searchString != null && isInteger(searchString) && searchString.length() <= 7)
				sellerId = Integer.parseInt(searchString);
			Criteria criteria = session.createCriteria(EngageSeller.class);
			Junction condition1 = Restrictions.disjunction().add(Restrictions.eq("sellerMobileNumber", searchString))
					.add(Restrictions.eq("mobileAlias", searchString)).add(Restrictions.eq("sellerName", searchString))
					.add(Restrictions.eq("sellerShopName", searchString)).add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.eq("sellerEmail99", searchString)).add(Restrictions.like("tag", searchString));
			Junction condition2 = Restrictions.conjunction().add(condition1).add(Restrictions.ne("isDeleted", 1));
			criteria.add(condition2);
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Size : " + sellerList.size());
			if (sellerList.size() >= 1) {
				EngageSeller seller = sellerList.get(0);
				{
					responseBean.setSellerId(seller.getSellerId());
					responseBean.setSellerName(seller.getSellerShopName());
					responseBean.setSellerType(seller.getSellerType());
					responseBean.setBrandingUrl(seller.getBranding_url());
					responseBean.setBusinessType(seller.getBusinessType());
					responseBean.setMobileNumber(seller.getSellerMobileNumber());
					responseBean.setBusinessCategory(seller.getBusinessCategory());
					responseBean.setMapMarkerName(seller.getMapMarkerName());
					responseBean.setAboutSeller(seller.getAboutSeller());
					responseBean.setCobranding(seller.isCobranding());
					responseBean.setEcomPayment(seller.getEcomPayment());
					Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerPayamentsConfiguration> configList = criteria2.list();
					log.debug("configs size : " + configList.size());
					if (configList.size() > 0) {
						for (SellerPayamentsConfiguration config : configList) {
							responseBean.setCitrusSellerId(config.getCitrusSellerId());
							responseBean.setPaymentAlert(config.getPaymentAlert());
							if (config.getPayOnDelivery() == 1) {
								responseBean.setPodAvailable(true);
							} else if (config.getPayOnDelivery() == 0) {
								responseBean.setPodAvailable(false);
							}
							responseBean.setDeliveryMinAmount(config.getDevliveryMInAmt());
							responseBean.setDeliveryFee(config.getDeliveryFee());
							responseBean.setDeliveryRadius(config.getDeliveryRadius());
							if (config.getConvenienceFee() == 1) {
								responseBean.setConvenienceFee(true);
							} else if (config.getConvenienceFee() == 0) {
								responseBean.setConvenienceFee(false);
							}
						}
					}
					responseBean.setMessage("Success");
				}
			} else {
				responseBean.setMessage("No results found for " + searchString);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting seller details by mobile failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public AmbassadorResponseBean ambassadorCount(String ambassadorMobileNumber) {
		log.debug("Getting seller details by mobile");
		AmbassadorResponseBean responseBean = new AmbassadorResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.eq("ambassadorMobile", ambassadorMobileNumber));
			List<EngageSeller> sellersList = criteria.list();
			log.debug("Sellers Size : " + sellersList.size());
			if (sellersList.size() >= 2) {
				int counter = 0;
				for (EngageSeller seller : sellersList) {
					counter++;
				}
				responseBean.setAmbassadorMobileNumber(ambassadorMobileNumber);
				responseBean.setMerchantOnboardCount(counter);
				responseBean.setMessage("Success");
			} else if (sellersList.size() < 2 || sellersList.isEmpty()) {
				responseBean.setAmbassadorMobileNumber(ambassadorMobileNumber);
				responseBean.setMerchantOnboardCount(sellersList.size());
				responseBean.setMessage("NotEligible");
			} else {
				responseBean.setMessage("Failed");
			}
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting seller details by mobile failed");
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public String getSellerVersion() {
		log.debug("Getting Seller Version");
		String sellerVersion = "1.0.0";
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			SellerVersion version = (SellerVersion) session
					.get("com.limitless.services.engage.sellers.dao.SellerVersion", 1);
			if (version != null)
				sellerVersion = version.getVersion();
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			sellerVersion = "1.0.0";
			log.error("Getting seller version failed :" + e);

		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return sellerVersion;
	}

	public AliasCheckResponseBean getAliasNumber(String aliasNumber) {
		log.debug("Checking mobile alias");
		AliasCheckResponseBean responseBean = new AliasCheckResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.eq("mobileAlias", aliasNumber));
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Seller size : " + sellerList.size());
			if (sellerList.isEmpty()) {
				responseBean.setMessage("Success");
			} else if (sellerList.size() > 0) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Checking alias number failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public MerchantRequestCountBean getRequestCount() {
		log.debug("Getting request count");
		MerchantRequestCountBean countBean = new MerchantRequestCountBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.eq("citrusSellerId", 0));
			List<EngageSeller> sellerList = criteria.list();
			log.debug("seller list size : " + sellerList.size());
			if (sellerList.size() > 0) {
				countBean.setMessage("Success");
				countBean.setRequestCount(sellerList.size());
			} else if (sellerList.isEmpty()) {
				countBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting request count failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return countBean;
	}

	public NewMerchantsRequestBean getRequestList() {
		log.debug("Getting new request list");
		NewMerchantsRequestBean requestBean = new NewMerchantsRequestBean();
		List<MerchantRequestListBean> listBean = new ArrayList<MerchantRequestListBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.eq("citrusSellerId", 0));
			List<EngageSeller> sellerList = criteria.list();
			log.debug("seller list size : " + sellerList.size());
			if (sellerList.size() > 0) {
				for (EngageSeller seller : sellerList) {
					MerchantRequestListBean bean = new MerchantRequestListBean();
					bean.setSellerId(seller.getSellerId());
					bean.setSellerName(seller.getSellerName());
					bean.setSellerShopName(seller.getSellerShopName());
					bean.setSellerEmailId(seller.getSellerEmail99());
					bean.setSellerMobile(seller.getSellerMobileNumber());
					bean.setSellerIdType(seller.getSellerKycDocValue());
					bean.setSellerAddress1(seller.getSellerAddress());
					bean.setSellerAddress2(seller.getSellerAddress());
					bean.setSellerState("KA");
					bean.setSellerCity(seller.getSellerCity());
					bean.setSellerZip("560001");

					Criteria criteria2 = session.createCriteria(SellerTemp.class);
					criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
					List<SellerTemp> tempList = criteria2.list();
					log.debug("Seller Temp List size : " + sellerList.size());
					if (tempList.size() == 1) {
						for (SellerTemp temp : tempList) {
							bean.setSellerIdProof(temp.getSellerKycImage());
							bean.setSellerBankIfsc(temp.getSellerIfsc());
							bean.setSellerBankAccountNumber(temp.getSellerAccount());
							bean.setSellerBankProof(temp.getSellerBankProof());
						}
					}
					listBean.add(bean);
					bean = null;
				}
				requestBean.setRequestList(listBean);
				requestBean.setRequestCount(sellerList.size());
				requestBean.setMessage("Success");
			} else if (sellerList.isEmpty()) {
				requestBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting request count failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return requestBean;
	}

	public MerchantRequestListBean sellerRequest(int sellerId) {
		log.debug("getting seller");
		MerchantRequestListBean listBean = new MerchantRequestListBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (seller != null) {
				listBean.setSellerId(sellerId);
				listBean.setSellerName(seller.getSellerName());
				listBean.setSellerEmailId(seller.getSellerEmail99());
				listBean.setSellerMobile(seller.getSellerMobileNumber());
				listBean.setSellerAddress1(seller.getSellerAddress());
				listBean.setSellerAddress2(seller.getSellerAddress());
				listBean.setSellerCity(seller.getSellerCity());
				listBean.setSellerState("KA");
				listBean.setSellerZip("560001");
				Criteria criteria = session.createCriteria(SellerTemp.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<SellerTemp> tempList = criteria.list();
				if (tempList.size() == 1) {
					for (SellerTemp temp : tempList) {
						listBean.setSellerBankAccountNumber(temp.getSellerAccount());
						listBean.setSellerBankIfsc(temp.getSellerIfsc());
					}
				}
				listBean.setMessage("Success");
			} else if (seller == null) {
				listBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting request count failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public SellerUpdateResponseBean sellerActivate(SellerUpdateRequestBean requestBean) {
		log.debug("activating seller");
		SellerUpdateResponseBean responseBean = new SellerUpdateResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					requestBean.getSellerId());
			if (seller != null) {

				seller.setIsActive(1);
				session.update(seller);

				Criteria criteria = session.createCriteria(SellerPayamentsConfiguration.class);
				criteria.add(Restrictions.eq("sellerId", requestBean.getSellerId()));
				List<SellerPayamentsConfiguration> configList = criteria.list();
				log.debug("config list size : " + configList.size());
				if (configList.size() == 1) {
					for (SellerPayamentsConfiguration config : configList) {
						SellerPayamentsConfiguration spc = (SellerPayamentsConfiguration) session.get(
								"com.limitless.services.engage.dao.SellerPayamentsConfiguration", config.getSpcId());
						if (spc != null) {
							spc.setCitrusSellerId(requestBean.getCitrusSellerId());
							session.update(spc);
						}
					}
				}

				responseBean.setCitrusSellerId(requestBean.getCitrusSellerId());
				responseBean.setSellerId(requestBean.getSellerId());
				responseBean.setMessage("Success");
			} else if (seller == null) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting request count failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public SellerContactsResponseBean addSellerContacts(SellerContactsRequestBean requestBean) {
		log.debug("adding seller contacts");
		SellerContactsResponseBean responseBean = new SellerContactsResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			if (requestBean.getPhoneNumberList().size() > 0) {
				for (PhoneNumber number : requestBean.getPhoneNumberList()) {
					Criteria criteria = session.createCriteria(EngageCustomer.class);
					criteria.add(Restrictions.eq("customerMobileNumber", number.getPhoneNumber()));
					List<EngageCustomer> customerList = criteria.list();
					if (customerList.size() == 1) {
						for (EngageCustomer customer : customerList) {
							Criteria criteria2 = session.createCriteria(SellerCustomerMapper.class);
							Junction condition = Restrictions.conjunction()
									.add(Restrictions.eq("customerId", customer.getCustomerId()))
									.add(Restrictions.eq("sellerId", requestBean.getSellerId()));
							criteria2.add(condition);
							List<SellerCustomerMapper> mapperList = criteria2.list();
							log.debug("Mapper List size : " + mapperList.size());
							if (mapperList.isEmpty()) {
								SellerCustomerMapper mapper = new SellerCustomerMapper();
								mapper.setSellerId(requestBean.getSellerId());
								mapper.setCustomerId(customer.getCustomerId());

								session.persist(mapper);
								log.debug("SCM ID : " + mapper.getScmId());
							}
						}
					}
				}
				responseBean.setMessage("Success");
			} else if (requestBean.getPhoneNumberList().isEmpty()) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding seller contacts failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public CustomerNotifyResponseBean postToCircle(CustomerNotifyRequestBean requestBean) {
		log.debug("notifying circle customers");
		CustomerNotifyResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					requestBean.getSellerId());
			if (seller != null) {
				String sellerMobileNumber = seller.getSellerMobileNumber();

				CircleNotify notify = new CircleNotify();
				notify.setSellerId(requestBean.getSellerId());
				notify.setCustomerId(requestBean.getCustomerId());
				notify.setSellerMobile(sellerMobileNumber);
				notify.setTitle(requestBean.getTitle());
				notify.setBody(requestBean.getBody());
				notify.setImageUrl(requestBean.getImageUrl());
				notify.setStatus("NOTYET");
				notify.setPostType("CIRCLE");
				session.persist(notify);

				responseBean = new CustomerNotifyResponseBean();
				responseBean.setNotifyId(notify.getCircleNotifyId());
				responseBean.setMessage("Success");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding seller contacts failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public CustomerNotifyResponseBean postToAll(CustomerNotifyRequestBean requestBean) {
		log.debug("notify to all");
		CustomerNotifyResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					requestBean.getSellerId());
			if (seller != null) {
				String sellerMobileNumber = seller.getSellerMobileNumber();

				CircleNotify notify = new CircleNotify();
				notify.setSellerId(requestBean.getSellerId());
				notify.setCustomerId(requestBean.getCustomerId());
				notify.setSellerMobile(sellerMobileNumber);
				notify.setTitle(requestBean.getTitle());
				notify.setBody(requestBean.getBody());
				notify.setImageUrl(requestBean.getImageUrl());
				notify.setStatus("NOTYET");
				notify.setPostType("ALL");
				session.persist(notify);

				responseBean = new CustomerNotifyResponseBean();
				responseBean.setNotifyId(notify.getCircleNotifyId());
				responseBean.setMessage("Success");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding seller contacts failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public boolean authenticateMerchant(String sessionKey, int sessionId) {
		log.debug("authenticating customer");
		boolean isCustomerAuthorized = false;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			JSONObject sessionJson = new JSONObject(sessionKey);
			int sellerId = sessionJson.getInt("key");

			SessionKeys sessionKeys = (SessionKeys) session.get("com.limitless.services.engage.dao.SessionKeys",
					sessionId);
			if (sessionKeys != null) {
				if (sessionKeys.getKeyAlive() == 1 && sessionKeys.getUserId() == sellerId) {
					isCustomerAuthorized = true;
				} else {
					isCustomerAuthorized = false;
				}
			} else {
				isCustomerAuthorized = false;
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("authenticating custimer failed :" + e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return isCustomerAuthorized;
	}

	public MerchantDeviceIdResponseBean sellerDeviceIdUpadte(MerchantDeviceIdRequestBean requestBean) {
		log.debug("updating device id");
		MerchantDeviceIdResponseBean responseBean = new MerchantDeviceIdResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					requestBean.getSellerId());
			if (seller != null) {
				seller.setSellerDeviceId(requestBean.getDeviceId());
				session.update(seller);
				responseBean.setSellerId(requestBean.getSellerId());
				responseBean.setMessage("Success");

				Criteria criteria = session.createCriteria(SellerDeviceIdMapper.class);
				criteria.add(Restrictions.eq("sellerDeviceId", requestBean.getDeviceId()));
				List<SellerDeviceIdMapper> mapperList = criteria.list();
				log.debug("sdmMapperList size : " + mapperList.size());
				if (mapperList.size() > 0) {
					for (SellerDeviceIdMapper mapper : mapperList) {
						SellerDeviceIdMapper updateInstance = (SellerDeviceIdMapper) session
								.get("com.limitless.services.engage.dao.SellerDeviceIdMapper", mapper.getSdmId());
						if (updateInstance != null) {
							updateInstance.setDeviceActive(1);
							session.update(updateInstance);
						}
					}
				} else if (mapperList.isEmpty()) {
					SellerDeviceIdMapper insertInstance = new SellerDeviceIdMapper();
					insertInstance.setSellerId(requestBean.getSellerId());
					insertInstance.setSellerDeviceId(requestBean.getDeviceId());
					insertInstance.setDeviceActive(1);
					session.persist(insertInstance);
				}

			} else if (seller == null) {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding device id failed : ", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public MerchantLogoutResponseBean logoutSeller(MerchantLogoutRequestBean requestBean) {
		log.debug("deactivting session key");
		MerchantLogoutResponseBean responseBean = new MerchantLogoutResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			SessionKeys key = (SessionKeys) session.get("com.limitless.services.engage.dao.SessionKeys",
					requestBean.getSessionId());
			if (key != null) {
				key.setKeyAlive(0);
				session.update(key);
				responseBean.setSellerId(requestBean.getSellerId());
				responseBean.setMessage("Success");

				Criteria criteria = session.createCriteria(SellerDeviceIdMapper.class);
				criteria.add(Restrictions.eq("sellerDeviceId", requestBean.getSellerDeviceId()));
				List<SellerDeviceIdMapper> mapperList = criteria.list();
				log.debug("mapper size : " + mapperList.size());
				if (mapperList.size() > 0) {
					for (SellerDeviceIdMapper mapper : mapperList) {
						SellerDeviceIdMapper instance = (SellerDeviceIdMapper) session
								.get("com.limitless.services.engage.dao.SellerDeviceIdMapper", mapper.getSdmId());
						if (instance != null) {
							instance.setDeviceActive(0);
							session.update(instance);
						}
					}
				}

			} else {
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("deactivting session key failed :" + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public SubMerchantListResponseBean getSubMerchants(int sellerId) {
		log.debug("getting sub-merchants");
		SubMerchantListResponseBean listBean = new SubMerchantListResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (seller != null) {
				int citrusSellerId = 0;
				Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
				criteria2.add(Restrictions.eq("sellerId", sellerId));
				List<SellerPayamentsConfiguration> configList = criteria2.list();
				log.debug("config list size : " + configList.size());
				if (configList.size() == 1) {
					for (SellerPayamentsConfiguration config : configList) {
						citrusSellerId = config.getCitrusSellerId();
					}
				}

				Criteria criteria = session.createCriteria(EngageSeller.class);
				criteria.add(Restrictions.eq("citrusSellerId", citrusSellerId));
				List<EngageSeller> list = criteria.list();
				List<SubMerchantBean> subMerchants = new ArrayList<SubMerchantBean>();
				if (list.size() > 0) {
					for (EngageSeller listItem : list) {

						SubMerchantBean subMerchantBean = new SubMerchantBean();
						subMerchantBean.setId(listItem.getSellerId());
						subMerchantBean.setName(listItem.getSellerName());
						subMerchantBean.setShopName(listItem.getSellerShopName());
						subMerchants.add(subMerchantBean);
						subMerchantBean = null;
					}
				}
				listBean.setSubMerchants(subMerchants);
				listBean.setMessage("Success");
			} else if (seller == null) {
				listBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting request count failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public SellerContactsResponseBean addSearchMapper(int customerId, int sellerId) {
		log.debug("adding search mapper");
		SellerContactsResponseBean responseBean = new SellerContactsResponseBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(SellerCustomerMapper.class);
			Junction condition = Restrictions.conjunction().add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.eq("customerId", customerId));
			criteria.add(condition);
			List<SellerCustomerMapper> mapperList = criteria.list();
			log.debug("mapper size : " + mapperList.size());
			if (mapperList.isEmpty()) {
				SellerCustomerMapper mapper = new SellerCustomerMapper();
				mapper.setCustomerId(customerId);
				mapper.setSellerId(sellerId);
				mapper.setVisitCount(1);
				session.persist(mapper);
			} else if (mapperList.size() == 1) {
				for (SellerCustomerMapper mapper : mapperList) {
					SellerCustomerMapper instance = (SellerCustomerMapper) session
							.get("com.limitless.services.engage.dao.SellerCustomerMapper", mapper.getScmId());
					if (instance != null) {
						instance.setVisitCount(instance.getVisitCount() + 1);
						session.update(instance);
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding search mapper failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public CustomerNotifyUpdateResponseBean updateNotifyRequest(int notifyId, int statusCode) {
		log.debug("updating notify request");
		CustomerNotifyUpdateResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			CircleNotify notify = (CircleNotify) session.get("com.limitless.services.engage.dao.CircleNotify",
					notifyId);
			if (notify != null) {
				if (statusCode > 0) {
					if (statusCode == 2) {
						notify.setStatus("APPROVED");
						session.update(notify);

						if (notify.getPostType().equals("CIRCLE")) {
							Criteria criteria = session.createCriteria(SellerCustomerMapper.class);
							criteria.add(Restrictions.eq("sellerId", notify.getSellerId()));
							List<SellerCustomerMapper> mapperList = criteria.list();
							log.debug("mapper size : " + mapperList.size());
							if (mapperList.size() > 0) {
								for (SellerCustomerMapper mapper : mapperList) {
									EngageCustomer customer = (EngageCustomer) session.get(
											"com.limitless.services.engage.dao.EngageCustomer", mapper.getCustomerId());
									if (customer != null) {
										if (customer.getDeviceId() != null) {
											CustomerFcmRequestBean fcmBean = new CustomerFcmRequestBean();
											log.debug("Device ID : " + customer.getDeviceId());
											fcmBean.setTo(customer.getDeviceId());
											fcmBean.setPriority("high");
											CustomerDataBean dataBean = new CustomerDataBean();
											dataBean.setImageUrl(notify.getImageUrl());
											dataBean.setMerchantMobile(notify.getSellerMobile());
											dataBean.setTitle(notify.getTitle());
											dataBean.setBody(notify.getBody());
											fcmBean.setData(dataBean);
											log.debug("FCM Bean : " + fcmBean.toString());
											WebResource webResource2 = client
													.resource("https://fcm.googleapis.com/fcm/send");
											ClientResponse clientResponse = webResource2.type("application/json")
													.header("Authorization",
															"key=AIzaSyAP4xJ6VMm4vpj2A1ocGDvvvwzxtUNuKI0")
													.post(ClientResponse.class, fcmBean);
											System.out.println(clientResponse.getStatus());
											System.out.println(clientResponse.getEntity(String.class));
										}
									}
								}
							}
						} else if (notify.getPostType().equals("ALL")) {
							Criteria criteria2 = session.createCriteria(EngageCustomer.class);
							List<EngageCustomer> customerList = criteria2.list();
							if (customerList.size() > 0) {
								for (EngageCustomer customer : customerList) {
									if (customer.getDeviceId() != null) {
										CustomerFcmRequestBean fcmBean = new CustomerFcmRequestBean();
										log.debug("Device ID : " + customer.getDeviceId());
										fcmBean.setTo(customer.getDeviceId());
										fcmBean.setPriority("high");
										CustomerDataBean dataBean = new CustomerDataBean();
										dataBean.setImageUrl(notify.getImageUrl());
										dataBean.setMerchantMobile(notify.getSellerMobile());
										dataBean.setTitle(notify.getTitle());
										dataBean.setBody(notify.getBody());
										fcmBean.setData(dataBean);
										log.debug("FCM Bean : " + fcmBean.toString());
										WebResource webResource2 = client
												.resource("https://fcm.googleapis.com/fcm/send");
										ClientResponse clientResponse = webResource2.type("application/json")
												.header("Authorization", "key=AIzaSyAP4xJ6VMm4vpj2A1ocGDvvvwzxtUNuKI0")
												.post(ClientResponse.class, fcmBean);
										System.out.println(clientResponse.getStatus());
										System.out.println(clientResponse.getEntity(String.class));
									}
								}
							}
						}
						responseBean = new CustomerNotifyUpdateResponseBean();
						responseBean.setNotifyId(notifyId);
						responseBean.setMessage("Success");
					} else if (statusCode == 3) {
						notify.setStatus("CANCELLED");
						session.update(notify);
						responseBean = new CustomerNotifyUpdateResponseBean();
						responseBean.setNotifyId(notifyId);
						responseBean.setMessage("Success");
					}
				}
			} else {
				responseBean = new CustomerNotifyUpdateResponseBean();
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("updating notify request failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}

	public CustomerNotifyListBean getAllNotifyRequest() {
		log.debug("getting all notify request");
		CustomerNotifyListBean notifyBeanList = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(CircleNotify.class);
			Criterion statusNyCriterion = Restrictions.ne("status", "APPROVED");
			Criterion statusCnlCriterion = Restrictions.ne("status", "CANCELLED");
			LogicalExpression logExp = Restrictions.and(statusNyCriterion, statusCnlCriterion);
			criteria.add(logExp);
			List<CircleNotify> notifyList = criteria.list();
			log.debug("notify size : " + notifyList.size());
			if (notifyList.size() > 0) {
				List<CustomerNotifyBean> beanList = new ArrayList<CustomerNotifyBean>();
				for (CircleNotify notify : notifyList) {
					CustomerNotifyBean bean = new CustomerNotifyBean();
					bean.setNotifyId(notify.getCircleNotifyId());
					bean.setSellerId(notify.getSellerId());
					bean.setSellerMobileNumber(notify.getSellerMobile());
					EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
							notify.getSellerId());
					if (seller != null) {
						bean.setSellerName(seller.getSellerName());
					}
					bean.setTitle(notify.getTitle());
					bean.setBody(notify.getBody());
					bean.setImageUrl(notify.getImageUrl());
					bean.setPostType(notify.getPostType());
					bean.setNotifyCreatedTime(notify.getNotifyCreatedTime());
					bean.setStatus(notify.getStatus());

					beanList.add(bean);
					bean = null;
				}
				notifyBeanList = new CustomerNotifyListBean();
				notifyBeanList.setNotifyList(beanList);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting all notify request failed " + re);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return notifyBeanList;
	}

	public SellerRestaurantsBean getSellerRestaurants(int sellerId) {
		log.debug("getting restaurant");
		SellerRestaurantsBean restaurantsBean = new SellerRestaurantsBean();
		List<SellerRestaurantListBean> listBean = new ArrayList<SellerRestaurantListBean>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (seller != null) {
				restaurantsBean.setSellerId(sellerId);

				restaurantsBean.setSellerName(seller.getSellerName());
				restaurantsBean.setSellerCity(seller.getSellerCity());
				restaurantsBean.setSellerMobileNumber(seller.getSellerMobileNumber());
				restaurantsBean.setSellerEmailId(seller.getSellerEmail99());
				Criteria criteria = session.createCriteria(Restaurants.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<Restaurants> restaurantsList = criteria.list();
				log.debug("restaurant size : " + restaurantsList.size());
				if (restaurantsList.size() > 0) {
					for (Restaurants restaurant : restaurantsList) {
						SellerRestaurantListBean bean = new SellerRestaurantListBean();
						bean.setRestaurantId(restaurant.getRestaurantId());
						bean.setRestaurantName(restaurant.getRestaurantName());
						bean.setRestaurantCity(restaurant.getRestaurantCity());
						listBean.add(bean);
						bean = null;
					}
					restaurantsBean.setRestaurantList(listBean);
					restaurantsBean.setMessage("Success");
				} else if (restaurantsList.isEmpty()) {
					restaurantsBean.setMessage("Failed");
				}
				Criteria criteria2 = session.createCriteria(SellerPayamentsConfiguration.class);
				criteria2.add(Restrictions.eq("sellerId", seller.getSellerId()));
				List<SellerPayamentsConfiguration> configList = criteria2.list();
				log.debug("configs size : " + configList.size());
				if (configList.size() > 0) {
					for (SellerPayamentsConfiguration config : configList) {
						restaurantsBean.setCitrusSellerId(config.getCitrusSellerId());
						if (config.getPayOnDelivery() == 1) {
							restaurantsBean.setPodAvailable(true);
						} else if (config.getPayOnDelivery() == 0) {
							restaurantsBean.setPodAvailable(false);
						}
						restaurantsBean.setDeliveryMinAmount(config.getDevliveryMInAmt());
						restaurantsBean.setDeliveryFee(config.getDeliveryFee());
						restaurantsBean.setDeliveryRadius(config.getDeliveryRadius());
						if (config.getConvenienceFee() == 1) {
							restaurantsBean.setConvenienceFee(true);
						} else if (config.getConvenienceFee() == 0) {
							restaurantsBean.setConvenienceFee(false);
						}
					}
				}
			} else if (seller == null) {
				restaurantsBean.setMessage("Failed");
			}

		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting restaurant failed " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return restaurantsBean;
	}

	public CustomerNotifyListBean getSellerPostList(int sellerId) {
		log.debug("getting seller post list");
		CustomerNotifyListBean notifyListBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					sellerId);
			if (seller != null) {
				String sellerName = seller.getSellerName();

				Criteria criteria = session.createCriteria(CircleNotify.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<CircleNotify> notifyList = criteria.list();
				log.debug("notify size : " + notifyList.size());
				if (notifyList.size() > 0) {
					notifyListBean = new CustomerNotifyListBean();
					notifyListBean.setSellerId(sellerId);
					List<CustomerNotifyBean> beanList = new ArrayList<CustomerNotifyBean>();
					for (CircleNotify notify : notifyList) {
						CustomerNotifyBean bean = new CustomerNotifyBean();
						bean.setBody(notify.getBody());
						bean.setImageUrl(notify.getImageUrl());
						bean.setNotifyCreatedTime(notify.getNotifyCreatedTime());
						bean.setNotifyId(notify.getCircleNotifyId());
						bean.setPostType(notify.getPostType());
						bean.setSellerId(sellerId);
						bean.setSellerMobileNumber(notify.getSellerMobile());
						bean.setSellerName(sellerName);
						bean.setStatus(notify.getStatus());
						bean.setTitle(notify.getTitle());
						beanList.add(bean);
						bean = null;
					}
					notifyListBean.setNotifyList(beanList);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller post list failed " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return notifyListBean;
	}

	public List<String> getAllSellerDeviceId(int sellerId) {
		log.debug("getting seller deviceIds");
		List<String> deviceIdList = new ArrayList<String>();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(SellerDeviceIdMapper.class);
			Criterion sidCriterion = Restrictions.eq("sellerId", sellerId);
			Criterion daCRiterion = Restrictions.eq("deviceActive", 1);
			LogicalExpression logExp = Restrictions.and(sidCriterion, daCRiterion);
			criteria.add(logExp);
			List<SellerDeviceIdMapper> mapperList = criteria.list();
			log.debug("mapper size : " + mapperList.size());
			if (mapperList.size() > 0) {
				for (SellerDeviceIdMapper mapper : mapperList) {
					String deviceId = mapper.getSellerDeviceId();
					deviceIdList.add(deviceId);
					deviceId = null;
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting devicesid failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return deviceIdList;
	}

	public void sellerPaymentConfigAdd(SellerPayamentsConfiguration payConfig) {
		log.debug("adding configuration");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			session.persist(payConfig);
			log.debug("spc id : " + payConfig.getSpcId());

			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding configuration failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	public SellerAdsListBean getSellerAds() {
		log.debug("getting seller ads");
		SellerAdsListBean listBean = new SellerAdsListBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			List<SellerAdBean> adList = new ArrayList<SellerAdBean>();

			EngageSeller seller1 = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					5000149);
			if (seller1 != null) {
				SellerAdBean bean1 = new SellerAdBean();
				bean1.setSellerBannerUrl(
						"https://s3-us-west-2.amazonaws.com/limitlesscircle-images/ProductImages/s5000149/BannerAdJugniz.jpg");
				bean1.setSellerId(seller1.getSellerId());
				bean1.setSellerMobile(seller1.getSellerMobileNumber());
				bean1.setSellerName(seller1.getSellerName());
				bean1.setSellerShopName(seller1.getSellerShopName());
				adList.add(bean1);
			}
			EngageSeller seller2 = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
					5000145);
			if (seller2 != null) {
				SellerAdBean bean2 = new SellerAdBean();
				bean2.setSellerBannerUrl(
						"https://s3-us-west-2.amazonaws.com/limitlesscircle-images/ProductImages/s5000145/BannerAdSugarRush.jpg");
				bean2.setSellerId(seller2.getSellerId());
				bean2.setSellerMobile(seller2.getSellerMobileNumber());
				bean2.setSellerName(seller2.getSellerName());
				bean2.setSellerShopName(seller2.getSellerShopName());
				adList.add(bean2);
			}
			listBean.setAdBean(adList);
			listBean.setMessage("Success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller ads failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public SellerBusinessCategoryListBean getSellerBusinessCategoryList() {
		log.debug("getting seller business category");
		SellerBusinessCategoryListBean listBean = new SellerBusinessCategoryListBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.ne("businessCategory", ""))
					.setProjection(Projections.distinct(Projections.property("businessCategory")));
			criteria.addOrder(Order.asc("businessCategory"));
			List<String> categoryList = criteria.list();
			log.debug("category list : " + categoryList.size());
			listBean.setBusinessCategoryList(categoryList);
			listBean.setMessage("Success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller business category failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public SellerBusinessCategoryBean getBusinessCategorySellerList(String categoryName) {
		log.debug("getting seller list");
		SellerBusinessCategoryBean categoryBean = new SellerBusinessCategoryBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			List<SellerMinBean> sellerList = new ArrayList<SellerMinBean>();
			if (categoryName.equalsIgnoreCase("TV")) {
				Criteria criteria = session.createCriteria(EngageSeller.class);
				Junction condition = Restrictions.conjunction().add(Restrictions.eq("businessCategory", "TV"))
						.add(Restrictions.eq("isActive", 1)).add(Restrictions.eq("isDeleted", 0))
						.add(Restrictions.eq("businessCategory", "TV"));
				criteria.add(condition).addOrder(Order.asc("sellerShopName"));
				List<EngageSeller> sellersList = criteria.list();
				log.debug("seller list size : " + sellersList.size());
				if (sellersList.size() > 0) {
					categoryBean.setSellerBusinessCategory(categoryName);
					for (EngageSeller seller : sellersList) {
						SellerMinBean bean = new SellerMinBean();
						bean.setSellerId(seller.getSellerId());
						bean.setSellerName(seller.getSellerName());
						bean.setSellerShopName(seller.getSellerShopName());
						bean.setSellerMobileNumber(seller.getSellerMobileNumber());
						bean.setSellerCity(seller.getSellerCity());
						bean.setSellerBrandingUrl(seller.getBranding_url());
						bean.setSellerIconUrl(seller.getSellerIconURL());
						sellerList.add(bean);
						bean = null;
					}
					categoryBean.setSellerList(sellerList);
				}
			} else {
				Criteria criteria = session.createCriteria(EngageSeller.class);
				Junction condition = Restrictions.conjunction().add(Restrictions.eq("businessCategory", categoryName))
						.add(Restrictions.eq("isActive", 1)).add(Restrictions.eq("isDeleted", 0))
						.add(Restrictions.eq("sellerRole", "admin")).add(Restrictions.eq("ecomPayment", 1));
				criteria.add(condition);
				criteria.addOrder(Order.asc("sellerShopName"));
				criteria.setMaxResults(10);
				List<EngageSeller> sellersList = criteria.list();
				log.debug("seller list size : " + sellersList.size());
				if (sellersList.size() > 0) {
					categoryBean.setSellerBusinessCategory(categoryName);
					for (EngageSeller seller : sellersList) {
						SellerMinBean bean = new SellerMinBean();
						bean.setSellerId(seller.getSellerId());
						bean.setSellerName(seller.getSellerName());
						bean.setSellerShopName(seller.getSellerShopName());
						bean.setSellerMobileNumber(seller.getSellerMobileNumber());
						bean.setSellerCity(seller.getSellerCity());
						bean.setSellerBrandingUrl(seller.getBranding_url());
						bean.setSellerIconUrl(seller.getSellerIconURL());
						sellerList.add(bean);
						bean = null;
					}
					categoryBean.setSellerList(sellerList);
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return categoryBean;
	}

	public SellerBusinessCategoryBean getSellerBusinessCategoryPagination(String categoryName, int count) {
		log.debug("getting seller list");
		SellerBusinessCategoryBean categoryBean = new SellerBusinessCategoryBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			List<SellerMinBean> sellerList = new ArrayList<SellerMinBean>();
			Criteria criteria = session.createCriteria(EngageSeller.class);
			Junction condition = Restrictions.conjunction().add(Restrictions.eq("businessCategory", categoryName))
					.add(Restrictions.eq("isActive", 1)).add(Restrictions.eq("isDeleted", 0))
					.add(Restrictions.eq("sellerRole", "admin")).add(Restrictions.eq("ecomPayment", 1));
			criteria.add(condition);
			criteria.addOrder(Order.asc("sellerShopName"));
			criteria.setFirstResult(count);
			criteria.setMaxResults(10);
			List<EngageSeller> sellersList = criteria.list();
			log.debug("seller list size : " + sellersList.size());
			if (sellersList.size() > 0) {
				categoryBean.setSellerBusinessCategory(categoryName);
				for (EngageSeller seller : sellersList) {
					SellerMinBean bean = new SellerMinBean();
					bean.setSellerId(seller.getSellerId());
					bean.setSellerName(seller.getSellerName());
					bean.setSellerShopName(seller.getSellerShopName());
					bean.setSellerMobileNumber(seller.getSellerMobileNumber());
					bean.setSellerCity(seller.getSellerCity());
					bean.setSellerBrandingUrl(seller.getBranding_url());
					bean.setSellerIconUrl(seller.getSellerIconURL());
					sellerList.add(bean);
					bean = null;
				}
				categoryBean.setSellerList(sellerList);
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return categoryBean;
	}

	public List<SellerBusinessCategoryBean> getSellerCategoryList() {
		log.debug("getting seller business list");
		List<SellerBusinessCategoryBean> sellerCategoryList = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.ne("businessCategory", ""))
					.setProjection(Projections.distinct(Projections.property("businessCategory")));
			criteria.addOrder(Order.asc("businessCategory"));
			List<String> categoryList = criteria.list();
			log.debug("category list : " + categoryList.size());
			if (categoryList.size() > 0) {
				sellerCategoryList = new ArrayList<SellerBusinessCategoryBean>();
				for (String category : categoryList) {
					log.debug("category name : " + category);
					List<SellerMinBean> beanList = new ArrayList<SellerMinBean>();
					Criteria criteria2 = session.createCriteria(EngageSeller.class);
					Junction condition = Restrictions.conjunction().add(Restrictions.eq("businessCategory", category))
							.add(Restrictions.eq("isActive", 1)).add(Restrictions.eq("isDeleted", 0))
							.add(Restrictions.eq("sellerRole", "admin")).add(Restrictions.eq("ecomPayment", 1));
					criteria2.add(condition);
					criteria2.setMaxResults(10);
					List<EngageSeller> sellerList = criteria2.list();
					log.debug("seller size : " + sellerList.size());
					if (sellerList.size() > 0) {
						SellerBusinessCategoryBean categoryBean = new SellerBusinessCategoryBean();
						categoryBean.setSellerBusinessCategory(category);
						for (EngageSeller seller : sellerList) {
							SellerMinBean bean = new SellerMinBean();
							bean.setSellerId(seller.getSellerId());
							bean.setSellerName(seller.getSellerName());
							bean.setSellerShopName(seller.getSellerShopName());
							bean.setSellerMobileNumber(seller.getSellerMobileNumber());
							bean.setSellerCity(seller.getSellerCity());
							bean.setSellerBrandingUrl(seller.getBranding_url());
							bean.setSellerIconUrl(seller.getSellerIconURL());
							bean.setSellerTags(seller.getTag());
							beanList.add(bean);
							bean = null;
						}
						categoryBean.setSellerList(beanList);
						sellerCategoryList.add(categoryBean);
						categoryBean = null;
					}
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return sellerCategoryList;
	}

	public SellerBrandPromotionListBean getSellerBrandPromotionsList() {
		log.debug("getting promotion list");
		SellerBrandPromotionListBean listBean = new SellerBrandPromotionListBean();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(SellerBrandPromotion.class);
			criteria.add(Restrictions.eq("isActive", true));
			List<SellerBrandPromotion> promotionList = criteria.list();
			log.debug("promo size : " + promotionList.size());
			if (promotionList.size() > 0) {
				List<SellerBrandPromotionBean> beanList = new ArrayList<SellerBrandPromotionBean>();
				for (SellerBrandPromotion promo : promotionList) {
					SellerBrandPromotionBean bean = new SellerBrandPromotionBean();
					bean.setSbpId(promo.getSbpId());
					bean.setSellerId(promo.getSellerId());
					bean.setSellerBrandingUrl(promo.getAdUrl());
					EngageSeller seller = (EngageSeller) session.get("com.limitless.services.engage.dao.EngageSeller",
							promo.getSellerId());
					if (seller != null) {
						bean.setSellerName(seller.getSellerName());
						bean.setSellerShopName(seller.getSellerShopName());
						bean.setSellerMobileNumber(seller.getSellerMobileNumber());
						if (bean.getSellerBrandingUrl() == null || bean.getSellerBrandingUrl().equalsIgnoreCase("")) {
							bean.setSellerBrandingUrl(seller.getBranding_url());
						}
					}

					beanList.add(bean);
					bean = null;
				}
				listBean.setPromotionList(beanList);
				listBean.setMessage("Success");
			} else if (promotionList.isEmpty()) {
				listBean.setMessage("Failed");
			}
			transaction.commit();
		} catch (

		RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting promotion list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return listBean;
	}

	public SellerPayamentsConfiguration getSellerPaymentConfig(int sellerId) {
		log.debug("getting seller payment config");
		SellerPayamentsConfiguration config = new SellerPayamentsConfiguration();
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(SellerPayamentsConfiguration.class);
			criteria.add(Restrictions.eq("sellerId", sellerId));
			List<SellerPayamentsConfiguration> configList = criteria.list();
			log.debug("config list size : " + configList.size());
			if (configList.size() == 1) {
				config = configList.get(0);
				System.out.println("config : " + config.getCitrusSellerId());
			}
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting promotion list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return config;
	}

	public List<SellerBusinessCategoryBean> getSellerCategoryWithLocation(CustomerCoordsBean coordsBean)
			throws Exception {
		log.debug("getting seller category");
		List<SellerBusinessCategoryBean> sellerCategoryList = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();

			Criteria criteria = session.createCriteria(EngageSeller.class);
			criteria.add(Restrictions.ne("businessCategory", ""))
					.setProjection(Projections.distinct(Projections.property("businessCategory")));
			criteria.addOrder(Order.asc("businessCategory"));
			List<String> categoryList = criteria.list();
			log.debug("category list : " + categoryList.size());
			if (categoryList.size() > 0) {
				sellerCategoryList = new ArrayList<SellerBusinessCategoryBean>();
				for (String category : categoryList) {
					List<SellerMinBean> beanList = new ArrayList<SellerMinBean>();
					Class.forName("com.mysql.jdbc.Driver");
					Connection connection = DriverManager.getConnection(
							"jdbc:mysql://limitless-engage.cchjaguu68a3.us-west-2.rds.amazonaws.com:3306/llcdb?autoReconnect=true&useSSL=false",
							"root", "pmt11cd3");
					Statement statement = connection.createStatement();
					ResultSet resultSet = statement
							.executeQuery("SELECT *,ACOS( SIN( RADIANS( `seller_location_latitude` ) ) * SIN( RADIANS( "
									+ coordsBean.getLatitude()
									+ " ) ) + COS( RADIANS( `seller_location_latitude` ) )* COS( RADIANS( "
									+ coordsBean.getLatitude()
									+ " )) * COS( RADIANS( `seller_location_longitude` ) - RADIANS( "
									+ coordsBean.getLongitude()
									+ " )) ) * 6380 AS `distance` FROM llcdb.engage_seller WHERE ACOS( SIN( RADIANS( `seller_location_latitude` ) ) * SIN( RADIANS( "
									+ coordsBean.getLatitude()
									+ " ) ) + COS( RADIANS( `seller_location_latitude` ) ) * COS( RADIANS( "
									+ coordsBean.getLatitude()
									+ " )) * COS( RADIANS( `seller_location_longitude` ) - RADIANS( "
									+ coordsBean.getLongitude() + " )) ) * 6380 < " + coordsBean.getRadius()
									+ " and business_category='" + category
									+ "' and isActive=1 and is_deleted=0 and ecom_payment=1 ORDER BY `distance` limit 10;");
					SellerBusinessCategoryBean categoryBean = new SellerBusinessCategoryBean();
					categoryBean.setSellerBusinessCategory(category);
					while (resultSet.next()) {
						SellerMinBean bean = new SellerMinBean();
						bean.setSellerId(resultSet.getInt("seller_id"));
						bean.setSellerName(resultSet.getString("seller_name"));
						bean.setSellerShopName(resultSet.getString("seller_shop_name"));
						bean.setSellerMobileNumber(resultSet.getString("seller_mobile_number"));
						bean.setSellerCity(resultSet.getString("seller_city"));
						bean.setSellerBrandingUrl(resultSet.getString("branding_url"));
						bean.setSellerIconUrl(resultSet.getString("seller_icon_url"));
						bean.setSellerTags(resultSet.getString("tag"));
						beanList.add(bean);
						bean = null;
					}
					if (beanList != null && beanList.size() > 0) {
						categoryBean.setSellerList(beanList);
						sellerCategoryList.add(categoryBean);
					}
					categoryBean = null;
					connection.close();
				}
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting seller list failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return sellerCategoryList;
	}
	
	public NewPromoCodeResponseBean addNewPromoCode(NewPromoCodeRequestBean requestBean){
		log.debug("adding new promo code");
		NewPromoCodeResponseBean responseBean = new NewPromoCodeResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SellerPromoCode promoCode = new SellerPromoCode();
			promoCode.setSellerId(requestBean.getSellerId());
			promoCode.setPromoCode(requestBean.getPromoCode());
			promoCode.setRate(requestBean.getRate());
			promoCode.setRateType(requestBean.getRateType());
			promoCode.setExpiryDate(requestBean.getExpiryDate());
			promoCode.setActive(true);
			session.persist(promoCode);
			log.debug("promocode id : " + promoCode.getPromoCodeId());
			responseBean.setPromoCodeId(promoCode.getPromoCodeId());
			responseBean.setSellerId(promoCode.getSellerId());
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding new promo code failed : " + re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public PromoCodeResponseBean getPromoCode(PromoCodeRequestBean requestBean){
		log.debug("getting promo code");
		PromoCodeResponseBean responseBean = new PromoCodeResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", requestBean.getSellerId());
			if(seller!=null){
				Criteria criteria = session.createCriteria(SellerPromoCode.class);
				Junction condition = Restrictions.conjunction()
						.add(Restrictions.eq("promoCode", requestBean.getPromoCode()))
						.add(Restrictions.eq("sellerId", requestBean.getSellerId()))
						.add(Restrictions.eq("isActive", true))
						.add(Restrictions.eq("customerId", requestBean.getCustomerId()));
				criteria.add(condition);
				List<SellerPromoCode> promoCodeList = criteria.list();
				log.debug("promocode list size : " + promoCodeList.size());
				if(promoCodeList.size()==1){
					for(SellerPromoCode promoCode : promoCodeList){
						responseBean.setMessage("Success");
						responseBean.setPromoCodeId(promoCode.getPromoCodeId());
						responseBean.setSellerId(promoCode.getSellerId());
						responseBean.setPromoCode(promoCode.getPromoCode());
						responseBean.setRate(promoCode.getRate());
						responseBean.setRateType(promoCode.getRateType());
						responseBean.setExpiryDate(promoCode.getExpiryDate());
						responseBean.setActive(promoCode.isActive());
					}
				}
				else{
					responseBean.setMessage("Invalid Promo Code");
				}
			}
			else{
				responseBean.setMessage("Invalid Promo Code");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("adding new promo code failed : " + re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
	
	public void deactivatePromoCodes() throws Exception{
		log.debug("deactivating promo codes");
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(SellerPromoCode.class);
			criteria.add(Restrictions.eq("isActive", true));
			List<SellerPromoCode> promoCodeList = criteria.list();
			log.debug("promo code list size : " + promoCodeList.size());
			if(promoCodeList.size()>0){
				for(SellerPromoCode promoCode : promoCodeList){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date expiryDate = sdf.parse(promoCode.getExpiryDate());
					Date todayDate = new Date();
					if(expiryDate.before(todayDate)){
						SellerPromoCode code = (SellerPromoCode) session
								.get("com.limitless.services.engage.dao.SellerPromoCode", promoCode.getPromoCodeId());
						if(code!=null){
							code.setActive(false);
							session.update(code);
						}
					}
				}
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("deactivating promo codes failed : " + re);
			throw re;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	/*
	 * public static void main(String[] args) { EngageSellerManager manager =
	 * new EngageSellerManager(); manager.findById(325); }
	 */
}

package com.limitless.services.engage.dao;

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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;

import com.limitless.services.engage.AliasCheckResponseBean;
import com.limitless.services.engage.AmbassadorResponseBean;
import com.limitless.services.engage.CoordinatesResponseBean;
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
import com.limitless.services.engage.PhoneNumber;
import com.limitless.services.engage.SellerContactsRequestBean;
import com.limitless.services.engage.SellerContactsResponseBean;
import com.limitless.services.engage.SellerLoginRequestBean;
import com.limitless.services.engage.SellerLoginResponseBean;
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
					respBean.setCitrusSellerId(seller.getCitrusSellerId());
					respBean.setSellerType(seller.getSellerType());
					respBean.setSellerRole(seller.getSellerRole());
					respBean.setBusinessType(seller.getBusinessType());
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
				bean.setLatitude(seller.getSellerLocationLatitude());
				bean.setLongitude(seller.getSellerLocationLongitude());
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
					responseBean.setCitrusSellerId(seller.getCitrusSellerId());
					responseBean.setSellerName(seller.getSellerShopName());
					responseBean.setSellerType(seller.getSellerType());
					responseBean.setBrandingUrl(seller.getBranding_url());
					responseBean.setBusinessType(seller.getBusinessType());
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

	public SellerLoginResponseBean getSellerBySearchString(String searchString) {
		log.debug("Getting seller details by mobile");
		SellerLoginResponseBean responseBean = new SellerLoginResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(EngageSeller.class);
			Junction condition1 = Restrictions.disjunction().add(Restrictions.eq("sellerMobileNumber", searchString))
					.add(Restrictions.eq("mobileAlias", searchString)).add(Restrictions.eq("sellerName", searchString))
					.add(Restrictions.eq("sellerShopName", searchString))
					.add(Restrictions.eq("sellerEmail99", searchString))
					.add(Restrictions.like("tag", searchString));
			Junction condition2 = Restrictions.conjunction().add(condition1).add(Restrictions.ne("isDeleted", 1));
			criteria.add(condition2);
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Size : " + sellerList.size());
			if (sellerList.size() >= 1) {
				EngageSeller seller = sellerList.get(0);
				{
					responseBean.setSellerId(seller.getSellerId());
					responseBean.setCitrusSellerId(seller.getCitrusSellerId());
					responseBean.setSellerName(seller.getSellerShopName());
					responseBean.setSellerType(seller.getSellerType());
					responseBean.setBrandingUrl(seller.getBranding_url());
					responseBean.setBusinessType(seller.getBusinessType());
					responseBean.setMobileNumber(seller.getSellerMobileNumber());
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
				seller.setCitrusSellerId(requestBean.getCitrusSellerId());
				seller.setIsActive(1);
				session.update(seller);
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
				int citrusSellerId = seller.getCitrusSellerId();

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
				session.persist(mapper);
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
			Criterion statusNyCriterion = Restrictions.ne("status", "NOTYET");
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
	
	public SellerRestaurantsBean getSellerRestaurants(int sellerId){
		log.debug("getting restaurant");
		SellerRestaurantsBean restaurantsBean = new SellerRestaurantsBean();
		List<SellerRestaurantListBean> listBean = new ArrayList<SellerRestaurantListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
			if(seller!=null){
				restaurantsBean.setSellerId(sellerId);
				restaurantsBean.setCitrusSellerId(seller.getCitrusSellerId());
				restaurantsBean.setSellerName(seller.getSellerName());
				restaurantsBean.setSellerCity(seller.getSellerCity());
				restaurantsBean.setSellerMobileNumber(seller.getSellerMobileNumber());
				restaurantsBean.setSellerEmailId(seller.getSellerEmail99());
				Criteria criteria = session.createCriteria(Restaurants.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<Restaurants> restaurantsList = criteria.list();
				log.debug("restaurant size : " + restaurantsList.size());
				if(restaurantsList.size()>0){
					for(Restaurants restaurant : restaurantsList){
						SellerRestaurantListBean bean = new SellerRestaurantListBean();
						bean.setRestaurantId(restaurant.getRestaurantId());
						bean.setRestaurantName(restaurant.getRestaurantName());
						bean.setRestaurantCity(restaurant.getRestaurantCity());
						listBean.add(bean);
						bean = null;
					}
					restaurantsBean.setRestaurantList(listBean);
					restaurantsBean.setMessage("Success");
				}
				else if(restaurantsList.isEmpty()){
					restaurantsBean.setMessage("Failed");
				}
			}
			else if(seller==null){
				restaurantsBean.setMessage("Failed");
			}
			
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return restaurantsBean;
	}

	/*
	 * public static void main(String[] args) { EngageSellerManager manager =
	 * new EngageSellerManager(); manager.findById(325); }
	 */
}

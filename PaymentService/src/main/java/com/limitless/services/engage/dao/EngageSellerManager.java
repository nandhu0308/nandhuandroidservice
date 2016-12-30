package com.limitless.services.engage.dao;

import java.util.ArrayList;

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

import com.limitless.services.engage.AmbassadorResponseBean;
import com.limitless.services.engage.CoordinatesResponseBean;
import com.limitless.services.engage.SellerLoginRequestBean;
import com.limitless.services.engage.SellerLoginResponseBean;
import com.limitless.services.engage.SellerPasswdRequestBean;
import com.limitless.services.engage.SellerPasswdResponseBean;
import com.limitless.services.engage.sellers.dao.SellerVersion;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class EngageSeller.
 * 
 * @see com.limitless.EngageSeller
 * @author Hibernate Tools
 */
public class EngageSellerManager {

	private static final Log log = LogFactory.getLog(EngageSellerManager.class);

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

	public SellerLoginResponseBean sellerLogin(SellerLoginRequestBean reqBean) {
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
					respBean.setMessage("Success");
					respBean.setStatus(1);
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
			criteria.add(Restrictions.eq("sellerMobileNumber", sellerMobileNumber));
			List<EngageSeller> sellerList = criteria.list();
			log.debug("Size : " + sellerList.size());
			if (sellerList.size() > 0) {
				for (EngageSeller seller : sellerList) {
					responseBean.setSellerId(seller.getSellerId());
					responseBean.setCitrusSellerId(seller.getCitrusSellerId());
					responseBean.setSellerName(seller.getSellerName());
					responseBean.setSellerType(seller.getSellerType());
					responseBean.setMessage("Success");
				}
			} else if (sellerList.isEmpty()) {
				Criteria criteria2 = session.createCriteria(EngageCustomer.class);
				criteria2.add(Restrictions.eq("customerMobileNumber", sellerMobileNumber));
				List<EngageCustomer> customerList = criteria2.list();
				log.debug("Size : " + customerList.size());
				if (customerList.size() == 1) {
					for (EngageCustomer customer : customerList) {
						if (customer.getCitrusSellerId() == 0) {
							responseBean.setMessage("Mobile Number Not Registered For Funds Transfer");
						} else {
							responseBean.setCitrusSellerId(customer.getCitrusSellerId());
							responseBean.setSellerName(customer.getCustomerName());
							responseBean.setSellerId(customer.getCustomerId());
							responseBean.setMobileNumber(customer.getCustomerMobileNumber());
							responseBean.setMessage("Success");
						}
					}
				} else {
					responseBean.setMessage("Mobile Number Not Registered");
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

	/*
	 * public static void main(String[] args) { EngageSellerManager manager =
	 * new EngageSellerManager(); manager.findById(325); }
	 */
}

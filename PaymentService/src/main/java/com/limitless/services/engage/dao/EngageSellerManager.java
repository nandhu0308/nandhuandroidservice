package com.limitless.services.engage.dao;

// Generated Oct 14, 2016 12:16:27 AM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class EngageSeller.
 * @see com.limitless.EngageSeller
 * @author Hibernate Tools
 */
public class EngageSellerManager {

	private static final Log log = LogFactory.getLog(EngageSellerManager.class);

	/*private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}*/
	
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(EngageSeller transientInstance) {
		log.debug("persisting EngageSeller instance");
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally {
			tx.commit();
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
			EngageSeller result = (EngageSeller) sessionFactory
					.getCurrentSession().merge(detachedInstance);
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
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			EngageSeller instance = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EngageSeller instance) {
		log.debug("finding EngageSeller instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.limitless.EngageSeller")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public boolean checkDuplicateEmail(String sellerEmail){
		 log.debug("Checking Email whether already exist");
		 Transaction transaction = null;
		 try{
			 boolean emailExist = false;
			 Session session = sessionFactory.getCurrentSession();
			 transaction = session.beginTransaction();
			 Query query = session.createQuery("from EngageSeller where sellerEmail99 = :sellerEmail");
			 query.setParameter("sellerEmail", sellerEmail);
			 List<EngageSeller> seller = query.list();
			 if(seller != null && seller.size() > 0){
				 emailExist = true;
			 }
			 return emailExist;
		 }
		 catch(RuntimeException re){
			 log.error("Checking Email failed");
			 throw re;
		 }
		 finally{
			 transaction.commit();
		 }
	}
	
	public boolean checkDuplicateMobile(String sellerMobile){
		log.debug("Checking Mobile whether already exist");
		Transaction transaction = null;
		try{
			boolean mobileExist = false;
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from EngageSeller where sellerMobileNumber = :sellerMobile");
			query.setParameter("sellerMobile", sellerMobile);
			List<EngageSeller> seller = query.list();
			if(seller != null && seller.size() > 0){
				mobileExist = true;
			}
			return mobileExist;
		}
		catch(RuntimeException re){
			log.error("Checking mobile failed");
			throw re;
		}
		finally{
			 transaction.commit();
		 }
	}
	
	/*public static void main(String[] args) {
		EngageSellerManager manager = new EngageSellerManager();
		manager.findById(325);
	}*/
}

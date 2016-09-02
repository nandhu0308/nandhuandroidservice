package com.limitless.services.payment.PaymentService.dao;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class PaymentTxn.
 * @see com.limitless.services.payment.PaymentService.dao.PaymentTxn
 * @author Hibernate Tools
 */
public class PaymentTxnManager {

	private static final Log log = LogFactory.getLog(PaymentTxnManager.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	/*protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}*/

	public void persist(PaymentTxn transientInstance) {
		log.debug("persisting PaymentTxn instance");
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}

/*	public void attachDirty(PaymentTxn instance) {
		log.debug("attaching dirty PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentTxn instance) {
		log.debug("attaching clean PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}*/

	public void delete(PaymentTxn persistentInstance) {
		log.debug("deleting PaymentTxn instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentTxn merge(PaymentTxn detachedInstance) {
		log.debug("merging PaymentTxn instance");
		try {
			PaymentTxn result = (PaymentTxn) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}
	
	public PaymentTxn updateSplitId(int txnId, int citrusMpTxnId, int splitId, String txnStatus) {
		Transaction tx = null;
		try {
			
			//TODO
			
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			instance.setCitrusMpTxnId(citrusMpTxnId);
			instance.setSplitId(splitId);
			instance.setTxnStatus(txnStatus);
			
			session.update(instance);
			
			PaymentTxn paymentTxn = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			return paymentTxn;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		} finally {
			tx.commit();
		}
	}
	
	public PaymentTxn updateTxn(int txnId, String txnStatus) {
		Transaction tx = null;
		try {
			
			//TODO
			
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			instance.setTxnStatus(txnStatus);
			
			session.update(instance);
			
			PaymentTxn paymentTxn = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
					txnId);
			
			return paymentTxn;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		} finally {
			tx.commit();
		}
	}

	public PaymentTxn findById(int id) {
		log.debug("getting PaymentTxn instance with id: " + id);
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			PaymentTxn instance = (PaymentTxn)session.get("com.limitless.services.payment.PaymentService.dao.PaymentTxn",
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
		} finally{
			tx.commit();
		}
	}

	public List findByExample(PaymentTxn instance) {
		log.debug("finding PaymentTxn instance by example");
		try {
			List results = sessionFactory
					.getCurrentSession()
					.createCriteria(
							"com.limitless.services.payment.PaymentService.dao.PaymentTxn")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	
	public static void main(String[] args) {
		PaymentTxnManager manager = new PaymentTxnManager();
		//manager.updateSplitId(946706, 28698, 31747, TxnStatus.PAYMENT_SUCCESSFUL.toString());
		manager.findById(946707);
	}
}

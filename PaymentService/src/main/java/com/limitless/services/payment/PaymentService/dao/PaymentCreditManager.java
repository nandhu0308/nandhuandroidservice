package com.limitless.services.payment.PaymentService.dao;

// Generated Oct 19, 2016 7:49:46 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class PaymentCredit.
 * @see com.limitless.PaymentCredit
 * @author Hibernate Tools
 */
public class PaymentCreditManager {

	private static final Log log = LogFactory.getLog(PaymentCreditManager.class);

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

	public void persist(PaymentCredit transientInstance) {
		log.debug("persisting PaymentCredit instance");
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

	public void attachDirty(PaymentCredit instance) {
		log.debug("attaching dirty PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentCredit instance) {
		log.debug("attaching clean PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(PaymentCredit persistentInstance) {
		log.debug("deleting PaymentCredit instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentCredit merge(PaymentCredit detachedInstance) {
		log.debug("merging PaymentCredit instance");
		try {
			PaymentCredit result = (PaymentCredit) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public PaymentCredit findById(java.lang.Integer id) {
		log.debug("getting PaymentCredit instance with id: " + id);
		try {
			PaymentCredit instance = (PaymentCredit) sessionFactory
					.getCurrentSession().get("com.limitless.PaymentCredit", id);
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

	public List findByExample(PaymentCredit instance) {
		log.debug("finding PaymentCredit instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.limitless.PaymentCredit")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}

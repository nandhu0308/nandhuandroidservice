package com.limitless.services.engage.upi.dao;

// Generated Oct 24, 2016 8:29:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;

import com.limitless.services.payment.PaymentService.util.HibernateUtil;

/**
 * Home object for domain model class UpiOrder.
 * @see com.limitless.UpiOrder
 * @author Hibernate Tools
 */
public class UpiOrderManager {

	private static final Log log = LogFactory.getLog(UpiOrderManager.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public void persist(UpiOrder transientInstance) {
		log.debug("persisting UpiOrder instance");
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}

	public void attachDirty(UpiOrder instance) {
		log.debug("attaching dirty UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UpiOrder instance) {
		log.debug("attaching clean UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(UpiOrder persistentInstance) {
		log.debug("deleting UpiOrder instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UpiOrder merge(UpiOrder detachedInstance) {
		log.debug("merging UpiOrder instance");
		try {
			UpiOrder result = (UpiOrder) sessionFactory.getCurrentSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public UpiOrder findById(java.lang.Integer id) {
		log.debug("getting UpiOrder instance with id: " + id);
		Transaction tx = null;
		try {
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			UpiOrder instance = (UpiOrder)session.get("com.limitless.services.engage.upi.dao.UpiOrder", id);
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

	public List findByExample(UpiOrder instance) {
		log.debug("finding UpiOrder instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("com.limitless.UpiOrder")
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

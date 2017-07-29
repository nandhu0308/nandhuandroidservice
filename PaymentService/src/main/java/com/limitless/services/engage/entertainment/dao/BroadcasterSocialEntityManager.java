package com.limitless.services.engage.entertainment.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.payment.PaymentService.dao.PaymentTxn;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.payment.PaymentService.util.RestClientUtil;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.SocialEntityResultBean;
import com.limitless.services.socialentity.dao.EntityFollow;
import com.limitless.services.socialentity.dao.EntityLike;
import com.limitless.services.socialentity.dao.EntityShare;
import com.limitless.services.socialentity.dao.EntityViewers;
import com.sun.jersey.api.client.Client;

public class BroadcasterSocialEntityManager {
	private static final Log log = LogFactory.getLog(BroadcasterManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public SocialEntityResultBean like(SocialEntityRequestBean requestBean) {
		SocialEntityResultBean response = null;
		log.debug("Like");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EntityLike like = null;
			Criteria criteria = session.createCriteria(EntityLike.class);
			criteria.add(Restrictions.eq("customerId", requestBean.getCustomerId()));
			criteria.add(Restrictions.eq("entityId", requestBean.getEntityId()));
			criteria.add(Restrictions.eq("entityType", requestBean.getEntityType()));
			List<EntityLike> list = criteria.list();
			if (list != null && list.size() > 0)
				like = list.get(0);
			if (like == null)
				like = new EntityLike();
			like.setEntityId(requestBean.getEntityId());
			like.setCustomerId(requestBean.getCustomerId());
			like.setLiked(requestBean.isAction());
			like.setEntityType(requestBean.getEntityType());			
			session.saveOrUpdate(like);
			response = new SocialEntityResultBean();
			response.setMessage("success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Like failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

	public SocialEntityResultBean share(SocialEntityRequestBean requestBean) {
		SocialEntityResultBean response = null;
		log.debug("Share");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EntityShare share = new EntityShare();
			share.setEntityId(requestBean.getEntityId());
			share.setCustomerId(requestBean.getCustomerId());
			share.setEntityType(requestBean.getEntityType());
			share.setCustomerLoggedIn(requestBean.getIsLoggedIn());
			session.save(share);
			response = new SocialEntityResultBean();
			response.setMessage("success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Share failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

	public SocialEntityResultBean follow(SocialEntityRequestBean requestBean) {
		SocialEntityResultBean response = null;
		log.debug("Follow");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EntityFollow follow = null;
			Criteria criteria = session.createCriteria(EntityFollow.class);
			criteria.add(Restrictions.eq("customerId", requestBean.getCustomerId()));
			criteria.add(Restrictions.eq("entityId", requestBean.getEntityId()));
			criteria.add(Restrictions.eq("entityType", requestBean.getEntityType()));
			List<EntityFollow> list = criteria.list();
			if (list != null && list.size() > 0)
				follow = list.get(0);
			if (follow == null)
				follow = new EntityFollow();
			follow.setEntityId(requestBean.getEntityId());
			follow.setCustomerId(requestBean.getCustomerId());
			follow.setFollowed(requestBean.isAction());
			follow.setEntityType(requestBean.getEntityType());
			follow.setCustomerLoggedIn(requestBean.getIsLoggedIn());
			session.saveOrUpdate(follow);
			response = new SocialEntityResultBean();
			response.setMessage("success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Follow failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

	public SocialEntityResultBean processView(SocialEntityRequestBean requestBean) {
		SocialEntityResultBean response = null;
		log.debug("Viewers");
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			EntityViewers viewers = null;
			Criteria criteria = session.createCriteria(EntityViewers.class);
			criteria.add(Restrictions.eq("customerId", requestBean.getCustomerId()));
			criteria.add(Restrictions.eq("entityId", requestBean.getEntityId()));
			criteria.add(Restrictions.eq("entityType", requestBean.getEntityType()));
			List<EntityViewers> list = criteria.list();
			if (list != null && list.size() > 0)
				viewers = list.get(0);
			if (viewers == null)
				viewers = new EntityViewers();
			viewers.setEntityId(requestBean.getEntityId());
			viewers.setCustomerId(requestBean.getCustomerId());
			viewers.setViewing(requestBean.isAction());
			viewers.setEntityType(requestBean.getEntityType());
			viewers.setCustomerLoggedIn(requestBean.getIsLoggedIn());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String viewDate = sdf.format(date);
			viewers.setViewDate(viewDate);
			session.save(viewers);
			response = new SocialEntityResultBean();
			response.setMessage("success");
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Viewers failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return response;
	}

}

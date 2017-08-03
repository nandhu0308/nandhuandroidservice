package com.limitless.services.socialentity.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.SocialEntityResponseBean;
import com.sun.jersey.api.client.Client;

public class SocialEntityManager {

	public static SocialEntityManager getInstance() {
		return new SocialEntityManager();
	}

	private static final Log log = LogFactory.getLog(SocialEntityManager.class);

	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public static void setSocialEntity(BroadcasterChannelResponseBean broadcasterChannelResponseBean, Session session,
			int customerId, boolean isLoggedIn) {
		SocialEntityRequestBean socialEntityRequestBean = new SocialEntityRequestBean();
		socialEntityRequestBean.setEntityId(broadcasterChannelResponseBean.getBroadcasterId());
		socialEntityRequestBean.setEntityType(SocialEntityType.B.toString());
		socialEntityRequestBean.setCustomerId(customerId);
		socialEntityRequestBean.setIsLoggedIn(isLoggedIn);
		broadcasterChannelResponseBean
				.setSocialEntity(SocialEntityManager.getInstance().processRequest(socialEntityRequestBean, session));
	}

	public static void setSocialEntity(VideoBean videoBean, Session session, int customerId, boolean isLoggedIn) {
		SocialEntityRequestBean socialEntityRequestBean = new SocialEntityRequestBean();
		socialEntityRequestBean.setEntityId(videoBean.getVideoId());
		socialEntityRequestBean.setEntityType(SocialEntityType.V.toString());
		socialEntityRequestBean.setCustomerId(customerId);
		socialEntityRequestBean.setIsLoggedIn(isLoggedIn);
		videoBean.setSocialEntity(SocialEntityManager.getInstance().processRequest(socialEntityRequestBean,
				videoBean.getAlbumId(), session));
	}

	private SocialEntityResponseBean getSocialEntityResponse(SocialEntityRequestBean requestbean) {
		log.debug("getting SocialEntityResponseBean");
		SocialEntityResponseBean responseBean = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			responseBean = processRequest(requestbean, session);
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("getting SocialEntityResponseBean failed : " + re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;

	}

	public static void setSocialEntity(AlbumBean bean, int customerId, Session session) {
		SocialEntityRequestBean SocialEntityRequestBean = new SocialEntityRequestBean();
		SocialEntityRequestBean.setEntityId(bean.getAlbumId());
		SocialEntityRequestBean.setEntityType(SocialEntityType.C.toString());
		SocialEntityRequestBean.setCustomerId(customerId);
		bean.setSocialEntity(SocialEntityManager.getInstance().processRequest(SocialEntityRequestBean, session));

	}

	private SocialEntityResponseBean processRequest(SocialEntityRequestBean socialEntityRequestBean, Session session) {
		return processRequest(socialEntityRequestBean, 0, session);
	}

	private SocialEntityResponseBean processRequest(SocialEntityRequestBean requestbean, int parentEntityId,
			Session session) {
		SocialEntityResponseBean responseBean = new SocialEntityResponseBean();
		Criterion entityIdCriteria = Restrictions.eq("entityId", requestbean.getEntityId());
		Criterion entityTypeCriteria = Restrictions.eq("entityType", requestbean.getEntityType());
		Criterion customerIdCriteria = Restrictions.eq("customerId", requestbean.getCustomerId());
		Long actionCount = null;
		Criteria entityViewersCriteria = session.createCriteria(EntityViewers.class);
		entityViewersCriteria.add(entityIdCriteria);
		entityViewersCriteria.add(entityTypeCriteria);
		entityViewersCriteria.setProjection(Projections.rowCount());
		Long totalViewers = (Long) entityViewersCriteria.uniqueResult();

		Criteria entityLikeCriteria = session.createCriteria(EntityLike.class);
		entityLikeCriteria.add(entityIdCriteria);
		entityLikeCriteria.add(entityTypeCriteria);
		entityLikeCriteria.setProjection(Projections.rowCount());
		Long totalLikes = (Long) entityLikeCriteria.uniqueResult();
		entityLikeCriteria.add(customerIdCriteria);
		entityLikeCriteria.add(Restrictions.eq("liked", true));
		actionCount = (Long) entityLikeCriteria.uniqueResult();
		responseBean.setLike(actionCount != null && actionCount > 0);

		Criteria entityFollowCriteria = session.createCriteria(EntityFollow.class);

		if (requestbean.getEntityType().equalsIgnoreCase(SocialEntityType.V.toString())) {
			entityFollowCriteria.add(Restrictions.eq("entityType", SocialEntityType.C.toString()));
			entityFollowCriteria.add(Restrictions.eq("entityId", parentEntityId));
		} else {
			entityFollowCriteria.add(entityIdCriteria);
			entityFollowCriteria.add(entityTypeCriteria);
		}
		entityFollowCriteria.setProjection(Projections.rowCount());
		Long totalFollowers = (Long) entityFollowCriteria.uniqueResult();
		entityFollowCriteria.add(customerIdCriteria);
		entityFollowCriteria.add(Restrictions.eq("followed", true));
		actionCount = (Long) entityFollowCriteria.uniqueResult();
		responseBean.setFollow(actionCount != null && actionCount > 0);

		Criteria entityShareCriteria = session.createCriteria(EntityShare.class);
		entityShareCriteria.add(entityIdCriteria);
		entityShareCriteria.add(entityTypeCriteria);
		entityShareCriteria.setProjection(Projections.rowCount());
		Long totalShares = (Long) entityShareCriteria.uniqueResult();

		responseBean.setTotalViews(totalViewers != null ? totalViewers.toString() : "");
		responseBean.setLikes(totalLikes != null ? totalLikes.toString() : "");
		responseBean.setFollowers(totalFollowers != null ? totalFollowers.toString() : "");
		responseBean.setShares(totalShares != null ? totalShares.toString() : "");
		return responseBean;
	}

}

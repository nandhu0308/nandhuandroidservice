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

	public static void setSocialEntity(BroadcasterChannelResponseBean broadcasterChannelResponseBean, Session session) {
		SocialEntityRequestBean SocialEntityRequestBean = new SocialEntityRequestBean();
		SocialEntityRequestBean.setEntityId(broadcasterChannelResponseBean.getBroadcasterId());
		SocialEntityRequestBean.setEntityType(SocialEntityType.B.toString());
		broadcasterChannelResponseBean
				.setSocialEntity(SocialEntityManager.getInstance().processRequest(SocialEntityRequestBean, session));
	}

	public static void setSocialEntity(VideoBean videoBean, Session session) {
		SocialEntityRequestBean SocialEntityRequestBean = new SocialEntityRequestBean();
		SocialEntityRequestBean.setEntityId(videoBean.getVideoId());
		SocialEntityRequestBean.setEntityType(SocialEntityType.V.toString());
		videoBean.setSocialEntity(SocialEntityManager.getInstance().processRequest(SocialEntityRequestBean, session));
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

	private SocialEntityResponseBean processRequest(SocialEntityRequestBean requestbean, Session session) {
		SocialEntityResponseBean responseBean = new SocialEntityResponseBean();
		Criterion entityIdCriteria = Restrictions.eq("entityId", requestbean.getEntityId());
		Criterion entityTypeCriteria = Restrictions.eq("entityType", requestbean.getEntityType());

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

		Criteria entityFollowCriteria = session.createCriteria(EntityFollow.class);
		entityFollowCriteria.add(entityIdCriteria);
		entityFollowCriteria.add(entityTypeCriteria);
		entityFollowCriteria.setProjection(Projections.rowCount());
		Long totalFollowers = (Long) entityFollowCriteria.uniqueResult();

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

	public static void setSocialEntity(AlbumBean bean, Session session) {
		SocialEntityRequestBean SocialEntityRequestBean = new SocialEntityRequestBean();
		SocialEntityRequestBean.setEntityId(bean.getAlbumId());
		SocialEntityRequestBean.setEntityType(SocialEntityType.C.toString());
		bean.setSocialEntity(SocialEntityManager.getInstance().processRequest(SocialEntityRequestBean, session));

	}

}

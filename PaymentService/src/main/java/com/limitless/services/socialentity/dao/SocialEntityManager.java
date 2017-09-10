package com.limitless.services.socialentity.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.SqlResultSetMapping;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.entertainment.AlbumBean;
import com.limitless.services.engage.entertainment.BroadcasterChannelResponseBean;
import com.limitless.services.engage.entertainment.ChannelResponseBean;
import com.limitless.services.engage.entertainment.VideoBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;
import com.limitless.services.socialentity.SocialEntityRequestBean;
import com.limitless.services.socialentity.SocialEntityResponseBean;
import com.sun.jersey.api.client.Client;

public class SocialEntityManager {

	private static final String sqlQuery = " SELECT COALESCE( SUM(CASE " + "WHEN l.customer_id =:cid THEN 1 "
			+ "ELSE 0 " + "  END),0) AS customerSuccess, " + "count(*) AS totalSuccess "
			+ "FROM {h-schema}entity_like l where liked=1 and entity_id=:eid and entity_type=:et " + "union all "
			+ "SELECT COALESCE( SUM(CASE " + "WHEN f.customer_id =:cid THEN 1 " + "ELSE 0 "
			+ "END),0) AS customerSuccess, " + "count(*) AS totalSuccess "
			+ "FROM {h-schema}entity_follow f where followed=1 and entity_id=:veid and entity_type=:vet " + "union all "
			+ "SELECT COALESCE( SUM(CASE  " + " WHEN v.viewing =1 THEN 1 " + " ELSE 0 " + "END),0) AS customerSuccess, "
			+ "count(*) AS totalSuccess " + "FROM {h-schema}entity_viewers v where  entity_id=:eid and entity_type=:et "
			+ " union all " + " SELECT COALESCE(0) AS customerSuccess, " + "  count(*) AS totalSuccess "
			+ "FROM {h-schema}entity_share v where  entity_id=:eid and entity_type=:et  ";

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

	
	public static void setSocialEntity(ChannelResponseBean channelResponseBean, Session session,
			int customerId, boolean isLoggedIn) {
		SocialEntityRequestBean socialEntityRequestBean = new SocialEntityRequestBean();
		socialEntityRequestBean.setEntityId(channelResponseBean.getChannelId());
		socialEntityRequestBean.setEntityType(SocialEntityType.C.toString());
		socialEntityRequestBean.setCustomerId(customerId);
		socialEntityRequestBean.setIsLoggedIn(isLoggedIn);
		channelResponseBean
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
		SQLQuery query = session.createSQLQuery(sqlQuery);
		// query.setEntity("alias", EngageSeller.class);
		query.setParameter("cid", requestbean.getCustomerId());
		query.setParameter("eid", requestbean.getEntityId());
		query.setParameter("et", requestbean.getEntityType());
		if (requestbean.getEntityType().equalsIgnoreCase(SocialEntityType.V.toString())) {
			query.setParameter("veid", parentEntityId);
			query.setParameter("vet", SocialEntityType.C.toString());
		} else {
			query.setParameter("veid", requestbean.getEntityId());
			query.setParameter("vet", requestbean.getEntityType());
		}
		List<Object[]> records = query.list();

		int counter = 0;
		for (Object[] record : records) {
			SocialEntityResult result = new SocialEntityResult();
			switch (counter) {
			case 0:
				responseBean.setLike(record[0] != null && !record[0].toString().equalsIgnoreCase("0"));
				responseBean.setLikes(record[1].toString());

				break;
			case 1:
				responseBean.setFollow(record[0] != null && !record[0].toString().equalsIgnoreCase("0"));
				responseBean.setFollowers(record[1].toString());

				break;
			case 2:
				responseBean.setTotalViews(record[1].toString());
				break;
			case 3:
				responseBean.setShares(record[1].toString());
				break;
			}

			counter++;
		}

		return responseBean;
	}

}

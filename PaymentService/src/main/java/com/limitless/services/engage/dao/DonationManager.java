package com.limitless.services.engage.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.DonationResponseBean;
import com.limitless.services.engage.DonationsBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class DonationManager {
	private static final Log log = LogFactory.getLog(EngageCustomerManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	public DonationResponseBean getDonations(int merchantId) {
		log.debug("Getting merchant donations");
		DonationResponseBean responseBean = new DonationResponseBean();
		Transaction transaction = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(SellerDonation.class);
			criteria.add(Restrictions.eq("sellerId", merchantId));
			List<SellerDonation> sellerDonationsList = criteria.list();
			log.debug("Size: " + sellerDonationsList.size());
			if (sellerDonationsList.size() > 0) {
				responseBean.setMessage("Success");
				List<DonationsBean> donationsList = new ArrayList<DonationsBean>();
				for (SellerDonation sellers : sellerDonationsList) {
					Donation donation = (Donation) session.get("com.limitless.services.engage.dao.Donation",
							sellers.getDonationId());
					DonationsBean bean = new DonationsBean();
					bean.setDonationId(donation.getDinationId());
					bean.setDonationName(donation.getDonationName());
					bean.setCitrusSellerId(donation.getCitrusSellerId());
					donationsList.add(bean);
					bean = null;
				}
				responseBean.setDonationsList(donationsList);
			} else if (sellerDonationsList.isEmpty()) {
				responseBean.setMessage("No Doantions List Found");
			}
			transaction.commit();
		} catch (RuntimeException re) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Getting merchant donations failed", re);
			throw re;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return responseBean;
	}
}

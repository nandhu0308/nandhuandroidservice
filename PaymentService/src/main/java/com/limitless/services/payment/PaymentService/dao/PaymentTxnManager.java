package com.limitless.services.payment.PaymentService.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/*
 * @author veejay.developer@gmail.com
 * ©www.limitlesscircle.com 
 */

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageCustomer;
import com.limitless.services.engage.dao.EngageCustomerManager;
import com.limitless.services.payment.PaymentService.GeneralSellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.PaymentTxnBean.TxnStatus;
import com.limitless.services.payment.PaymentService.PaymentsSettlementResponseBean;
import com.limitless.services.payment.PaymentService.SellerTxnHistoryBean;
import com.limitless.services.payment.PaymentService.TxnDayWiseBean;
import com.limitless.services.payment.PaymentService.TxnHistoryBean;
import com.limitless.services.payment.PaymentService.TxnMonthWiseBean;
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
	
	public List<PaymentTxn> getTxnHistory(int customerId){
		log.debug("Getting History");
		Transaction tx = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("from PaymentTxn where engageCustomerId = :customerId order by txnId desc");
			query.setParameter("customerId", customerId);
			query.setMaxResults(10);
			List<PaymentTxn> paymentHistory = query.list();
			return paymentHistory;
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		} finally{
			tx.commit();
		}
	}
	
	public List<PaymentTxn> getTxnHistoryPagination(int customerId, int firstTxnId){
		log.debug("Getting History");
		Transaction tx = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			tx = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion custIdCriterion = Restrictions.eq("engageCustomerId", customerId);
			Criterion txnIdCriterion = Restrictions.lt("txnId", firstTxnId);
			LogicalExpression logicalExpression = Restrictions.and(custIdCriterion, txnIdCriterion);
			criteria.add(logicalExpression);
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentTxnsList = criteria.list();
			return paymentTxnsList;
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			tx.commit();
		}
	}
	
	public double getCreditAmount(int txnId){
		log.debug("getting credit amount");
		Transaction transaction = null;
		double creditAmount = 0;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentCredit.class);
			criteria.add(Restrictions.eq("txnId", txnId));
			List<PaymentCredit> creditsList = criteria.list();
			if(creditsList!=null && creditsList.size()>0){
				for(PaymentCredit credit : creditsList){
					creditAmount = credit.getCreditAmount();
				}
			}
		}
		catch(RuntimeException re){
			log.error("Getting credit amount Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return creditAmount;
	}
	
	public SellerTxnHistoryBean getSellerTxns(int citrusSellerId) throws Exception{
		log.debug("Getting Seller Txn History");
		Transaction transaction = null;
		SellerTxnHistoryBean sthBean = new SellerTxnHistoryBean();
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			criteria.add(Restrictions.eq("citrusSellerId", citrusSellerId));
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentList = criteria.list();
			if(paymentList.size() > 0){
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("Success");
				List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn payment : paymentList){
					TxnHistoryBean bean = new TxnHistoryBean();
					bean.setTxnId(payment.getTxnId());
					int txnId = payment.getTxnId();
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> creditList = criteria2.list();
					if(creditList != null || creditList.size() == 0){
						for(PaymentCredit credit : creditList){
							bean.setCreditAmount(credit.getCreditAmount());
						}
					}
					bean.setCustomerId(payment.getEngageCustomerId());
					int customerId = payment.getEngageCustomerId();
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					bean.setCustomerName(customer.getCustomerName());
					bean.setSellerId(payment.getSellerId());
					bean.setSellerName(payment.getSellerName());
					bean.setTxtAmount(payment.getTxnAmount());
					bean.setCitrusMpTxnId(payment.getCitrusMpTxnId());
					bean.setSplitId(payment.getSplitId());
					bean.setTxtStatus(payment.getTxnStatus());
					String gmtTime = payment.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					bean.setTxnTime(localTime);
					Criteria criteria3 = session.createCriteria(PaymentSettlement.class);
					criteria3.add(Restrictions.eq("txnId", payment.getTxnId()));
					List<PaymentSettlement> settlements = criteria3.list();
					for(PaymentSettlement settlement : settlements){
						PaymentsSettlementResponseBean responseBean = new PaymentsSettlementResponseBean();
						responseBean.setPsId(settlement.getPsId());
						responseBean.setReleasefundRefId(settlement.getReleasefundRefId());
						responseBean.setSettlementId(settlement.getSettlementId());
						responseBean.setSettlementAmount(settlement.getSettlementAmount());
						responseBean.setErrorIdSettle(settlement.getErrorIdSettle());
						responseBean.setErrorDescriptionSettle(settlement.getErrorDescriptionSettle());
						responseBean.setErrorIdRelease(settlement.getErrorIdRelease());
						responseBean.setErrorDescriptionRelease(settlement.getErrorDescriptionRelease());
						bean.setSettlement(responseBean);
					}
					historyBeanList.add(bean);
					bean = null;
				}
				sthBean.setHistoryBean(historyBeanList);
			}
			else{
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("No Record Found");				
			}
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return sthBean;
	}
	
	public SellerTxnHistoryBean sellerTxnHistoryPagination(int citrusSellerId, int firstTxnId) throws Exception{
		log.debug("Getting Seller Txn History");
		Transaction transaction = null;
		SellerTxnHistoryBean sthBean = new SellerTxnHistoryBean();
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion txnIdCriterion = Restrictions.lt("txnId", firstTxnId);
			Criterion csidCriterion = Restrictions.eq("citrusSellerId", citrusSellerId);
			LogicalExpression logicalExpression = Restrictions.and(txnIdCriterion, csidCriterion);
			criteria.add(logicalExpression);
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> paymentList = criteria.list();
			if(paymentList.size() > 0){
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("Success");
				List<TxnHistoryBean> historyBeanList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn payment : paymentList){
					TxnHistoryBean bean = new TxnHistoryBean();
					bean.setTxnId(payment.getTxnId());
					int txnId = payment.getTxnId();
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> creditList = criteria2.list();
					if(creditList != null || creditList.size() == 0){
						for(PaymentCredit credit : creditList){
							bean.setCreditAmount(credit.getCreditAmount());
						}
					}
					bean.setCustomerId(payment.getEngageCustomerId());
					int customerId = payment.getEngageCustomerId();
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					bean.setCustomerName(customer.getCustomerName());
					bean.setSellerId(payment.getSellerId());
					bean.setSellerName(payment.getSellerName());
					bean.setTxtAmount(payment.getTxnAmount());
					bean.setCitrusMpTxnId(payment.getCitrusMpTxnId());
					bean.setSplitId(payment.getSplitId());
					bean.setTxtStatus(payment.getTxnStatus());
					String gmtTime = payment.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					bean.setTxnTime(localTime);
					Criteria criteria3 = session.createCriteria(PaymentSettlement.class);
					criteria3.add(Restrictions.eq("txnId", payment.getTxnId()));
					List<PaymentSettlement> settlements = criteria3.list();
					for(PaymentSettlement settlement : settlements){
						PaymentsSettlementResponseBean responseBean = new PaymentsSettlementResponseBean();
						responseBean.setPsId(settlement.getPsId());
						responseBean.setReleasefundRefId(settlement.getReleasefundRefId());
						responseBean.setSettlementId(settlement.getSettlementId());
						responseBean.setSettlementAmount(settlement.getSettlementAmount());
						bean.setSettlement(responseBean);
					}
					historyBeanList.add(bean);
					bean = null;
				}
				sthBean.setHistoryBean(historyBeanList);
			}
			else{
				log.debug("Size: "+paymentList.size());
				sthBean.setMessage("No More Records Found");				
			}
		}
		catch(RuntimeException re){
			log.error("Getting History Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return sthBean;
	}
	
	public SellerTxnHistoryBean getDayTxns(int citrusSellerId) throws Exception{
		log.debug("Getting Daywise Txns");
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat txnSdf = new SimpleDateFormat("dd MMM yyyy");
			
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			
			List<TxnDayWiseBean> dayBeanList = new ArrayList<TxnDayWiseBean>();
			
			for(int i=0;i<30;i++){
				TxnDayWiseBean dayBean = new TxnDayWiseBean();
				calendar.setTime(now);
				calendar.add(Calendar.DATE, -i);
				String date = txnSdf.format(calendar.getTime());
				String txnDateStart = sdf.format(calendar.getTime()) + " 00:00:00";
				String txnDateEnd = sdf.format(calendar.getTime()) + " 23:59:59";
				log.debug("String Date : "+ txnDateStart);
				log.debug("End Time : " + txnDateEnd);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date dateStart = sdf2.parse(txnDateStart);
				Date dateEnd = sdf2.parse(txnDateEnd);
				log.debug("DateStart : "+ dateStart);
				log.debug("EndDate : "+ dateEnd);
				
				Criteria criteria = session.createCriteria(PaymentTxn.class);
				Criterion csidCriterion = Restrictions.eq("citrusSellerId", citrusSellerId);
				Criterion dateCriterion = Restrictions.between("txnUpdatedTime", dateStart, dateEnd);
				LogicalExpression logicalExp = Restrictions.and(csidCriterion, dateCriterion);
				criteria.add(logicalExp);
				List<PaymentTxn> txns = criteria.list();
				log.debug("Txns Size: "+txns.size());
				
				if(txns.size()>0){
					double totalAmount = 0;
					for(PaymentTxn txn : txns){
						totalAmount = totalAmount + txn.getTxnAmount();
					}
					
					dayBean.setTotalAmount(totalAmount);
					dayBean.setDate(date);
					dayBeanList.add(dayBean);
					historyBean.setDayHistory(dayBeanList);
					
				}
				dayBean = null;
			}
			if(dayBeanList.isEmpty()){
				historyBean.setMessage("No Record Found");
			}
			else if(dayBeanList.size()>0){
				historyBean.setMessage("Success");
			}
			
		}
		catch(RuntimeException re){
			log.error("Getting Daywise Txns Failed", re);
			throw re;
		}
		finally {
			transaction.commit();
		}
		return historyBean;
	}
	
	public SellerTxnHistoryBean getMonthTxns(int citrusSellerId) throws Exception{
		log.debug("Getting Monthly Txns");
		SellerTxnHistoryBean historyBean = new SellerTxnHistoryBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
			SimpleDateFormat sdf2 = new SimpleDateFormat("MMM yyyy");
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			
			List<TxnMonthWiseBean> monthBeanList = new ArrayList<TxnMonthWiseBean>();
			
			for(int i=0;i<12;i++){
				TxnMonthWiseBean monthBean = new TxnMonthWiseBean();
				 calendar.setTime(now);
				 calendar.add(Calendar.MONTH, -i);
				 String txnMonth = sdf2.format(calendar.getTime());
				 String txnMonthStart = sdf1.format(calendar.getTime())+"-01 00:00:00";
				 String txnMonthEnd = sdf1.format(calendar.getTime())+"-31 23:59:59";
				 log.debug("Start : "+txnMonthStart);
				 log.debug("End : " + txnMonthEnd );
				 SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				 Date startDate = sdf3.parse(txnMonthStart);
				 Date endDate = sdf3.parse(txnMonthEnd);
				 
				 Criteria criteria = session.createCriteria(PaymentTxn.class);
				 Criterion csidCriterion = Restrictions.eq("citrusSellerId", citrusSellerId);
				 Criterion dateCriterion = Restrictions.between("txnUpdatedTime", startDate, endDate);
				 LogicalExpression logicalExp = Restrictions.and(csidCriterion, dateCriterion);
				 criteria.add(logicalExp);
			     List<PaymentTxn> txns = criteria.list();
				 log.debug("Txns Size: "+txns.size());
				 
				 if(txns.size()>0){
						double totalAmount = 0;
						for(PaymentTxn txn : txns){
							totalAmount = totalAmount + txn.getTxnAmount();
						}
						
						monthBean.setTotalAmount(totalAmount);
						monthBean.setMonth(txnMonth);
						monthBeanList.add(monthBean);
						historyBean.setMonthHistory(monthBeanList);
						
					}
				
					monthBean = null;
			}
			if(monthBeanList.isEmpty()){
				historyBean.setMessage("No Record Found");
			}
			else if(monthBeanList.size()>0){
				historyBean.setMessage("Success");
			}
		}
		catch(RuntimeException re){
			log.error("Getting Monthly Txns Failed", re);
			throw re;
		}
		finally{
			transaction.commit();
		}
		return historyBean;
	}
	
	public GeneralSellerTxnHistoryBean getGenSellerTxns(int merchantId) throws Exception{
		log.debug("Getting seller Txn History");
		GeneralSellerTxnHistoryBean genHistoryBean = new GeneralSellerTxnHistoryBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			criteria.add(Restrictions.eq("sellerId", merchantId));
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> txnList = criteria.list();
			log.debug("Txns Size : "+ txnList.size());
			if(txnList.size()>0){
				List<TxnHistoryBean> historyList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn txn : txnList){
					TxnHistoryBean historyBean = new TxnHistoryBean();
					int txnId = txn.getTxnId();
					historyBean.setTxnId(txnId);
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> credits = criteria2.list();
					log.debug("Credits Size: "+ credits.size());
					if(credits.size()>0){
						for(PaymentCredit credit : credits){
							historyBean.setCreditAmount(credit.getCreditAmount());
						}
					}
					int customerId = txn.getEngageCustomerId();
					historyBean.setCustomerId(customerId);
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					historyBean.setCustomerName(customer.getCustomerName());
					historyBean.setTxtAmount(txn.getTxnAmount());
					historyBean.setSellerId(txn.getSellerId());
					historyBean.setSellerName(txn.getSellerName());
					String gmtTime = txn.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					historyBean.setTxnTime(localTime);
					Criteria criteria3 = session.createCriteria(PaymentSettlement.class);
					criteria3.add(Restrictions.eq("txnId", txnId));
					List<PaymentSettlement> settlementList = criteria3.list();
					log.debug("Settlement List : "+ settlementList.size());
					if(settlementList.size()>0){
						for(PaymentSettlement settlement : settlementList){
							PaymentsSettlementResponseBean responseBean = new PaymentsSettlementResponseBean();
							responseBean.setPsId(settlement.getPsId());
							responseBean.setSettlementId(settlement.getSettlementId());
							responseBean.setReleasefundRefId(settlement.getReleasefundRefId());
							responseBean.setSettlementAmount(settlement.getSettlementAmount());
							historyBean.setSettlement(responseBean);
						}
					}
					historyList.add(historyBean);
					historyBean = null;
				}
				genHistoryBean.setTxnHistory(historyList);
				genHistoryBean.setMessage("Success");
			}
			else{
				genHistoryBean.setMessage("No Record Found");
			}
		}
		catch(RuntimeException re){
			log.error("Getting seller Txn History failed");
			throw re;
		}
		finally{
			transaction.commit();
		}
		return genHistoryBean;
	}
	
	public GeneralSellerTxnHistoryBean getGenSellerTxnsPagination(int merchantId, int firstTxnId) throws Exception{
		log.debug("Getting Seller Txn History Pagination");
		GeneralSellerTxnHistoryBean genHistoryBean = new GeneralSellerTxnHistoryBean();
		Transaction transaction = null;
		try{
			Session session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(PaymentTxn.class);
			Criterion txnIdCriterion = Restrictions.lt("txnId", firstTxnId);
			Criterion merchantCriterion = Restrictions.eq("sellerId", merchantId);
			LogicalExpression logExp = Restrictions.and(txnIdCriterion, merchantCriterion);
			criteria.add(logExp);
			criteria.addOrder(Order.desc("txnId"));
			criteria.setMaxResults(10);
			List<PaymentTxn> txnList = criteria.list();
			log.debug("Txns Size : "+ txnList.size());
			if(txnList.size()>0){
				List<TxnHistoryBean> historyList = new ArrayList<TxnHistoryBean>();
				for(PaymentTxn txn : txnList){
					TxnHistoryBean historyBean = new TxnHistoryBean();
					int txnId = txn.getTxnId();
					historyBean.setTxnId(txnId);
					Criteria criteria2 = session.createCriteria(PaymentCredit.class);
					criteria2.add(Restrictions.eq("txnId", txnId));
					List<PaymentCredit> credits = criteria2.list();
					log.debug("Credits Size: "+ credits.size());
					if(credits.size()>0){
						for(PaymentCredit credit : credits){
							historyBean.setCreditAmount(credit.getCreditAmount());
						}
					}
					int customerId = txn.getEngageCustomerId();
					historyBean.setCustomerId(customerId);
					EngageCustomer customer = (EngageCustomer) session
							.get("com.limitless.services.engage.dao.EngageCustomer", customerId);
					historyBean.setCustomerName(customer.getCustomerName());
					historyBean.setTxtAmount(txn.getTxnAmount());
					historyBean.setSellerId(txn.getSellerId());
					historyBean.setSellerName(txn.getSellerName());
					String gmtTime = txn.getTxnUpdatedTime().toString();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = sdf.parse(gmtTime);
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(date);
					calendar.add(Calendar.HOUR, 5);
					calendar.add(Calendar.MINUTE, 30);
					String localTime = sdf.format(calendar.getTime());
					historyBean.setTxnTime(localTime);
					Criteria criteria3 = session.createCriteria(PaymentSettlement.class);
					criteria3.add(Restrictions.eq("txnId", txnId));
					List<PaymentSettlement> settlementList = criteria3.list();
					log.debug("Settlement List : "+ settlementList.size());
					if(settlementList.size()>0){
						for(PaymentSettlement settlement : settlementList){
							PaymentsSettlementResponseBean responseBean = new PaymentsSettlementResponseBean();
							responseBean.setPsId(settlement.getPsId());
							responseBean.setSettlementId(settlement.getSettlementId());
							responseBean.setReleasefundRefId(settlement.getReleasefundRefId());
							responseBean.setSettlementAmount(settlement.getSettlementAmount());
							historyBean.setSettlement(responseBean);
						}
					}
					historyList.add(historyBean);
					historyBean = null;
				}
				genHistoryBean.setTxnHistory(historyList);
				genHistoryBean.setMessage("Success");
			}
			else{
				genHistoryBean.setMessage("No Record Found");
			}
		}
		catch(RuntimeException re){
			log.error("Getting seller Txn History failed");
			throw re;
		}
		finally {
			transaction.commit();
		}
		return genHistoryBean;
	}
	
	public static void main(String[] args) {
		PaymentTxnManager manager = new PaymentTxnManager();
		//manager.updateSplitId(946706, 28698, 31747, TxnStatus.PAYMENT_SUCCESSFUL.toString());
		manager.findById(946707);
	}
}

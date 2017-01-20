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

import com.limitless.services.engage.AddressListBean;
import com.limitless.services.engage.CustomerAddressListBean;
import com.limitless.services.engage.CustomerAddressRequestBean;
import com.limitless.services.engage.CustomerAddressResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class CustomerAddressBookManager {
	private static final Log log = LogFactory.getLog(CustomerAddressBookManager.class);
	private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	
	public CustomerAddressResponseBean addAddress(CustomerAddressRequestBean requestBean){
		log.debug("adding address");
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CustomerAddressBook addressBook = new CustomerAddressBook();
			addressBook.setReceiverName(requestBean.getReceiverName());
			addressBook.setCustomerId(requestBean.getCustomerId());
			addressBook.setCustomerAddress1(requestBean.getCustomerAddress1());
			addressBook.setCustomerAddress2(requestBean.getCustomerAddress2());
			addressBook.setCustomerCity(requestBean.getCustomerCity());
			addressBook.setCustomerState(requestBean.getCustomerState());
			addressBook.setCustomerZip(requestBean.getCustomerZip());
			addressBook.setCustomerLandmark(requestBean.getCustomerLandmark());
			addressBook.setCustomerDeliveryMobile(requestBean.getCustomerDeliveryMobile());
			
			session.persist(addressBook);
			
			if(addressBook.getCabId()>0){
				responseBean.setAddressId(addressBook.getCabId());
				responseBean.setCustomerId(addressBook.getCustomerId());
				responseBean.setMessage("Success");
			}
			else{
				responseBean.setCustomerId(requestBean.getCustomerId());
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding address failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public CustomerAddressListBean getCustomerAddressList(int customerId){
		log.debug("getting customer address");
		CustomerAddressListBean listBean = new CustomerAddressListBean();
		List<AddressListBean> addressList = new ArrayList<AddressListBean>();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(CustomerAddressBook.class);
			criteria.add(Restrictions.eq("customerId", customerId));
			List<CustomerAddressBook> bookList = criteria.list();
			log.debug("Address Size : " +bookList.size());
			if(bookList.size()>0){
				for(CustomerAddressBook book : bookList){
					AddressListBean address = new AddressListBean();
					address.setCadId(book.getCabId());
					address.setCustomerId(book.getCustomerId());
					address.setReceiverName(book.getReceiverName());
					address.setCustomerAddress1(book.getCustomerAddress1());
					address.setCustomerAddress2(book.getCustomerAddress2());
					address.setCustomerCity(book.getCustomerCity());
					address.setCustomerState(book.getCustomerState());
					address.setCustomerZip(book.getCustomerZip());
					address.setCustomerDeliveryMobile(book.getCustomerDeliveryMobile());
					address.setCustomerLandmark(book.getCustomerLandmark());
					addressList.add(address);
					address = null;
				}
				listBean.setMessage("Success");
				listBean.setCustomerId(customerId);
				listBean.setAddressList(addressList);
			}
			else if(bookList.isEmpty()){
				listBean.setCustomerId(customerId);
				listBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting address failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return listBean;
	}
	
	public CustomerAddressResponseBean editCustomerAddress(CustomerAddressRequestBean requestBean){
		log.debug("Editing customer address");
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CustomerAddressBook addressBook = (CustomerAddressBook) session
					.get("com.limitless.services.engage.dao.CustomerAddressBook", requestBean.getCabId());
			addressBook.setReceiverName(requestBean.getReceiverName());
			addressBook.setCustomerId(requestBean.getCustomerId());
			addressBook.setCustomerAddress1(requestBean.getCustomerAddress1());
			addressBook.setCustomerAddress2(requestBean.getCustomerAddress2());
			addressBook.setCustomerCity(requestBean.getCustomerCity());
			addressBook.setCustomerState(requestBean.getCustomerState());
			addressBook.setCustomerZip(requestBean.getCustomerZip());
			addressBook.setCustomerDeliveryMobile(requestBean.getCustomerDeliveryMobile());
			addressBook.setCustomerLandmark(requestBean.getCustomerLandmark());
			
			session.update(addressBook);
			
			responseBean.setAddressId(requestBean.getCabId());
			responseBean.setCustomerId(requestBean.getCustomerId());
			responseBean.setMessage("Success");
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting address failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public CustomerAddressResponseBean deleteCustomerAddress(int addressId){
		log.debug("deleting customer address");
		CustomerAddressResponseBean responseBean = new CustomerAddressResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			CustomerAddressBook address = (CustomerAddressBook) session
					.get("com.limitless.services.engage.dao.CustomerAddressBook", addressId);
			if(address!=null){
				session.delete(address);
				responseBean.setAddressId(addressId);
				responseBean.setMessage("Success");
			}
			else{
				responseBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("deleting address failed : " +re);
			throw re;
		}
		finally {
			if(session!=null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
}

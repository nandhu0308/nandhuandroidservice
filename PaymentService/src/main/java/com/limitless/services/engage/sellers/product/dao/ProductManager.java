package com.limitless.services.engage.sellers.product.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.limitless.services.engage.dao.EngageSeller;
import com.limitless.services.engage.sellers.NewProductsRequestBean;
import com.limitless.services.engage.sellers.NewProductsResponseBean;
import com.limitless.services.engage.sellers.ProductBean;
import com.limitless.services.engage.sellers.ProductInventoryRequestBean;
import com.limitless.services.engage.sellers.ProductInventoryResponseBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.payment.PaymentService.util.HibernateUtil;

public class ProductManager {

	private static final Log log = LogFactory.getLog(ProductManager.class);

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
	
	public List<ProductBean> getAllProducts(int sellerId){
		log.debug("Getting all product ids for a seller");
		Transaction transaction = null;
		Session session = null;
		Set<Integer> productIds = new HashSet<Integer>();
		
		List<ProductBean> productsList = new ArrayList<ProductBean>();
		
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Criteria criteria = session.createCriteria(SellerProduct.class);
			criteria.add(Restrictions.eq("sellerId", sellerId));
			List<SellerProduct> sellerProductList = criteria.list();
			log.debug("Size : " + sellerProductList.size());
			if(sellerProductList.size() > 0){
				
				//Populate the Product Ids from Seller_Product Table
				for(SellerProduct sellerProduct : sellerProductList){
					productIds.add(sellerProduct.getProductId());
				}
				
				//Get All products
				Criteria productcriteria = session.createCriteria(Product.class);
				productcriteria.add(Restrictions.in("productId", productIds));
				List<Product> products = productcriteria.list();
				log.debug("Size : " + products.size());
				if(products.size()>0){
					for(Product product : products){
						ProductBean bean = new ProductBean();
						bean.setProductId(product.getProductId());
						bean.setProductName(product.getProductName());
						bean.setProductDescription(product.getProductDescription());
						bean.setProductPrice(product.getProductPrice());
						bean.setDiscountRate(product.getDiscountRate());
						float discountedPrice = (float) ((Float) product.getProductPrice() - (product.getProductPrice()*(product.getDiscountRate()/100)));
						bean.setDiscountedPrice(discountedPrice);
						bean.setProduct_image(product.getProduct_image());
						bean.setProductInStock(product.getProductInStock());
						productsList.add(bean);
						bean = null;
					}
				}
			}
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Finding deviceId failed");
			throw re;
		}
		finally{
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return productsList;
	}
	
	public NewProductsResponseBean addNewProducts(NewProductsRequestBean requestBean){
		log.debug("adding new products");
		NewProductsResponseBean responseBean = new NewProductsResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			for(ProductBean bean : requestBean.getProductList()){
				Product product = new Product();
				product.setProductName(bean.getProductName());
				product.setProductPrice(bean.getProductPrice());
				product.setProduct_image(bean.getProduct_image());
				product.setProductDescription(bean.getProductDescription());
				
				session.persist(product);
				
				SellerProduct sellerProduct = new SellerProduct();
				sellerProduct.setProductId(product.getProductId());
				sellerProduct.setSellerId(requestBean.getSellerId());
				
				session.persist(sellerProduct);
			}
			responseBean.setMessage("Success");
			responseBean.setSellerId(requestBean.getSellerId());
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("adding product failed");
			throw re;
		}
		finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public ProductResponseBean updateProduct(ProductBean bean){
		log.debug("updating product");
		ProductResponseBean responseBean = new ProductResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Product product = (Product) session
					.get("com.limitless.services.engage.sellers.product.dao.Product", bean.getProductId());
			if(product != null){
				product.setProductName(bean.getProductName());
				product.setProductDescription(bean.getProductDescription());
				product.setProductPrice(bean.getProductPrice());
				product.setProduct_image(bean.getProduct_image());
				
				session.update(product);
				
				responseBean.setProductId(bean.getProductId());
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
			log.error("updating product failed");
			throw re;
		}
		finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public ProductInventoryResponseBean addProductInventory(ProductInventoryRequestBean requestBean){
		log.debug("adding inventory");
		ProductInventoryResponseBean responseBean = new ProductInventoryResponseBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			ProductInventory inventoryInstance = (ProductInventory) session
					.get("com.limitless.services.engage.sellers.product.dao.ProductInventory", requestBean.getProductId());
			if(inventoryInstance!=null){
				inventoryInstance.setProductStock(inventoryInstance.getProductStock() + requestBean.getProductStock());
				session.update(inventoryInstance);
				responseBean.setProductId(requestBean.getProductId());
				responseBean.setMessage("Success");
			}
			else if(inventoryInstance==null){
				ProductInventory inventory = new ProductInventory();
				inventory.setProductId(requestBean.getProductId());
				inventory.setProductStock(requestBean.getProductStock());
				inventory.setProductSold(0);
				session.persist(inventory);
				responseBean.setProductId(requestBean.getProductId());
				responseBean.setMessage("Success");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("updating product failed");
			throw re;
		}
		finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return responseBean;
	}
	
	public static void main(String[] args) {
		ProductManager manager = new ProductManager();
//		List<Product> products = manager.getAllProducts(5000000);
//		for (Iterator iterator = products.iterator(); iterator.hasNext();) {
//			Product product = (Product) iterator.next();
//			System.out.println(product.getProductId() + " | " + product.getProductName() + " | " + product.getProductPrice() );
//		}
	}
	
}
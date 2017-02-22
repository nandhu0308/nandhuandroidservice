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
import com.limitless.services.engage.sellers.ProductListBean;
import com.limitless.services.engage.sellers.ProductModelsBean;
import com.limitless.services.engage.sellers.ProductResponseBean;
import com.limitless.services.engage.sellers.ProductSubCategoryListBean;
import com.limitless.services.engage.sellers.ProductsCategoryListBean;
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
						bean.setParentProductId(product.getParentProductId());
						bean.setProductName(product.getProductName());
						bean.setProductDescription(product.getProductDescription());
						bean.setProductPrice(product.getProductPrice());
						bean.setDiscountRate(product.getDiscountRate());
						float discountedPrice = (float) ((Float) product.getProductPrice() - (product.getProductPrice()*(product.getDiscountRate()/100)));
						bean.setDiscountedPrice(discountedPrice);
						bean.setProduct_image(product.getProduct_image());
						bean.setProductInStock(product.getProductInStock());
						bean.setCategoryId(product.getCategoryId());
						bean.setGroupId(product.getGroupId());
						bean.setIsDefault(product.getIsDefault());
						bean.setProductColor(product.getProductColor());
						bean.setProductSizeText(product.getProductSizeText());
						bean.setProductSizeNumber(product.getProductSizeNumber());
						bean.setImage1(product.getImage1());
						bean.setImage2(product.getImage2());
						bean.setImage3(product.getImage3());
						bean.setImage4(product.getImage4());
						bean.setImage5(product.getImage5());
						bean.setImage6(product.getImage6());
						bean.setImage7(product.getImage7());
						bean.setImage8(product.getImage8());
						bean.setImage9(product.getImage9());
						bean.setImage10(product.getImage10());
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
	
	public ProductBean getProductById(int productId){
		log.debug("Getting product by id");
		ProductBean productBean = null;
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			Product product = (Product) session
					.get("com.limitless.services.engage.sellers.product.dao.Product", productId);
			if(product != null){
				productBean = new ProductBean();
				productBean.setProductId(productId);
				productBean.setParentProductId(product.getParentProductId());
				productBean.setProductName(product.getProductName());
				productBean.setProduct_image(product.getProduct_image());
				productBean.setProductDescription(product.getProductDescription());
				productBean.setProductPrice(product.getProductPrice());
				productBean.setProductInStock(product.getProductInStock());
				float discountedPrice = (float) ((Float) product.getProductPrice() - (product.getProductPrice()*(product.getDiscountRate()/100)));
				productBean.setDiscountRate(product.getDiscountRate());
				productBean.setDiscountedPrice(discountedPrice);
				
				ProductImages images = (ProductImages) session
						.get("com.limitless.services.engage.sellers.product.dao.ProductImages", productId);
				if(images != null){
					productBean.setImage1(images.getImage1());
					productBean.setImage2(images.getImage2());
					productBean.setImage3(images.getImage3());
					productBean.setImage4(images.getImage4());
					productBean.setImage5(images.getImage5());
					productBean.setImage6(images.getImage6());
					productBean.setImage7(images.getImage7());
					productBean.setImage8(images.getImage8());
					productBean.setImage9(images.getImage9());
					productBean.setImage10(images.getImage10());
				}
				
				List<ProductModelsBean> modelsList = new ArrayList<ProductModelsBean>();
				Criteria criteria = session.createCriteria(ProductPricesMapper.class);
				criteria.add(Restrictions.eq("productId", productId));
				List<ProductPricesMapper> mappersList = criteria.list();
				log.debug("mapper size : " + mappersList.size());

				if(mappersList.size()>0){
					for(ProductPricesMapper mapper : mappersList){
						ProductModelsBean modelsBean = new ProductModelsBean();
						modelsBean.setProductPricesMapperId(mapper.getPpmId());
						modelsBean.setProductId(productId);
						modelsBean.setColor(mapper.getProductColor());
						modelsBean.setSizeNumber(mapper.getProductSizeNumber());
						modelsBean.setSizeText(mapper.getProductSizeText());
						modelsBean.setProductPrice(mapper.getProductPrice());
						modelsBean.setDiscountRate(mapper.getDiscountRate());
						float discountedPrice2 = (float) ((Float) mapper.getProductPrice() - (mapper.getProductPrice()*(mapper.getDiscountRate()/100)));
						modelsBean.setDiscountedPrice(discountedPrice2);
						modelsBean.setImage1(mapper.getImage1());
						modelsBean.setImage2(mapper.getImage2());
						modelsBean.setImage3(mapper.getImage3());
						modelsBean.setImage4(mapper.getImage4());
						modelsBean.setImage5(mapper.getImage5());
						modelsBean.setImage6(mapper.getImage6());
						modelsBean.setImage7(mapper.getImage7());
						modelsBean.setImage8(mapper.getImage8());
						modelsBean.setImage9(mapper.getImage9());
						modelsBean.setImage10(mapper.getImage10());
						modelsList.add(modelsBean);
						modelsBean = null;
					}
				}
				else if(mappersList.isEmpty()){
					modelsList = null;
				}
				productBean.setModelsList(modelsList);
			}
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting product failed : " + re);
			throw re;
		}
		finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return productBean;
	}
	
	public ProductListBean getSellerProductsV2(int sellerId){
		log.debug("getting seller products");
		ProductListBean listBean = new ProductListBean();
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.getCurrentSession();
			transaction = session.beginTransaction();
			
			EngageSeller seller = (EngageSeller) session
					.get("com.limitless.services.engage.dao.EngageSeller", sellerId);
			if(seller!=null){
				listBean.setSellerId(sellerId);
				listBean.setCitrusSellerId(seller.getCitrusSellerId());
				listBean.setSellerName(seller.getSellerShopName());
				listBean.setSellerCity(seller.getSellerCity());
				List<ProductsCategoryListBean> categoryProductList = new ArrayList<ProductsCategoryListBean>();
				Criteria criteria = session.createCriteria(ProductCategory.class);
				criteria.add(Restrictions.eq("sellerId", sellerId));
				List<ProductCategory> categoryList = criteria.list();
				log.debug("category size : " + categoryList.size());
				if (categoryList.size() > 0) {
					for (ProductCategory category : categoryList) {
						ProductsCategoryListBean categoryBean = new ProductsCategoryListBean();
						categoryBean.setCategoryId(category.getProductCategoryId());
						categoryBean.setProductCategorName(category.getProductCategoryName());
						Criteria criteria2 = session.createCriteria(ProductSubcategory.class);
						criteria2.add(Restrictions.eq("productCategoryId", category.getProductCategoryId()));
						List<ProductSubcategory> subcategoryList = criteria2.list();
						log.debug("sub category size : " + subcategoryList.size());
						if (subcategoryList.size() > 0) {
							List<ProductSubCategoryListBean> scListBean = new ArrayList<ProductSubCategoryListBean>();
							for (ProductSubcategory subcategory : subcategoryList) {
								ProductSubCategoryListBean subCategoryBean = new ProductSubCategoryListBean();
								subCategoryBean.setCategoryId(subcategory.getProductCategoryId());
								subCategoryBean.setSubcategoryId(subcategory.getProductScId());
								subCategoryBean.setSubcategoryName(subcategory.getProductScName());
								List<ProductBean> subcategoryProductList = new ArrayList<ProductBean>();
								Criteria criteria3 = session.createCriteria(Product.class);
								criteria3.add(Restrictions.eq("subcategoryId", subcategory.getProductScId()));
								List<Product> productList = criteria3.list();
								log.debug("products list size : " + productList.size());
								if (productList.size() > 0) {
									for (Product product : productList) {
										ProductBean bean = new ProductBean();
										bean.setProductId(product.getProductId());
										bean.setParentProductId(product.getParentProductId());
										bean.setProductName(product.getProductName());
										bean.setProductDescription(product.getProductDescription());
										bean.setProductPrice(product.getProductPrice());
										bean.setDiscountRate(product.getDiscountRate());
										float discountedPrice = (float) ((Float) product.getProductPrice()
												- (product.getProductPrice() * (product.getDiscountRate() / 100)));
										bean.setDiscountedPrice(discountedPrice);
										bean.setProduct_image(product.getProduct_image());
										bean.setProductInStock(product.getProductInStock());
										bean.setCategoryId(product.getCategoryId());
										bean.setGroupId(product.getGroupId());
										bean.setIsDefault(product.getIsDefault());
										bean.setProductColor(product.getProductColor());
										bean.setProductSizeText(product.getProductSizeText());
										bean.setProductSizeNumber(product.getProductSizeNumber());
										bean.setImage1(product.getImage1());
										bean.setImage2(product.getImage2());
										bean.setImage3(product.getImage3());
										bean.setImage4(product.getImage4());
										bean.setImage5(product.getImage5());
										bean.setImage6(product.getImage6());
										bean.setImage7(product.getImage7());
										bean.setImage8(product.getImage8());
										bean.setImage9(product.getImage9());
										bean.setImage10(product.getImage10());
										subcategoryProductList.add(bean);
										bean = null;
									}
									subCategoryBean.setProductsList(subcategoryProductList);
								}
								scListBean.add(subCategoryBean);
								subCategoryBean = null;
							}
							categoryBean.setSubcategoryList(scListBean);
						} else if (subcategoryList.isEmpty()) {
							List<ProductBean> productList = new ArrayList<ProductBean>();
							Criteria criteria4 = session.createCriteria(Product.class);
							criteria4.add(Restrictions.eq("catergoryId", category.getProductCategoryId()));
							List<Product> productList2 = criteria4.list();
							log.debug("products list size : " + productList2.size());
							if (productList2.size() > 0) {
								for (Product product : productList2) {
									ProductBean bean = new ProductBean();
									bean.setProductId(product.getProductId());
									bean.setParentProductId(product.getParentProductId());
									bean.setProductName(product.getProductName());
									bean.setProductDescription(product.getProductDescription());
									bean.setProductPrice(product.getProductPrice());
									bean.setDiscountRate(product.getDiscountRate());
									float discountedPrice = (float) ((Float) product.getProductPrice()
											- (product.getProductPrice() * (product.getDiscountRate() / 100)));
									bean.setDiscountedPrice(discountedPrice);
									bean.setProduct_image(product.getProduct_image());
									bean.setProductInStock(product.getProductInStock());
									bean.setCategoryId(product.getCategoryId());
									bean.setGroupId(product.getGroupId());
									bean.setIsDefault(product.getIsDefault());
									bean.setProductColor(product.getProductColor());
									bean.setProductSizeText(product.getProductSizeText());
									bean.setProductSizeNumber(product.getProductSizeNumber());
									bean.setImage1(product.getImage1());
									bean.setImage2(product.getImage2());
									bean.setImage3(product.getImage3());
									bean.setImage4(product.getImage4());
									bean.setImage5(product.getImage5());
									bean.setImage6(product.getImage6());
									bean.setImage7(product.getImage7());
									bean.setImage8(product.getImage8());
									bean.setImage9(product.getImage9());
									bean.setImage10(product.getImage10());
									productList.add(bean);
									bean = null;
								}
								categoryBean.setProductsList(productList);
							}
						}
						categoryProductList.add(categoryBean);
						categoryBean = null;
					}
					listBean.setCategoryList(categoryProductList);
				} else if(categoryList.isEmpty()) {
					List<ProductBean> beanList = new ArrayList<ProductBean>();
					Criteria criteria5 = session.createCriteria(Product.class);
					criteria5.add(Restrictions.eq("sellerId", sellerId));
					List<Product> productList = criteria5.list();
					log.debug("product size :" + productList.size());
					if(productList.size()>0){
						for(Product product : productList){
							ProductBean bean = new ProductBean();
							bean.setProductId(product.getProductId());
							bean.setParentProductId(product.getParentProductId());
							bean.setProductName(product.getProductName());
							bean.setProductDescription(product.getProductDescription());
							bean.setProductPrice(product.getProductPrice());
							bean.setDiscountRate(product.getDiscountRate());
							float discountedPrice = (float) ((Float) product.getProductPrice()
									- (product.getProductPrice() * (product.getDiscountRate() / 100)));
							bean.setDiscountedPrice(discountedPrice);
							bean.setProduct_image(product.getProduct_image());
							bean.setProductInStock(product.getProductInStock());
							bean.setCategoryId(product.getCategoryId());
							bean.setGroupId(product.getGroupId());
							bean.setIsDefault(product.getIsDefault());
							bean.setProductColor(product.getProductColor());
							bean.setProductSizeText(product.getProductSizeText());
							bean.setProductSizeNumber(product.getProductSizeNumber());
							bean.setImage1(product.getImage1());
							bean.setImage2(product.getImage2());
							bean.setImage3(product.getImage3());
							bean.setImage4(product.getImage4());
							bean.setImage5(product.getImage5());
							bean.setImage6(product.getImage6());
							bean.setImage7(product.getImage7());
							bean.setImage8(product.getImage8());
							bean.setImage9(product.getImage9());
							bean.setImage10(product.getImage10());
							beanList.add(bean);
							bean = null;
						}
						listBean.setProductsList(beanList);
					}
				}
				listBean.setMessage("Success");
			}
			else{
				listBean.setMessage("Failed");
			}
			transaction.commit();
		}
		catch(RuntimeException re){
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("getting products failed : " + re);
			throw re;
		}
		finally {
			if(session != null && session.isOpen()){
				session.close();
			}
		}
		return listBean;
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
package com.limitless.services.payment.PaymentService.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.payment.PaymentService.PaymentsSettlementResponseBean;
import com.limitless.services.payment.PaymentService.TxnSettlementResponseBean;
import com.limitless.services.payment.PaymentService.dao.PaymentSettlementManager;

@Path("/settle")
public class PaymentSettlementResource {
	
	final static Logger logger = Logger.getLogger(PaymentResource.class);
	
	@GET
	@Path("/payments/{days}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PaymentsSettlementResponseBean> settlePaymenets(@PathParam("days") int days) throws Exception{
		List<PaymentsSettlementResponseBean> respBeanList = new ArrayList<PaymentsSettlementResponseBean>();
		try{
			PaymentsSettlementResponseBean bean = new PaymentsSettlementResponseBean();
			if(days == 0){
//				Calendar calendar = Calendar.getInstance();
//				int hour = calendar.get(Calendar.HOUR_OF_DAY);
//				int minute = calendar.get(Calendar.MINUTE);
//				if(hour == 12 && (minute >= 45 && minute <=59)){
					PaymentSettlementManager manager = new PaymentSettlementManager();
					respBeanList = manager.doSettlement(1);
//				}
//				else{
//					bean.setMessage("API call not allowed at this time");
//					respBeanList.add(bean);
//				}
			}
			else if(days == 1){
				PaymentSettlementManager manager = new PaymentSettlementManager();
				respBeanList = manager.doSettlement(2);
			}
			else if(days == 2){
				PaymentSettlementManager manager = new PaymentSettlementManager();
				respBeanList = manager.doSettlement(3);
			}
			else if(days == 3){
				PaymentSettlementManager manager = new PaymentSettlementManager();
				respBeanList = manager.doSettlement(4);
			}
			else if(days == 4){
				PaymentSettlementManager manager = new PaymentSettlementManager();
				respBeanList = manager.doSettlement(4);
			}
			else{
				bean.setMessage("Not more than 4 days allowed!");
				respBeanList.add(bean);
			}
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBeanList;
	}
	
	@GET
	@Path("/txn/{txnId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TxnSettlementResponseBean settleTxn(@PathParam("txnId") int txnId) throws Exception{
		TxnSettlementResponseBean responseBean = new TxnSettlementResponseBean();
		try{
			PaymentSettlementManager manager = new PaymentSettlementManager();
			responseBean = manager.settleTxnById(txnId);
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return responseBean;
	}
}

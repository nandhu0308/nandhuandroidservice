package com.limitless.services.payment.PaymentService.resources;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.limitless.services.payment.PaymentService.PaymentsSettlementResponseBean;
import com.limitless.services.payment.PaymentService.dao.PaymentSettlementManager;

@Path("/settle")
public class PaymentSettlementResource {
	
	final static Logger logger = Logger.getLogger(PaymentResource.class);
	
	@GET
	@Path("/payments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PaymentsSettlementResponseBean> settlePaymenets() throws Exception{
		List<PaymentsSettlementResponseBean> respBeanList = new ArrayList<PaymentsSettlementResponseBean>();
		try{
			PaymentsSettlementResponseBean bean = new PaymentsSettlementResponseBean();
			Calendar calendar = Calendar.getInstance();
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			if(hour == 12 && (minute >= 45 && minute <=59)){
				PaymentSettlementManager manager = new PaymentSettlementManager();
				respBeanList = manager.doSettlement();
			}
			else{
				bean.setMessage("API call not allowed at this time");
				respBeanList.add(bean);
			}
		}
		catch(Exception e){
			logger.error("API Error", e);
			throw new Exception("Internal Server Error");
		}
		return respBeanList;
	}
}

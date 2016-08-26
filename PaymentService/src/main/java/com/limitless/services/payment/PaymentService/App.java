package com.limitless.services.payment.PaymentService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	/*ClientConfig clientConfig = new DefaultClientConfig();              
    	clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
    	Client client = Client.create(clientConfig);

    	WebResource webResource = client.resource("http://ec2-54-186-117-110.us-west-2.compute.amazonaws.com:8080/LLCWeb/payment/trans");

    	PaymentTxnBean bean = new PaymentTxnBean();
    	bean.setSellerId(Integer.parseInt("1138"));
    	bean.setSellerName("Dummy");
    	bean.setTxnAmount(Float.parseFloat("100.0"));
    	bean.setTxnId((int)System.currentTimeMillis());

    	ClientResponse splitResponse = webResource.type("application/json").post(ClientResponse.class, bean);
    	System.out.println(splitResponse.getEntity(String.class).toString());*/
    	
    	/*ClientConfig clientConfig = new DefaultClientConfig();              
    	clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);     
    	Client client = Client.create(clientConfig);

    	String txnId = "26470";
    	WebResource webResource = client.resource("http://ec2-54-186-117-110.us-west-2.compute.amazonaws.com:8080/LLCWeb/payment/trans").path(txnId).path("split");
    	SplitResponseBean bean = webResource.type("application/json").accept("application/json").post(SplitResponseBean.class);

    	System.out.println("Split Id" + bean.getSplitId());*/
    }
}

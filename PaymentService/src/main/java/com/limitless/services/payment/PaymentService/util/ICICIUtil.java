package com.limitless.services.payment.PaymentService.util;

import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.MultivaluedMap;

import sun.misc.BASE64Encoder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class ICICIUtil {
	
	public static final String RANDOMKEY = "aaaaaaaaaaaaaaaa";
	
	public static final String SALTKEY = "ICICI_BANK1_SALT";
	
	public static final String APIKEY = "1001";
	
	public static final String APIVERSION = "1.8";
	
	public static final String MERCHANTID = "MID001";
	
	public static final String ICICIUPIURL = "https://imob.icicibank.com/isdkCUG/GatewayController";

	public static String encrypt(String key, String inputString) {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),
					"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(1, secretKeySpec);
			byte[] aBytes = cipher.doFinal(inputString.getBytes());
			BASE64Encoder encoder = new BASE64Encoder();
			String base64 = encoder.encode(aBytes).toString();
			base64 = URLEncoder.encode(base64, "UTF-8");
			return base64;
		} catch (Exception ex) {
			System.out.println("Exception occured in encrypt : "
					+ ex.toString());
		}
		return null;
	}

	public static String generateChecksum(String inputString) {
		String checksum = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			checksum = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checksum;
	}

	public static void main(String[] args) {
/*		String randomKey = "aaaaaaaaaaaaaaaa";

		String inputString = "APIKey=1001&SDKVersion=1.8&Package=com.limitless.engage&OrderId=ORD56792&MID=MID001&TAmt=145.50&Currency=INR&CustomField1=&CustomField2=&CustomField3=";

		String encrypt = encrypt(randomKey, inputString);

		System.out.println("Encrypted String: " + encrypt);

		String saltKey = "ICICI_BANK1_SALT";

		String salt = encrypt(saltKey, randomKey);

		System.out.println("Salt: " + salt);
		
		String chkSum = generateChecksum(encrypt);
		
		System.out.println("Chksum: " + chkSum);
		
		ClientConfig clientConfig = new DefaultClientConfig();
		//clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("https://imob.icicibank.com/isdkCUG/GatewayController");
		
		MultivaluedMap<String, String> map = new MultivaluedMapImpl();
		map.add("OPERATIONID", "ISDK0008");
		map.add("inputString", encrypt);
		map.add("salt", salt);
		map.add("chkSum", chkSum);
		
		ClientResponse response = webResource.type("application/x-www-form-urlencoded").
				post(ClientResponse.class, map);
		
		String output = response.getEntity(String.class).toString();
		
		System.out.println(output);
		
		//String output = "<MB><RQ><salt><![CDATA[7Mgo9v%2BJGbVlx1xIjy0YtZUtYUv6qzulawOppBr9Klw%3D]]></salt><chkSum><![CDATA[6442e6c9b178a2ef7798dad295a22131]]></chkSum><inputString><![CDATA[dPEZCREDaggYmrlMbOokcixpNgEpcqtRqcN909LmSXiHwzr2aT%2FpEQRXrF%2BCYZXPFdwxUIImYXvn%0D%0ANubYJUxKHg312xVIJ9ntq%2FKlje4Dv36dN2r4FjZ1H7u2Wwm9CVGX54z3Smx8iOolHHZ8gM%2BBVyTS%0D%0A9Fn1%2B1x8RSojCrEh5KVhLU2%2BT%2FIFuXyAKL703aANxOro8e%2BmS10iPIUCtPeTow%3D%3D]]></inputString><OPERATIONID><![CDATA[ISDK0008]]></OPERATIONID></RQ><RS><OperationStatus>0000</OperationStatus><OperationCode>0000</OperationCode><OperationErrorMessage>Order details saved successfully for merchant.				</OperationErrorMessage></RS></MB>";
		
		System.out.println(output.substring(output.lastIndexOf("<OperationStatus>") + 17, output.lastIndexOf("</OperationStatus>"))); */
		
	}
}

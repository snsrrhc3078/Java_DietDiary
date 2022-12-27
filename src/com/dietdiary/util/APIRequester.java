package com.dietdiary.util;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;

public class APIRequester {
	
	private String requestURL;
	private String serviceKey;
	private StringBuilder totalURL;
	StringBuilder result = new StringBuilder();
	public APIRequester(String url, String key) {
        totalURL = new StringBuilder();
        init(url, key);
	}
	public APIRequester() {
		totalURL = new StringBuilder();
	}
	public void init(String url, String key) {
		totalURL.setLength(0);
		result.setLength(0);
		setRequestURL(url);
		setServiceKey(key);
		addRequestURL();
        addServiceKey();
	}
	public void init() {
		totalURL.setLength(0);
		result.setLength(0);
		addRequestURL();
        addServiceKey();
	}
	
	public void addRequestURL() {
		totalURL.append(requestURL);
	}
	public void addServiceKey() {
		try {
			totalURL.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public void addProperty(String key, String value) {
		try {
			totalURL.append("&" + URLEncoder.encode(key,"UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String request() {
		String result = null;
		HttpURLConnection con = null;
		BufferedReader buffr= null;
		try {
			URL url = new URL(totalURL.toString());
			con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("GET");
			if(con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {
			    buffr = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
//			    buffr = new BufferedReader(new InputStreamReader(con.getErrorStream()));
				throw new MalformedURLException();
			}
			String data = null;
			while(true) {
				data = buffr.readLine();
				if(data==null) break;
				this.result.append(data);
			}
			result = this.result.toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(buffr!=null) {
				try {
					buffr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				con.disconnect();
			}
		}
		return result;
	}
	public String getResult() {
		if(result.length()<1) {
			return null;
		}else {
			return result.toString();
		}
	}
	public String getRequestURL() {
		return requestURL;
	}
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}
	public String getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}
	public StringBuilder getTotalURL() {
		return totalURL;
	}
	
	
	
//	public static void main(String[] args) {
//		String url = "http://apis.data.go.kr/1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1";
//		String key = "BjqQYzHtLjlq2NcHvzh%2BC1B%2FhWHIPERqruRu2rOMsG0bFYCyHaeFqy%2BKcHPHEPanneBg0nBjmqKSs82VGw7s%2BQ%3D%3D";
//		APIRequester f= new APIRequester();
//		f.init(url, key);
//		f.addProperty("desc_kor", "오레오");
//		f.addProperty("type", "json");
//		f.request();
//		System.out.println(f.getResult());
//	}
}

package eu.openminted.omtdcache.core;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

public class Main {

	public static void main(String args[]){
		
		CacheFactory factory = new CacheFactory();
		
		CacheProperties cacheProperties = new CacheProperties();
		
		cacheProperties.setCacheID("OMTDCache");
		cacheProperties.setRestEndpoint("http://localhost:8080/");
		cacheProperties.setType(CacheOMTDStoreImpl.class.getName());
		cacheProperties.setBuckets(100);
		cacheProperties.setOverwrite(true);
		
		Cache myCache = factory.getCache(cacheProperties);
		
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				
			for(int i = 0; i < 50000; i++){
			
				String publication = RandomStringUtils.randomAlphanumeric(10000);
				messageDigest.update(publication.getBytes());
				String hashID = new BigInteger(1, messageDigest.digest()).toString(16);
							
				boolean contains = myCache.contains(hashID);
				System.out.println(hashID + " Contained:" + contains);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}

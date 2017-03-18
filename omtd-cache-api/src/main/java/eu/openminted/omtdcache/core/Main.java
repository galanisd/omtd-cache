package eu.openminted.omtdcache.core;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

public class Main {

	public static void main(String args[]){
						
		// Define cache properties
		CacheProperties cacheProperties = new CacheProperties();		
		cacheProperties.setCacheID("OMTDCache");
		cacheProperties.setRestEndpoint("http://localhost:8080/");
		cacheProperties.setType(CacheOMTDStoreImpl.class.getName());
		cacheProperties.setBuckets(100);
		cacheProperties.setOverwrite(true);
		
		// Create a cache with these properties.
		Cache myCache = CacheFactory.getCache(cacheProperties);
		
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				
			for(int i = 0; i < 10000; i++){			
				String fakePublication = RandomStringUtils.randomAlphanumeric(10000);
				messageDigest.update(fakePublication.getBytes());
				String dataID = new BigInteger(1, messageDigest.digest()).toString(16);
							
				boolean existsInCache = myCache.contains(dataID);
				System.out.println(dataID + " Contained:" + existsInCache);
				
				if(!existsInCache){
					Data data = new Data(fakePublication);
					myCache.putData(dataID, data);
					
					//if(myCache.contains(dataID)){
						
					//}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}

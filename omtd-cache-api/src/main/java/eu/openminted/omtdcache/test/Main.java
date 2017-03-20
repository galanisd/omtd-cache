package eu.openminted.omtdcache.test;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

import eu.openminted.omtdcache.core.Cache;
import eu.openminted.omtdcache.core.CacheFactory;
import eu.openminted.omtdcache.core.CacheOMTDStoreImpl;
import eu.openminted.omtdcache.core.CacheProperties;
import eu.openminted.omtdcache.core.Data;

public class Main {	
	// == === ==	
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
		
		// Run a simulation.
		int dataChunksNum = 10000;
		int numOfChars = 200000;		
		storeDataInCacheSimulation(myCache, dataChunksNum, numOfChars);

	}
	
	// == === ==
	// == === ==	
	/**
	 * Simulates the data caching in Cache.
	 * @param myCache
	 * @param dataChunksNum Number of data chunks to be saved in Cache.
	 * @param numOfChars Number of chars per chunk.
	 */
	public static void storeDataInCacheSimulation(Cache myCache, int dataChunksNum, int numOfChars){
			 
		int numOfDataChunksThatAlreadyExistInCache = 0;
		int numOfSuccesfullyInsertedDataChunks = 0;
				
		try{
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
				
			for(int i = 0; i < dataChunksNum; i++){
				
				String dataStr = RandomStringUtils.randomAlphanumeric(numOfChars);
				messageDigest.update(dataStr.getBytes());
				String dataID = new BigInteger(1, messageDigest.digest()).toString(16);
							
				boolean existsInCache = myCache.contains(dataID);
				System.out.println(dataID + " exists in Cache:" + existsInCache);
				
				if(!existsInCache){
					Data data = new Data(dataStr);
					myCache.putData(dataID, data);
					
					if(myCache.contains(dataID)){
						System.out.println(dataID + " was inserted in Cache:" + existsInCache);
						numOfSuccesfullyInsertedDataChunks++;
					}
				}else{
					numOfDataChunksThatAlreadyExistInCache++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("Total:" + dataChunksNum);
		System.out.println("numOfDataChunksThatAlreadyExistInCache:" + numOfDataChunksThatAlreadyExistInCache);
		System.out.println("numOfSuccesfullyInsertedDataChunks:" + numOfSuccesfullyInsertedDataChunks);
	}
	// == === ==
}

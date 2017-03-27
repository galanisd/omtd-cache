package eu.openminted.omtdcache;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.lang3.RandomStringUtils;

import eu.openminted.omtdcache.core.Cache;
import eu.openminted.omtdcache.core.CacheFactory;
import eu.openminted.omtdcache.core.CacheOMTDStoreImpl;
import eu.openminted.omtdcache.core.CacheProperties;
import eu.openminted.omtdcache.core.Data;
import eu.openminted.store.common.StoreResponse;

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
	 * Runs a simulation of data caching.
	 * @param myCache A {@code Cache} handler.
	 * @param dataChunksNum Number of data chunks to be saved in Cache.
	 * @param numOfChars Number of chars per chunk.
	 */
	public static void storeDataInCacheSimulation(Cache myCache, int dataChunksNum, int numOfChars){
			 
		int numOfDataChunksThatAlreadyExistInCache = 0;
		int numOfSuccesfullyInsertedDataChunks = 0;
		int ties = 0;
		int numOfSuccesfullyRemoved = 0;
		
		try{
			CacheDataID cacheDataIDProvider = new CacheDataIDMD5(); 
				
			for(int i = 0; i < dataChunksNum; i++){
				
				String dataStr = RandomStringUtils.randomAlphanumeric(numOfChars);				
				String dataID = cacheDataIDProvider.getID(dataStr.getBytes()); 
							
				boolean existsInCache = myCache.contains(dataID);
				System.out.println(dataID + " exists in Cache:" + existsInCache);
				
				// Not exists in Cache.
				if(!existsInCache){
					
					// Put it
					Data data = new Data(dataStr);
					myCache.putData(dataID, data);
					
					// Check if was inserted.
					if(myCache.contains(dataID)){
												
						System.out.println(dataID + " was inserted in Cache:" + existsInCache);
						numOfSuccesfullyInsertedDataChunks++;
						// Retrieve data.
						Data retrievedData = myCache.getData(dataID);
						if(retrievedData != null){
							String retrievedStr = new String(retrievedData.getBytes());
							// Check if the retrieved data are the same with original ones. 
							if(dataStr.equals(retrievedStr)){
								ties++;
							}
						}
												
						//if(myCache.remove(dataID)){
						//	numOfSuccesfullyRemoved++;
						//}
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
		System.out.println("ties:" + ties);
		System.out.println("numOfSuccesfullyRemoved:" + numOfSuccesfullyRemoved);
	}
	// == === ==
}

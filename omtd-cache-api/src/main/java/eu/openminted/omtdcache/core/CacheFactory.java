package eu.openminted.omtdcache.core;

import eu.openminted.store.common.OMTDStoreHandler;
import eu.openminted.store.restclient.StoreRESTClient;

/**
 * @author galanisd
 *
 */
public class CacheFactory {
			
	/**
	 * Creates a Cache object as this is specified from {@code CacheProperties}
	 * @param cacheProperties
	 * @return
	 */
	public static Cache getCache(CacheProperties cacheProperties){
		
		if(cacheProperties.getType().equalsIgnoreCase(CacheOMTDStoreImpl.class.getName())){
			OMTDStoreHandler handler = new StoreRESTClient(cacheProperties.getRestEndpoint());
			return new CacheOMTDStoreImpl(handler, cacheProperties.getCacheID(), cacheProperties.isOverwrite(), cacheProperties.getBuckets());
		}else{
			return null;
		}
	}
	
}

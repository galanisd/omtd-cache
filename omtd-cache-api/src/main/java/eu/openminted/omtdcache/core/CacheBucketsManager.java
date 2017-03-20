package eu.openminted.omtdcache.core;

/**
 * @author galanisd
 *
 */
public class CacheBucketsManager {
	
	private int buckets;
	
	public CacheBucketsManager(int b){
		this.buckets = b;
	}
	
	public int getBucket(String str){
		int i = Math.abs(str.hashCode());		
		int bucket = i % buckets;
		return bucket;
	}
}

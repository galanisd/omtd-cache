package eu.openminted.omtdcache.core;

/**
 * @author galanisd
 *
 */
public class Buckets {
	
	private int buckets;
	
	public Buckets(int b){
		this.buckets = b;
	}
	
	public int getBucket(String str){
		int i = str.hashCode();		
		int bucket = i % buckets;
		return bucket;
	}
}

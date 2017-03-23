package eu.openminted.omtdcache.core;

/**
 * @author galanisd
 *
 */
public class CacheProperties {
	
	private String cacheID;
	private String type;
	
	private boolean overwrite;
	private int buckets;
	private String restEndpoint;  
	
	
	public String getCacheID() {
		return cacheID;
	}

	public void setCacheID(String cacheID) {
		this.cacheID = cacheID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRestEndpoint() {
		return restEndpoint;
	}

	public void setRestEndpoint(String restEndpoint) {
		this.restEndpoint = restEndpoint;
	}

	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public int getBuckets() {
		return buckets;
	}

	public void setBuckets(int buckets) {
		this.buckets = buckets;
	}	
	
}

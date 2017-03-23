package eu.openminted.omtdcache;

public interface CacheDataID {
	
	/**
	 * Returns an ID based on the data
	 * @param bytes from data.
	 * @return the generated ID.
	 */
	public String getID(byte[] data);
}

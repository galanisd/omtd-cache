package eu.openminted.omtdcache.core;

import java.util.Iterator;

/**
 * This interface defines the {@code Cache} API of OMTD. {@code Cache} stores
 * a piece of data (e.g. a publication) that has already been transfered from an external source. By using {@code Cache} we avoid 
 * fetching the same data multiple times. Each piece of data {@code data} has a unique id ({@code dataID}); for example, this can be 
 * calculated using an appropriate hash function.
 * @author galanisd 
 */
public interface Cache {
	
	/**
	 * Determines whether the cache contains a {@code dataID}
	 * @param hashKey
	 * @return true or false depending on whether {@code dataID} is contained in the {@code Cache} store. 
	 */
	public boolean contains(String dataID);

	/**
	 * Retrieves the {@code data} that correspond to {@code dataHash}.
	 * @param dataHash
	 * @return a string representation the data.
	 */
	public String getData(String dataHash);
	
	/**
	 * Stores the {@code data} that correspond to {@code dataHash} in the {@code Cache}.
	 * @param dataHash
	 * @param data
	 * @return
	 */
	public boolean putData(String dataID, String data);
	
	/**
	 * Lists all {@code dataID}'s of the {@code Cache}.
	 * @return
	 */
	public Iterator<String> getIDs();
	
	
	/**
	 * Removes a piece of data from the {@code Cache}
	 * @return true or false depending on whether the removal has succeeded or not.
	 */
	public boolean remove(String dataID);
}

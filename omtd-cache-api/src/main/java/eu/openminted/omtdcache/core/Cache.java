package eu.openminted.omtdcache.core;

import java.util.Iterator;

/**
 * This interface defines the {@code Cache} API of OMTD. {@code Cache} stores
 * a piece of data (e.g. a publication) that has already been transfered from an external source; this way we avoid 
 * fetching the same data multiple times. Each piece of {@code data} is thought to have a unique {@code dataID}. 
 * For example, this can be calculated using an appropriate hash function.
 * @author galanisd 
 */
public interface Cache {
	
	/**
	 * Determines whether the cache contains a {@code dataID} and the respective data.
	 * @param dataID
	 * @return true or false depending on whether {@code dataID} is contained in the {@code Cache} store. 
	 */
	public boolean contains(String dataID);

	/**
	 * Removes a piece of data from the {@code Cache}.
	 * @param dataID
	 * @return true or false depending on whether the removal has succeeded or not.
	 */
	public boolean remove(String dataID);
	
	/**
	 * Lists all {@code dataID}s of the {@code Cache}.
	 * @return an iterator over all IDs in the Store.
	 */
	public Iterator<String> getIDs();
	
	/**
	 * Retrieves the {@code data} that correspond to {@code dataHash}.
	 * @param dataHash
	 * @return a string representation of the data.
	 */
	public String getData(String dataHash);
	
	/**
	 * Stores the {@code data} that correspond to {@code dataHash} in the {@code Cache}.
	 * @param dataHash
	 * @param data
	 * @return true or false depending on whether the put action has succeeded or not.
	 */
	public boolean putData(String dataID, String data);
		
	/**
	 * Deletes everything from the {@code dataID}.
	 * @return true or false depending on whether the removal has succeeded or not.
	 */
	public boolean removeAll();

}

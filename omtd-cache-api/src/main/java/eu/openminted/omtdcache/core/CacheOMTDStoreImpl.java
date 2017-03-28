package eu.openminted.omtdcache.core;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import eu.openminted.store.common.OMTDStoreHandler;
import eu.openminted.store.common.StoreResponse;

/**
 * 
 * @author galanisd
 *
 */
public class CacheOMTDStoreImpl implements Cache{	
	
	private final static Logger log = LoggerFactory.getLogger(CacheOMTDStoreImpl.class);
	
	private OMTDStoreHandler OMTDStoreHandler;		
	private String cacheID;
	private CacheBucketsManager bucketsManager;
	
	/**
	 * Constructor
	 * @param OMTDStoreHandler
	 * @param cacheID
	 * @param overwrite
	 */
	public CacheOMTDStoreImpl(OMTDStoreHandler OMTDStoreHandler, String cacheID, boolean overwrite, int buckets){
		this.OMTDStoreHandler = OMTDStoreHandler;
		this.cacheID = cacheID;
		this.bucketsManager = new CacheBucketsManager(buckets);
		
		if(overwrite){
			//delete & re-create.
			this.OMTDStoreHandler.deleteArchive(cacheID);
			this.OMTDStoreHandler.createArchive(cacheID);
		}else{
			StoreResponse resp = OMTDStoreHandler.archiveExists(cacheID);
			// if not exists create it.
			if(resp.getResponse().equalsIgnoreCase("false")){
				this.OMTDStoreHandler.createArchive(cacheID);
			}
		}
	}				

	// == === == === == === == === ==
	// Cache implementation methods.
	// == === == === == === == === ==	
	
	@Override
	public boolean contains(String dataID) {
		String subArchiveId = buildArchiveName(dataID);		
		StoreResponse resp = OMTDStoreHandler.fileExistsInArchive(subArchiveId, dataID);
		return resp != null && resp.getResponse().equalsIgnoreCase("true");		
	}

	@Override
	public boolean remove(String dataID) {
		boolean dataIDExistsInCache = contains(dataID); 
		if(dataIDExistsInCache){
			String subArchiveId = buildArchiveName(dataID);
			StoreResponse resp =  OMTDStoreHandler.deleteFile(subArchiveId, dataID);
			return resp.getResponse().equalsIgnoreCase("true");
		}else{
			return false;
		}
	}

	@Override
	public boolean putData(String dataID, Data data) {
		String subArchiveId = buildArchiveName(dataID);	
		StoreResponse resp = OMTDStoreHandler.storeFile(data.getBytes(), subArchiveId, dataID);
		return resp.getResponse().equalsIgnoreCase("true");
	}

	@Override
	public Data getData(String dataID) {		
		String subArchiveId = buildArchiveName(dataID);
		String fileName = subArchiveId + "/" + dataID;
		Data data = null;
		File temp = null;
		
		try{
			temp = File.createTempFile(dataID, ".tmp");
			log.debug("Temp file : " + temp.getAbsolutePath());									
			StoreResponse resp = OMTDStoreHandler.downloadFile(fileName, temp.getAbsolutePath());
			
			if(resp.getResponse().equalsIgnoreCase("true")){
				byte [] bytes = Files.readAllBytes(Paths.get(temp.getAbsolutePath()));
				data = new Data(bytes);
			}
						
		}catch(Exception e){
			return null;	
		}finally{
			if(temp != null && temp.exists()){
				boolean tempFileDeleted = temp.delete();
				log.debug("Temp file deleted: " + tempFileDeleted);
			}	
		}
				
		return data;
	}
	
	@Override
	public boolean removeAll() {
		StoreResponse resp = OMTDStoreHandler.deleteArchive(cacheID);
		return resp.getResponse().equalsIgnoreCase("true");
	}
	
	@Override
	public Iterator<String> getIDs() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// == === == === == === == === ==
	// == === == === == === == === ==	
	private String buildArchiveName(String dataID){
		String archiveID = cacheID + "/" + bucketsManager.getBucket(dataID) + "";
		return archiveID;
	}
}

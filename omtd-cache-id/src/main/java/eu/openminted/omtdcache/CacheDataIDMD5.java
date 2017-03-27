package eu.openminted.omtdcache;

import java.math.BigInteger;
import java.security.MessageDigest;

public class CacheDataIDMD5 implements CacheDataID{

	private MessageDigest messageDigest;
	
	public CacheDataIDMD5() {	
		try{
			messageDigest = MessageDigest.getInstance("MD5");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	@Override
	public String getID(byte[] data) {
		messageDigest.update(data);
		String dataID = new BigInteger(1, messageDigest.digest()).toString(16);
		
		return dataID;
	}

}

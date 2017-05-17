package eu.openminted.omtdcache;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

public class CacheDataIDSHA1 implements CacheDataID{
	
	public CacheDataIDSHA1() {
	
	}

	@Override
	public String getID(byte[] data) {
		return DigestUtils.sha1Hex(data);
	}
	
}

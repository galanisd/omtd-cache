package eu.openminted.omtdcache.core;

/**
 * @author galanisd
 *
 */
public class Data {	
	private byte[] bytes;
	
	public Data(String data){
		bytes = data.getBytes();
	}

	public Data(byte[] bytes){
		this.bytes = bytes;
	}
	
	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
		
}

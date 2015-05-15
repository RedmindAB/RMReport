package se.redmind.rmtest.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Gzipper {

	 public String compress(String str) {
	        if (str == null || str.length() == 0) {
	            return str;
	        }
	        String outStr = null;
			try {
				outStr = compress(str.getBytes()).toString("ISO-8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        return outStr;
	     }

	private ByteArrayOutputStream compress(byte[] bytes) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip;
		try {
			gzip = new GZIPOutputStream(out);
			gzip.write(bytes);
			gzip.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out;
	}
	    
	public String decompress(String str) {
	        if (str == null || str.length() == 0) {
	            return str;
	        }
	        String outStr = null;
			try {
				outStr = decompress(str.getBytes("ISO-8859-1"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        return outStr;
	}

	private String decompress(byte[] bytes) {
		GZIPInputStream gis;
		String outStr = "";
		try {
			gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
			BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
			String line;
			while ((line=bf.readLine())!=null) {
				outStr += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return outStr;
	}
	
}

package se.redmind.rmtest.db.jdbm;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jdbm.PrimaryHashMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

public class JDBMConnection {

	private static final String MESSAGE_LIST_NAME = "message";
	
	Logger log = LogManager.getLogger(JDBMConnection.class);
	
	private RecordManager dbCon;

	private PrimaryHashMap<Integer, String> messageMap;
	
	
	public JDBMConnection() {
		dbCon = openDatabase("messageDB");
		messageMap = dbCon.hashMap(MESSAGE_LIST_NAME);
	}
	
	public JDBMConnection(String filename){
		dbCon = openDatabase(filename);
		messageMap = dbCon.hashMap(MESSAGE_LIST_NAME);
	}
	
	private RecordManager openDatabase(String filename){
		try {
			new File(System.getProperty("user.dir")+"/messagedb/").mkdir();
			return RecordManagerFactory.createRecordManager("messagedb/"+filename);
		} catch (IOException e) {
			log.error("Could not create or open JDBM connection: "+e.getMessage()); 
			return null;
		}
	}
	
	public boolean dropDatabase(){
		messageMap.clear();
		return commit();
	}

	public boolean commit() {
		try {
			dbCon.commit();
			return true;
		} catch (IOException e) {
			log.error("failed to commit to JDBM:"+e.getMessage());
			rollback();
			return false;
		}
	}
	
	public boolean rollback(){
		try {
			dbCon.rollback();
			return true;
		} catch (IOException e) {
			log.error(e.getMessage());
			return false;
		}
	}
	
	public int getNextIndex() {
		return messageMap.size()+1;
	}
	
	public int save(String value){
		int key = getNextIndex();
		return save(key,value);
	}
	
	public int save(int key, String value) {
		messageMap.put(key, value);
		return key;
	}

	public String get(int id) {
		return messageMap.get(id);
	}
	
	public int size(){
		return messageMap.size();
	}
}

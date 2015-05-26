package se.redmind.rmtest.db.jdbm;

import java.io.File;
import java.util.SortedMap;

import org.apache.jdbm.DB;
import org.apache.jdbm.DBMaker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JDBMConnection {

	private static final String MESSAGE_LIST_NAME = "message";
	
	Logger log = LogManager.getLogger(JDBMConnection.class);
	
	private DB dbCon;

	private SortedMap<Integer, String> messageMap;
	
	
	public JDBMConnection() {
		dbCon = openDatabase("messageDB");
		messageMap = getMap(MESSAGE_LIST_NAME);
	}
	

	public JDBMConnection(String filename){
		dbCon = openDatabase(filename);
		messageMap = getMap(MESSAGE_LIST_NAME);
	}
	
	private SortedMap<Integer, String> getMap(String messageListName) {
		SortedMap<Integer, String> treeMap = dbCon.getTreeMap(messageListName);
		if (treeMap == null) {
			treeMap = dbCon.createTreeMap(messageListName);
		}
		return treeMap;
	}
	
	private DB openDatabase(String filename){
		File path = new File(System.getProperty("user.dir")+"/messagedb/");
		boolean exists = path.exists();
		if (!exists) {
			path.mkdir();
		}
		return DBMaker.openFile("messagedb/"+filename).closeOnExit().make();
	}
	
	public boolean dropDatabase(){
		dbCon.deleteCollection(MESSAGE_LIST_NAME);
		messageMap = dbCon.createTreeMap(MESSAGE_LIST_NAME);
		return commit();
	}

	public boolean commit() {
		try {
			dbCon.commit();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean rollback(){
		dbCon.rollback();
		return true;
	}
	
	public int getNextIndex() {
		try {
			if (messageMap.isEmpty()) {
				//Bug in the API that ignores the first insert... bad apache.
				messageMap.put(1, "Hack to make it work");
				return 1;
			}
			else {
				return (messageMap.size()+1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return messageMap.size()+1;
		}
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

	public void close() {
		dbCon.close();
	}


	public SortedMap<Integer, String> getMap() {
		return messageMap;
	}
	
	public void deleteDBFiles(){
		dbCon.deleteCollection(MESSAGE_LIST_NAME);
	}


	public boolean isClosed() {
		return dbCon.isClosed();
	}
}

package se.redmind.rmtest.db.jdbm.message;

import java.util.SortedMap;

import se.redmind.rmtest.db.jdbm.JDBMConnection;

public class MessageDAO {

	private JDBMConnection con;
	private static MessageDAO instance;
	
	private MessageDAO() {
		con = new JDBMConnection();
	}
	
	private MessageDAO(String filename){
		con = new JDBMConnection(filename);
	}
	
	private MessageDAO(JDBMConnection con){
		this.con = con;
	}
	
	public static MessageDAO getInstance(){
		if (instance == null) {
			instance = new MessageDAO();
		}
		return instance;
	}
	
	public static MessageDAO getInstance(String filename){
		if (instance == null) {
			instance = new MessageDAO(filename);
		}
		return instance;
	}
	
	public static MessageDAO getInstance(JDBMConnection connection){
		if (instance == null) {
			instance = new MessageDAO(connection);
		}
		return instance;
	}
	
	public void saveIfNotExists(int id, String message){
		String result = con.get(id);
		if (result == null) {
			con.save(id, message);
		}
	}
	
	/**
	 * saves a String in the hashmap, needs to be commited to be persisted.
	 * @param value - the string that should be saved into the database, null or empty values return -1
	 * @return - returns the index of the String, -1 if an error occurs.
	 */
	public int save(String value){
		if (value == null || value.isEmpty()) return -1;
		try {
			return con.save(value);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public void commit(){
		con.commit();
	}

	public String get(int index) {
		return con.get(index);
	}

	public void dropDatabase() {
		con.dropDatabase();
	}
	
	public int getSize(){
		return con.size();
	}

	public void rollback() {
		con.rollback();
	}

	public void close() {
		con.close();
		instance = null;
	}

	public void prinContent() {
		SortedMap<Integer, String> map = con.getMap();
		System.out.println(map);
	}
	
}

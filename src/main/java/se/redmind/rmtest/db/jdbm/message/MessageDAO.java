package se.redmind.rmtest.db.jdbm.message;

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
	 * Persists a String in the database
	 * @param value - the string that should be saved into the database.
	 * @return - returns the index of the String, -1 if an error occurs.
	 */
	public int save(String value){
		try {
			return con.save(value);
		} catch (Exception e) {
			return -1;
		}
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
	
}

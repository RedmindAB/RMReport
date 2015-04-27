package se.redmind.rmtest.db.jdbm.message;

import se.redmind.rmtest.db.jdbm.JDBMConnection;

public class MessageDAO {

	JDBMConnection con;
	
	public MessageDAO() {
		con = new JDBMConnection();
	}
	
	public MessageDAO(JDBMConnection con){
		this.con = con;
	}
	
	public void saveIfNotExists(String hash, String message){
		String result = con.get(hash);
		if (result == null) {
			con.save(hash, message);
		}
	}
	
}

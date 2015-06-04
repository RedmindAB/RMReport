package se.redmind.rmtest.web.route.api.util.timestamp;

import java.util.Date;

public class TimestampUtil {

	private static TimestampUtil instance;
	private Date lastUpdate;
	
	private TimestampUtil() {
	}
	
	public static TimestampUtil getInstance(){
		if (instance == null) {
			instance = new TimestampUtil();
		}
		return instance;
	}
	
	
//	public void update(){
//		new TimestampUtilDAO();
//	}
	
	public long getMinTimestamp(int suiteid, int limit){
		TimestampUtilDAO timestampUtilDAO = new TimestampUtilDAO();
		return timestampUtilDAO.getMinTimestamp(suiteid, limit);
	}
	
}

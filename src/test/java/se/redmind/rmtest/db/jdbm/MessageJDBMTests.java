package se.redmind.rmtest.db.jdbm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.db.jdbm.message.MessageDAO;

public class MessageJDBMTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}
	
	@After
	public void after(){
		MessageDAO.getInstance("test").dropDatabase();
	}

	@Test
	public void persistAndGet() {
		MessageDAO dao = MessageDAO.getInstance("test");
		int index = dao.save("hej");
		String result = dao.get(index);
		assertEquals("hej", result);
	}
	
	@Test
	public void getIndex() {
		MessageDAO dao = MessageDAO.getInstance("test");
		int index = dao.save("hej");
		assertEquals(1, index);
	}
	
	@Test
	public void multipleInserts(){
		MessageDAO dao = MessageDAO.getInstance("test");
		for (int i = 0; i < 10; i++) {
			dao.save("String"+i);
		}
		int size = dao.getSize();
		assertEquals(10, size);
	}
	

}

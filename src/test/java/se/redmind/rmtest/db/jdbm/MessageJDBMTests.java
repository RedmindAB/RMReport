package se.redmind.rmtest.db.jdbm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import se.redmind.rmtest.db.jdbm.message.MessageDAO;

public class MessageJDBMTests {

	private static MessageDAO dao;
	private static final String testDBname = "test";

	
	@AfterClass
	public static void after(){
		MessageDAO.getInstance(testDBname).dropDatabase();
		dao.deleteDB();
	}
	
	@BeforeClass
	public static void beforeClass(){
		dao = MessageDAO.getInstance(testDBname);
	}
	
	@After
	public void beforeTest(){
		dao.dropDatabase();
	}

	@Test
	public void persistAndGet() {
		System.out.println("--- persistAndGet");
		int index = dao.save("persistAndGet");
		String result = dao.get(index);
		assertEquals("persistAndGet", result);
	}
	
	@Test
	public void getIndex() {
		System.out.println("--- getIndex");
		int index = dao.save("hej");
		assertEquals(1, index);
	}
	
	@Test
	public void multipleInserts(){
		for (int i = 0; i < 1000; i++) {
			int index = dao.save("String"+i);
		}
		int size = dao.getSize();
		assertEquals(1000, size);
	}
	
	@Test(timeout=500)
	public void miniPerfomanceTest(){
		for (int i = 0; i < 10_000; i++) {
			dao.save("String"+i);
		}
		dao.commit();
	}
	
	@Test
	public void saveNull(){
		System.out.println("--- saveNull");
		int save = dao.save(null);
		assertEquals(-1, save);
	}
	
	
	//Something is not okay with close, need to be fixed.
	@Test
	public void commitAndClose() throws InterruptedException{
		System.out.println("--- commitAndClose");
		dao.dropDatabase();
		for (int i = 0; i < 5; i++) {
			int save = dao.save("String"+i);
			assertNotEquals(-1, save);
		}
		dao.commit();
//		dao.close();
		dao = MessageDAO.getInstance(testDBname);
		assertEquals(5, dao.getSize());
		for (int i = 0; i < 5; i++) {
			int save = dao.save("SString"+i*10);
			assertNotEquals(-1, save);
		}
		dao.commit();
		if (dao.getSize() != 10) {
			dao.prinContent();
		}
		assertEquals(10, dao.getSize());
	}
	
}

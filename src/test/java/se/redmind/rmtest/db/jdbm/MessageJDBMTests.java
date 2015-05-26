package se.redmind.rmtest.db.jdbm;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import se.redmind.rmtest.db.jdbm.message.MessageDAO;

public class MessageJDBMTests {

	private static MessageDAO dao;
	private static final String testDBname = "test";

	
	@After
	public void after(){
		MessageDAO.getInstance(testDBname).dropDatabase();
	}
	
	@BeforeClass
	public static void beforeClass(){
		dao = MessageDAO.getInstance(testDBname);
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
	
	@Test(timeout=10000)
	public void miniPerfomanceTest(){
		for (int i = 0; i < 500_000; i++) {
			dao.save("String"+i);
		}
//		dao.commit();
	}
	
	@Test
	public void saveNull(){
		System.out.println("--- saveNull");
		int save = dao.save(null);
		assertEquals(-1, save);
	}
	
	@Test
	public void commitAndClose(){
		System.out.println("--- commitAndClose");
		for (int i = 0; i < 5; i++) {
			dao.save("String"+i);
		}
		dao.commit();
		dao.close();
		dao = MessageDAO.getInstance(testDBname);
		for (int i = 0; i < 5; i++) {
			dao.save("String"+i*10);
		}
		dao.commit();
		assertEquals(10, dao.getSize());
	}
	
	@Test
	public void commitCloseOpenRead(){
		System.out.println("--- commitCloseOpenRead");
//		dao.dropDatabase();
		int index = dao.save("hej");
		dao.save("dawd");
		dao.save("dawd");
		dao.save("dawd");
		dao.save("dawd");
		dao.save("dawd");
		dao.save("dawd");
		dao.save("dawd");
		dao.commit();
		dao.close();
		dao = MessageDAO.getInstance(testDBname);
		dao.prinContent();
		String res = dao.get(index);
		assertEquals("hej", res);
	}
	
}

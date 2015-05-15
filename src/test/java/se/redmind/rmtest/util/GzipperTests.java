package se.redmind.rmtest.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class GzipperTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void test_InAndOut() {
		Gzipper zipper = new Gzipper();
		String compress = zipper.compress("hej awd awd f ioqwd d8912h82d1hdnadwnuwdahu h9 h9dw hawd h98awd h dqun1 dun nudu");
		System.out.println(compress.getBytes().length);
		String actual = zipper.decompress(compress);
		assertEquals("hej awd awd f ioqwd d8912h82d1hdnadwnuwdahu h9 h9dw hawd h98awd h dqun1 dun nudu", actual);
	}
	
	@Test
	public void gzipperTest(){
		Gzipper zipper = new Gzipper();
		String stringToZip = "";
		String compress = zipper.compress(stringToZip);
		System.out.println(stringToZip.getBytes().length);
		System.out.println(compress.getBytes().length);
	}

}

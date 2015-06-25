package se.redmind.rmtest.report.parser.xml;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.ReportTestCase;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class XMLReport extends Report<Element>{
	
	private Logger log = LogManager.getLogger(XMLReport.class);
	
	private String 
	NAME = "name",
	TESTS = "tests",
	ERRORS = "errors",
	SKIPPED = "skipped",
	FAILURES = "failures",
	TIME = "time",
	TESTCASE = "testcase";
	
	private int tests, errors, skipped, failures;
	private double time;
	private long timestamp;
	private String name, suite_name;
//	private JsonObject jsonObject;
	private Element file;
	private boolean simpleReport;
	private List<XMLReportTestCase> testCaseArray;
	private List<String> presentTestClasses;

	private boolean passed;

	private HashSet<String> driverSet;

	public XMLReport(Element element) {
		super(element);
	}
	
	public XMLReport(Element element, boolean simpleReport) {
		super(element, simpleReport);
	}

	
	
	public String removePunctuations(String string, String replacement){
		return string.replace(".", replacement);
	}
	
//	public String extractSuitePackage(String name){
//		int end = name.lastIndexOf("(");
//		return name.substring(0, end);
//	}
	
	
	@Override
	protected int tests(Element obj) {
		String testsString = obj.getAttribute("tests");
		return Integer.valueOf(testsString);
	}

	@Override
	protected int errors(Element obj) {
		return Integer.valueOf(obj.getAttribute("errors"));
	}

	@Override
	protected int failures(Element obj) {
		return Integer.valueOf(obj.getAttribute("failures"));
	}

	@Override
	protected long extractTimestamp(Element obj, String name) {
		int start = name.lastIndexOf("(");
		int end = name.lastIndexOf(")");
		String timestampString = name.substring(start+1, end);
		long timestamp = Long.valueOf(timestampString.replaceAll("-", ""));
		return timestamp;	
	}

	@Override
	protected int skipped(Element fullReport) {
		return Integer.valueOf(fullReport.getAttribute("skipped"));
	}

	@Override
	protected String extractSuiteName(Element fullReport, String name) {
		int start = name.lastIndexOf(".");
		int end = name.lastIndexOf("(");
		return name.substring(start+1, end);
	}

	@Override
	protected String getName(Element fullReport) {
		int length = fullReport.getAttributes().getLength();
		for (int i = 0; i < length; i++) {
			Node item = fullReport.getAttributes().item(i);
		}
		return fullReport.getAttribute("name");
	}

	@Override
	protected double getTime(Element element) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected List<Element> getTestCases(Element fullReport) {
		NodeList elementsNodes = fullReport.getElementsByTagName("testcase");
		List<Element> array = new ArrayList<Element>();
		for (int i = 0; i < elementsNodes.getLength(); i++) {
			array.add((Element) elementsNodes.item(i));
		}
		return array;
	}

	@Override
	protected ReportTestCase extractTestCase(Element testcase) {
		return new XMLReportTestCase(testcase, extractSuitePackage(null,getName())).build();
	}

	@Override
	protected String extractSuitePackage(Element fullReport, String name) {
		int end = name.lastIndexOf("(");
		return name.substring(0, end);
	}

}

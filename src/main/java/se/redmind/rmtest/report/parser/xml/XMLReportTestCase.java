package se.redmind.rmtest.report.parser.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

import se.redmind.rmtest.report.parser.ReportTestCase;
import se.redmind.rmtest.report.parser.ReportTestCase.ResultType;

public class XMLReportTestCase extends ReportTestCase<Element>{
	
	Logger log = LogManager.getLogger(XMLReportTestCase.class);

	public static final String 	
	NAME = "name",
	DRIVER_NAME = "driverName",
	CLASSNAME = "className",
	ERROR = "error",
	FAILURE = "failure",
	SKIPPED = "skipped",
	TYPE = "type",
	MESSAGE = "message",
	PASSED = "passed",
	TIME = "time";
	
	public XMLReportTestCase(Element element, String suite_name) {
		super(element,suite_name);
	}

	@Override
	protected String getTestcaseName(Element testcase) {
		return testcase.getAttribute("name");
	}

	@Override
	protected String getClassname(Element testcase) {
		return testcase.getAttribute("classname");
	}

	@Override
	protected ResultType getResult(Element testcase) {
		if (isElementType(testcase, "error")) return ResultType.ERROR;
		else if (isElementType(testcase, "failure")) return ResultType.FAILURE;
		else if (isElementType(testcase, "skipped")) return ResultType.SKIPPED;
		else return ResultType.PASSED;
	}

	@Override
	protected String getErrorMessage(Element testcase) {
		return testcase.getTextContent();
	}

	@Override
	protected double getRunTime(Element testcase) {
		String timeString = testcase.getAttribute("time");
		return Double.valueOf(timeString);
	}
	
	
	private boolean isElementType(Element testcase, String type){
		return (Element) testcase.getElementsByTagName(type).item(0) != null;
	}
	
}

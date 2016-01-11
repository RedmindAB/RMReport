package test.java.se.redmind.suite;

import java.util.Properties;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import se.redmind.rmtest.api.cache.WSCacheTests;
import se.redmind.rmtest.calendar.CalendarCounterTest;
import se.redmind.rmtest.db.jdbm.MessageJDBMTests;
import se.redmind.rmtest.db.read.graphoptions.ReadGraphOptionsTest;
import se.redmind.rmtest.db.test.SaveToDBTest;
import se.redmind.rmtest.report.parser.DriverParserTest;
import se.redmind.rmtest.report.reportloader.ReportLoader;
import se.redmind.rmtest.report.reportvalidation.DriverValidationTest;
import se.redmind.rmtest.report.reportvalidation.ValidFilenameTest;
import se.redmind.rmtest.report.test.JsonReportParserTest;
import se.redmind.rmtest.report.test.ReportLoaderTest;
import se.redmind.rmtest.report.test.ReportXMLParserTest;
import se.redmind.rmtest.suitejsonbuilder.test.SuiteJsonBuilderTest;
import se.redmind.rmtest.util.GzipperTests;
import se.redmind.rmtest.util.ScriptRunnerTest;
import se.redmind.rmtest.util.TimeEstimatorTest;
import se.redmind.rmtest.util.test.StringKeyValueParserTest;
import se.redmind.rmtest.web.route.api.suite.SuiteJsonBuilder;
import se.redmind.rmtest.webservicetests.DeviceFailStatsWSTEst;
import se.redmind.rmtest.webservicetests.GetClassesWSTest;
import se.redmind.rmtest.webservicetests.GetDriverByTestcaseWSTest;
import se.redmind.rmtest.webservicetests.GetGraphDataWSTest;
import se.redmind.rmtest.webservicetests.GetGraphOptionsWSTest;
import se.redmind.rmtest.webservicetests.GetLatestSuiteWSTest;
import se.redmind.rmtest.webservicetests.GetMethodsWSTest;
import se.redmind.rmtest.webservicetests.GetScreenshotStructureWSTest;
import se.redmind.rmtest.webservicetests.GetSuiteByTimestampWSTest;
import se.redmind.rmtest.webservicetests.GetSuiteSysoWSTest;
import se.redmind.rmtest.webservicetests.GetSuitesWSTest;
import se.redmind.rmtest.webservicetests.MethodFailJsonBuilderTest;
import se.redmind.rmtest.webservicetests.MethodPassWSTest;
import se.redmind.rmtest.webservicetests.PassFailClassWSTest;
import se.redmind.rmtest.webservicetests.RMDocsFilterFileWSTest;
import se.redmind.rmtest.webservicetests.ReportDirTests;
import se.redmind.rmtest.webservicetests.RunRMDoctorTest;
import se.redmind.rmtest.webservicetests.SeleniumGridDAOTest;
import se.redmind.rmtest.webservicetests.SuiteParametersWSTest;
import se.redmind.rmtet.web.properties.test.PropertiesTests;
import test.se.redmind.rmtest.testrun.TestRunTests;

@RunWith(Suite.class)
@SuiteClasses({ 
	WSCacheTests.class,
	CalendarCounterTest.class,
	MessageJDBMTests.class,
	ReadGraphOptionsTest.class,
	SaveToDBTest.class,
	DriverParserTest.class,
	DriverValidationTest.class,
	ValidFilenameTest.class,
	JsonReportParserTest.class,
	ReportLoaderTest.class,
	ReportXMLParserTest.class,
	SuiteJsonBuilderTest.class,
	GzipperTests.class,
	ScriptRunnerTest.class,
	TimeEstimatorTest.class,
	StringKeyValueParserTest.class,
	DeviceFailStatsWSTEst.class, //WS tests
	GetClassesWSTest.class,
	GetDriverByTestcaseWSTest.class,
	GetGraphDataWSTest.class,
	GetGraphOptionsWSTest.class,
	GetLatestSuiteWSTest.class,
	GetMethodsWSTest.class,
	GetScreenshotStructureWSTest.class,
	GetSuiteByTimestampWSTest.class,
	GetSuitesWSTest.class,
	GetSuiteSysoWSTest.class,
	MethodFailJsonBuilderTest.class,
	MethodPassWSTest.class,
	PassFailClassWSTest.class,
	ReportDirTests.class,
	RMDocsFilterFileWSTest.class,
	RunRMDoctorTest.class,
	SeleniumGridDAOTest.class,
	SuiteParametersWSTest.class,
	PropertiesTests.class,
	TestRunTests.class,
	RMReportAutoSuite.class
	})
public class AllTests {

}

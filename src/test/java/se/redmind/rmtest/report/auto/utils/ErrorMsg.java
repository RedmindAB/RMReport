package se.redmind.rmtest.report.auto.utils;

public class ErrorMsg {
	
	public static final String SeeThisAssert =					"\n See this assert: ";
	
	public static final String LegendListTextIsDifferent = 		"\n Expected text in legend list does not match the actual text." + SeeThisAssert;
	public static final String LegendListTextIsSame = 			"\n Expected text in legend list does NOT differ from actual text." + SeeThisAssert;
	public static final String LegendListSize =					"\n Expected amount of rows in legend list does not match the actual amount of rows." + SeeThisAssert;
	public static final String LegendListDown =					"\n Test failed because: \n"
																+ "1) Did not move DOWN in legend list \n"
																+ " or \n"
																+ "2) The legend list number did not update after moving DOWN in the legend list. \n"
																+ SeeThisAssert;
	public static final String LegendListUp = 					"Test failed because: \n"
																+ "1) Did not move UP in legend list \n"
																+ " or \n"
																+ "2) The legend list number did not update after moving UP in the legend list. \n"
																+ SeeThisAssert;
	
	public static final String ChartTitleIsDifferent = 			"\n Expected chart title differs from actual chart title." + SeeThisAssert;
	public static final String ChartTitleIsSame = 				"\n Expected chart title does NOT differ from actual chart title." + SeeThisAssert;
	
	public static final String ProjectTitleIsDifferent = 		"\n Expected project title differs from actual project title." + SeeThisAssert;
	public static final String ProjectTitleIsSame = 			"\n Expected project title does NOT differ from actual project title." + SeeThisAssert;
	
	public static final String HighchartsTitleIsDifferent =		"\n Expected text in the highcharts title differs from the actual text." + SeeThisAssert;
	public static final String HighchartsTitleIsSame = 			"\n Expected text in the highcharts title does NOT differ from actual text." + SeeThisAssert;
	public static final String HighchartsSubTitle = 			"\n Expected text in the highcharts (sub)title differs from the actual text." + SeeThisAssert;
	
	public static final String PageRedirect = 					"\n Was not redirected to expected page." + SeeThisAssert;
	
	public static final String ClassNameIsDifferent = 			"\n Expected class name differs from actual class name." + SeeThisAssert;
	public static final String ClassBarTextIsDifferent = 		"\n Expected text in the class bar does not match the actual text." + SeeThisAssert;
	public static final String ClassBarTextIsSame = 			"\n Expected text in the class bar does NOT differ from actual text." + SeeThisAssert;
	
	public static final String MethodNameIsDifferent = 			"\n Expected method name differs from actual method name" + SeeThisAssert;
	public static final String MethodNameIsSame = 				"\n Expected method name does NOT differ from actual name" + SeeThisAssert;
	public static final String MethodBarNameIsDifferent = 		"\n Expected method bar name differs from actual method name" + SeeThisAssert;
	public static final String MethodBarNameIsSame = 			"\n Expected method bar name does NOT differ from actual name" + SeeThisAssert;
	public static final String MethodBarTextIsDifferent = 		"\n Expected text in the method bar does not match actual text." + SeeThisAssert;
	public static final String MethodBarTextIsSame = 			"\n Expected text in the method bar does NOT differ from actual text." + SeeThisAssert;
	
	public static final String CaseBarNameIsDifferent = 		"\n Expected case bar name differs from actual case name" + SeeThisAssert;
	public static final String CaseBarNameIsSame =		 		"\n Expected case bar name does NOT differ from actual case name" + SeeThisAssert;
	
	public static final String RuntimeIsDifferent = 			"\n Expected runtime differs from actual runtime" + SeeThisAssert;
	public static final String RuntimeIsSame = 					"\n Expected runtime does NOT differ from actual runtime" + SeeThisAssert;
	
	public static final String StackTraceIsDifferent = 			"\n Expected stack trace differs from actual stack trace." + SeeThisAssert;
	
	public static final String FilterFieldTextIsDifferent = 	"\n Expected text in the 'Filter' search bar does not match the actual text. \n Note: may be blank." + SeeThisAssert;
	public static final String CaseListSizeIsDifferent = 		"\n Expected amount of cases does not match actual amount of cases." + SeeThisAssert;

	public static final String HashSetSizeIsDifferent = 		"\n Expected size in the Hash set does not match the actual size." + SeeThisAssert;
	
	public static final String JsonModalTextIsDifferent = 		"\n Expected text in the Json modal does not match the actual text." + SeeThisAssert;
	
	public static final String ShouldNotBeEnabled = 			"\n This function IS enabled, it shouldn't be." + SeeThisAssert;
	public static final String ShouldBeEnabled = 				"\n This function is NOT enabled, it should be." + SeeThisAssert;
	
	public static final String SysoDidNotOpen =					"\n The system out print modal did not open." + SeeThisAssert;
	public static final String SysoDidNotClose =				"\n The system out print modal did not close." + SeeThisAssert;
	
	public static final String ScopeUnableToChange = 			"\n Was not able to change to another 'scope'. \n ('Scope' = 'Home/Classes/Methods', top left." + SeeThisAssert;
	
	public static final String ProjectNameIsDifferent = 		"\n The expected project name differs from the actual name." + SeeThisAssert;
	
	public static final String TimestampIsDifferent = 			"\n The expected timestamp differs from the actual timestamp." + SeeThisAssert;
	
	public static final String ScreenshotThumbnailNotPresent =	"\n Screenshot thumbnail is not present." + SeeThisAssert;
	
	public static final String LogoNotDisplayed = 				"\n The Redmind logo is not displayed." + SeeThisAssert;
}

package se.redmind.report.auto.nav;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.xalan.trace.EndSelectionEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.google.gson.annotations.Until;

import se.redmind.report.auto.expectedconditions.ChartReloadFinished;
import se.redmind.report.auto.expectedconditions.SizeOfWebelement;
import se.redmind.report.auto.expectedconditions.UrlChanged;

public class SuiteController extends BaseController {

	public SuiteController(WebDriver pDriver) {
		super(pDriver);
	}

	public String getNameFrom(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(type+"-name-"+index)));
		return getElementByID(type+"-name-"+index).getText();
	}
	
	public String getRunTimeFrom(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(type+"-runtime-"+index)));
		return getElementByID(type+"-runtime-"+index).getText();
	}
	
	public void clickRunTime(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("order_by_runtime")));
		getElementByID("order_by_runtime").click();
	}
	
	public void clickBarsOrderByName(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("name")));
		getElementByName("name").click();
//		System.out.println(getElementByName("name").getText());
	}
	
	public void clickBarsOrderByPassed(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("passed")));
		getElementByName("passed").click();
//		System.out.println(getElementByName("passed").getText());
	}
	
	public void clickBarsOrderByFailed(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("failed")));
		getElementByName("failed").click();
//		System.out.println(getElementByName("passed").getText());
	}
	
	public void clickBarsOrderByPassFail(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("pass-fail")));
		getElementByName("pass-fail").click();
	}
	
	public void clickBarsOrderByPlatform(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("platform")));
		getElementByName("platform").click();
	}
	
	public void clickBarsOrderByDevice(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("device")));
		getElementByName("device").click();
	}
	
	public void clickBarsOrderByBrowser(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("browser")));
		getElementByName("browser").click();
	}
	
	public void clickBarsOrderByRuntime(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name("runtime")));
		getElementByName("runtime").click();
	}
	
	public void clickOnBar(String type, String index){
		String currentUrl = driver.getCurrentUrl();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(type+"-"+index)));
		getElementByID(type+"-"+index).click();
		if (!type.equals("case")) {
			driverFluentWait(15).until(new UrlChanged(currentUrl));
		}
	}
	
	public void filterOn(String text){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("filter-field")));
		getElementByID("filter-field").sendKeys(text);
	}
	
	public String getFilterFieldText (){
		return getElementByID("filter-field").getText();
	}
	
	public void checkBoxOn(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(type+"-name-"+index)));
		WebElement element =getElementByID("checkbox"+index);
		WebElement box = element.findElement(By.tagName("span"));
		box.click();
	}
	
	public void clickThisTestOnly(String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("case-thistest-"+index)));
		getElementByID("case-thistest-"+index).click();
		//driverFluentWait(20).until(new ChartReloadFinished());
		driverFluentWait(15).until(ExpectedConditions.invisibilityOfElementLocated(By.className("highcharts-loading")));
	}
	
	public WebElement getCurrentPossition(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("suite-position")));
		return getElementByID("suite-position");
	}
	
	public String getStackTrace(){
		driverFluentWait(15).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*/div[contains(@class, ' in')]//*/pre[contains(@class, 'stack-trace')]")));
		WebElement stackTrace = driver.findElement(By.xpath("//*/div[contains(@class, ' in')]//*/pre[contains(@class, 'stack-trace')]"));
		return stackTrace.getText();
	}
	
	public List <WebElement> getCaseList(){
		WebElement group = driver.findElement(By.id("case-group"));
		WebElement panelGroup = group.findElement(By.className("panel-group"));
		List <WebElement> caseList = panelGroup.findElements(By.className("panel-heading"));
		return caseList;
	}
	
	public List <WebElement> listCaseNames(int index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("case-name-"+index)));
		List <WebElement> listCaseNames = driver.findElements(By.id("case-name-"+index));
		return listCaseNames;
	}
	
	public List <WebElement> listCaseRuntimes(int index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("case-runtime-"+index)));
		List <WebElement> listCaseRuntimes = driver.findElements(By.id("case-runtime-"+index));
		return listCaseRuntimes;
	}
	
	public String getPassFail(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(type+"-passfail-"+index)));
		WebElement getClassText = getElementByID(type+"-passfail-"+index);
		return getClassText.getText();
	}
	
	public WebElement methodTable(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("method-name")));
		return getElementByID("method-name");
	}
	
	public List <WebElement> getAllBars(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("suite-partial-name-container")));
		List<WebElement> allBars = methodTable().findElements(By.className("suite-partial-name-container"));
		return allBars;
	}
	
	public String getAllBarStats(String type, int index){
		List <WebElement> allBars = getAllBars();
		String[] statType = new String[allBars.size()];
		for (int i = 0; i < allBars.size(); i++) {
			statType[i] = (listPassedSkippedFailed(type,""+i).get(index).getText());
		}
		StringBuilder builder = new StringBuilder();
    	for (String methodStat : statType) {
			builder.append(methodStat+",");
		}
    	String allStats = builder.toString();
		return allStats;
	}
	
	public String getAllBarNames(String type){
		List <WebElement> allBars = getAllBars();
		String[] statType = new String[allBars.size()];
		for (int i = 0; i < allBars.size(); i++) {
			statType[i] = (listBarNames(type,""+i).get(0).getText());
		}
		StringBuilder builder = new StringBuilder();
    	for (String methodName : statType) {
			builder.append(methodName+",");
		}
    	String allNames = builder.toString();
		return allNames;
	}
	
	public String getAllCaseNames(){
		List <WebElement> allBars = getCaseList();
		String[] statType = new String[allBars.size()];
		for (int i = 0; i < allBars.size(); i++) {
			statType[i] = (listCaseNames(+i).get(0).getText());
		}
		StringBuilder builder = new StringBuilder();
    	for (String caseName : statType) {
			builder.append(caseName+",");
		}
    	String allNames = builder.toString();
		return allNames;
	}
	
	public String getAllCaseRuntimes(){
		List <WebElement> allBars = getCaseList();
		String[] statType = new String[allBars.size()];
		for (int i = 0; i < allBars.size(); i++) {
			statType[i] = (listCaseRuntimes(+i).get(0).getText());
		}
		StringBuilder builder = new StringBuilder();
    	for (String caseRuntime : statType) {
			builder.append(caseRuntime+",");
		}
    	String allRuntimes = builder.toString();
		return allRuntimes;
	}
	
	public List <WebElement> listBarNames(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(type+"-name-"+index)));
		List <WebElement> listBarNames = driver.findElements(By.id(type+"-name-"+index));
		return listBarNames;
	}
	
	public List <WebElement> listPassedSkippedFailed(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(type+"-passfail-"+index)));
		List <WebElement> listPassedSkippedFailed = driver.findElements(By.id(type+"-passfail-"+index));
		return listPassedSkippedFailed;
	}
	
	public List <WebElement> listRuntime(String type, String index){
		driverFluentWait(15).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("case-runtime-" + index)));
		List <WebElement> listRuntime = driver.findElements(By.id(type+"-runtime-"+index));
		return listRuntime;
	}
	
	public String getTotal(String type,String index){
		String total =listPassedSkippedFailed(type, index).get(0).getText();
		return total;
		
	}
	
	public String getPassed(String type,String index){
		String passed = listPassedSkippedFailed(type, index).get(1).getText();
		return passed;
	}
	
	public String getSkipped(String type,String index){
		String skipped = listPassedSkippedFailed(type, index).get(2).getText();
		return skipped;
	}
	
	public String getFailed(String type,String index){
		String failed = listPassedSkippedFailed(type, index).get(3).getText();
		return failed;
	}
	
	public String getPassedSkippedFailed(String type, String index){
		String passedSkippedFailed = getTotal(type,index) + getPassed(type, index) + getSkipped(type, index) + getFailed(type, index);
		return passedSkippedFailed;
	}
	
	public void ClickOnSuiteLinkText(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("suite-position")));
		String current = getUrl();
		getElementByID("suite-position").click();
		driverFluentWait(15).until(new UrlChanged(current));
	}

	
	/**
	 * This waits for x buttons to the right of the testcases, should be changed to something more steady.
	 * @param expectedSize
	 */
	public void waitForCaseListSize(int expectedSize) {
		By by = By.cssSelector(".accordion-toggle > .btn");
		driverFluentWait(15).until(new SizeOfWebelement(by, expectedSize));
	}
	
}

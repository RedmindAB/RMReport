package se.redmind.report.auto.nav;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.report.auto.expectedconditions.UrlChanged;
import se.redmind.rmtest.selenium.framework.HTMLPage;

abstract class BaseNav extends HTMLPage{

	public BaseNav(WebDriver pDriver) {
		super(pDriver);
		this.driver.get("http://localhost:4567");
		this.driver.manage().window().setSize(new Dimension(1920, 1080));
		initialWait();
		navigate();
	}

	public void initialWait(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("highcharts-series-group")));
	}
	
	abstract void navigate();
	
	public WebElement getRedmindLogo(){
		return this.driver.findElement(By.id("home"));
	}
	
	public WebElement getGraphView(){
		return this.driver.findElement(By.id("reports"));
	}
	
	public WebElement getScreenshot(){
		return this.driver.findElement(By.id("visual"));
	}

	public void click(WebElement element){
		element.click();
	}
	
	public WebElement getElementByXPath(String xPath){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
		return driver.findElement(By.cssSelector(xPath));
	}
	
	public WebElement getElementByID(String id){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		return driver.findElement(By.id(id));
	}
	
	public WebElement getElementByClass(String NavClass){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className(NavClass)));
		return driver.findElement(By.className(NavClass));
	}
	
	public void chooseTimestampFromDropdown(String timestamp){
		getElementByID("choose_timestamp").click();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(timestamp)));
		WebElement chooseTimestamp = getElementByID(timestamp);
		chooseTimestamp.click();
	}
	
	public void getFirstSuiteSection(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("highcharts-series")));
		WebElement rect = getElementByClass("highcharts-series");
		List<WebElement> findElements = rect.findElements(By.tagName("rect"));
		WebElement listItem = findElements.get(0);
		listItem.click();
	}
	
	public WebElement getChartTitle(){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id("button_reload")));
		WebElement findElement = driver.findElement(By.className("highcharts-title"));
		return findElement;
	}
	
	public WebElement getChartSubtitle(){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id("button_reload")));
		return driver.findElement(By.className("highcharts-subtitle"));
	}
	
	public void clickNavButton(){
		driverFluentWait(30).until(ExpectedConditions.presenceOfElementLocated(By.className("navbar-toggle")));
		WebElement button = driver.findElement(By.className("navbar-toggle"));
		if(button.isDisplayed())
			button.click();
	}
	
	public boolean isNavButtonVisible(){
		driverFluentWait(10).until(ExpectedConditions.presenceOfElementLocated(By.className("navbar-toggle")));
		WebElement button = driver.findElement(By.className("navbar-toggle"));
		if(button.isDisplayed())
			return true;
		else
			return false;
	}
	
	public void goToGrid(){
		String id = "grid_view";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "grid_view_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement gridButton = driver.findElement(By.id(id));
		gridButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void goToAdmin(){
		String id = "admin_view";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "admin_view_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement adminButton = driver.findElement(By.id(id));
		adminButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void goToDashboard(){
		String id = "home_view";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "home_view_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement dashButton = driver.findElement(By.id(id));
		dashButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void chooseProject(int index){
		String id = "choose_project";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "choose_project_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		WebElement projectButton = driver.findElement(By.id(id));
		projectButton.click();
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.className("dropdown-menu")));
		WebElement dropdown = driver.findElement(By.className("dropdown-menu"));
		List<WebElement> projects = dropdown.findElements(By.tagName("a"));
		projects.get(index).click();
		
	}
	
	public String getProjectNameFromList(int index){
		String id = "choose_project";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "choose_project_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		WebElement projectButton = driver.findElement(By.id(id));
		projectButton.click();
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.className("dropdown-menu")));
		WebElement dropdown = driver.findElement(By.className("dropdown-menu"));
		List<WebElement> projects = dropdown.findElements(By.tagName("a"));
		return projects.get(index).getText();
	}
	
	public void chooseTimeStamp(String timestamp){
		String id = "choose_timestamp";
		if(isNavButtonVisible()){
			
			id = "choose_timestamp_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		WebElement timestampButton = driver.findElement(By.id(id));
		timestampButton.click();
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id("dropdown-timestamp")));
		WebElement dropdown = driver.findElement(By.id("dropdown-timestamp"));
		List<WebElement> timestamps = dropdown.findElements(By.tagName("a"));
		for (WebElement webElement : timestamps) {
			if (webElement.getText().equals(timestamp)) {
				webElement.click();
				return;	
			}	
		}
	}
	
	public String getCurrentTimestamp(){
		String id = "choose_timestamp";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "choose_timestamp_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		WebElement timestampButton = driver.findElement(By.id(id));
		return timestampButton.getText();
	}
	
	public String getCurrentProjectName(){
		String id = "choose_project";
		if(isNavButtonVisible()){
			id = "choose_project_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		WebElement projectButton = driver.findElement(By.id(id));
		return projectButton.getText();
	}
	
	public String getCurrentUrl(){
		return driver.getCurrentUrl();
	}
	
	public void clickLogo(){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.className("rm-logo-a")));
		String before = getCurrentUrl();
		WebElement logoButton = driver.findElement(By.className("rm-logo-a"));
		logoButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void goToReports(){
		String id = "reports_view";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "reports_view_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement reportsButton = driver.findElement(By.id(id));
		reportsButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void goToVisualizer(){
		String id = "visual_view";
		if(isNavButtonVisible()){
			clickNavButton();
			id = "visual_view_dd";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement visualizerButton = driver.findElement(By.id(id));
		visualizerButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
	}
	
	public void goToLiveTests(){
		String id = "live-tests_view";
		if (isNavButtonVisible()) {
			clickNavButton();
			id = "live-tests_view";
		}
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id(id)));
		String before = getCurrentUrl();
		WebElement livetestsButton = driver.findElement(By.id(id));
		livetestsButton.click();
		driverFluentWait(15).until(new UrlChanged(before));
		
	}
	
}

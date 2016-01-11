package se.redmind.report.auto.nav;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import se.redmind.report.auto.expectedconditions.ChartReloadFinished;
import se.redmind.report.auto.expectedconditions.UrlChanged;

public class VisualNav extends BaseNav{
	
	public VisualNav(WebDriver pDriver) {
		super(pDriver);
	}

	@Override
	void navigate() {
		String currentURL = driver.getCurrentUrl();
		getFirstSuiteSection();
		driverFluentWait(15).until(new UrlChanged(currentURL));
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("visual_view")));
		getElementByID("visual_view").click();
	}
	
	public WebElement getMethodID(){
		return getElementByID("method-0");
	}
	
	public void chooseClass(String id){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("suite-info-partial-table")));
		WebElement suites = getElementByClass("suite-info-partial-table");
		List<WebElement> findElements = suites.findElements(By.tagName("p"));
		for (WebElement listItem : findElements) {
			if (listItem.getText().equals(id)) {
				listItem.click();
				return;
			}
	}
	}
	
	public WebElement getNav(String NavID){
		return driver.findElement(By.className(NavID));
	}
	
	public boolean isEnabled(String NavID){
		boolean isEnabled = false;
		WebElement list = driver.findElement(By.id(NavID));
		String enabled = list.getAttribute("class");
		if(enabled.equals(NavID + " active")){
			isEnabled = true;
		}
		else{
			isEnabled = false;
		}
		return isEnabled;
	}
	
	public WebElement getTimestamp(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("choose_timestamp")));
		return getElementByID("choose_timestamp");
	}
	
	public WebElement getProject(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("choose_project")));
		return getElementByID("choose_project");
	}
	
	public boolean isTimestampSet(String timestamp){
		boolean isActive = false;
    	if(getTimestamp().getText().equals(timestamp))
    		isActive = true;
    	return isActive;
	}
	
	public void changeTimestamp(String timestamp){
		getTimestamp().click();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown-timestamp")));
		WebElement dropDown = getElementByID("dropdown-timestamp");
		List<WebElement> findElements = dropDown.findElements(By.tagName("a"));
		for (WebElement listItem : findElements) {
			if (listItem.getText().equals(timestamp)) {
				listItem.click();
				return;
			}
		}
	}
		
		public void changeTimestamp2(int index){
			getTimestamp().click();
			driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown-timestamp")));
			WebElement dropDown = getElementByID("dropdown-timestamp");
			List<WebElement> findElements = dropDown.findElements(By.tagName("ul"));
			for (WebElement listItem : findElements) {
				if (listItem.getTagName().equals(index)) {
					listItem.click();
					return;
				}
			}
	}
	
	public boolean checkAmountOfClasses(int amount){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("class_container")));
		WebElement dropDown = getElementByID("class_container");
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("class_name")));
		List<WebElement> findElements = dropDown.findElements(By.id("class_name"));
		if(findElements.size() > amount)
			return true;
		else
			return false;
	}
	
	public void openSysos(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("syso-header")));
		getElementByID("syso-header").click();
	}
	
	public boolean isSysosOpen(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("syso-header")));
		if(getElementByID("syso-header").isDisplayed())
			return true;
		else 
			return false;
	}
	
	public void closeSysos(){
        driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("modal-console-dialog")));
        WebElement modal = getElementByID("modal-console-dialog");
        modal.findElement((By.id("close"))).click();
	}
	
	public boolean isSysosClosed(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("close")));
		if(!getElementByID("close").isDisplayed())
			return true;
		else 
			return false;
	}
	
	public void sleep(){
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void changeProject(String project){
		getProject().click();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("dropdown-project")));
		WebElement dropDown = getElementByID("dropdown-project");
		List<WebElement> findElements = dropDown.findElements(By.tagName("a"));
		for (WebElement listItem : findElements) {
			if (listItem.getText().equals(project)) {
				listItem.click();
				return;
			}
		}
	}
	
	public boolean isProjectSet(String project){
		boolean isActive = false;
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("choose_project")));
    	if(getProject().getText().equals(project))
    		isActive = true;
    	return isActive;
	}
	
	public void goTo(String byID){
		String currentURL = driver.getCurrentUrl();
		getElementByID(byID).click();
		driverFluentWait(15).until(new UrlChanged(currentURL));
	}
	
	public void goToScopeByID(String byID){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(byID)));
		getElementByID(byID).click();
	}
	
	public WebElement getGoBackButton(){
		return getElementByClass("screenshot-nav-left");
	}
	
	public boolean isAtClassView(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("go-to-suites")));
		if(getElementByID("go-to-suites").isDisplayed())
			return true;
		else 
			return false;
	}
	
	public String getMethodTitle(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("screenshot-center-center")));
		return driver.findElement(By.className("screenshot-center-center")).getText();
	}
	
	public String getClassTitle(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("screenshot-nav")));
		return driver.findElement(By.className("screenshot-nav")).getText();
	}
		
	public void openMethod(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("accordion-name-container")));
		getElementByClass("accordion-name-container").click();
	}
	
	public void openMethod2(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("panel-group")));
		getElementByClass("panel-default").click();
	}
	
	public boolean isThumbnailPresent(String method){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("screenshot-header-" + method)));
		if(getElementByID("screenshot-header-" + method).isDisplayed()){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void waitForSlideAnimation(){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.className("slide-animation")));
	}
	
	public void openScreenshot(String num){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(By.id("screenshot" + num + "-0")));
		getElementByID("screenshot" + num + "-0").click();
	}
	
	public boolean isScreenshotPresent(){
		if(getElementByClass("slide-animation").isDisplayed())
			return true;
		else
			return false;
	}
	
	public boolean isScreenShotSwitched(){
		String textBefore;
		String textAfter;
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("white")));
		textBefore = getElementByClass("white").getText();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("arrow_prev")));
		getElementByID("arrow_prev").click();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("white")));
		textAfter = getElementByClass("white").getText();
		if(textBefore.equals(textAfter))
			return false;
		else
			return true;
	}
	
}
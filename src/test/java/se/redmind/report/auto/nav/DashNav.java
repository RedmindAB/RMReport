package se.redmind.report.auto.nav;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import se.redmind.report.auto.expectedconditions.ChartReloadFinished;
import se.redmind.report.auto.expectedconditions.UrlChanged;

public class DashNav extends BaseNav{
	
	public DashController dash;
	
	public DashNav(WebDriver pDriver) {
		super(pDriver);
		dash = new DashController(pDriver);
	}

	@Override
	void navigate() {
		String currentURL = driver.getCurrentUrl();
		getFirstSuiteSection();
		driverFluentWait(15).until(new UrlChanged(currentURL));
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("dash_view")));
		getElementByID("dash_view").click();
		/*getElementByID("section").click();
		getElementByID("visual_view").click();*/
	}
	
	public WebElement getNav(String NavID){
		return driver.findElement(By.className(NavID));
	}
	
	public boolean isDisabled(String NavID){
		boolean isDisabled = false;
		WebElement list = driver.findElement(By.id(NavID));
		String disabled = list.getAttribute("class");
		if(disabled.equals(NavID + " disabled")){
			isDisabled = true;
		}
		else{
			isDisabled = false;
		}
		return isDisabled;
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
	
	public void goToGraph(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("go_graph_0")));
		getElementByID("go_graph_0").click();
	}
	
	public void goToScreenshots(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("go_ss_0")));
		getElementByID("go_ss_0").click();
	}
}

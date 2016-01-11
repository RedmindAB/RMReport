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

public class NavbarNav extends BaseNav{
	
	public NavbarNav(WebDriver pDriver) {
		super(pDriver);
	}

	@Override
	void navigate() {
		
	}

	public WebElement getMethodID(){
		return getElementByID("methods_0");
	}
	
	public void chooseClass(String id){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		getElementByID(id).click();
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("method_0")));
	}
	
	public WebElement getNav(String NavID){
		return driver.findElement(By.className(NavID));
	}
	
	public boolean isEnabled(String NavID){
    	driverFluentWait(15).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(NavID + " active")));
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
	
}
package se.redmind.report.auto.nav;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.report.auto.expectedconditions.UrlChanged;

public class GridNav extends BaseNav{
	
	public GridNav(WebDriver pDriver) {
		super(pDriver);
	}

	@Override
	void navigate() {
		String currentURL = driver.getCurrentUrl();
		goToGrid();
		driverFluentWait(15).until(new UrlChanged(currentURL));
	}
	
	public void clickOnLogo(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("logo")));
		getElementByClass("logo").click();
	}
	
	public String getJsonHeader(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("json-header")));
		WebElement getLogoText = getElementByID("json-header");
		return getLogoText.getText();
	}
	
}
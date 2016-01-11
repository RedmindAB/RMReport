package se.redmind.report.auto.nav;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.rmtest.selenium.framework.HTMLPage;

public abstract class BaseController extends HTMLPage{

	public BaseController(WebDriver pDriver) {
		super(pDriver);
	}

	protected WebElement getElementByID(String id){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		return this.driver.findElement(By.id(id));
	}
	
	protected WebElement getElementByClass(String className){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className(className)));
		return this.driver.findElement(By.className(className));
	}
	
	protected WebElement getElementByCss(String css){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(css)));
		return this.driver.findElement(By.xpath(css));
	}
	
	protected WebElement getElementByName(String name){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.name(name)));
		return this.driver.findElement(By.name(name));
	}
	
	public String getUrl(){
		return driver.getCurrentUrl();
	}
	
}

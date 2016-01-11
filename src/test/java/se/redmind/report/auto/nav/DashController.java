package se.redmind.report.auto.nav;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashController extends BaseController{

	public DashController(WebDriver pDriver) {
		super(pDriver);
	}
	
	public void clickLegend(String type){
		int indexSelector = 0;
		switch (type) {
		case "passed":
			indexSelector = 1;
			break;
		case "skipped":
			indexSelector = 2;
			break;
		case "failed":
			indexSelector = 3;
			break;
		default:
			break;
		} 
		String legendCSS = "g[class^='highcharts-legend']> g:nth-of-type(1) > g:nth-of-type(1) > g:nth-of-type(" + indexSelector + ")";
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(legendCSS)));
		WebElement legend = driver.findElement(By.cssSelector(legendCSS));
		legend.click();
	}
	
	public boolean isEnabled(String type){
		int indexSelector = 0;
		switch (type) {
		case "passed":
			indexSelector = 1;
			break;
		case "skipped":
			indexSelector = 2;
			break;
		case "failed":
			indexSelector = 3;
			break;
		default:
			break;
		}
		String passedCSS = "g[class^='highcharts-legend']> g:nth-of-type(1) > g:nth-of-type(1) > g:nth-of-type(" + indexSelector + ") > rect:nth-of-type(1)";
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(passedCSS)));
		WebElement passed = driver.findElement(By.cssSelector(passedCSS));
		String fill = passed.getAttribute("fill");
		if(fill.equals("#CCC"))
			return false;
		else
			return true;
	}
	
	public String getProjectName(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.className("highcharts-title")));
		WebElement getProjectTitle = driver.findElement(By.className("highcharts-title"));
		String projectName = getProjectTitle.getText();
		return projectName;
	}
	
	public String getProjectNameInReports(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("choose_project")));
		WebElement getProjectTitle = driver.findElement(By.id("choose_project"));
		String projectName = getProjectTitle.getText();
		return projectName;
	}
	


}

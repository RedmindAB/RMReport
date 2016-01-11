package se.redmind.report.auto.nav;

import java.util.ArrayList;
import java.util.List;

import org.apache.bcel.generic.NEWARRAY;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import se.redmind.report.auto.expectedconditions.ChartReloadFinished;
import se.redmind.report.auto.expectedconditions.WebElementListSize;

public class GraphController extends BaseController{

	public GraphController(WebDriver pDriver) {
		super(pDriver);
		// TODO Auto-generated constructor stub
	}

	public WebElement getGraph(){
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.id("chart1")));
		return driver.findElement(By.id("chart1"));
	}
	
//	public WebElement getChartTitle(){
//		WebElement titleElement = getElementByID("display-dropdown");
//		return titleText;
//	}
	
	public Chart getChart(){
		WebElement chartElement = getElementByID("chart1");
		return new Chart(chartElement);
	}
	
	public void waitForLegendListSize(int expectedSize){
		By by = By.className("highcharts-legend-item");
		driverFluentWaitForCondition(new WebElementListSize(by, expectedSize),15);
		
	}
	
	public WebElement getLegendListItem(int index) {
		By legenditems = By.className("highcharts-legend");
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(legenditems));
		List <WebElement> list = driver.findElements(By.className("highcharts-legend-item"));
		return list.get(index);
	}
	
	public WebElement getProjectName(int index){
		By projectname = By.id("choose_project");
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(projectname));
		List <WebElement> list = driver.findElements(projectname);
		return list.get(index);		
}
	
	public void clickDownArrow(){
		String downArrowCSS = "g[class^='highcharts-legend']> g:nth-of-type(2) >  path:nth-of-type(2)";
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(downArrowCSS)));
		WebElement downArrow = driver.findElement(By.cssSelector(downArrowCSS));
		downArrow.click();
	}
	
	public void clickUpArrow(){
		String upArrowCSS = "g[class^='highcharts-legend']> g:nth-of-type(2) >  path:nth-of-type(1)";
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(upArrowCSS)));
		WebElement upArrow = driver.findElement(By.cssSelector(upArrowCSS));
		upArrow.click();
	}
	
	public String getListNumber(){
		String listNumberCSS = "g[class^='highcharts-legend']> g:nth-of-type(2) >  text:nth-of-type(1)";
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(listNumberCSS)));
		WebElement listNumber = driver.findElement(By.cssSelector(listNumberCSS));
		return listNumber.getText();
	}
	
	public List <WebElement> getLegendList() {
		By legenditems = By.className("highcharts-legend-item");
		driverFluentWait(15).until(ExpectedConditions.presenceOfElementLocated(legenditems));
		List <WebElement> list = driver.findElements(By.className("highcharts-legend-item"));
		return list;
	}
	
	public List <String> getLegendListColors(){
		List<WebElement> legendList = getLegendList();
		List <String> legendColors = new ArrayList<String>();
		for (WebElement legendItem : legendList) {
			WebElement rect = legendItem.findElement(By.tagName("rect"));
			String attribute = rect.getAttribute("fill");
			legendColors.add(attribute);
		}
		return legendColors;
	}
	
	public void clickOnLegend(int index){
		driverFluentWait(15).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.className("highcharts-legend-item"))));
		getLegendListItem(index).click();
	}
	

}

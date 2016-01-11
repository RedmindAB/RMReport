package se.redmind.report.auto.nav;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Chart {

	WebElement chart;
	
	public Chart(WebElement chart) {
		this.chart = chart;
	}
	
	public String getTitle(){
		WebElement title = this.chart.findElement(By.className("highcharts-title"));
		return title.getText();
	}
	
	public String getSubtitle(){
		WebElement subtitle = this.chart.findElement(By.className("highcharts-subtitle"));
		return subtitle.getText();
	}
	
	public int getGraphResultSize(){
		WebElement series = this.chart.findElement(By.className("highcharts-series"));
		WebElement path = series.findElement(By.tagName("path"));
		String attribute = path.getAttribute("d");
		return attribute.split(" L ").length;
	}
	
}

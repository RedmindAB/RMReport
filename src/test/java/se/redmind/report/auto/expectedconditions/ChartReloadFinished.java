package se.redmind.report.auto.expectedconditions;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ChartReloadFinished implements ExpectedCondition<Boolean> {

	@Override
	public Boolean apply(WebDriver chart) {
		WebElement loadingScreen = chart.findElement(By.className("highcharts-loading"));
		String style = loadingScreen.getAttribute("style");
		String[] split = style.split(";");
		for (String styleVal : split) {
			boolean equals = styleVal.contains("display: none");
			if (equals) {
				return true;
			}
		}
		return false;
	}

}

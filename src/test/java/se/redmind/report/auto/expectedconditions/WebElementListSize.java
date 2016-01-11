package se.redmind.report.auto.expectedconditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WebElementListSize implements ExpectedCondition<Boolean> {

	private By by;
	private int expectedSize;

	public WebElementListSize(By by, int expectedSize) {
		this.by = by;
		this.expectedSize = expectedSize;
		
	}
	
	@Override
	public Boolean apply(WebDriver driver) {
		List<WebElement> findElements = driver.findElements(by);
		if (findElements.size() == expectedSize) {
			return true;
		}
		return false;
	}

}

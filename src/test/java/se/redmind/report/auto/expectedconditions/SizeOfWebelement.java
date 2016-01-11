package se.redmind.report.auto.expectedconditions;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class SizeOfWebelement implements ExpectedCondition<Boolean>{

	
	private By by;
	private int size;

	public SizeOfWebelement(By by, int size) {
		this.by = by;
		this.size = size;
	}
	
	@Override
	public Boolean apply(WebDriver driver) {
		List<WebElement> findElements = driver.findElements(by);
		System.out.println(findElements.size());
		if (findElements.size() == size) {
			return true;
		}
		else return false;
	}

}

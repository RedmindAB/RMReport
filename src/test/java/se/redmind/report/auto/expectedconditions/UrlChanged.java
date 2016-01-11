package se.redmind.report.auto.expectedconditions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class UrlChanged implements ExpectedCondition<Boolean>{

	String currentUrl;
	
	public UrlChanged(String currentUrl) {
		this.currentUrl  = currentUrl; 
	}
	
	@Override
	public Boolean apply(WebDriver driver) {
		if (this.currentUrl.equals(driver.getCurrentUrl())) {
			return false;
		} else{
			return true;
		}
	}

}

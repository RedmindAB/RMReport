package se.redmind.report.auto.nav;

import org.openqa.selenium.WebDriver;

public class ScreenshotNav extends BaseNav{

	public ScreenshotNav(WebDriver pDriver) {
		super(pDriver);
	}

	@Override
	void navigate() {
		getFirstSuiteSection();
		getScreenshot().click();
	}
}

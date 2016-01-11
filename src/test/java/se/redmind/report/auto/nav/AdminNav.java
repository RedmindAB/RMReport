package se.redmind.report.auto.nav;

import org.openqa.selenium.WebDriver;

public class AdminNav extends BaseNav{

	public AdminController admin;
	
	public AdminNav(WebDriver pDriver) {
		super(pDriver);
		admin = new AdminController(pDriver);
	}

	@Override
	void navigate() {
		getElementByID("admin_view").click();
	}

}

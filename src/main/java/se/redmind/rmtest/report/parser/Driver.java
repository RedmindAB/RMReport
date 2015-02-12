package se.redmind.rmtest.report.parser;

public class Driver {

	private final String driver;
	private String os, osVer, device, browser, browserVer;
	public Driver(String driver) {
		this.driver = driver;
		handleDriver();
	}
	
	private void handleDriver(){
		String[] sliced = driver.split("_");
		if (sliced.length > 1) {
			this.os 		= getValue(sliced, 0);
			this.osVer 		= getValue(sliced, 1);
			this.device 	= getValue(sliced, 2);
			this.browser	= getValue(sliced, 3);
			this.browserVer	= getValue(sliced, 4);
		}
	}
	
	private String getValue(String[] sliced, int index){
		try {
			return sliced[index];
		} catch (Exception e) {
			return null;
		}
	}

	public String getDriver() {
		return driver;
	}

	public String getOs() {
		return os;
	}

	public String getOsVer() {
		return osVer;
	}

	public String getDevice() {
		return device;
	}

	public String getBrowser() {
		return browser;
	}

	public String getBrowserVer() {
		return browserVer;
	}
	
}

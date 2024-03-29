package se.redmind.rmtest.report.parser;

public class Driver {

	private String driver;
	private String os, osVer, device, browser, browserVer;
	private boolean broken;
	public Driver(String driver) {
		this.driver = driver;
		handleDriver();
	}

	public Driver() {
		this.driver = null;
	}

	private void handleDriver(){
		String[] sliced = getSlized();
		if (sliced.length > 1) {
			this.os 		= getValue(sliced, 0);
			this.osVer 		= getValue(sliced, 1);
			this.device 	= getValue(sliced, 2);
			this.browser	= getValue(sliced, 3);
			this.browserVer	= getValue(sliced, 4);
		}
	}

	private String[] getSlized() {
		String[] sliced = driver.split("_");
		return sliced;
	}
	
	private String getValue(String[] sliced, int index){
		try {
			String value = sliced[index];
			if (value.length() <= 0) {
				broken = true;
			}
			return value;
		} catch (Exception e) {
			broken = true;
			return null;
		}
	}
	
//	public boolean isValidDriverSpecification(){
//		return driver.matches("([a-zA-Z0-9])+(_)+([a-zA-Z0-9])+(_)+([a-zA-Z0-9])+(_)+([a-zA-Z0-9])+(_)+([a-zA-Z0-9])");
//	}

	public String getDriverFullName() {
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
	
	public boolean isBroken(){
		return broken;
	}

	public String getDriver() {
		return driver;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public void setBrowserVer(String browserVer) {
		this.browserVer = browserVer;
	}
	
	public void setFullName(String fullname){
		this.driver = fullname;
	}
}

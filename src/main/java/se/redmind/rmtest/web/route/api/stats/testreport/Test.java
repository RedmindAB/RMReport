package se.redmind.rmtest.web.route.api.stats.testreport;

public class Test {

	private String klass;
	private String name;
	private String result;

	public Test(String klass, String name, String result) {
		this.klass = klass;
		this.name = name;
		this.result = result;
	}

	public String getKlass() {
		return klass;
	}

	public void setKlass(String klass) {
		this.klass = klass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}

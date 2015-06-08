package se.redmind.rmtest.web.route.api.cache;

import java.util.Date;

import com.google.gson.JsonElement;

public class WSCacheContainer {

	private JsonElement json;
	private Date creationDate;

	public WSCacheContainer(JsonElement json) {
		this.setJson(json);
		this.setCreationDate(new Date());
	}

	public JsonElement getJson() {
		return json;
	}

	public void setJson(JsonElement json) {
		if (json == null) {
			throw new NullPointerException();
		}
		this.json = json;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	@Override
	public String toString(){
		return json.toString();
	}
}

package se.redmind.rmtest.web.route.api.seleniumgrid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import se.redmind.rmtest.web.properties.ConfigHandler;
import se.redmind.rmtest.web.route.api.ErrorResponse;

public class SeleniumGridDAO {

	public SeleniumGridDAO() {
		
	}
	
	public String getSelenumGridData() {
		URL url;
		try {
			String servletPath = ConfigHandler.getInstance().getSeleniumGridURL();
			if (servletPath == null) {
				servletPath = "http://localhost:4444/grid/admin/GridQueryServlet";
			}
			url = new URL(servletPath);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				return new ErrorResponse("offline or wrong URL", SeleniumGridDAO.class).toString();
			}
			InputStream inputStream = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (IOException e) {
			return new ErrorResponse("offline or wrong URL", SeleniumGridDAO.class).toString();
		}
	}

}

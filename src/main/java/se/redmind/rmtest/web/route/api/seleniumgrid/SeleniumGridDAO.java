package se.redmind.rmtest.web.route.api.seleniumgrid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import se.redmind.rmtest.web.route.api.ErrorResponse;

public class SeleniumGridDAO {

	public SeleniumGridDAO() {
	}
	
	public String getSelenumGridData() {
		URL url;
		try {
			url = new URL("http://localhost:4444/grid/admin/GridQueryServlet");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			int responseCode = con.getResponseCode();
			if (responseCode != 200) {
				return new ErrorResponse("offline", SeleniumGridDAO.class).toString();
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
			e.printStackTrace();
			return new ErrorResponse("offline", SeleniumGridDAO.class).toString();
		}
	}

}

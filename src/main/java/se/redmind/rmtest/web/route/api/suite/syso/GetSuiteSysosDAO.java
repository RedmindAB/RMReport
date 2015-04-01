package se.redmind.rmtest.web.route.api.suite.syso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import se.redmind.rmtest.report.sysout.ReportSystemOutPrintFile;

public class GetSuiteSysosDAO {

	private static final String BASEDIR = ReportSystemOutPrintFile.BASEDIR;
	
	public String getSysos(long timestamp, int suiteid){
		try {
			File file = new File(BASEDIR+"/"+suiteid+"/"+timestamp+".txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			return "No system out prints for this timestamp";
		}
	}
}

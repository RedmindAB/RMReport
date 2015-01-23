package se.redmind.rmtest.report.test;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import se.redmind.rmtest.report.gson.ReportSerializer;
import se.redmind.rmtest.report.parser.Report;
import se.redmind.rmtest.report.parser.Reports;
import se.redmind.rmtest.report.reporthandler.ReportHandler;

public class ReportHandlerTest {

	private ReportHandler handler = new ReportHandler();
	
	@Test
	public void test() {
		List<Report> reports = handler.getLogList();
	}
	
	@Test
	public void testGson(){
		Reports reps = new Reports();
		reps.setReports(new ReportHandler().getLogList());
		System.out.println("Reports found");
		Gson g = new GsonBuilder().registerTypeAdapter(Reports.class, new ReportSerializer()).create();
		String json = g.toJson(reps);
		System.out.println(json);
	}

}

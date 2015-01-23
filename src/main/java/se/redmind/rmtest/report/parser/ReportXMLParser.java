package se.redmind.rmtest.report.parser;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReportXMLParser {

	public static final String TESTSUITE_TAG = "testsuite";
	
	public ReportXMLParser() {
		
	}

	public NodeList getNodeList(File xmlFile, String tagName) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc;
				doc = dBuilder.parse(xmlFile);

			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName(tagName);
			return nList;
		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Report getReportFromFile(File file){
		Element report = (Element) getNodeList(file, "testsuite").item(0);
		return new Report(report);
	}

}

package se.redmind.rmtest.report.reportloader;

import java.io.File;
import java.util.ArrayList;

import se.redmind.rmtest.main.properties.PropertiesReader;

public class ReportLoader {
	
	public static String reportFolderPath;
	private File reportFolder = null;
	
	/**
	 * Creates a new instance of the ReportLoader where the path to the report folder is expected to be PROJECT_DIR/target/surefire-reports.
	 */
	public ReportLoader() {
		reportFolderPath = searchReportFolderPath();
	}
	
	/**
	 * Create a new instance of the ReportLoader where the path is set to a specific folder.
	 * @param pathToReportFolder - this parameter should be from root.
	 * @param createFolderIfNotExists - creates a new folder if the folder dose not exists.
	 */
	public ReportLoader(String pathToReportFolder, boolean createFolderIfNotExists){
		reportFolderPath = findFolder(pathToReportFolder, createFolderIfNotExists);
	}
	
	private String searchReportFolderPath(){
		String reportDir = new PropertiesReader().getTestDirectory();
		return findFolder(reportDir, false);
	}
	
	/**
	 * 
	 * @param path - the path to the folder.
	 * @param createFolderIfNotExists - if the folder dose not exist it will create that folder and return the path.
	 * @return
	 */
	private String findFolder(String path, boolean createFolderIfNotExists){
		File f = new File(path);
		if (f.exists()) {
			return f.getAbsolutePath();
		}
		else if (createFolderIfNotExists){
			f.mkdirs();
			return f.getAbsolutePath();
		}
		else{
			System.err.println("The report folder was not found, its expected to be: "+path);
			return null;
		}
	}
	
	
	/**
	 * @return - the report folder as a File object.
	 */
	public File getReportFolderAsFile(){
		if (reportFolder != null) {
			return reportFolder;
		}
		else return new File(reportFolderPath);
	}
	
	public File getXMLReportByFileName(String fileName){
		return findOneFile(getReportFolderAsFile(), fileName);
	}
	
	/**
	 * This method is only returning the .xml files in the reports folder.
	 * @return - ArrayList of File, from the xml files in the reports folder.
	 */
	public ArrayList<File> getXMLReports(){
		return getFilesFromFolder(getReportFolderAsFile(), ".xml");
	}
	
	/**
	 * Finds the output txt-file based on the XML-report file.
	 * @param xmlReport - File object based for the search.
	 * @return - The output file from the test.
	 */
	public File getSystemOutFile(File xmlReport){
		int fileExtentionIndex = xmlReport.getName().lastIndexOf(".");
		String reportName = xmlReport.getName().substring(5, fileExtentionIndex);
		String searchString = reportName+"-output.txt";
		return findOneFile(getReportFolderAsFile(), searchString);
	}
	
	/**
	 * Gets the Maven output file from the test
	 * @param xmlReport - The report based for the search
	 * @return - the Maven test output file from the test
	 */
	public File getOutputFile(File xmlReport){
		int fileExtentionIndex = xmlReport.getName().lastIndexOf(".");
		String reportName = xmlReport.getName().substring(5, fileExtentionIndex);
		String searchString = reportName+".txt";
		return findOneFile(getReportFolderAsFile(), searchString);
	}
	
	/**
	 * Returning files from a specific folder  
	 * @param folder - the folder searching though
	 * @param fileExtention - if null this method will return all files in the folder
	 * @return - ArrayList of the files in the specific folder.
	 */
	private ArrayList<File> getFilesFromFolder(File folder, String fileExtention){
		ArrayList<File> fileArray = new ArrayList<File>();
		if (fileExtention != null && !fileExtention.contains(".")) {
			fileExtention = "."+fileExtention;
		}
		for (File file : folder.listFiles()) {
			if (fileExtention != null) {
				boolean matchFileExtention = file.getName().endsWith(fileExtention);
				if (matchFileExtention) {
					fileArray.add(file);
				}
			}
			else fileArray.add(file);
		}
		return fileArray;
	}
	
	private File findOneFile(File folder, String filename){
		for (File file : folder.listFiles()) {
			boolean matchFileName = file.getName().equals(filename);
			if (matchFileName) {
				return file;
			}
		}
		return null;
	}
	
	public String getReportFolderPath(){
		return reportFolderPath;
	}
}

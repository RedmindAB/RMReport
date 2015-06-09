package se.redmind.rmtest.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class ScriptRunner {


	public ScriptRunner() {}
	
	public String run(Process process){
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				sb.append(temp).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return sb.toString();
	}
	
	public String run(String cmd){
		String[] cmds = new String[]{"/bin/bash", "pwd"}; 
		return run(cmds);
	}

	
	public String run(String... cmds){
		try {
			return run(Runtime.getRuntime().exec(cmds, getEnvs()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private String[] getEnvs() {
		Map<String, String> envs = System.getenv();
		Set<Entry<String, String>> entrySet = envs.entrySet();
		String[] envArray = new String[entrySet.size()];
		int index = 0;
		for (Entry<String, String> entry : entrySet) {
			envArray[index] = entry.getKey()+"="+entry.getValue();
			index++;
		}
//		for (String string : envArray) {
//			System.out.println(string);
//		}
		return envArray;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.redmind.rmtest.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gustavholfve
 */
public class StringKeyValueParser {
    
    String template;
    Pattern pattern;
    Matcher matcher;

    public StringKeyValueParser(String template) {
        initMatcher(template);
    }

    public StringKeyValueParser() {
    }
    
	private void initMatcher(String template) {
		this.template = template;
        pattern = Pattern.compile("\\{(.+?)\\}");
        matcher = pattern.matcher(template);
	}
    

    
    /**
     * Takes a String and a HashMap and parse the string to a new String with the keys from the hashmap inserted.
     * 
     *  example: 	String: "my name is {name} and i'm {age} years old"
     *  HashMap: 	key : value
     *  			name : foo
     *  			age : 23 
     *  result : 	"my name is foo and i'm 23 years old"
     * @param stringTemplate - String with "hello {key}" pattern
     * @param map - map with keys equal to the string thats should be parsed.
     * @return - a new string with the values of the keys inserted.
     */
    public String getString(HashMap<String, String> map) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (matcher.find()) {
        	
        	// strip { and } from string and get value from map 
            String replacement = (String) map.get(matcher.group(1));
            
            // if value doesn't exist, set it to be the expected token
            if (replacement == null) {
                replacement = matcher.group(0);
            }
            
            // replace template token with the new value
            matcher.appendReplacement(stringBuffer, replacement.replace("$", ""));
            i = matcher.end();
        }
        
        // append what is left of the string
        stringBuffer.append(template.substring(i, template.length()));
        
        matcher.reset();
        return stringBuffer.toString();
    }
    
    public String getString(String template, HashMap<String, String> map){
    	initMatcher(template);
    	return getString(map);
    }
    
}

import java.io.*;
import facebook4j.*;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;
import java.util.*;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

/**
 * The purpose of the code is to find connections between people present in a Facebook Group
 * Input file consists of Unique Ids for individuals 
 * @author mayank
 *
 */
public class MainDriver {
	public static String inputFilePath = "/home/mayank/Documents/Temp/inputs";
	public static String outputEdgelistPath = "/home/mayank/Documents/Temp/outputs";
	public static String accessToken = "CAACEdEose0cBAPUL62hdJTGJjzWLEcCMYAAZBrwGRbtwo9fGtynkSfbEVFSYYIWbEZCN8OWQZC8NQLAXZB7gskpUwhhNFXwWpOZBNM0OOy0m01TmQYJAATF3TvnXPJYK5EAobm1owDsGjNuETeZCUUmqWd9ZBJPvgj8rL8yZAIwXAAZDZD";
	
	public static void main(String [] args) throws IOException,IllegalStateException, FacebookException, JSONException{
		BufferedReader readInput = new BufferedReader(new FileReader(inputFilePath));
		String cn;
		Facebook fb = new FacebookFactory().getInstance();
		fb.setOAuthAppId("1409413749277692", "42282946a0ac67b5762ebb228e8ba4f2");
		AccessToken access = new AccessToken(accessToken);
		fb.setOAuthAccessToken(access);

	
		ArrayList <String> j = new ArrayList<String>();
		while ((cn=readInput.readLine())!= null){
			String temp = cn.split(":")[1];
			j.add(temp.substring(2,temp.length()-1));
		}
		
		BufferedWriter writeOut = new BufferedWriter(new FileWriter(outputEdgelistPath));
		for(int i=0;i<j.size();i++){
			for(int k=i+1;k<j.size();k++){
				String tempQuery = "SELECT uid2 FROM friend WHERE uid1 = '" + j.get(i) + "' and uid2 = '" + j.get(k) + "'";
		
				JSONArray jsonArray = fb.executeFQL(tempQuery);
				if(jsonArray.length()>0){
					writeOut.append(j.get(i)+","+j.get(k));
					writeOut.append("\n");
				}
				
			}	
		}
	}
}

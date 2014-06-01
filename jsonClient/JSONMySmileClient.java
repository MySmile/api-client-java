package ua.com.mysmile.jsonClient;

import org.json.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import ua.com.mysmile.*;

public class JSONMySmileClient extends MySmileClient<JSONObject> {
	
	
	/**
	 * Constructor with default ParserFactory contained json and xml parsers.
	 */
	public JSONMySmileClient(URL endpoint, String resString) {
		this.factory = new JSONParseFactory();
		this.factory.addParser(new JSONParser(), "application/json");
		this.factory.addParser(new XMLParser(), "application/xml");
		this.endpoint = endpoint;
		resource = new Resource(resString);
	}
	
	
    /**
     * Simple client to the site on MySmile site.
     * Show lists of all site languages and menus and as well as contact information of the site.   
     * 
     * @param args -- URL to the MySmile site API
     */
	public static void main(String[] args) {
		try {
			String url;
			String delim1 = "==================================================";
			String delim2 = "--------------------------------------------------";
			if (args.length == 0)
				url = "http://demo.mysmile.com.ua/api/";
			else
				url = args[0];
			URL endpoint = new URL(url);
			System.out.println("");
			System.out.println("Site: " + endpoint.toString() );
			JSONMySmileClient client = new JSONMySmileClient(endpoint, "");
            Resource resource = client.getResource();
            
            // Configuration of Client
            resource.setParam("format", "json");
       /*     Map<String,String> reqProp = new TreeMap<String,String>();
            reqProp.put("timeout", "5");
            reqProp.put("connecttimeout", "5");
			client.setRequestProperties(reqProp);/**/
            
			//Print languages of the site: 
            resource.setMainResource("language");
			JSONArray lang = client.request().getJSONArray("data");
			System.out.println(delim1);
			System.out.println("List of languges of the site:");
			for (int i = 0; i < lang.length(); i++) {
				System.out.println("  - " + lang.optString(i));
			}
			System.out.println(delim1);
			
			//Print the list of menus of the site by all languages
			resource.setMainResource("content");
			for (int i = 0; i < lang.length(); i++) {
				String l = lang.optString(i);
				resource.setParam("lang", l);
				JSONArray menus = client.request().getJSONArray("data");
				if(i != 0) System.out.println(delim2);
				System.out.println("List of menus by " + l + ": ");
				for(int j = 0; j< menus.length(); j++) {
					JSONObject menu = menus.getJSONObject(j);
					System.out.println("  - " + menu.optString(JSONObject.getNames(menu)[0]) );
				}
			}
			System.out.println(delim1);
			
			//Print the contact information of the site
			resource.removeParam("lang");
			resource.setMainResource("contact");
			JSONObject contacts = client.request().getJSONObject("data");
			String[] names = JSONObject.getNames(contacts);
			System.out.println("Contact information of the site:");
			for(int i = 0; i< names.length; i++) {
				System.out.println("  - " + names[i] + " : " + contacts.getString(names[i]));
			}
			System.out.println(delim1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

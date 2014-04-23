package ua.com.mysmile.jsonClient;

import org.json.*;

import java.net.MalformedURLException;
import java.net.URL;

import ua.com.mysmile.*;

public class JSONMySmileClient extends MySmileClient<JSONObject> {

	public JSONMySmileClient(URL endpoint, String resString) {
		this.factory = new JSONParseFactory();
		this.factory.addParser(new JSONParser(), "application/json");
		this.factory.addParser(new XMLParser(), "application/xml");
		this.endpoint = endpoint;
		resource = new Resource(resString);
	}

	public static void main(String[] args) {
		try {
			String url;
			if (args.length == 0)
				url = "http://demo.mysmile.com.ua/api/";
			else
				url = args[0];
			URL endpoint = new URL(url);
			JSONMySmileClient client = new JSONMySmileClient(endpoint, "");

			client.getResource().setParam("format", "json");
			client.getResource().setMainResource("language");
			JSONArray lang = client.request().getJSONArray("data");

			System.out.println("The url: " + url + "has the next languages:");

			for (int i = 0; i < lang.length(); i++) {
				System.out.println("  - " + lang.optString(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

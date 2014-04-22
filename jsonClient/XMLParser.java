package ua.com.mysmile.jsonClient;
import java.io.*;
import java.util.*;
import org.json.*;

import ua.com.mysmile.Parser;


public class XMLParser implements Parser<JSONObject> {
	@Override
	public JSONObject parse(String str) {
		return XML.toJSONObject(str);
	}
	@Override
	public JSONObject parse(InputStream in) {
		String str = new Scanner(in,"UTF-8").useDelimiter("\\A").next();
		return XML.toJSONObject(str);
	}

	@Override
	public Parser<JSONObject> getInstance() {
		// TODO Auto-generated method stub
		return new XMLParser();
	}
}

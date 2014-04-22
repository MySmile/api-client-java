package ua.com.mysmile.jsonClient;
import java.io.InputStream;

import org.json.*;

import ua.com.mysmile.Parser;

public class JSONParser implements Parser<JSONObject> {
	
	@Override
	public Parser<JSONObject> getInstance() {
		return new JSONParser();
	}
	
	@Override
	public JSONObject parse(String str) {
		return new JSONObject(str);
	}
	
	@Override
	public JSONObject parse(InputStream in) {
		return new JSONObject(new JSONTokener(in));
	}

}

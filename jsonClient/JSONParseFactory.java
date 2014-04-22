package ua.com.mysmile.jsonClient;
import org.json.*;

import ua.com.mysmile.Parser;
import ua.com.mysmile.ParserFactory;

import java.util.*;

public class JSONParseFactory implements ParserFactory<JSONObject>  {

	private Map<String,Parser<JSONObject> > map;
	
	JSONParseFactory() {
		map = new TreeMap<String,Parser<JSONObject> >();
	}
	
	@Override
	public Parser<JSONObject> makeParser(String format) {
		return map.get(format).getInstance();
	}
	@Override
	public boolean hasFormat(String format) {
		return map.containsKey(format);
	}
	@Override
	public void removeParser(String format) {
		map.remove(format);
	}
	@Override
	public void addParser(Parser<JSONObject> parser, String format) {
		map.put(format, parser);
	}

}

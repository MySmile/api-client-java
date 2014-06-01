package ua.com.mysmile;

import java.io.IOException;
import java.io.*;
import java.net.*;

/**
 * MySmileClient is the simple client to the web-service such as MySmile REST
 * API. It uses the factory of parsers to parse response from the server with
 * different formats (content-type). To represent resource part of request it is
 * used Resource class.
 * 
 * @author Banyas Miron
 * 
 * @param <ParserObject>
 *            -- the parser object that contain all data and
 * 
 * @see ParserFactory
 * @see Resource
 */
public class MySmileClient<ParserObject> {

	protected Resource resource = null;
	protected ParserFactory<ParserObject> factory = null;
	protected URL endpoint = null;

	public MySmileClient(ParserFactory<ParserObject> factory, URL endpoint,
			String resString) {
		this.factory = factory;
		this.resource = new Resource(resString);
		this.endpoint = endpoint;
	}

	/**
	 * Protected default constructor of MySmileClient to derived class
	 * initialization
	 */
	protected MySmileClient() {
	}

	/**
	 * Get the resource of the request to a web-service
	 * 
	 * @return resource with parameters
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * Set the endpoint of the request to the web-service
	 * 
	 * @param endpoint
	 *            of the request
	 */
	public void setEndpoint(URL endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @return endpoint of the request to web-service
	 */
	public URL getEndpoint() {
		return this.endpoint;
	}

	/**
	 * 
	 * @return HTTP connection to the web-service
	 * @throws IOException
	 */
	public HttpURLConnection getHttpConnectinon() throws IOException {
		URL reqURL = new URL(endpoint, resource.constructResourceString());
		return (HttpURLConnection) reqURL.openConnection();
	}

	/**
	 * Make the request to the web-service and parse response to ParserObject.
	 * 
	 * @return ParserObject of response from the web-service
	 * @throws IOException
	 */
	public ParserObject request() throws IOException {
		HttpURLConnection con = getHttpConnectinon();
		if (con.getResponseCode() != 200)
			throw new java.lang.IllegalStateException(
					"The code of response doesn't equal to 200");
		String format = con.getContentType();
		if (format == null)
			throw new java.lang.IllegalStateException(
					"The header of ht response doesn't contain Content-Type message");
		InputStream response = con.getInputStream();
		Parser<ParserObject> parser = factory.makeParser(format);
		return parser.parse(response);
	}

}

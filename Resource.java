package ua.com.mysmile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Resource is the class to represent the resource part of URL to access to a
 * web-service or a web-site. It contains directly the resource, parameters of
 * GET request and fragment id.
 * 
 * @author Banyas Miron
 * 
 */
public class Resource {

	private static final String verMRes = "(^|.+)(=|\\?|#|\\&)(.+|$)";
	private static final String verElem = "(^$)||((^|.+)(=|\\?|#|\\&)(.+|$))";

	public String mainResource;
	public String fragmentId;
	public Map<String, String> param;

	/**
	 * Create the empty Resource
	 */
	public Resource() {
		this.param = new TreeMap<String, String>();
		mainResource = "";
		fragmentId = "";
	}

	/**
	 * Create the Resource from the string and the set of parameters
	 * 
	 * @param res
	 *            -- the resource string
	 * @param map
	 *            -- the container contained the pairs of names and values of
	 *            the parameters
	 */
	public Resource(String res, Map<String, String> map) {
		this.param = map;
		if (!verify(map))
			throw new IllegalArgumentException(
					"Incorrect map of the parameters");
		parseResourceString(res);
	}

	/**
	 * Create the Resource from string
	 * 
	 * @param res
	 *            -- resource string
	 */
	public Resource(String res) {
		this.param = new TreeMap<String, String>();
		parseResourceString(res);
	}

	/**
	 * Set the main part of Resource
	 * 
	 * @param res
	 *            -- resource string
	 */
	public void setMainResource(String res) {
		if (!verify(res, verMRes))
			throw new IllegalArgumentException("Incorrect main resource: '"
					+ res + "'");
		this.mainResource = res;
	}

	/**
	 * @return the main part of the Resource;<br>
	 *         <Code>null</Code> if the main part of the Resource isn't
	 *         specified
	 */
	public String getMainResource() {
		return this.mainResource;
	}

	/**
	 * Set the fragment_id of the Resource
	 * 
	 * @param res
	 *            -- the fragment_id string
	 */
	public void setFragmentId(String res) {
		if (!verify(res, verElem))
			throw new IllegalArgumentException("Incorrect fragmentId: '" + res
					+ "'");
		this.fragmentId = res;
	}

	/**
	 * @return the fragment_id of the Resource;<br>
	 *         <Code> null </Code> if fragment_id isn't specified
	 */
	public String getFragmentId() {
		return this.fragmentId;
	}

	/**
	 * Set parameters of Resource
	 * 
	 * @param parName
	 *            -- the name of parameter
	 * @param parValue
	 *            -- the value of parameter
	 */
	public void setParam(String parName, String parValue) {
		if (!verify(parName, verElem))
			throw new IllegalArgumentException("Incorrect parameter name: '"
					+ parName + "'");
		if (!verify(parValue, verElem))
			throw new IllegalArgumentException("Incorrect parameter value: '"
					+ parName + "'");
		this.param.put(parName, parValue);
	}

	/**
	 * Get parameter of the Resource by name
	 * 
	 * @param parName
	 *            -- the name of parameter
	 * @return the value of parameter;<br>
	 *         <Code>null</Code> if there is no parameter with that name
	 */
	public String getParam(String parName) {
		return (String) this.param.get(parName);
	}

	/**
	 * Remove parameter from the Resource
	 * 
	 * @param parName
	 *            --- the name of parameter
	 * @return the value of parameter;<br>
	 *         <Code>null</Code> if there is no parameter with that name
	 */
	public String removeParam(String parName) {
		return (String) this.param.remove(parName);
	}

	/**
	 * @return the map of parameters of the Resource
	 */
	public Map<String, String> getParams() {
		return this.param;
	}

	/**
	 * Construct the resource string from the main part of resource,<br>
	 * the set of parameters and fragment_id
	 * 
	 * @return the resource string
	 */
	public String constructResourceString() {
		String res = new String();
		if (!this.param.isEmpty()) {
			Set<String> kSet = this.param.keySet();
			for (String k : kSet) {
				res = res + "&" + k + "=" + (String) this.param.get(k);
			}
			res = res.replaceFirst("\\&", "?");
		}
		res = this.mainResource + res
				+ (!this.fragmentId.equals("") ? "#" + this.fragmentId : "");
		return res;
	}

	public String toString() {
		return "{ Main Resourse: " + this.mainResource + ", Parameters: "
				+ this.param.toString() + ", FragmentId: " + this.fragmentId
				+ "}";
	}

	/**
	 * Parse the resource sting to main part of resource and the set of
	 * parameters
	 * 
	 * @param res
	 *            -- the Resource string
	 */
	protected void parseResourceString(String res) {
		String[] res_arr = res.split("#", 2);
		if (res_arr.length > 2)
			throw new IllegalArgumentException(
					"The resource string has more than one symbol '#'");
		if (res_arr.length == 2) {
			if (!verify(res_arr[1], "(^$)||((^|.+)(=|\\?|#|\\&)(.+|$))"))
				throw new IllegalArgumentException("Incorrect fragmentId: '"
						+ res_arr[1] + "'");
			this.fragmentId = res_arr[1];
		} else {
			this.fragmentId = "";
		}
		res = res_arr[0];

		res_arr = res.split("\\?");
		if (res_arr.length == 0) {
			this.mainResource = "";
			return;
		}
		if (res_arr.length > 2)
			throw new IllegalArgumentException(
					"The resource string hasn't or has more than one symbol '?'.");
		if (!verify(res_arr[0], "(^|.+)(=|\\?|#|\\&)(.+|$)"))
			throw new IllegalArgumentException("Incorect main resource: '"
					+ res_arr[0] + "'");
		this.mainResource = res_arr[0];
		if (res_arr.length == 1)
			return;

		res_arr = res_arr[1].split("\\&");

		for (int i = 0; i < res_arr.length; i++) {
			String[] par_arr = res_arr[i].split("\\=");
			if ((par_arr.length != 2) || (par_arr[0].isEmpty())
					|| (par_arr[0].isEmpty()))
				throw new IllegalArgumentException("Incorrect parameter: '"
						+ res_arr[i] + "'");
			this.param.put(new String(par_arr[0]), new String(par_arr[1]));
		}
	}

	/**
	 * Verify the string by the pattern
	 * 
	 * @param s
	 *            -- the string
	 * @param pattern
	 *            -- the pattern
	 * @return <code>true</code> if string s satisfy the pattern;<br>
	 *         <code>false</code> if otherwise
	 */
	protected static boolean verify(String s, String pattern) {
		return !s.matches(pattern);
	}

	/**
	 * Verify the map of parameters and its values
	 * 
	 * @param map
	 * @return <code>true</code> if map satisfy the pattern;<br>
	 */
	protected static boolean verify(Map<String, String> map) {
		Set<String> kSet = map.keySet();
		Iterator<String> it = kSet.iterator();
		while (it.hasNext()) {
			String k = (String) it.next();
			if ((verify(k, "(^$)||((^|.+)(=|\\?|#|\\&)(.+|$))"))
					&& (verify((String) map.get(k),
							"(^$)||((^|.+)(=|\\?|#|\\&)(.+|$))")))
				return false;
		}
		return true;
	}

	public static void main(String[] args) throws IOException {
		if (args.length == 0) {
			System.out.println("Calling without name of file");
			return;
		}

		DataInputStream in = new DataInputStream(new FileInputStream(args[0]));
		String resStr = in.readUTF();
		System.out.println(resStr);
		Resource res = new Resource(resStr);

		System.out.println(res.toString());
		System.out.println(res.constructResourceString());
		in.close();
	}
}

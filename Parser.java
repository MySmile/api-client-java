package ua.com.mysmile;

import java.io.*;

/**
 * Parser is the interface of parsing document which use
 * <Code>ParserObject</Code> as a class that provide treatment with parsed
 * document.
 * 
 * @author Banyas Miron
 * 
 * @param <ParserObject>
 *            class that contains all data and implements all methods to parse
 *            the document
 */
public interface Parser<ParserObject> {

	/**
	 * @return the instance of the Parser
	 */
	public Parser<ParserObject> getInstance();

	/**
	 * 
	 * @param str
	 *            -- The string that contains document that must be parsed
	 * @return ParserObject that contains all data and implements all methods to
	 *         parse the document
	 */
	public ParserObject parse(String str);

	/**
	 * 
	 * @param str
	 *            -- The Input Stream that contains document that must be parsed
	 * @return ParserObject that contains all data and implements all methods to
	 *         parse the document
	 */
	public ParserObject parse(InputStream in);

}

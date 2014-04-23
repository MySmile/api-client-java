package ua.com.mysmile;

/**
 * ParserFactory is the interface of factory to parsing the document of
 * different formats. The implementation of this interface must allow to create
 * for the different formats of document an object of generic interface
 * <Code>Parser</Code> which can parse document into ParserObject. ParserObject
 * is a type variable that must provide treatment of parsed document.
 * <p>
 * To use ParserFactory you must create own hierarchy of Parsers and
 * ParserObjects with a common parent parsing class.
 * 
 * @author Banyas Miron
 * 
 * @param <ParserObject>
 *            -- class that contains all data and implements all methods to
 *            parse the document
 * @see Parser
 */
public interface ParserFactory<ParserObject> {

	/**
	 * Create parser of this format
	 * 
	 * @param format
	 *            -- the format of parser
	 * @return parser
	 */
	public Parser<ParserObject> makeParser(String format);

	/**
	 * Check whether the ParserFactory has the parser of this format
	 * 
	 * @param format
	 *            of the parsed document
	 * @return true if the parser can create the parser for this format;<br>
	 *         false otherwise
	 */
	public boolean hasFormat(String format);

	/**
	 * Add new parser to the Factory
	 * 
	 * @param parser
	 *            -- added parser
	 * @param format
	 *            -- the format of parser
	 */
	public void addParser(Parser<ParserObject> parser, String format);

	/**
	 * Remove parser from the factory
	 * 
	 * @param format
	 *            -- the format of removed parser
	 */
	public void removeParser(String format);

}

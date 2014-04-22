package ua.com.mysmile;


public interface ParserFactory<ParserObject> {
	/**
	 * Create parser of this format
	 * 
	 * @param format of the document that must be parsed
	 * @return Parser of the document 
	 */
	public Parser<ParserObject> makeParser(String format);
	/**
	 * Check whether the ParserFactory has the parser of this format 
	 * 
	 * @param format of the parsed document
	 * @return true  if the parser can create the parser for this format
	 * 		   false else 
	 */	
	public boolean hasFormat(String format);
	/**
	 * Add new parser to the Factory
	 * 
	 * @param parser
	 * @param format
	 */
	public void addParser(Parser<ParserObject> parser,String format);
	/**
	 * Remove parser from the factory
	 * 
	 * @param format of removed parser 
	 */
	public void removeParser(String format);
	
}

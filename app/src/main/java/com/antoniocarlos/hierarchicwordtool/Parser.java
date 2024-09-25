package com.antoniocarlos.hierarchicwordtool;

/**
 * This Interface is meant to 
 * parse against a defined set of tokens in a 
 * steam of tokens of a generic type. The instance
 * communicates with the client through
 * its methods.  
 */
public interface Parser<I,T>{
  /**communicate how much of the
   * input the parser intends to consume
   */
  public  enum Outcomes{
    ACCEPT,ACCEPT_SINGLE,REJECT
  }
  /**
   * checks if current input meets the criteria to trigger input consuming
   * It should be noted tha the parser will readily consume the input tested
   * with this method if it mets the criteia, and if it's the case the parser will
   * signal that has already consumed enough through the accept_single outcome.
   * 
   * @param input current token of the stream.
   * @param start offset from the start of the token.
   * @return boolean whether or not the parser should keep consuming tokens from  the current stream.
   */
  Outcomes check(I input, int start);

  /**
   * Accepts input and return whether th parser intends to keep
   * processing input. While a parsing is consuming input, other parsers won't have
   * the chance to check or consume input.
   * 
   * @param input current token of the stream.
   * @param start offset from the start of the token.
   * @return boolean whether or not the parser should keep consuming tokens from  the current stream.
   */
  boolean consume(I input, int start);
  
  /**
   * Returns the already parsed data
   */
  T getParsedData();
  
  /**
   * Restores the parser tob its original condition
   * before the parsing of any input
   */
  void reset();
}

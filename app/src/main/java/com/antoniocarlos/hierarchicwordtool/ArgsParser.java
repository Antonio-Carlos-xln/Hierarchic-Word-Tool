package com.antoniocarlos.hierarchydata;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.time.Instant;
import java.time.Duration;

/*
 * A client for the Parser impementations provided in this package
 * Holds the logic of parsing, comunicating with the parsing
 * instances through the Parser interface methods.
 * It's also a concrete implementation of the DataNode abstract class
 * When requested, the instances does the parsing but returns only the time it took
 * clients that want the data from the parsers, must subscribe to them before
 * the injection in this class and the reuest of parsing.
 */
public class ArgsParser extends DataNode<String,Long>{
  protected Map<String,Object> args;
  protected String[] input;
  protected List<Parser> parsers;
  public ArgsParser(String[] args,List<Parser> parsers){
    this.input = args;
    this.parsers = parsers;
  }

  @Override
  public Long getData(){
    Instant start = Instant.now();
    processArgs();
    return Duration.between(start,Instant.now()).toMillis();
  }
  
  public void processArgs(){
    int a = 0;
    Parser currentParser = null;
    Parser.Outcomes outcome = null;
    for(String arg : this.input){
      if(currentParser != null){
        if(currentParser.consume(arg,a)){
          continue;
        }else{
          currentParser = null;
          continue;
        }
      }
      for(Parser parser : this.parsers){
        outcome = parser.check(arg,a);
        if(outcome == Parser.Outcomes.ACCEPT_SINGLE){
          break;
        }
        if(outcome == Parser.Outcomes.ACCEPT){
          currentParser = parser;
          break;
        }
      }
      if(currentParser == null && outcome != Parser.Outcomes.ACCEPT_SINGLE){
        throw new IllegalStateException("Unexpected input token = " +arg+ " for input = "+String.join(" ",input)); 
      }
    }
  }
}

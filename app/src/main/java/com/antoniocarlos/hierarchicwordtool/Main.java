package com.antoniocarlos.hierarchicwordtool;

import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Instantiating and relating the DataNodes, including the ones
 * that are also implementations of the Parser inteerface.
 * Without this step, the data and notifications won't flow properly
 */
public class Main{
  // Usually the configuration of the src and dst files would be through
  // parrameters in the CLI, but the options required in the specification
  // did not include those
  static String sourcePath = "./dicts/hierarchy.json";
  static String destinationPath = "output.txt";
  static boolean write = false;
  public static void main(String[] args){
    var jsonParser = new JSONCParserDataNode(Main.sourcePath);
    var analyzeParser = new AnalyzeParser();
    var phraseParser = new PhraseParser();
    var depthParser = new DepthParser();
    var verboseParser = new VerboseParser();
    var argsparser = new  ArgsParser(args, Arrays.asList(
      analyzeParser,
      depthParser,
      verboseParser,
      phraseParser
    ));
    var counterContext  = new CounterContextDataNode();
    counterContext.set("phrase",phraseParser);
    counterContext.set("json",jsonParser);
    counterContext.set("depth",depthParser);
    counterContext.set("t0",argsparser);
    var formatter = new FormatterDataNode();
    formatter.set("verbose",verboseParser);
    formatter.set("counter-reports",counterContext);
    if(write){
      try(FileWriter writer = new FileWriter(Main.destinationPath)){
        writer.write(formatter.getData());
      }catch(IOException e){}
    }else{
      System.out.println(formatter.getData());
    }
  }
}

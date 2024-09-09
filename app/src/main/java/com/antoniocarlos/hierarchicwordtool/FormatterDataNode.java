package com.antoniocarlos.hierarchicwordtool;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

/**
 * Implementation of the DataNode meant to create the input 
 * in the specified format, requires the List of counted words,
 * the data regarding the verbose option from the CLI and 
 * the list of relevant measured times, where
 * (t0 -> time to read the json and create the daya structure,
 *  t1 -> time it took to parse the options from the cli
 *  t2 -> time it took to count the the words in the given phrase) 
 *  @DataNode
 */
public class FormatterDataNode extends DataNode<String,String>{
  protected StringBuilder out;
  public static final int PADDING = 5;
  public static final String L1  = "Tempo de captura dos parametros";
  public static final String L2  = "Tempo de comparaçao";
  @Override
  public String getData(){
    // gets the data fromother nodes
    List<TreeCounter.TreeNode> data = (List<TreeCounter.TreeNode>)this.from("counter-reports").get("counts");
    boolean verbose = (boolean)this.ask("verbose");
    List<Integer>  times = (List<Integer>)this.from("counter-reports").get("times");
    out = new StringBuilder();
    //if there's no data to report, just print a zero
    if(data.isEmpty()){
      out.append("0;");
    }
    boolean zeros = true;
    for(TreeCounter.TreeNode e:data){
      out.append(e.getName());
      out.append(" = ");
      out.append(e.getValue());
      out.append(";");
      zeros = zeros && e.getValue() == 0;
    }
    if(zeros){
      out.setLength(0);
      out.append("0;");
    }
    //if all the reports where zero, again, print only a zero
    out.append("\n");
    if(verbose){
      int max  = Math.max(L1.length(),L2.length());
      int dif = (L1.length() - L2.length());
      String t1 = String.valueOf(times.get(0));
      String t2 = String.valueOf(times.get(1));
      int max2 =  Math.max(t1.length(),t2.length());
      int dif2 = (t1.length() - t2.length());
      //parameters for correctly allign the strings
      out.append("|");
      out.append("-".repeat(PADDING + max + max2));
      out.append("| ");
      out.append("\n");
      out.append("| ");
      out.append(L1);
      if(dif < 0){
        out.append(" ".repeat(-dif));
      }
      out.append(" | ");
      out.append(t1);
      if(dif2 < 0){
        out.append(" ".repeat(-dif2));
      }
      out.append(" |");
      out.append("\n");
      out.append("|");
      out.append("-".repeat(PADDING + max + max2));
      out.append("|");
      out.append("\n");
      out.append("| ");
      out.append(L2);
      if(dif > 0){
        out.append(" ".repeat(dif));
      }
      out.append(" | ");
      out.append(t2);
      if(dif2 > 0){
         out.append(" ".repeat(dif2));
      }
      out.append(" |");
      out.append("\n");
      out.append("|");
      out.append("-".repeat(PADDING + max + max2));
      out.append("|");
    }
    return out.toString();
    }
}  

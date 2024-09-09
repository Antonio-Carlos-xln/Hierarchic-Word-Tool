package com.antoniocarlos.hierarchicwordtool;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This class does the counting using a mapping to every node 
 * (@See JSONCParserDataNode) allow hits to be computed in O(1) time.
 * Every node has a number that keeps the number of its name (a string property)
 * in the input data, the nodes also have a list wit the name of 
 * the children in the original tree that lives in the json file 
 * and describes their hierarchy. This refernce alongside with the 
 * mapping from name to node allows the creation of the reporting 
 * list to be computed in mO(t), where m is the number of nodes at 
 * specified depth and t is the height of each subtree formed, 
 * but since are both constants irrelated to the phrase input,
 * it can be said that it takes O(1).
 */
public class TreeCounter implements Counter<String,TreeCounter.TreeNode>{
  protected Map counts;
  protected int depth;

  TreeCounter(Map hierarchyData, int depth){
    this.counts = hierarchyData;
    this.depth = depth;
  }
  
  @Override
  public void begin(){}
  @Override
  public void stop(){}
  @Override
  public void count(String word){
    if(counts.containsKey(word)){
      ((TreeNode)counts.get(word)).inc();
    }
  }

  public int traverseSumming(TreeNode t,Map nodes){
    int total = 0;
    total += t.getValue();
    if(t.getChildren().size() == 0){
      return total;
    }
    for(String name : t.getChildren()){
      total += traverseSumming((TreeNode)nodes.get(name),counts);
    }
    return total;
  }
  
  @Override
  public List<TreeNode> report(){
    var heads = (List<TreeNode>)counts.get(depth);
    for(TreeNode t : heads){
      t.setValue(traverseSumming(t,counts));
    }
    return heads;
  }
  /**
   * Node that keeps the reference of the 
   * children of the current nde in the hierarchy data.
   */
  static class TreeNode{
    int val;
    String name;
    List<String> children;
    TreeNode(String name){
      this.val = 0;
      this.name = name;
      this.children = new ArrayList<String>();
    }
    public void dec(){
      this.val--;
    }
    public void inc(){
      this.val++;
    }
    public String getName(){
      return this.name;
    }
    public int getValue(){
      return this.val;
    }
    public void setValue(int value){
      this.val = value;
    }
    public List<String> getChildren(){
      return this.children;
    }
    public void reset(){
      this.val = 0;
    }
  }
  
}

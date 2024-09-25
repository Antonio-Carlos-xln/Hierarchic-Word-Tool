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
  /** 
   * Holds a map for every single element from the  counting tree.
   * 
   */
  protected Map counts;
  /** 
   * The depth that should be used to group the countin of eements.
   */
  protected int depth;
  /** 
   * @param hierarchyData the map for every single word that came from the hierarchy file and that should  be counted.
   * @param depth the depth that should be used when grouping the data.
   */
  TreeCounter(Map hierarchyData, int depth){
    this.counts = hierarchyData;
    this.depth = depth;
  }
  
  
  /** 
   * Not used in this instance.
   */
  @Override
  public void begin(){}
  /** 
   * Not used in this instance.
   */
  @Override
  public void stop(){}
  @Override
  
  /** 
   * Updates the counting data if the supplyed data is one of the expected ones.
   * 
   * @param word the word to be matched agains the ones being counted.
   */
  public void count(String word){
    if(counts.containsKey(word)){
      ((TreeNode)counts.get(word)).inc();
    }
  }

  
  /**
   * Helper method that traverses the data from given node down to the leaves  summing all the values of the visited nodes.
   *  
   * @param t the given node to traverse.
   * @param nodes The data structure. It's required because each element holds only a string with the name of its children.
   * @return int the total sum of the value of the children of this node.
   */
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
  
  /** 
   * Does the counting data retrieval, performs the grouping and returns the list of elements
   * with its value updated to account for the sum of all of its children counts.
   * 
   * @return list of the counting instances after the grouping.
   */
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
    /** 
     * The current counting of the word of this element.
     */
    int val;
    /** 
     * the word that this element represents.
     */
    String name;
    /** 
     * The name of the children of this node to allow for tree traverdal with the hierarchy map.
     */
    List<String> children;
    /** 
     * The main constructor.
     * 
     *@param name theword that this element should represent and hold counting of.
     */
    TreeNode(String name){
      this.val = 0;
      this.name = name;
      this.children = new ArrayList<String>();
    }

    /** 
     * decreases  the counting of this word..
     */
    public void dec(){
      this.val--;
    }

    /** 
     * Increases the counts of this word.
     */
    public void inc(){
      this.val++;
    }

    /** 
     * Returns the word this instance is responsible for.
     * 
     * @return the word this instance is responsible for.
     */
    public String getName(){
      return this.name;
    }

     /** 
     * Returns the counting of the word this instance is responsible for.
     * 
     * @return the counting of the word.
     */
    public int getValue(){
      return this.val;
    }
    /** 
     * Sets the counting of the word this instance is responsible for.
     * 
     */
    public void setValue(int value){
      this.val = value;
    }

     /** 
     * Returns the list of children in the hierarchy of the word this instance is responsable for.
     * 
     * @return the list of children in the hierarchy of the  word this instance is responsible for.
     */
    public List<String> getChildren(){
      return this.children;
    }

     /** 
     * Resets the counting of this word.
     * 
     */
    public void reset(){
      this.val = 0;
    }
  }
  
}

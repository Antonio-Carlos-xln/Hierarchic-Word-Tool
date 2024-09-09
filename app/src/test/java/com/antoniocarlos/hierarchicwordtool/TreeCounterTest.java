package com.antoniocarlos.hierarchicwordtool;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


public class TreeCounterTest{
  @Test
  public void countTest(){
    var root = new TreeCounter.TreeNode("b");
    var leafA = new TreeCounter.TreeNode("a");
    var leafC= new TreeCounter.TreeNode("c");
    var map = new HashMap();
    map.put("b",root);
    map.put("a",leafA);
    map.put("c",leafC);
    map.put(0,Arrays.asList(root));
    map.put(1,Arrays.asList(leafA,leafC));
    var t = new TreeCounter(map,0);
    String test = "a b b c a c a b a c c b a c b b";
    Arrays.asList(test
      .split(" "))
      .stream()
      .forEach(k ->t.count(k));
    assertThat(leafA.getValue())
      .isEqualTo(5);
    assertThat(leafC.getValue())
      .isEqualTo(5);
    assertThat(root.getValue())
      .isEqualTo(6);       
  }
  @Test
  public void reportTest(){
    var root = new TreeCounter.TreeNode("b");
    var leafA = new TreeCounter.TreeNode("a");
    var leafC= new TreeCounter.TreeNode("c");
    var map = new HashMap();
    map.put("b",root);
    map.put("a",leafA);
    map.put("c",leafC);
    root.getChildren().add("a");
    root.getChildren().add("c");
    map.put(0,Arrays.asList(root));
    map.put(1,Arrays.asList(leafA,leafC));
    var t = new TreeCounter(map,0);
    String test = "a b b c a c a b a c c b a c b b";
    Arrays.asList(test
      .split(" "))
      .stream()
      .forEach(k ->t.count(k));
    List<TreeCounter.TreeNode> nodes = t.report();
    assertThat(nodes.get(0).getChildren())
      .isEqualTo(Arrays.asList("a","c"));
    assertThat(nodes.get(0).getValue())
      .isEqualTo(16);
    var t2 = new TreeCounter(map,1);
    root.reset();
    leafA.reset();
    leafC.reset();
    Arrays.asList(test
      .split(" "))
      .stream()
      .forEach(k ->t2.count(k));
    var nodes2 = t2.report();
    assertThat(nodes2.get(0).getValue())
      .isEqualTo(5);
    assertThat(nodes2.get(1).getValue())
      .isEqualTo(5);
  }

  
}

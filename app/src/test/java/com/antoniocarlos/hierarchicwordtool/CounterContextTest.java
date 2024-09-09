package com.antoniocarlos.hierarchydata;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


public class CounterContextTest{
  @Test
  public void getData1Test(){
    var root = new TreeCounter.TreeNode("b");
    var leafA = new TreeCounter.TreeNode("a");
    var leafC= new TreeCounter.TreeNode("c");
    var map = new HashMap();
    map.put("b",root);
    map.put("a",leafA);
    map.put("c",leafC);
    map.put(0,Arrays.asList(root));
    map.put(1,Arrays.asList(leafA,leafC));
    root.getChildren().add("a");
    root.getChildren().add("c");
    Map m = new HashMap();
    m.put("hierarchy-table",map);
    m.put("t1",6777L);
    var t = new TreeCounter(map,0);
    String test = "a b b c a c a b a c c b a c b b";
    DataNode jsonProvider = Mockito.mock(DataNode.class);
    DataNode phraseProvider = Mockito.mock(DataNode.class);
    DataNode depthProvider = Mockito.mock(DataNode.class);
    DataNode t0Provider = Mockito.mock(DataNode.class);
    Mockito.when(jsonProvider.getData()).thenReturn(m);
    Mockito.when(phraseProvider.getData()).thenReturn(test);
    Mockito.when(depthProvider.getData()).thenReturn(0);
    Mockito.when(t0Provider.getData()).thenReturn(78L);
    DataNode target = new CounterContextDataNode();
    target.set("json",jsonProvider);
    target.set("phrase",phraseProvider);
    target.set("depth",depthProvider);    
    target.set("t0",t0Provider);
    Map result = (Map) target.getData();
    assertThat(((List<TreeCounter.TreeNode>)result.get("counts")).get(0).getValue())
      .isEqualTo(16);
    assertThat((List<Long>)result.get("times"))
      .isNotEmpty();       
  }

  @Test
  public void getData2Test(){
    var root = new TreeCounter.TreeNode("b");
    var leafA = new TreeCounter.TreeNode("a");
    var leafC= new TreeCounter.TreeNode("c");
    var map = new HashMap();
    map.put("b",root);
    map.put("a",leafA);
    map.put("c",leafC);
    map.put(0,Arrays.asList(root));
    map.put(1,Arrays.asList(leafA,leafC));
    Map m = new HashMap();
    m.put("hierarchy-table",map);
    m.put("t1",6777L);
    var t = new TreeCounter(map,0);
    String test = "a b b c a c a b a c c b a c b b";
    DataNode jsonProvider = Mockito.mock(DataNode.class);
    DataNode phraseProvider = Mockito.mock(DataNode.class);
    DataNode depthProvider = Mockito.mock(DataNode.class);
    DataNode t0Provider = Mockito.mock(DataNode.class);
    Mockito.when(jsonProvider.getData()).thenReturn(m);
    Mockito.when(phraseProvider.getData()).thenReturn(test);
    Mockito.when(depthProvider.getData()).thenReturn(0);
    Mockito.when(t0Provider.getData()).thenReturn(78L);
    DataNode target = new CounterContextDataNode();
    target.set("json",jsonProvider);
    target.set("phrase",phraseProvider);
    target.set("depth",depthProvider);    
    target.set("t0",t0Provider);
    Map result = (Map) target.getData();
    assertThat((List<Long>)result.get("times"))
      .isNotEmpty();       
  }
    
}

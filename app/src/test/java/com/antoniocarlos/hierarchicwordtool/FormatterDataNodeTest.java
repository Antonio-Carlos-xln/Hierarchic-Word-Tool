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


public class FormatterDataNodeTest{
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
    m.put("counts",Arrays.asList(root,leafA,leafC));
    m.put("times",Arrays.asList(56,89));
    DataNode counterProvider = Mockito.mock(DataNode.class);
    DataNode verboseProvider = Mockito.mock(DataNode.class);
    Mockito.when(counterProvider.getData()).thenReturn(m);
    Mockito.when(verboseProvider.getData()).thenReturn(true);
    DataNode target = new FormatterDataNode();
    target.set("counter-reports",counterProvider);
    target.set("verbose",verboseProvider);
    String result = (String) target.getData();
    assertThat(result)
      .isNotNull()
      .isNotEmpty()
      .isNotBlank();
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
    root.getChildren().add("a");
    root.getChildren().add("c");
    Map m = new HashMap();
    m.put("counts",Arrays.asList(root,leafA,leafC));
    m.put("times",Arrays.asList(56,89));
    DataNode counterProvider = Mockito.mock(DataNode.class);
    DataNode verboseProvider = Mockito.mock(DataNode.class);
    Mockito.when(counterProvider.getData()).thenReturn(m);
    Mockito.when(verboseProvider.getData()).thenReturn(true);
    DataNode target = new FormatterDataNode();
    target.set("counter-reports",counterProvider);
    target.set("verbose",verboseProvider);
    String result = (String) target.getData();
    assertThat(result)
      .contains("56","89","Tempo de captura dos parametros","Tempo de comparaçao");
  }   
}

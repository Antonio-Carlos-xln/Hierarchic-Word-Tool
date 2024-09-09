package com.antoniocarlos.hierarchicwordtool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MainIntegrationTest{
  @Test
  public void checkTest(){
    Main.write = true;
    Main.main(new String[]{"analyze","car car motorcycle motorcycle motorcycle","--depth","2","--verbose"});   
    Main.destinationPath = "output2.txt";
    Main.main(new String[]{"analyze","carro carro","--depth","2","--verbose"});
    Main.destinationPath = "output3.txt";
    Main.main(new String[]{"analyze","car car motorcycle motorcycle motorcycle","--depth","1"});
    Main.destinationPath = "output4.txt";
    var path = Paths.get("./txts/longInput.txt");
    List<String>lines = null;
    try{
    lines =  Files.readAllLines(path);
    }catch(IOException e){}
    String longInput = String.join("\n",lines);
    Main.main(new String[]{"analyze","--depth","2","--verbose",longInput});
    Main.destinationPath = "output5.txt";
    Main.main(new String[]{"analyze","--depth","3","--verbose",longInput});

  }
}

# Hierarchic-Word-Tool


This application is a Java CLI application that analyses an input string containg text with data from a multi level hierarchy of words saved in a file under a prespecified format. 

## Components of the app

In a broad perspective, the application can be seen as a pipeline of data transformations from the reading of the parameters from the standard input and the reading of the hierarchy data JSON file to the printing of the output in the specified format at the standard output.



In this Pileline, there are two main interfaces, that are
 - DataNode -> This interface defines a collection of related nodes of the same interface (through generic methods add/remove) and a getData() method, being expected that all DataNodes, but the first one in the sequence to call its related nodes getData methods, similarly to a Chin of Reponsability patter, but in the reverse order. If in some implementation, it come to be defined that this colection can hol only zero or one reated node, then it will becomea chain, but in he general case, a directed graph will be formed in the memory as the data flows from one side to the other
 - Parser -> A recursive interface that allows strings containing formal language to be parsed to data structures of some prespecified kind. It has methods that allow a father component to manage the parsing of the current input by its chid nodes in a recursive way.  
And then we have some of the concrete implementations of both of those interfaces 
 - ArgsParserDataNode
 - JSONFileParseDataNode
And we have implementations of only the DataNode interface
 - TimedCounterDataNode
 - CounterContextDataNode
 - FormatterDataNode
And the ones that only interpret the Parser Interface
 - DepthParser
 - PhraseParser
 - VerboseParser
 - AnalyzeParser

## Underlying data format

The application requires an hierarchy of words to be provided so that it can properly work. The  specified format of this hierarhy data was defined as being a json file, but in irder to be able to make sense out of the data the passed json must follow some rules in order to be parsable by the application, becoming a kind of json dialect, a version with stricter (in the sense of allowing less variation of the the types present) grammar. To summarise, it can be said that a hierarchy datatree can be totally described by a json file that has as root an object whose all values are either objects (that follow this same pattern) or null. This gramma can be described as 

```
NULL : null;
STRING : ;
key : STRING;
value : NULL|object;
object : {(key:value)+(,)?};
root : object;
```

So, given an example of hierarchy of data such as:

```
vehicle
    individual-vehicle
        car
        motorcycle
    collective-vehicles
        train
        bus
   
```


It can be represented as:

```JSON
{"vehicle":{
    "individual-vehicle":{
        "car":null,
        "motorcycle":null
        },
    "collective-vehicle":{
              "train":null,
              "bus":null
        }        
    }
}
```


The reading and parsing of the JSON is done with the help of the jakarta.json module and the created nested object that mirrors the content of the hierarchy file is then used as source to create a hashtable that maps every single element of the tree (having the word as key) to a tree node that will store how many times this word was found in the input phrase and string references of the children of this node. Here, an advantage of the chosen aproach (data pipeline) stands out, as if it ever comes the need to update the counting strategy, the json parsing technique (or the format altogether) or the parsing technique of the CLI args, then it can be done by only replacing the data node in the already existing structure, implementing another node and switching to it. The same (update and/or extension through simple switching of components) goes if the need for changing the output format or the parameters that need to be parse from the commandline ever comes. The structure of both the parser of the CLI args as well of the concept of a pipeline of data processing nodes came from functional programming concepts, especially from the Reader and Writer Monads, seen quite frequently when someone talks about functional programming.

## Complexity 

The chosen approach, that relies in a single hash table to hold all the references to nodes, where every node holds the data about a word in the original json hierarchy data file. Since is a common hash table, the updating of the counting variable for a single word can be done in O(1) regardless of the depth of the word in the original hierarchic data. To provide the counting with respect to the requested depth, the hash table holds an additional refeence to every node, with those references being stored according to the depth they occur in the hierarchy json file. This allows for generation of the requested data to be done in mO(t) where m is the number of nodes (of the json hierarchy data) and t is the height of the subtree that has one of this node as root, but since m and t arefixed properties thatdon't depend on the length of the input phrase, it can be said that the generation of such reporting data happens at time complexity of O(1), at expense of a space complexity of O(n + 2t) that equals O(n), since we still need to store the string (2t is the number of references savd in the hash table). 

#### Extra 

It's worth noting that, if it ever gets concluded that prohibitively long phrases will be the regular input, then, since all the application is based on (very) decoupled components, it's possible to change the phrase reading technique by simply updating the counting node in the pipeline, so that it would now read chunks of the input and avoid memory limitation issues.

## Expected Arguments

As most CLI applications, arguments can be passed through the commandline at the moment of invoking. The expected synthax is:

```
java -jar cli.jar analyze <phrase> --depth <n> [--verbose]
```

where:
 - Phrase is the nameless non optional string parameter that must be processed. Every occurence of a word that is present at the hierarchy data will be counted;
 - Depth is the named non optional integer parameter that tells the level of the hierarchy data we should get the classes and report the countings;
 - Verbose is a named optional parameter that when present will cause the application to print the time of loading the paramers from command line and json files (as well as data structure creation) the time it took to process the phrase and do the counting in the following format:
```
|----------------------------|
| tempo de carregamento  | x |
|----------------------------|
| tempo de processamento | y |
|----------------------------|
```
Where the measures are in milisseconds (ms) and the label that proceed each parameter was already provided.
| tempo de processamento | y |
|----------------------------|
```
Where the measures are in milisseconds (ms) and the label that proceed each parameter was already provided

# Count Lines CLI
CLI for counting lines in Java files.

**Author:** [Vladyslav Tytarenko](https://github.com/VladTytarenko)
## Prerequisites

1. You need to [install Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html) 
and [Maven](https://maven.apache.org).

## Run Count Lines CLI 

1. Build the project with Maven by running this command from the project root:

    ```mvn clean package -DskipTests```

2. From project root run the following command to add Count Lines CLI to PATH variable 

    ```export PATH=$PATH:$(pwd)/bin```

3. To run the CLI, use the following command:

    ```clines <file/directory path>```

Output example:
```
$ clines src/main/java
   java : 194
      com : 194
         codeminders : 194
            models : 36
               FileNode.java : 36
            services : 119
               impl : 107
                  CountLinesServiceImpl.java : 71
                  PrintFileTreeServiceImpl.java : 36
               PrintFileTreeService.java : 5
               CountLinesService.java : 7
            CountLinesCli.java : 39
```

## Run unit tests

1. Run unit tests: 
    
    ```mvn clean test```

package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;


/* Instructions (Ubuntu 18.04)

  1. Maven must be installed.
    -> https://www.howtoforge.com/tutorial/ubuntu-apache-maven-installation/
    -> verify installation via `mvn --version`

  2. Download protobuf  
    -> https://github.com/protocolbuffers/protobuf/releases 

  3. Extract the downloaded file -> /extractedProto
  4. Follow and execute the commands inside /extractedProto/src/README.md, and do a `protoc --version` to verify installation

  5. Go inside extractedProto/java
  6. `mvn install`

  7. Start a new project via maven:
   
    `
    mvn -B archetype:generate \
    -DarchetypeGroupId=org.apache.maven.archetypes \
    -DgroupId=com.mycompany.app \
    -DartifactId=my-app
    `

  8. Add dependency to pom.xml inside my-app:
    <dependency>
      <groupId>com.google.protobuf</groupId>
      <artifactId>protobuf-java</artifactId>
      <version>3.6.1</version>
    </dependency>
  
  9. Copy everything inside from extractedProto/java/core/src/main/java to myapp/src/main/java
  10. Place your source codes inside myapp/src/main/java, including the <Protofile>.proto
  11. Compile proto file, go to myapp/src/main/java, and do a "protoc -I=./ --java_out=./ ./<Protofile>.proto"
  12. Compile your whole project, go to myapp/, and do a "mvn package"
  13. Run project, "java -cp target/myapp-1.0-SNAPSHOT.jar /path/to/class/of/Main"

*/


public class Main {

  static Scanner sc = new Scanner(System.in);

  public static void main (String[] args) {

    System.out.println("\n");
    Main main = new Main();

    int choice;

    do {

      System.out.println("=== MENU ===");
      System.out.println("[1] Read");
      System.out.println("[2] Write");
      System.out.println("[3] Exit");
      System.out.print("Choice: ");
      choice = sc.nextInt();

      if (choice == 1)
        main.readz();
      else if (choice == 2)
        main.writez();

    } while(choice != 3);



  }


  void readz() {

    String fileName, name;
    int age;

    System.out.print("\nEnter filename: ");
    fileName = sc.next();

    try {

      // Read from person generated by the filename
      Person person = Person.parseFrom(new FileInputStream(fileName));
      System.out.println("Name: " + person.getName());
      System.out.println("Age: " + person.getAge() + "\n");
      
    } catch (Exception e) {

      System.out.print("Error: ");
      System.out.println(e.toString() + "\n");
    }

  }

  // Initializes an entity from compiled protoc file
  void writez() {

    String fileName, name;
    int age;
    
    // Person class comes from Basilio.proto
    Person.Builder person = Person.newBuilder();

    System.out.print("\nEnter name: ");
    name = sc.next();
    System.out.print("Enter age: ");
    age = sc.nextInt();
    System.out.print("Enter filename: ");
    fileName = sc.next();

    // The methods generated by compiling the Basilio.proto
    person.setName(name);
    person.setAge(age);

    // Write to a file using protobuf generated method
    try {

      FileOutputStream output = new FileOutputStream(fileName);
      person.build().writeTo(output);
      System.out.println("[!] Data written to file.\n");

    } catch (Exception e) {

      System.out.println("Error: ");
      System.out.println(e.toString());
    }


  }

}
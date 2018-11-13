package com.main.app;

import com.google.protobuf.*;
import com.main.app.Mainprotos.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class Chat {

  static Scanner sc = new Scanner(System.in);

  public static void main (String[] args) {

    System.out.println("\n");
    Chat main = new Chat();

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
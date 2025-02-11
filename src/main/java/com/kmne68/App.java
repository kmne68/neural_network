/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author kemery
 */
public class App {
  
  public static void main(String[] args) {
      
    if(args.length == 0 || !new File(args[0]).isDirectory()) {
      System.out.println("Usage: [app] < MNIST DATA DIRECTORY");
    }
    
    System.out.println("Initialized.");

    
  }
  
}

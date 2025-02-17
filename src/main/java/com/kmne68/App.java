/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68;

import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.image.ImageLoader;
import java.io.File;

/**
 *
 * @author kemery
 */
public class App {
  
  public static void main(String[] args) {
    
    System.out.println("ARGS: " + args[0]);
      
    String directory = args[0];
    
    if(args.length == 0 && new File(args[0]).isDirectory()) {
      System.out.println("Usage: [app] < MNIST DATA DIRECTORY");
    }
    
    
    final String trainingImages = String.format("%s%s%s", directory, File.separator, "train-images-idx3-ubyte");
    final String trainingLabels = String.format("%s%s%s", directory, File.separator, "train-labels-idx1-ubyte");
    final String testImages = String.format("%s%s%s", directory, File.separator, "t10k-images-idx3-ubyte");
    final String testLabels = String.format("%s%s%s", directory, File.separator, "t10k-labels-idx1-ubyte"); 

    Loader trainingLoader = new ImageLoader(trainingImages, trainingLabels, 32);
    Loader testLoader = new ImageLoader(testImages, testLabels, 32);
    
    trainingLoader.open();
    testLoader.open();
    
    trainingLoader.close();
    testLoader.close();
  }
  
}

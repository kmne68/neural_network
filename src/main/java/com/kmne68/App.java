/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68;

import com.kmne68.neural_network.NeuralNetwork;
import com.kmne68.neural_network.Transform;
import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.MetaData;
import com.kmne68.neural_network.loader.image.ImageLoader;
import com.kmne68.neural_network.loader.test.TestLoader;
import java.io.File;

/**
 *
 * @author kemery
 */
public class App {
  
  public static void main(String[] args) {
    
    final String filename = "mnistNeural0.net";
    
    System.out.println("ARGS: " + args[0]);
    
    if(args.length == 0) {
      System.out.println("Usage: [app] < MNIST DATA DIRECTORY");
      return;
    }
    
    String directory = args[0];
    
    if(!new File(directory).isDirectory()) {
      System.out.println("'" + directory + "' is not a directory");
    }
    
    
    final String trainingImages = String.format("%s%s%s", directory, File.separator, "train-images-idx3-ubyte");
    final String trainingLabels = String.format("%s%s%s", directory, File.separator, "train-labels-idx1-ubyte");
    final String testImages = String.format("%s%s%s", directory, File.separator, "t10k-images-idx3-ubyte");
    final String testLabels = String.format("%s%s%s", directory, File.separator, "t10k-labels-idx1-ubyte"); 

    Loader trainingLoader = new ImageLoader(trainingImages, trainingLabels, 32);
    Loader testLoader = new ImageLoader(testImages, testLabels, 32);
    
    MetaData metaData = trainingLoader.open();
    
    int inputSize = metaData.getInputSize();
    int outputSize = metaData.getExpectedSize();
            
    trainingLoader.close();
    NeuralNetwork neuralNetwork = NeuralNetwork.load(filename);

    if (neuralNetwork == null) {
      System.out.println("Unable to load neural network from saved file. Creating new.");

      // int inputRows = 10;
      // int outputRows = 3;

      neuralNetwork = new NeuralNetwork();
      neuralNetwork.setScaleInitialWeights(0.2);
      neuralNetwork.setThreads(5);
      neuralNetwork.setEpochs((10));
      neuralNetwork.setLearningRates(0.02, 0.001);
      
      neuralNetwork.add(Transform.DENSE, 200, inputSize);
      neuralNetwork.add(Transform.RELU);
      neuralNetwork.add(Transform.DENSE, outputSize);
      neuralNetwork.add(Transform.SOFTMAX);


    } else {
      System.out.println("Loaded from " + filename);
    }
 
    System.out.println("Runtime procesors: " + Runtime.getRuntime().availableProcessors());
    System.out.println("NeuralNetwork:" + neuralNetwork.toString());

    neuralNetwork.fit(trainingLoader, testLoader);

    if(neuralNetwork.save(filename)) {
      System.out.println("Saved to" + filename);
    } else {
      System.out.println("Unable to save to " + filename);
    }

  
    
    /*
    trainingLoader.open();
    // testLoader.open();
    
    MetaData metaData = testLoader.open();
    
    for(int i = 0; i < metaData.getNumberOfBatches(); i++) {
      BatchData batchData = testLoader. readBatch();
    }
    
    trainingLoader.close();
    testLoader.close();
    */

  }
  
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68;

import com.kmne68.neural_network.NeuralNetwork;
import com.kmne68.neural_network.Transform;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.test.TestLoader;

/**
 *
 * @author kemery
 */
public class App {
  
  public static void main(String[] args) {
    
    int inputRows = 10;
    int outputRows = 3;
    
    NeuralNetwork neuralNetwork = new NeuralNetwork();
    
      neuralNetwork.add(Transform.DENSE, 100, inputRows);
      neuralNetwork.add(Transform.RELU);
      neuralNetwork.add(Transform.DENSE, 50, inputRows);
      neuralNetwork.add(Transform.RELU);
      neuralNetwork.add(Transform.DENSE, outputRows);
      neuralNetwork.add(Transform.SOFTMAX);
      
      neuralNetwork.setThreads(5);
      neuralNetwork.setEpochs((1));
      neuralNetwork.setLearningRates(0.02, 0.001);
      
      System.out.println("NeuralNetwork:" + neuralNetwork.toString());
      
      Loader trainLoader = new TestLoader(60_000, 32);
      Loader testLoader = new TestLoader(10_000, 32);
      
      neuralNetwork.fit(trainLoader, testLoader);
      
      neuralNetwork.save("neural_network_output.nno");
    
  }
}

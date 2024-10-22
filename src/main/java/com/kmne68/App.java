/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68;

import com.kmne68.neural_network.NeuralNetwork;
import com.kmne68.neural_network.Transform;

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
      neuralNetwork.add(Transform.DENSE, outputRows);
      neuralNetwork.add(Transform.SOFTMAX);
    
    System.out.println("NeuralNetowrk:" + neuralNetwork);
  }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.neural_network.loader.Loader;

/**
 *
 * @author kemery
 */
public class NeuralNetwork {
  
  private Engine engine;
  
  private int epochs = 20; // The number of times we'll go through the dataset while training
  private double learningRate;
  private double initialLearningRate = 0.1;
  private double finalLearningRate = 0;
  
  
  
  public NeuralNetwork() {
    engine = new Engine();
  }
  
  
  public void add(Transform transform, double... params) {
    engine.add(transform, params);
  }
  
  
  public void setLearningRates(double initialLearningRate, double finalLearningRate) {
    this.initialLearningRate = initialLearningRate;
    this.finalLearningRate = finalLearningRate;
  }
  
 
  public void setEpochs(int epochs) {
    this.epochs = epochs;
  }
  
  
  public void fit(Loader trainLoader, Loader evalLoader) {
    learningRate = initialLearningRate;
    
    for(int epoch = 0; epoch < epochs; epoch++) {
      System.out.printf("Epoch %3d \n", epoch);
      
      runEpoch(trainLoader, true);
      
      if(evalLoader != null) {
        runEpoch(evalLoader, false);
      }
      
      learningRate -= (initialLearningRate - finalLearningRate) / epochs;
      
    }
  }
  

  @Override
  public String toString() {
    return "NeuralNetwork{" + "engine=" + engine.toString() + '}';
  }

  private void runEpoch(Loader loader, boolean trainingMode) {
    
  }
  
}

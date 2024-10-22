/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

/**
 *
 * @author kemery
 */
public class NeuralNetwork {
  
  private Engine engine;
  
  
  public NeuralNetwork() {
    engine = new Engine();
  }
  
  
  public void add(Transform transform, double... params) {
    engine.add(transform, params);
  }

  @Override
  public String toString() {
    return "NeuralNetwork{" + "engine=" + engine.toString() + '}';
  }
  
}

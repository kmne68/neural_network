/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

/**
 *
 * @author kemery
 */
public class RunningAverages {
  
  private int nCalls = 0;     // number of times the method has been called
  private double [][] values;
  private Callback callback;
  
  
  
  public interface Callback {
    public void apply(int callNumber, double[] averages);
  }

  public RunningAverages(int numberOfAverages, int windowSize, Callback callback) {
    this.callback = callback;
    
    values = new double[numberOfAverages][windowSize];
    
    System.out.println("Number of values: " + values.length);
    System.out.println("Values[0] length: " + values[0].length);
  }
  
  
  
}

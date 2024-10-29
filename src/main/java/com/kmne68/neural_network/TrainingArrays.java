/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;


/**
 *
 * @author kemery
 */
public class TrainingArrays {
  
  private double[] input;
  private double[] output;

  public double[] getInput() {
    return input;
  }

  public void setInput(double[] input) {
    this.input = input;
  }

  public double[] getOutput() {
    return output;
  }

  public void setOutput(double[] output) {
    this.output = output;
  }

  public TrainingArrays(double[] input, double[] output) {
    this.input = input;
    this.output = output;
  }
  
  
  
}

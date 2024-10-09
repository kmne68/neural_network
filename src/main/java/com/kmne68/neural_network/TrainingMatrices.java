/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;

/**
 *
 * @author kemery
 */
public class TrainingMatrices {

  public Matrix getInput() {
    return input;
  }

  public void setInput(Matrix input) {
    this.input = input;
  }

  public Matrix getOutput() {
    return output;
  }

  public void setOutput(Matrix output) {
    this.output = output;
  }
  
  private Matrix input;
  private Matrix output;

  public TrainingMatrices(Matrix input, Matrix output) {
    this.input = input;
    this.output = output;
  }
  
  
  
}

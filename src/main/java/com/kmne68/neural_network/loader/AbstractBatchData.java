/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader;

/**
 *
 * @author kemery
 */
public abstract class AbstractBatchData implements BatchData {

  private double[] inputBatch;
  private double[] expectedBatch;
  
  
  @Override
  public double[] getInputBatch() {
    return inputBatch;
  }

  @Override
  public void setInputBatch(double[] inputBatch) {
    this.inputBatch = inputBatch;
  }

  @Override
  public double[] getExpectedBatch() {
    return expectedBatch;
  }

  @Override
  public void setExpectedBatch(double[] expectedBatch) {
    this.expectedBatch = expectedBatch;
  }
  
  
  
}

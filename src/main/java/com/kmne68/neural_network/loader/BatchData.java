/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.kmne68.neural_network.loader;

/**
 *
 * @author kemery
 */
public interface BatchData {
  
  public double[] getInputBatch();

  public void setInputBatch(double[] inputBatch);

  public double[] getExpectedBatch();

  public void setExpectedBatch(double[] expectedBatch);
  
}

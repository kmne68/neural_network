/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;
import java.util.LinkedList;

/**
 *
 * @author kemery
 */
public class BatchResult {
  
  private LinkedList<Matrix> io = new LinkedList<>();
  private LinkedList<Matrix> weightErrors = new LinkedList<>();
  private Matrix inputError;
  
  public LinkedList<Matrix> getIo() {
    return io;
  }
  
  
  public Matrix getOutput() {
    return io.getLast();
  }
  
  
  public void addIo(Matrix m) {
    io.add(m);
  }

  public LinkedList<Matrix> getWeightErrors() {
    return weightErrors;
  }

  public void addWeightErrors(Matrix weightError) {
    weightErrors.addFirst(weightError);
  }

  public Matrix getInputError() {
    return inputError;
  }

  public void setInputError(Matrix inputError) {
    this.inputError = inputError;
  }
  
  
}

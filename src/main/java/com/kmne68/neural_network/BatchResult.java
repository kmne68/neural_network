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
  
  
  public LinkedList<Matrix> getIo() {
    return io;
  }
  
  
  public void addIo(Matrix m) {
    io.add(m);
  }
}

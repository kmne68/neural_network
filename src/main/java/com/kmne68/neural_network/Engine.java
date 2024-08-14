/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network;

import com.kmne68.matrix.Matrix;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author kemery
 */
public class Engine {
  
  private LinkedList<Transform> transforms = new LinkedList<>();
  private LinkedList<Matrix> weights = new LinkedList<>();
  private LinkedList<Matrix> biases = new LinkedList<>();
  
  private LossFunction lossFunction = LossFunction.CROSS_ENTROPY;
  private boolean storeInputError = false;
  
  public void add(Transform transform) {
    transforms.add(transform);
  }
  
  
  public BatchResult runForward(Matrix input) {
    
    BatchResult batchResult = new BatchResult();
    Matrix output = input;
  
    int denseIndex = 0;
    
    batchResult.addIo(output);

    for(var t: transforms) {
      if(t == Transform.DENSE) {
        
        Matrix weight = weights.get(denseIndex);
        Matrix bias = biases.get(denseIndex);
        
        output = weight.multiply(output).modify((row, col, value) -> value + bias.get(row));
                
        ++denseIndex;
      }
      else if(t == Transform.RELU) {
        output = output.modify(value -> value > 0 ? value : 0);
      }
      else if(t == Transform.SOFTMAX) {
        output = output.softmax();
      }
      
      batchResult.addIo(output);
    }
    
    return batchResult;
  }
  
  
  public void runBackward(BatchResult batchResult, Matrix expected) {
    
    var transformsIt = transforms.descendingIterator();
    
    if(lossFunction != LossFunction.CROSS_ENTROPY || transforms.getLast() != Transform.SOFTMAX) {
      throw new UnsupportedOperationException("Loss function must be cross entropy and last transform must be softmax");
    }
    
    var ioIterator = batchResult.getIo().descendingIterator();
    Matrix softmaxOutput = ioIterator.next();
    Matrix error = softmaxOutput.apply((index, value) -> value - expected.get(index));

    
    while(transformsIt.hasNext()) {
      
      Transform transform = transformsIt.next();
      
      switch(transform) {
        case DENSE:
          break;
        case RELU:
          break;
        case SOFTMAX:
          break;
        default:
          throw new UnsupportedOperationException("Not Implemented");
      }
      
      System.out.println(transform);
    }
    if (storeInputError) {
      batchResult.setInputError(error);
    }
  }
  
  
    public void add(Transform transform, double...params) {
    
    Random random = new Random();
    
    if(transform == Transform.DENSE) {
      int numberOfNeurons = (int)params[0];      
      int weightsPerNeuron = weights.size() == 0 ? (int)params[1] : weights.getLast().getRows();
      
      Matrix weight = new Matrix(numberOfNeurons, weightsPerNeuron, i -> random.nextGaussian());
      Matrix bias = new Matrix(numberOfNeurons, 1, i -> random.nextGaussian());
      
      weights.add(weight);
      biases.add(bias);
    }
    transforms.add(transform);
  }
  
/*  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    for(var t: transforms) {
      sb.append(t).append("\n");
    }
    
    return sb.toString();
  }
  */

    
  public void setStoreInputError(boolean storeInputError) {
    this.storeInputError = storeInputError;
  }
  
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    
    int weightIndex = 0;
    for(var t: transforms) {
      
      sb.append(t);
      
      if(t == Transform.DENSE) {
        sb.append(" ").append(weights.get(weightIndex).toString(false));
        
        weightIndex++;
      }
      sb.append("\n");
    }
    
    return sb.toString();
  }
}   

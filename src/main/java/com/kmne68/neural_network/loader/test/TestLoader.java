/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader.test;

import com.kmne68.neural_network.Utils;
import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.MetaData;

/**
 *
 * @author kemery
 */
public class TestLoader implements com.kmne68.neural_network.loader.Loader {
  
  private MetaData metaData;
  
  private int numberOfItems = 60_000;
  private int inputSize = 500;
  private int expectedSize = 3;
  private int numberOfBatches;
  private int batchSize = 32;
  private int totalItemsRead;
  private int itemsRead;
  
  public TestLoader() {
    metaData = new TestMetaData();
    metaData.setNumberOfItems(numberOfItems);
    
    numberOfBatches = numberOfItems / batchSize;
    
    if(numberOfItems % batchSize != 0) {
      numberOfBatches += 1;
    }
    
    metaData.setNumberOfBatches(numberOfBatches);
    metaData.setInputSize(inputSize);
    metaData.setExpectedSize(expectedSize);
  }

  @Override
  public MetaData open() {
    return metaData;
  }

  @Override
  public void close() {

  }

  @Override
  public MetaData getMetaData() {
    return metaData;
  }

  @Override
  public BatchData readBatch() {

    if(totalItemsRead == numberOfItems) {
      
      return null;
    }
    
    itemsRead = batchSize;

    totalItemsRead += itemsRead;
    
    int excessItems = totalItemsRead - numberOfItems;
    
    if(excessItems > 0) {
      totalItemsRead -= excessItems;
      itemsRead -= excessItems;
    }
    
    var io = Utils.generateTrainingArrays(inputSize, expectedSize, itemsRead);
    
    var batchData = new TestBatchData();
    batchData.setInputBatch(io.getInput());
    batchData.setExpectedBatch(io.getOutput());
    
    metaData.setTotalItemsRead(totalItemsRead);
    metaData.setItemsRead(itemsRead);
    
    return batchData;
  }
  
}

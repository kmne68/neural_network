/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit3TestClass.java to edit this template
 */
package com.kmne68.neural_network.loader.test;

import com.kmne68.matrix.Matrix;
import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.MetaData;
import junit.framework.TestCase;

/**
 *
 * @author kemery
 */
public class TestTestLoader extends TestCase {
  
  public TestTestLoader(String testName) {
    super(testName);
  }

  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }

    
  public void testLoader() {
    int batchSize = 33;
    Loader testLoader = new TestLoader(600, batchSize);
    
    MetaData metaData = testLoader.open();
    
    int numberOfItems = metaData.getNumberOfItems();
    
    int lastBatchSize = numberOfItems % batchSize;
    
    int numberOfBatches = metaData.getNumberOfBatches();
    
    for(int i = 0; i < numberOfBatches; i++) {
      BatchData batchData = testLoader.readBatch();
      
      assertTrue(batchData != null);
      
      int itemsRead = metaData.getItemsRead();
      int inputSize = metaData.getInputSize();
      int expectedSize = metaData.getExpectedSize();
      
      Matrix input = new Matrix(inputSize, itemsRead, batchData.getInputBatch());
      Matrix expected = new Matrix(expectedSize, itemsRead, batchData.getExpectedBatch());
      
      assertTrue(input.sum() != 0);
      assertTrue(expected.sum() == itemsRead);
      
      // Special condition when the batchSize doesn't divide evenly into number of batches
      if(i == numberOfBatches - 1) {
        assertTrue(itemsRead == lastBatchSize);
      }
      else {
        assertTrue(itemsRead == batchSize);
      }      
    }
  }
}

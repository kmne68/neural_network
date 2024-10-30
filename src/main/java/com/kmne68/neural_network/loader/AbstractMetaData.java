/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader;

/**
 *
 * @author kemery
 */
public abstract class AbstractMetaData implements MetaData {
  
  private int numberOfItems;
  private int inputSize;
  private int expectedSize;
  private int numberOfBatches;
  private int totalItemsRead;
  private int itemsRead;
  

  @Override
  public int getNumberOfItems() {
    return numberOfItems;
  }

  @Override
  public void setNumberOfItems(int numberOfItems) {
    this.numberOfItems = numberOfItems;
  }

  @Override
  public int getInputSize() {
    return inputSize;
  }

  @Override
  public void setInputSize(int inputSize) {
    this.inputSize = inputSize;
  }

  @Override
  public int getExpectedSize() {
    return expectedSize;
  }

  @Override
  public void setExpectedSize(int expectedSize) {
    this.expectedSize = expectedSize;
  }

  @Override
  public int getNumberOfBatches() {
    return numberOfBatches;
  }

  @Override
  public void setNumberOfBatches(int numberOfBatches) {
    this.numberOfBatches = numberOfBatches;
  }

  @Override
  public int getTotalItemsRead() {
    return totalItemsRead;
  }

  @Override
  public void setTotalItemsRead(int totalItemsRead) {
    this.totalItemsRead = totalItemsRead;
  }

  @Override
  public int getItemsRead() {
    return itemsRead;
  }

  @Override
  public void setItemsRead(int itemsRead) {
    this.itemsRead = itemsRead;
  }
  
}

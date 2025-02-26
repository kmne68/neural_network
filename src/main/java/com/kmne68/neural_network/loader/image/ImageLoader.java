/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader.image;

import com.kmne68.neural_network.loader.BatchData;
import com.kmne68.neural_network.loader.Loader;
import com.kmne68.neural_network.loader.MetaData;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author kemery
 */
public class ImageLoader implements Loader {

  private String imageFileName;
  private String labelFileName;
  private int batchSize;
  private DataInputStream dsImages;
  private DataInputStream dsLabels;
  private ImageMetaData metaData;

  private Lock readLock = new ReentrantLock();
  

  public ImageLoader(String imageFileName, String labelFileName, int batchSize) {
    this.imageFileName = imageFileName;
    this.labelFileName = labelFileName;
    this.batchSize = batchSize;
  }

  
  @Override
  public ImageMetaData open() {

    try {
      dsImages = new DataInputStream(new FileInputStream(imageFileName));
    } catch (Exception e) {
      throw new LoaderException("Cannot open " + imageFileName, e);
    }

    try {
      dsLabels = new DataInputStream(new FileInputStream(labelFileName));
    } catch (Exception e) {
      throw new LoaderException("Cannot open " + labelFileName, e);
    }

    metaData = readMetaData();
    return metaData;
  }

  @Override
  public void close() {
    
    metaData = null;

    try {
      dsImages.close();
    }
    catch(Exception e) {
      throw new LoaderException("Cannot close " + imageFileName, e);
    }

    try {
      dsLabels.close();
    }
    catch(Exception e) {
      throw new LoaderException("Cannot close " + labelFileName, e);
    }
    
  }

  @Override
  public ImageMetaData getMetaData() {
    return metaData;
  }
  

  @Override
  public BatchData readBatch() {
    
    readLock.lock();
    
    try {
      ImageBatchData batchData = new ImageBatchData();
      
      int inputItemsRead = readInputBatch(batchData);
      int expectedItemsRead = readExpectedBatch(batchData);
      
      if(inputItemsRead != expectedItemsRead) {
        throw new LoaderException("Mismatch between images read and labels read.");
      }
      
      metaData.setItemsRead(inputItemsRead);
      return batchData;
    }
    finally {
      readLock.unlock();
    }
    
  }
  
  
  private ImageMetaData readMetaData() {
    
    metaData = new ImageMetaData();
    
    int numberOfItems = 0;
    
    try {
      int magicLabelNumber = dsLabels.readInt();
      if (magicLabelNumber != 2049) {
         throw new LoaderException("Label file " + labelFileName + " has wrong file format.");
      }
      
      numberOfItems = dsLabels.readInt();
      
      metaData.setNumberOfItems(numberOfItems);
      System.out.println("Number of labels: " + numberOfItems);
    }
    catch(IOException e) {
      throw new LoaderException("Unable to load " + labelFileName, e);
    }
    
    
    try {
      int magicImageNumber = dsImages.readInt();
      if (magicImageNumber != 2051) {
         throw new LoaderException("Image file " + imageFileName + " has wrong file format.");
      }
      
      if(dsImages.readInt() != numberOfItems) {
        throw new LoaderException("Image file " + imageFileName + " has a different number of items compared to labelFileName.");
      }
      System.out.println("Number of Images: " + numberOfItems);
      int height = dsImages.readInt();
      int width = dsImages.readInt();
      
      metaData.setHeight(height);
      metaData.setWidth(width);
      metaData.setInputSize(width * height);
      System.out.println("Height: " + height + ", Width: " + width);
    }
    catch(IOException e) {
      throw new LoaderException("Unable to read " + imageFileName, e);
    }
    
    metaData.setExpectedSize(10);
    metaData.setNumberOfBatches((int) Math.ceil((double) numberOfItems) / batchSize);
    
    return metaData;
  }

  private int readInputBatch(ImageBatchData batchData) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  private int readExpectedBatch(ImageBatchData batchData) {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

}

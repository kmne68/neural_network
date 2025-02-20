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

  public ImageLoader(String imageFileName, String labelFileName, int batchSize) {
    this.imageFileName = imageFileName;
    this.labelFileName = labelFileName;
    this.batchSize = batchSize;
  }

  @Override
  public MetaData open() {

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

    readMetaData();
    return null;
  }

  @Override
  public void close() {

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
  public MetaData getMetaData() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }

  @Override
  public BatchData readBatch() {
    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }
  
  
  private MetaData readMetaData() {
    
    try {
      int magicLabelNumber = dsLabels.readInt();
      if (magicLabelNumber != 2049) {
         throw new LoaderException("Label file " + labelFileName + " has wrong file format.");
      }
      
      int numberOfLabels = dsLabels.readInt();
      System.out.println("Number of labels: " + numberOfLabels);
    }
    catch(IOException e) {
      throw new LoaderException("Unable to load " + labelFileName, e);
    }
    return null;
  }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader;

import com.kmne68.neural_network.loader.image.ImageLoader;
import com.kmne68.neural_network.loader.image.ImageMetaData;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author kemery
 */
public class ImageWriter {

  public static void main(String[] args) {

    if (args.length == 0) {
      System.out.println("Usage: [app] < MNIST DATA DIRECTORY");
      return;
    }
    
    File dir = new File(args[0]);

    if (!dir.isDirectory()) {
      try {
        System.out.println(dir.getCanonicalPath() + " is not a directory.");
      }
      catch(IOException e) {
        e.printStackTrace();  
      }
      return;
    }
    
    String directory = args[0];

    new ImageWriter().run(directory);

  }

  public void run(String directory) {
    
    final String trainingImages = String.format("%s%s%s", directory, File.separator, "train-images-idx3-ubyte");
    final String trainingLabels = String.format("%s%s%s", directory, File.separator, "train-labels-idx1-ubyte");
    final String testImages = String.format("%s%s%s", directory, File.separator, "t10k-images-idx3-ubyte");
    final String testLabels = String.format("%s%s%s", directory, File.separator, "t10k-labels-idx1-ubyte");
    
    int batchSize = 900;

    ImageLoader trainingLoader = new ImageLoader(trainingImages, trainingLabels, batchSize);
    ImageLoader testLoader = new ImageLoader(testImages, testLabels, batchSize);
    
    ImageLoader loader = testLoader;

    // trainingLoader.open();
    // testLoader.open();

    ImageMetaData metaData = loader.open();
    
    int imageWidth = metaData.getWidth();
    int imageHeight = metaData.getHeight();

    for (int i = 0; i < metaData.getNumberOfBatches(); i++) {
      BatchData batchData = testLoader.readBatch();
      
      var numberOfImages = metaData.getItemsRead();
      
      int horizontalImages = (int)Math.sqrt(numberOfImages);
      
      while(numberOfImages % horizontalImages != 0) {
        ++horizontalImages;
      }
      
      int verticalImages = numberOfImages / horizontalImages;
      
      int canvasWidth = horizontalImages * imageWidth;
      int canvasHeight = verticalImages * imageHeight;
      
      String montagePath = String.format("montage%d.jpg", i);      
      System.out.println("Writing " + montagePath);
      
      var montage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_BYTE_GRAY);
      
      try {
        ImageIO.write(montage, "jpg", new File(montagePath));
      } catch (IOException ex) {
        Logger.getLogger(ImageWriter.class.getName()).log(Level.SEVERE, null, ex);
      }
      
    }

    // trainingLoader.close();
    // testLoader.close();
    loader.close();
  }
}

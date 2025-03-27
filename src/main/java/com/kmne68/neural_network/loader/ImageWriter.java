/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kmne68.neural_network.loader;

import com.kmne68.neural_network.NeuralNetwork;
import com.kmne68.neural_network.loader.image.ImageLoader;
import com.kmne68.neural_network.loader.image.ImageMetaData;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
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
      } catch (IOException e) {
        e.printStackTrace();
      }
      return;
    }

    String directory = args[0];

    new ImageWriter().run(directory);

  }

  private int convertOneHotToInt(double[] labelData, int offset, int oneHotSize) {

    double maxValue = 0;
    int maxIndex = 0;

    for (int i = 0; i < oneHotSize; i++) {
      if (labelData[offset + i] > maxValue) {
        maxValue = labelData[offset + i];
        maxIndex = i;
      }
    }

    return maxIndex;
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

    var neuralNetwork = NeuralNetwork.load("mnistNeural0.net");

    int imageWidth = metaData.getWidth();
    int imageHeight = metaData.getHeight();
    int totalCorrect = 0;
    int totalIncorrect = 0;

    int labelSize = metaData.getExpectedSize();

    for (int i = 0; i < metaData.getNumberOfBatches(); i++) {
      BatchData batchData = testLoader.readBatch();

      var numberOfImages = metaData.getItemsRead();

      int horizontalImages = (int) Math.sqrt(numberOfImages);

      while (numberOfImages % horizontalImages != 0) {
        ++horizontalImages;
      }

      int verticalImages = numberOfImages / horizontalImages;

      int canvasWidth = horizontalImages * imageWidth;
      int canvasHeight = verticalImages * imageHeight;

      String montagePath = String.format("montage%d.jpg", i);
      System.out.println("Writing " + montagePath);

      var montage = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_RGB);

      double[] pixelData = batchData.getInputBatch();
      double[] labelData = batchData.getExpectedBatch();
      int imageSize = imageWidth * imageHeight;

      boolean[] correct = new boolean[numberOfImages];

      for (int n = 0; n < numberOfImages; n++) {
        double[] singleImage = Arrays.copyOfRange(pixelData, n * imageSize, (n + 1) * imageSize);
        double[] singleLabel = Arrays.copyOfRange(labelData, n * labelSize, (n + 1) * labelSize);
        double[] predictedLabel = neuralNetwork.predict(singleImage);
        int predicted = convertOneHotToInt(predictedLabel, 0, labelSize);
        int actual = convertOneHotToInt(singleLabel, 0, labelSize);

        correct[n] = predicted == actual;

        // System.out.println("Correct: " + correct[n]);


        if (correct[n] == true) {
          totalCorrect++;
        } else {
          totalIncorrect++;
        }


      }
      System.out.println("Correct: " + totalCorrect);
      System.out.println("Incorrect: " + totalIncorrect);

      for (int pixelIndex = 0; pixelIndex < pixelData.length; pixelIndex++) {

        int imageNumber = pixelIndex / imageSize;
        int pixelNumber = pixelIndex % imageSize;

        int montageRow = imageNumber / horizontalImages;
        int montageColumn = imageNumber % verticalImages;

        int pixelRow = pixelNumber / imageWidth;
        int pixelColumn = pixelNumber % imageWidth;

        int x = montageColumn * imageWidth + pixelColumn;
        int y = montageRow * imageHeight + pixelRow;

        double pixelValue = pixelData[pixelIndex];
        int color = (int) (0x100 * pixelValue);

        int pixelColor = 0;
        if (correct[imageNumber]) {
          pixelColor = color << 8;
        } else {
          pixelColor = color << 16;
        }

        montage.setRGB(x, y, pixelColor);

      }

      try {
        ImageIO.write(montage, "jpg", new File(montagePath));
      } catch (IOException ex) {
        Logger.getLogger(ImageWriter.class.getName()).log(Level.SEVERE, null, ex);
      }

      StringBuilder sb = new StringBuilder();

      for (int labelIndex = 0; labelIndex < numberOfImages; labelIndex++) {
        if (labelIndex % horizontalImages == 0) {
          sb.append("\n");
        }
        int label = convertOneHotToInt(labelData, labelIndex * labelSize, labelSize);
        sb.append(String.format("%d ", label));
      }

      String labelPath = String.format("labels%d.txt", i);
      System.out.println("Writing " + labelPath);

      try {
        FileWriter fw = new FileWriter(labelPath);
        fw.write(sb.toString());
        fw.close();
      } catch (IOException ex) {
        Logger.getLogger(ImageWriter.class.getName()).log(Level.SEVERE, null, ex);
      }

    }

    // trainingLoader.close();
    // testLoader.close();
    loader.close();
  }
}

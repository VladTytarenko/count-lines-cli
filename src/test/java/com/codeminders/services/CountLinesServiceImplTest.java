package com.codeminders.services;

import com.codeminders.models.FileNode;
import com.codeminders.services.impl.CountLinesServiceImpl;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

public class CountLinesServiceImplTest {

  @Test
  public void testCountingLinesInJavaFile() throws IOException {
    File javaFile = new File(getPath("test-folder/Test11.java"));
    CountLinesService countLinesService = new CountLinesServiceImpl();

    int actualLinesQuantity = countLinesService.countLines(javaFile).getLines();
    Assert.assertEquals(7, actualLinesQuantity);
  }

  @Test
  public void testCountingLinesInDirectory() throws IOException {
    File directory = new File(getPath("test-folder"));
    CountLinesService countLinesService = new CountLinesServiceImpl();

    int actualLinesQuantity = countLinesService.countLines(directory).getLines();
    Assert.assertEquals(21, actualLinesQuantity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncorrectExtension_IllegalArgumentException() throws IOException {
    File file = new File(getPath("test-folder/test-folder2/test.txt"));
    CountLinesService countLinesService = new CountLinesServiceImpl();

    countLinesService.countLines(file);
  }

  @Test
  public void testIncorrectExtensionInDirectory_IgnoreIncorrectExtensionFiles() throws IOException {
    File directory = new File(getPath("test-folder/test-folder2"));
    CountLinesService countLinesService = new CountLinesServiceImpl();

    FileNode fileNode = countLinesService.countLines(directory);
    Assert.assertFalse(
        fileNode.getChildNodes().stream()
            .map(FileNode::getName)
            .collect(Collectors.toList())
            .contains("test.txt"));
  }

  @Test
  public void testEmptyJavaFile_NoException() throws IOException {
    File javaFile = new File(getPath("test-folder/TestEmptyFile.java"));
    CountLinesService countLinesService = new CountLinesServiceImpl();

    FileNode fileNode = countLinesService.countLines(javaFile);
    int actualLinesQuantity = fileNode.getLines();
    Assert.assertEquals(0, actualLinesQuantity);
  }

  private String getPath(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
  }
}

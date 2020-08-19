package com.codeminders;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CountLinesCliTest {

  @Test
  public void testCorrectPathToDirectory_NoExceptionInOutput() {
    String pathToDirectory = getPath("test-folder");
    String output = execute(pathToDirectory);

    Assert.assertFalse(output.contains(pathToDirectory + " file should be with .java extension."));
    Assert.assertFalse(output.contains(pathToDirectory + " file or directory not found."));
  }

  @Test
  public void testCorrectPathToFile_NoExceptionInOutput() {
    String pathToFile = getPath("test-folder/Test11.java");
    String output = execute(pathToFile);

    Assert.assertFalse(output.contains(pathToFile + " file should be with .java extension."));
    Assert.assertFalse(output.contains(pathToFile + " file or directory not found."));
    Assert.assertTrue(output.contains("Test11.java : 7"));
  }

  @Test
  public void testIncorrectPath_ExceptionInOutput() {
    String incorrectPath = "/incorrect/path/to/file";
    String output = execute(incorrectPath);

    Assert.assertEquals(incorrectPath + " file or directory not found.\n", output);
  }

  @Test
  public void testIncorrectFileExtension_ExceptionInOutput() {
    String incorrectExtensionFilePath = getPath("test-folder/test-folder2/test.txt");
    String output = execute(incorrectExtensionFilePath);

    Assert.assertEquals(
        incorrectExtensionFilePath + " file should be with .java extension.\n", output);
  }

  @Test
  public void testEmptyInputParameter_ExceptionInOutput() {
    String output1 = execute(new String[]{null});
    Assert.assertEquals("Please, provide name of file or directory.\n", output1);

    String output2 = execute("");
    Assert.assertEquals("Please, provide name of file or directory.\n", output2);

    String output3 = execute(" ");
    Assert.assertEquals("Please, provide name of file or directory.\n", output3);
  }

  @Test
  public void testMultipleInputParameters_WarningInOutput() {
    String pathToFile = getPath("test-folder/Test11.java");
    String output = execute(pathToFile, "param1", "param2");

    Assert.assertTrue(output.contains("WARNING: The program expects one parameter. All other parameters will be ignored."));
    Assert.assertTrue(output.contains("Test11.java : 7"));
  }

  private String getPath(String fileName) {
    return Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
  }

  private String execute(String ... paths) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    System.setOut(new PrintStream(baos));
    new CountLinesCli().run(paths);
    return baos.toString();
  }
}

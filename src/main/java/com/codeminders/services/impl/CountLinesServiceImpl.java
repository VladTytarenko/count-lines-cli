package com.codeminders.services.impl;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.codeminders.models.FileNode;
import com.codeminders.services.CountLinesService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

public class CountLinesServiceImpl implements CountLinesService {

  private static final String COMMENT_REG_EX = "//.*|/\\*[\\s\\S]*?\\*/|(\"(\\\\.|[^\"])*\")";
  private static final String REG_EX_CAPTURE_GROUP = "$1"; // to ignore comments inside strings
  private static final String EMPTY_LINES_REG_EX = "(?m)^[ \t]*\r?\n";
  private static final String NEW_LINE_REG_EX = "\r\n|\r|\n";
  private static final String JAVA_FILE_EXTENSION = "java";
  private static final String UTF8 = "utf-8";

  /**
   * Recursive method for counting lines in directories and Java files.
   *
   * @param file java file or directory
   * @return tree of directories and Java file with lines quantity
   * @throws IllegalArgumentException throws when file have incorrect extension
   * @throws IOException throws when an issue occurs while reading file or directory
   */
  public FileNode countLines(File file) throws IllegalArgumentException, IOException {
    if (!file.exists()) {
      throw new FileNotFoundException(file.getAbsolutePath() + " file or directory not found.");
    }

    FileNode fileNode = new FileNode();
    fileNode.setName(file.getName());

    if (file.isFile()) {
      fileNode.isDirectory(false);

      Integer linesQuantity = countLinesInJavaFile(file);
      fileNode.setLines(linesQuantity);
    } else if (file.isDirectory()) {
      fileNode.isDirectory(true);

      File[] childFiles = file.listFiles();
      if (childFiles != null) {
        List<FileNode> childFileNodes = new ArrayList<>();

        for (File childFile : childFiles) {
          boolean isJavaFile = FilenameUtils.isExtension(childFile.getName(), JAVA_FILE_EXTENSION);

          if (isJavaFile || childFile.isDirectory()) {
            FileNode childFileNode = countLines(childFile);
            childFileNodes.add(childFileNode);
          }
        }

        fileNode.setChildNodes(childFileNodes);
      }

      fileNode.setLines(countChildNodesLines(fileNode.getChildNodes()));
    }

    return fileNode;
  }

  private Integer countChildNodesLines(List<FileNode> childFileNodes) {
    return childFileNodes.stream().mapToInt(FileNode::getLines).sum();
  }

  private Integer countLinesInJavaFile(File file) throws IllegalArgumentException, IOException {
    if (!FilenameUtils.isExtension(file.getName(), JAVA_FILE_EXTENSION)) {
      throw new IllegalArgumentException(
          file.getAbsolutePath() + " file should be with .java extension.");
    }

    String sourceCode = FileUtils.readFileToString(file, UTF8);
    String clearSourceCode = removeCommentsAndEmptyLines(sourceCode);
    return countSourceCodeLines(clearSourceCode);
  }

  private String removeCommentsAndEmptyLines(String sourceCode) {
    return sourceCode
        .replaceAll(COMMENT_REG_EX, REG_EX_CAPTURE_GROUP)
        .replaceAll(EMPTY_LINES_REG_EX, EMPTY);
  }

  private int countSourceCodeLines(String input) {
    Matcher matcher = Pattern.compile(NEW_LINE_REG_EX).matcher(input);
    int lines = 0;
    while (matcher.find()) {
      lines++;
    }
    return lines;
  }
}

package com.codeminders.services.impl;

import com.codeminders.models.FileNode;
import com.codeminders.services.PrintFileTreeService;
import org.apache.commons.lang3.StringUtils;

public class PrintFileTreeServiceImpl implements PrintFileTreeService {

  private static final int INDENT_QUANTITY = 3;
  private static final String DELIMITER = " : ";

  /**
   * Method print tree of directories and Java file with lines quantity.
   *
   * @param fileNode tree of directories and Java file
   */
  public void printDirectoryTreeInfo(FileNode fileNode) {
    StringBuilder stringBuilder = new StringBuilder();
    composeDirectoryTreeOutput(fileNode, 0, stringBuilder);
    System.out.println(stringBuilder.toString());
  }

  private static void composeDirectoryTreeOutput(FileNode fileNode, int indent, StringBuilder sb) {
    composeFileOutput(fileNode, indent, sb);

    if (fileNode.getChildNodes() != null) {
      for (FileNode childFileNode : fileNode.getChildNodes()) {
        if (childFileNode.isDirectory()) {
          composeDirectoryTreeOutput(childFileNode, indent + 1, sb);
        } else {
          composeFileOutput(childFileNode, indent + 1, sb);
        }
      }
    }
  }

  private static void composeFileOutput(FileNode fileNode, int indent, StringBuilder sb) {
    sb.append(getIndentString(indent));
    sb.append(StringUtils.repeat(StringUtils.SPACE, INDENT_QUANTITY));
    sb.append(fileNode.getName());
    sb.append(DELIMITER);
    sb.append(fileNode.getLines());
    sb.append(StringUtils.LF);
  }

  private static String getIndentString(int indent) {
    return StringUtils.repeat(StringUtils.SPACE, INDENT_QUANTITY * indent);
  }
}

package com.codeminders.models;

import java.util.ArrayList;
import java.util.List;

public class FileNode {

  private String name;
  private Integer lines;
  private Boolean isDirectory;
  private List<FileNode> childFileNodes;

  public FileNode() {
    this.childFileNodes = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getLines() {
    return lines;
  }

  public void setLines(Integer lines) {
    this.lines = lines;
  }

  public List<FileNode> getChildNodes() {
    return childFileNodes;
  }

  public void setChildNodes(List<FileNode> childFileNodes) {
    this.childFileNodes = childFileNodes;
  }

  public Boolean isDirectory() {
    return isDirectory;
  }

  public void isDirectory(Boolean directory) {
    isDirectory = directory;
  }
}

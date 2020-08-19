package com.codeminders.services;

import com.codeminders.models.FileNode;
import java.io.File;
import java.io.IOException;

public interface CountLinesService {

  FileNode countLines(File file) throws IllegalArgumentException, IOException;

}

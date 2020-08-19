package com.codeminders;

import com.codeminders.models.FileNode;
import com.codeminders.services.CountLinesService;
import com.codeminders.services.PrintFileTreeService;
import com.codeminders.services.impl.CountLinesServiceImpl;
import com.codeminders.services.impl.PrintFileTreeServiceImpl;
import java.io.File;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;

public class CountLinesCli {

  private static final int SUCCESSFUL_EXIT_CODE = 1;
  private static final int ERROR_EXIT_CODE = -1;

  private final CountLinesService countLinesService;
  private final PrintFileTreeService printFileTreeService;

  public CountLinesCli() {
    countLinesService = new CountLinesServiceImpl();
    printFileTreeService = new PrintFileTreeServiceImpl();
  }

  /**
   * Main method.
   *
   * @param args arguments of main method
   */
  public static void main(String[] args) {
    int exitStatus = new CountLinesCli().run(args);
    System.exit(exitStatus);
  }

  /**
   * The method runs counting lines and printing results to console.
   *
   * @param args arguments of main method
   */
  public int run(String[] args) {
    if (StringUtils.isBlank(args[0]) || args[0] == null) {
      System.out.println("Please, provide name of file or directory.");

      return ERROR_EXIT_CODE;
    } else {
      if (args.length > 1) {
        System.out.println(
            "WARNING: The program expects one parameter. All other parameters will be ignored.");
      }
      String fileName = args[0];
      File file = new File(fileName);

      try {
        FileNode resFileNode = countLinesService.countLines(file);
        printFileTreeService.printDirectoryTreeInfo(resFileNode);

        return SUCCESSFUL_EXIT_CODE;
      } catch (IllegalArgumentException | IOException e) {
        System.out.println(e.getMessage());
        return ERROR_EXIT_CODE;
      } catch (Throwable e) {
        e.printStackTrace();
        return ERROR_EXIT_CODE;
      }
    }
  }
}

package com.apollo.hadoop2;

import java.io.File;

public class TestSplit3 {
  private static final String PROCFS = "/proc/";

  public static void main(String[] args) {
    // split();
    testfile(Integer.parseInt(args[0]) > 0);
    split();
  }

  private static void testfile(Boolean flag) {
    long start = System.currentTimeMillis();
    if (flag) {
      for (int i = 0; i < 1000; i++) {
        new File(PROCFS + i);
      }
    }
    System.out.println("newfile:" + (System.currentTimeMillis() - start));
  }

  public static void split() {
    long start = System.currentTimeMillis();
    for (int j = 0; j < 1000; j++) {
      for (int i = 0; i < 1000; i++) {
        String str = "asas asa s asas asas asa sa sas as as as a a"
            + "asa sasa sa sa sas as as asas as as as as as as"
            + "as as a sas asdasdas dasd asda sd ada d";
        str.toString().split(" ");
      }
    }
    System.out.println("split:" + (System.currentTimeMillis() - start));
  }
}
package com.apollo.group;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class FileHelp {
	public static File mkdirFile(String filepath) throws IOException {
		File file = new File(filepath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			if (file.createNewFile()) {
				System.out.println("mkdir file " + filepath);

			}
		} else {
			System.out.println(" file " + filepath + " is exists");
		}
		return file;
	}

	public static PrintWriter getPrintWriter(String fileName)
			throws IOException {
		return new PrintWriter(new BufferedWriter(new FileWriter(
				FileHelp.mkdirFile(fileName), Boolean.TRUE)));
	}

	public static BufferedReader getBufferedReader(String fileName)
			throws IOException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(
				fileName), "utf-8"));
	}

}

package com.apollo.group;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HashGroup {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String dir = "d://temp//ip//ip.txt";
		BufferedReader in = FileHelp.getBufferedReader(dir);
		String line = null;
		Map<Integer, PrintWriter> outmap = new HashMap<Integer, PrintWriter>();
		while ((line = in.readLine()) != null) {
			Integer file = line.hashCode() % 100;
			if (file < 0) {
				file = file * -1;
			}
			PrintWriter out = null;
			if (!outmap.containsKey(file)) {
				out = FileHelp.getPrintWriter("d://temp//ip/temp/temp." + file);
				outmap.put(file, out);
			} else {
				out = outmap.get(file);
			}
			out.println(line);
		}
		for (PrintWriter out : outmap.values()) {
			out.flush();
		}
	}
}

package com.apollo.group;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class NumberGenerator {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		String dir = "d://temp//ip//ip.txt";
		String point = ".";
		int count = 100000000;
		Random random = new Random(System.currentTimeMillis());
		PrintWriter out = FileHelp.getPrintWriter(dir);
		while (count-- > 0) {
			out.println("172.0." + random.nextInt(256) + point
					+ random.nextInt(256));
			if (count % 10000 == 0) {
				out.flush();
			}
		}
		out.flush();
		out.close();
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

}

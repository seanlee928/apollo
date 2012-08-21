package com.apollo.group;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.apollo.FileHelp;

public class GroupSort {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Map<String, Integer> entry = new HashMap<String, Integer>();
		for (String path : Arrays.asList(new File("d://temp//ip/temp/").list())) {
			Map<String, Integer> map = MaxIp("d://temp//ip/temp/" + path);
			System.out.println(path + ":" + map);
			entry.putAll(map);
		}
		System.out.println(getMaxMap(entry));
	}

	private static Map<String, Integer> MaxIp(String path) throws IOException {
		BufferedReader in = FileHelp.getBufferedReader(path);
		String line = null;
		Map<String, Integer> ipMap = new HashMap<String, Integer>();
		while ((line = in.readLine()) != null) {
			if (ipMap.containsKey(line)) {
				ipMap.put(line, ipMap.get(line) + 1);
			} else {
				ipMap.put(line, 1);
			}
		}
		Map<String, Integer> entry = getMaxMap(ipMap);
		return entry;
	}

	private static Map<String, Integer> getMaxMap(Map<String, Integer> ipMap) {
		int max = 0;
		String maxIp = null;
		for (Map.Entry<String, Integer> entry : ipMap.entrySet()) {
			if (entry.getValue() >= max) {
				maxIp = entry.getKey();
				max = entry.getValue();
			}
		}
		Map<String, Integer> entry = new HashMap<String, Integer>();
		entry.put(maxIp, max);
		return entry;
	}
}

package com.apollo.security;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

public class PrintProvider {
	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String args[]) {
		for (Provider p : Security.getProviders()) {
			System.out.println(p);
			for (Map.Entry<Object, Object> entry : p.entrySet()) {
				System.out.println("\t "+entry.getKey());
			}
		}
	}
}

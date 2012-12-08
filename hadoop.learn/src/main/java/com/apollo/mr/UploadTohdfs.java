package com.apollo.mr;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

public class UploadTohdfs {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		String localSrc = args[0];
		String dst = "hdfs://db031217.sqa.cm4.tbsite.net:9516/user/hbase/";
		InputStream in = new BufferedInputStream(new FileInputStream(localSrc));
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(dst), conf);
		OutputStream out = fs.create(new Path(dst), new Progressable() {
			public void progress() {
				System.out.println(".");
			}
		});
		System.out.println("upload success!!!");
		IOUtils.copyBytes(in, out, 4096, true);
	}
}

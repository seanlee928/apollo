package com.apollo.io;

import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.hadoop.io.Text;

public class TestText {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Text text = new Text("123Œ“");
		DataOutput out=new ObjectOutputStream(new OutputStream(){
			public void write(int b) throws IOException {
				System.out.print(b+" ");
			}});
		text.write(out);
		System.out.println();
		for(byte b:text.getBytes()){
			System.out.print(b+" ");
		}
		
		System.out.println(text.getLength());

		System.out.println("Œ“".getBytes().length);
	}

}

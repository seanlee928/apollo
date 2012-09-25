package com.apollo.outputstream;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * limit writing data. if writing data size has reached limit,system writes log
 * message and throws IOException
 * 
 * @author dragon.caol
 * 
 */
public class LimitOutputSteam extends FilterOutputStream {

  private long remaining;
  private String msg;
  private boolean limit = Boolean.FALSE;
  private boolean isRestriction = Boolean.FALSE;

  public LimitOutputSteam(OutputStream out, long maxSize) {
    super(out);
    if (maxSize > 0) {
      this.isRestriction = Boolean.TRUE;
      this.remaining = maxSize;
      this.msg = "\nException:Written to stdout or stderr cann't exceed "
          + ((float) maxSize / 1024) + "kb. \n";
    }
  }

  public void write(int b) throws IOException {
    if (isRestriction) {
      if (!limit) {
        if (remaining-- > 0) {
          super.write(b);
        } else {
          for (int i = 0; i < msg.length(); i++) {
            super.write(msg.getBytes()[i]);
          }
          this.limit = Boolean.TRUE;
          throw new IOException(msg);
        }
      }
    } else {
      super.write(b);
    }
  }

  public static void main(String[] args) {
    System.setOut(new PrintStream(new LimitOutputSteam(System.out,2048)));
    for (int i = 0; i < 1000; i++) {
      System.out.println(i);
    }
  }
}

package com.apollo.mr;

import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
  public static final Log LOG = LogFactory.getLog(WordCount.class);

  enum WordCountCounter {
    SIZE,
  }

  public static class Qc1Comparator extends Text.Comparator {
    public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
      System.out.println("Comparato" + ",ret=");
      int len = "0".getBytes().length;
      int ret = super.compare(b1, s1 + len, l1 - len, b2, s2 + len, l2 - len);
      if (ret != 0) return ret;
      System.out.println("Comparator\u5168\u8f93\u51fa\uff1a" + ",ret=" + ret);
      return super.compare(b1, s1, len, b2, s2, len);
    }
  }

  public static class TokenizerMapper extends Mapper<Text, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    protected void setup(Context context) throws IOException, InterruptedException {
      System.out.println("file.txt is " + new File("file.txt").exists());
    }

    public void map(Text key, Text value, Context context) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
      context.write(key, one);
      System.out.println(key + ":" + value);
      while (itr.hasMoreTokens()) {
        context.getCounter(WordCountCounter.SIZE).increment(1);
        word.set(itr.nextToken());
        context.write(word, one);
      }
    }
  }

  public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException,
        InterruptedException {
      int sum = 0;
      System.out.println(key);
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
    }
  }

  public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: wordcount <in> <out>");
      System.exit(2);
    }

    conf.setOutputKeyComparatorClass(Qc1Comparator.class);
    Job job = new Job(conf, "dragon");
    job.setJarByClass(WordCount.class);

    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    // job.setInputFormatClass(SequenceFileInputFormat.class);
    job.setReducerClass(IntSumReducer.class);
    job.setInputFormatClass(org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat.class);
    job.setOutputKeyClass(Text.class);// add by dragon.caol
    job.setOutputValueClass(IntWritable.class);// getMapOutputKeyClass

    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

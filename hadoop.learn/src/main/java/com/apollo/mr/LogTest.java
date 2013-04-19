package com.apollo.mr;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class LogTest {
  public static final Log LOGTest = LogFactory.getLog(IntSumReducer.class);

  public static class TokenizerMapper extends
      Mapper<Object, Text, Text, IntWritable> {
    public static final Log LOG = LogFactory.getLog(TokenizerMapper.class);

    public void map(Object key, Text value, Context context)
        throws IOException, InterruptedException {
      for (int i = 0; i < 10000000; i++) {
        System.out.println("out map" + i);
        LOG.info("log map" + i);
      }
    }
  }

  public static class IntSumReducer extends
      Reducer<Text, IntWritable, Text, IntWritable> {
    public static final Log LOG = LogFactory.getLog(IntSumReducer.class);

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
        throws IOException, InterruptedException {
      System.out.println("out reduce");
      LOG.info("log reduce");
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = new GenericOptionsParser(conf, args)
        .getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: LogTest <in> <out>");
      System.exit(2);
    }

    LOGTest.info("Child starting");
    System.out.println("abcdefg");

    // org.apache.hadoop.io.LongWritable
    Job job = new Job(conf, "log");
    job.setJarByClass(LogTest.class);

    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);

    job.setOutputKeyClass(Text.class);// add by dragon.caol
    job.setOutputValueClass(IntWritable.class);// getMapOutputKeyClass

    FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

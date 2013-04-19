package com.apollo.hadoop2;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.DefaultCodec;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.SequenceFileInputFormat;
import org.apache.hadoop.mapred.SequenceFileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class TestZlib {

  /**
   * @param args
   * @throws IOException
   * @throws ClassNotFoundException
   * @throws InterruptedException
   */
  private static final Log LOG = LogFactory.getLog(TestZlib.class);

  public static void main(String[] args) throws IOException, InterruptedException,
      ClassNotFoundException {

    JobConf conf = new JobConf();
    String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
    if (otherArgs.length != 2) {
      System.err.println("Usage: TestZlib <in> <out>");
      System.exit(2);
    }
    conf.setJobName("TestZlib");

    conf.setMapOutputKeyClass(Text.class);
    conf.setMapOutputValueClass(Text.class);
    conf.setInputFormat(SequenceFileInputFormat.class);

    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);
    SequenceFileOutputFormat.setOutputCompressionType(conf, SequenceFile.CompressionType.BLOCK);
    SequenceFileOutputFormat.getOutputCompressorClass(conf, DefaultCodec.class);

    conf.setMapperClass(Map.class);
    conf.setNumReduceTasks(0);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));

    JobClient c = new JobClient(conf);
    RunningJob job = JobClient.runJob(conf);
    job.isSuccessful();

  }

  private static class Map extends MapReduceBase implements Mapper<NullWritable, Text, Text, Text> {
    Text key_text = new Text();
    Text value_text = new Text();

    public void map(NullWritable key, Text value, OutputCollector<Text, Text> output,
        Reporter reporter) throws IOException {
      // System.out.println("before Map");
      this.key_text.set("ok");
      String line = value.toString();
      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
//      line.toString().split("\001", -1);
      
      this.value_text.set(value);
      output.collect(this.key_text, this.value_text);
      // System.out.println("end Map");
    }
  }
}

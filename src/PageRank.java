import java.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.*;
import org.apache.hadoop.fs.Path;

enum MyCounters {
	NumberOfPages
}

public class PageRank {
	static long numberOfPages = 0;

	public static void runDecode(String in, String out) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
        //conf.set("fs.defaultFS", "hdfs://localhost:9000");
		conf.set(XmlInputFormat.START_TAG_KEY, "<page>");
		conf.set(XmlInputFormat.END_TAG_KEY, "</page>");
        Job job = Job.getInstance(conf, "page decode");
        job.setJarByClass(PageRank.class);

        job.setMapperClass(DecodeMapper.class);
        job.setReducerClass(DecodeReducer.class);

        job.setInputFormatClass(XmlInputFormat.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));
        job.waitForCompletion(true);
        Counters counters = job.getCounters();
        Counter c = counters.findCounter(MyCounters.NumberOfPages);
        numberOfPages = c.getValue();
	}


	public static void runRank(String in, String out) throws IOException, ClassNotFoundException, InterruptedException
	{
            Configuration conf = new Configuration();
            //conf.set("fs.defaultFS", "hdfs://localhost:9000");
            conf.setLong("numberOfPages", numberOfPages);
            Job job = Job.getInstance(conf, "page rank");
            job.setJarByClass(PageRank.class);

            job.setMapperClass(PageRankMapper.class);
            job.setReducerClass(PageRankReducer.class);

            job.setInputFormatClass(TextInputFormat.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            FileInputFormat.addInputPath(job, new Path(in));
            FileOutputFormat.setOutputPath(job, new Path(out));
            job.waitForCompletion(true);
	}

	public static void runSort(String in, String out) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException
	{
		Configuration conf = new Configuration();
        //conf.set("fs.defaultFS", "hdfs://localhost:9000");
        Job job = Job.getInstance(conf, "page sort");
        job.setJarByClass(PageRank.class);

        job.setMapperClass(SortMapper.class);
        job.setReducerClass(SortReducer.class);

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputKeyClass(FloatWritable.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(in));
        FileOutputFormat.setOutputPath(job, new Path(out));
        job.waitForCompletion(true);
		
	}

	public static void main(String[] args) throws Exception {
		final int ITERATIONS = 5;
		int n;
		// À décommenter au cours des exercices
		runDecode("input", "output0");
//		runRank("input", "output0");
		for (n = 0; n < ITERATIONS; n++)
			runRank("output"+n, "output"+(n+1));
		runSort("output"+5, "output");
	}
}

import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class PageRankMapper extends Mapper<LongWritable,Text,Text,Text> {
	private Text word = new Text();
	
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] strs = value.toString().split("\t");
		if(strs.length > 2) {
			for(String s : strs[2].split(",")) context.write(new Text(s), new Text(strs[0] + "\t" + strs[1] + "\t" + strs[2].split(",").length));
			word.set(strs[0]);
			context.write(word, new Text(strs[2]));
		}
	}
}

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SortMapper extends Mapper<LongWritable,Text,FloatWritable,Text> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String[] strs = value.toString().split("\t");
		context.write(new FloatWritable(Float.parseFloat(strs[1])), new Text(strs[0]));
	}
}

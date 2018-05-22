import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class SortReducer extends Reducer <FloatWritable, Text, FloatWritable, Text> {
	@Override
	public void reduce(FloatWritable rank, Iterable<Text> pages, Context context) throws IOException, InterruptedException {
		String result = "";
		for(Text t : pages) {
			if(result.length() == 0) result = t.toString();
			else result += "," + t.toString();
		}
		context.write(rank, new Text(result));
	}
}

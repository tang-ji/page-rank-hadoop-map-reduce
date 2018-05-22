import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class DecodeMapper extends Mapper<LongWritable,Text,Text,Text> {
	@Override
	public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] strs = value.toString().split("\t");
        context.write(new Text(strs[0]), new Text(strs[1]));
        context.write(new Text(strs[1]), new Text("#"));
	}
}

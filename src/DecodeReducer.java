import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class DecodeReducer extends Reducer <Text, Text, Text, Text> {
	@Override
	public void reduce(Text page, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		String result = "1.0\t";
		for(Text v : values) {
			if(v.toString().equals("#")) continue;
			if(result.equals("1.0\t")) result += v.toString();
			else result += "," + v.toString();
		}
		context.write(page, new Text(result));
		context.getCounter(MyCounters.NumberOfPages).increment(1);
	}
}

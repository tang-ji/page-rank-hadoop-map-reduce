import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;

public class PageRankReducer extends Reducer <Text, Text, Text, Text> {
	@Override
	public void reduce(Text page, Iterable<Text> values, Context context) throws IOException, InterruptedException {
		
		double d = 0.85, sum = 0, X = 0;
		Configuration conf = context.getConfiguration();
		Long numberOfPages = conf.getLong("numberOfPages", 0);
		String but = "";
		for(Text v : values) {
			String valeur = v.toString();
			if(valeur.indexOf("\t") < 0) {
				but = valeur;
			}
			else if(valeur.indexOf(",") < 0) {
				sum += Double.parseDouble(valeur.split("\t")[1]) / Double.parseDouble(valeur.split("\t")[2]);
			}
			else {
				d = Double.parseDouble(valeur.split("\t")[0]);
				but = valeur.split("\t")[1];
			}
			X = (1.0 - d) / (double)numberOfPages + d * sum;
		}
		context.write(page, new Text(X + "\t" + but ));
	}
}

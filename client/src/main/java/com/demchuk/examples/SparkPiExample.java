package com.demchuk.examples;

import com.demchuk.TestHelper;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class SparkPiExample {

    public static void main(String[] args) throws Exception {
        SparkSession spark = SparkSession.builder().getOrCreate();
        JavaSparkContext jsc = new JavaSparkContext(spark.sparkContext());
        TestHelper helper = TestHelper.create(jsc.getConf());
        helper.notifyAppStarted();

        // application logic
        int slices = (args.length == 1) ? Integer.parseInt(args[0]) : 2;
        int n = 100000 * slices;
        List<Integer> l = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            l.add(i);
        }

        JavaRDD<Integer> dataSet = jsc.parallelize(l, slices);

        int count = dataSet.map(integer -> {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            return (x * x + y * y <= 1) ? 1 : 0;
        }).reduce((integer, integer2) -> integer + integer2);

        String result = String.valueOf(4.0 * count / n);
        helper.writeResult(result);

        jsc.stop();
    }

}

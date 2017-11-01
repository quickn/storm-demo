package com.quickn.storm.demo;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

/**
 * Created by json2 on 2017/10/29.
 */
public class WordCountTopology {
    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        TopologyBuilder builder = new TopologyBuilder();
        //并发度10
        builder.setSpout("spout", new SentenceSpout(), 2);
        builder.setBolt("split", new SplitSentenceBolt(), 2).
                shuffleGrouping("spout").//与spout关联
                setNumTasks(2);
        builder.setBolt("count", new WordCountBolt(), 6).
                fieldsGrouping("split", new Fields("word"))//与上一个blot关联
                .setNumTasks(6);
        Config config = new Config();
        config.setDebug(true);
        if (args != null && args.length > 0) {
            config.setNumWorkers(2);
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        } else {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", config, builder.createTopology());
            try {
                Thread.sleep(1000 * 100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cluster.shutdown();
        }
    }
}

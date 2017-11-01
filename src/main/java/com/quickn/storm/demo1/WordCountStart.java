package com.quickn.storm.demo1;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountStart {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Spout", new WordCountSpout(), 2);
        //随机分组
        builder.setBolt("SplitBolt", new SplitSentence(), 3).shuffleGrouping("Spout", "spout");
        //按指定的field分组
        builder.setBolt("CountBolt", new WordCount(), 3).fieldsGrouping("SplitBolt", new Fields("word"));
        Config conf = new Config();
        conf.setDebug(false);
        if (args.length == 0) {
            LocalCluster localCluster = new LocalCluster();
            localCluster.submitTopology("wordcount-demo", conf, builder.createTopology());
        } else {
            try {
                StormSubmitter.submitTopology("wordcount-online", conf, builder.createTopology());
            } catch (InvalidTopologyException e) {
                System.out.println("[InvalidTopologyException] error:" + e);
            } catch (AuthorizationException e) {
                e.printStackTrace();
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            }
        }

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		cluster.killTopology("wordcount-demo");
//		cluster.shutdown();
    }

}

package com.quickn.storm.demo;


import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by json2 on 2017/10/29.
 */
public class WordCountBolt extends BaseBasicBolt {

    Logger logger = LoggerFactory.getLogger(WordCountBolt.class);
    private Map<String, Long> count = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //super.prepare(stormConf, context);
        this.count = new HashMap<String, Long>();
    }

    @Override
    public void cleanup() {
        // super.cleanup();
        for (String key : count.keySet()) {
            System.out.println(key + ":" + this.count.get(key));
        }
    }

    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String word = tuple.getStringByField("word");
        Long count = this.count.get(word);
        if (count == null) {
            count = 0L;
        }
        count++;
        logger.error("统计结果 word:" + word + " count:" + count);
        this.count.put(word, count);
        logger.error("暂停5s");
        //basicOutputCollector.ack
        Utils.sleep(5000);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}

package com.quickn.storm.demo;


import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by json2 on 2017/10/29.
 */
public class SplitSentenceBolt extends BaseBasicBolt {
    Logger logger = LoggerFactory.getLogger(SplitSentenceBolt.class);

    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        String sentence = tuple.getStringByField("sentence");
        String[] words = sentence.split(" ");
        for (String word : words) {
            //发送单词
            logger.error("发送单词:" + new Values(word));
            basicOutputCollector.emit(new Values(word));
        }
        logger.error("暂停5s");
        Utils.sleep(5000);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("word"));
    }
}

package com.quickn.storm.demo;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 创建spout数据源
 * Created by json2 on 2017/10/29.
 */
public class SentenceSpout extends BaseRichSpout {
    Logger logger = LoggerFactory.getLogger(SentenceSpout.class);
    private SpoutOutputCollector collector;
    private String[] sentences = {"Apache Storm is a free and open source distributed realtime computation system",
            "Storm makes it easy to reliably process unbounded streams of data",
            "doing for realtime processing what Hadoop did for batch processing",
            "Storm is simple", "can be used with any programming language",
            "and is a lot of fun to use"};
    private int index = 0;

    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    public void nextTuple() {
        if (index >= sentences.length) {
            return;
        }
        //发送字符串
        logger.error("发送字符串:" + sentences[index]);
        this.collector.emit(new Values(sentences[index]));
        index++;
        logger.error("等待10s");
        Utils.sleep(10000);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
        //outputFieldsDeclarer.declareStream("test",new Fields("sentence"));
    }

    /***
     * 验证数据是否成功
     * @param msgId
     */
    @Override
    public void ack(Object msgId) {
        //super.ack(msgId);
        logger.info("");
    }
}

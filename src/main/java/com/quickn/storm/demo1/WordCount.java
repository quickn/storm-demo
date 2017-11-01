package com.quickn.storm.demo1;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WordCount extends BaseRichBolt {

    Logger logger = LoggerFactory.getLogger(WordCount.class);

    OutputCollector outputCollector;
    public Map<String, Integer> countMap = new HashMap<String, Integer>();

    @Override
    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        // TODO Auto-generated method stub
        outputCollector = collector;
    }

    @Override
    public void execute(Tuple input) {
        //判断是不是同一个线程每次都是访问同一个实例
        logger.info(this.hashCode() + " size:"+countMap.size());
        // TODO Auto-generated method stub
        String word = input.getString(0);
        Integer count = this.countMap.get(word);
        if (null == count) {
            count = 0;
        }
        count++;
        this.countMap.put(word, count);
        Iterator<String> iter = this.countMap.keySet().iterator();
        while (iter.hasNext()) {
            String next = iter.next();
            //logger.info(next + ":" + this.countMap.get(next));
        }
        outputCollector.ack(input);
    }

    @Override
    public void cleanup() {
        // TODO Auto-generated method stub

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        // TODO Auto-generated method stub
    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        // TODO Auto-generated method stub
        return null;
    }

}

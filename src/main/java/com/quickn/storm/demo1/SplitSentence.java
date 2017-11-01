package com.quickn.storm.demo1;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;



public class SplitSentence extends BaseRichBolt {

	Logger logger = LoggerFactory.getLogger(SplitSentence.class);

	OutputCollector outputCollector;
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		outputCollector = collector;
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		String sentence = input.getString(0);
		for (String word : sentence.split(" ")){
			outputCollector.emit(new Values(word));
			//_collector.emit(input, new Values(word));
			// TODO
//			outputCollector.ack(input);
		}
		//验证Tuple是不是随机分发的
		//logger.info(sentence);
		outputCollector.ack(input);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("word"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}

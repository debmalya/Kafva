package org.deb;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Copyright 2015-2016 Debmalya Jash
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * @author debmalyajash
 *
 */
public class MyKafkaConsumer {

	private static final Logger LOGGER = Logger.getLogger("MyKafkaConsumer");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 1) {
			final String topic = args[0];

			// Set properties
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, args[1]);
			props.put("key.deserializer", StringDeserializer.class.getName());
			props.put("value.deserializer", StringDeserializer.class.getName());
			props.put("group.id", "mygroup");
			props.put("enable.auto.commit", "true");
			props.put("auto.commit.interval.ms", "1000");
			props.put("session.timeout.ms", "30000");

			KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
			consumer.subscribe(Arrays.asList(topic));

			try {
				while (true) {
					ConsumerRecords<String, String> consumerRecords = consumer.poll(10);
					for (ConsumerRecord<String, String> eachRecord : consumerRecords) {
						LOGGER.log(Level.INFO, "offset : " + eachRecord.offset() + " key : " + eachRecord.key()
								+ " value : " + eachRecord.value());
					}
				}

			} finally {
				consumer.close();
			}

		} else {
			System.err.println("Usage: <1st argument topic name> <server address with port>");
		}

	}

}

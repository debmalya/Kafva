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
package org.deb;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author debmalyajash
 *
 */
public class MyKafkaProducer {

	private static final Logger LOGGER = Logger.getLogger("MyKafkaProducer");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length > 2) {
			final String topic = args[0];

			// Set properties
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, args[1]);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

			// Kafka Producer
			KafkaProducer<String, String> producer = new KafkaProducer<>(props);
			LOGGER.log(Level.INFO, "Sending 1 message every 1 second, for a total of 100 messages");

			int count = 1;

			try {
				count = Integer.parseInt(args[2]);
			} catch (NumberFormatException nfe) {

			}
			LOGGER.log(Level.INFO, "Sending 1 message every 10 mili seconds, for a total of " + count + " messages");
			for (int i = 0; i < 100; i++) {
				String message = "Message number :" + i + " at " + new Date();
				String key = "key" + i;
				ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
				producer.send(record, new Callback() {

					@Override
					public void onCompletion(RecordMetadata arg0, Exception exc) {
						if (exc == null) {
							// There is no exception
							LOGGER.log(Level.INFO, "Message delivered successfully " + message);
						} else {
							LOGGER.log(Level.SEVERE, "Not able to send the message :" + exc.getMessage(), exc);
						}

					}
				});
				try {

					Thread.sleep(10);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} else {
			System.err.println("Usage: <1st argument topic name> <server address with port> <loop count>");
		}

	}

}

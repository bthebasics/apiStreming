package org.dataEtl;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.kafka.clients.producer.*;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class kafkaTwitterProducer {

    public static void run(String consumerKey, String consumerSecret, String token, String secret)
    {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.SERVERS);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        // add some track terms
        endpoint.trackTerms(Lists.newArrayList("twitterapi", "#AAPSweep"));

        Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
        // Authentication auth = new BasicAuth(username, password);

        // Create a new BasicClient. By default gzip is enabled.
        Client client = new ClientBuilder()
                        .hosts(Constants.STREAM_HOST)
                        .endpoint(endpoint).authentication(auth)
                        .processor(new StringDelimitedProcessor(queue))
                        .build();

        // Establish a connection
        client.connect();
        String TOPIC = "twitterTopic";

        for (int i = 0; i < 2000; i++) {
            ProducerRecord data = new ProducerRecord<String, String>("test1", "Hello this is record " + i);
            Future<RecordMetadata> recordMetadata = producer.send(data);
        }
        producer.close();


        // Do whatever needs to be done with messages
        for (int msgRead = 0; msgRead < 1000; msgRead++) {
            try {
               // message = new KeyedMessage<String, String>(topic, queue.take());
                ProducerRecord data = new ProducerRecord<String, String>(TOPIC, queue.take());
                Future<RecordMetadata> recordMetadata = producer.send(data);
                System.out.println(recordMetadata.toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        producer.close();
        client.stop();

    }

    public static void main(String[] args) {
        System.out.println("String ");

    }
}

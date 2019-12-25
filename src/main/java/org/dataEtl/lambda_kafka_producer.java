package org.dataEtl;
//https://github.com/apssouza22/lambda-arch/blob/master/README.md
//https://github.com/dbsheta/kafka-twitter-producer/blob/master/src/main/java/ml/dhoomilsheta/app/producer/TwitterKafkaProducer.java
/*

Keys and tokens
Keys, secret keys and access tokens management.

Consumer API keys
NKPSicZwVc75fxj3nEcw5kAti (API key)
kUerS4BXuTu6tIK7x53DTmE2Iv03ryhyEiu49xreYIPKq7CXlF (API secret key)


Regenerate
Access token & access token secret
750524357487759363-Xr1djhktykp8tObwmrKBoGuss41yD4k (Access token)
hL1hqCHYqeFBcW3L3IuLN7buZpCMdW9XxRKRFHmBJa3pX (Access token secret)

Read and write (Access level)
 */
import com.google.gson.Gson;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.dataEtl.TwitterConfiguration;
import org.dataEtl.User;
import org.dataEtl.Tweet;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class lambda_kafka_producer {
    private Client client;
    private BlockingQueue<String> queue;
    private Gson gson;
    private Callback callback;

    public void TwitterKafkaProducer() {
        // Configure auth
        Authentication authentication = new OAuth1(
                TwitterConfiguration.CONSUMER_KEY,
                TwitterConfiguration.CONSUMER_SECRET,
                TwitterConfiguration.ACCESS_TOKEN,
                TwitterConfiguration.TOKEN_SECRET);

        // track the terms of your choice. here im only tracking #bigdata.
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Collections.singletonList(TwitterConfiguration.HASHTAG));

        queue = new LinkedBlockingQueue<>(10000);

        client = new ClientBuilder()
                .hosts(Constants.STREAM_HOST)
                .authentication(authentication)
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(queue))
                .build();
        gson = new Gson();
      //  callback = new BasicCallback();
    }

    private Producer<Long, String> getProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfiguration.SERVERS);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }


    public static void main(String[] args) {
        System.out.println("String ");

    }
    // openWeather
}

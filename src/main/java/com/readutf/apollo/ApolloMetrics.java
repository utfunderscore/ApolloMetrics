package com.readutf.apollo;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.readutf.apollo.clickthrough.ClickThroughTracker;
import com.readutf.apollo.event.EventStore;
import org.bson.Document;

public class ApolloMetrics {

    private static ApolloMetrics instance;

    private final InfluxDBClient influxClient;
    private final InfluxDBClientOptions options;

    private ApolloMetrics(InfluxDBClientOptions options) {
        this.influxClient = InfluxDBClientFactory.create(options);
        this.options = options;
    }

    public static void init(InfluxDBClientOptions options) {
        if (instance != null) {
            throw new IllegalStateException("ApolloMetrics has already been initialized.");
        }
        instance = new ApolloMetrics(options);
    }

    public static EventStore getEventStore(String in) {
        return new EventStore(instance.influxClient, instance.options, in);
    }

    public static ClickThroughTracker getClickThroughTracker(String trackerName) {
        return new ClickThroughTracker(trackerName, instance.influxClient);
    }

}

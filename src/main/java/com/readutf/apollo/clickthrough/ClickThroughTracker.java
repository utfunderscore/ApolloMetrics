package com.readutf.apollo.clickthrough;

import com.google.common.base.Preconditions;
import com.influxdb.client.InfluxDBClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.readutf.apollo.ApolloMetrics;
import com.readutf.apollo.event.EventStore;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * A class that tracks click through rates for a specific task.
 * <p>
 * Call {@link #start()} when the task/action becomes available.
 * When the task/action is complete, complete the future returned with
 * true or false depending on whether the task was completed or not.
 *
 */
public class ClickThroughTracker {

    private final InfluxDBClient influxClient;
    private final String name;
    private final EventStore eventStore;

    public ClickThroughTracker(String name, InfluxDBClient influxClient) {
        Preconditions.checkArgument(name.matches("[a-zA-Z0-9_]+"), "Name must be alphanumeric with underscores.");
        this.influxClient = influxClient;
        this.name = name;
        this.eventStore = ApolloMetrics.getEventStore("click_through_" + name);
    }

    public CompletableFuture<Boolean> start(long timeoutMillis) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();

        future.completeOnTimeout(false, timeoutMillis, java.util.concurrent.TimeUnit.MILLISECONDS);
        future.whenComplete((aBoolean, throwable) -> track(aBoolean));

        return future;
    }

    private void track(boolean completed) {



    }

}

package com.readutf.apollo;

import com.influxdb.client.InfluxDBClientOptions;

public class SetupExample {

    public static void main(String[] args) {

        // Define your influxDb client options
        InfluxDBClientOptions clientOptions = InfluxDBClientOptions.builder()
                .url("http://localhost:8086")
                .authenticateToken("your-token".toCharArray())
                .org("your-org")
                .build();

        // Initialize ApolloMetrics using the created client options
        ApolloMetrics.init(clientOptions);


    }

}

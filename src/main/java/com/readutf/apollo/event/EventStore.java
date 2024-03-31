package com.readutf.apollo.event;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientOptions;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.BuilderAggregateFunctionType;
import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventStore {

    private final InfluxDBClient client;
    private final String name;
    private final InfluxDBClientOptions options;

    public EventStore(InfluxDBClient client, InfluxDBClientOptions options, String name) {
        this.client = client;
        this.options = options;
        this.name = name;
    }

    public void store(Event event) {
        WriteApiBlocking writeApiBlocking = client.getWriteApiBlocking();

        Point point = Point.measurement(name);

        for (Map.Entry<String, Number> entry : event.getValues().entrySet()) {
            point.addField(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, String> entry : event.getTags().entrySet()) {
            point.addTag(entry.getKey(), entry.getValue());
        }

        point.time(event.getTimestamp(), WritePrecision.MS);


        writeApiBlocking.writePoint(point);
    }

    public List<Event> getMeasurements(String value, Instant start, Instant end) {


        String flux = """
                from(bucket: "%s")
                  |> range(start: %s, stop: %s)
                  |> filter(fn: (r) => r["_measurement"] == "%s")
                  |> filter(fn: (r) => r["_field"] == "%s")
                  |> aggregateWindow(every: 1s, fn: mean, createEmpty: false)
                  |> yield(name: "mean")""".formatted(options.getBucket(), start.toString(), end.toString(), name, value);


        System.out.println(flux);

        List<Event> events = new ArrayList<>();

        for (FluxTable fluxTable : query(flux)) {
            for (FluxRecord record : fluxTable.getRecords()) {
                Event.EventBuilder builder = Event.builder();
                builder.timestamp(record.getTime());
                builder.addValue(value, (Number) record.getValueByKey("_value"));
                events.add(builder.build());
            }
        }

        return events;
    }

    public List<FluxTable> query(String flux) {
        return client.getQueryApi().query(flux);
    }


    public List<FluxTable> query(String flux, Map<String, Object> params) {
        return client.getQueryApi().query(flux, options.getOrg(), params);
    }

}

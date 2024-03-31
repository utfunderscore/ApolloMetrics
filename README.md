# ApolloMetrics
[![Java CI with Gradle](https://github.com/utfunderscore/ApolloMetrics/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/utfunderscore/ApolloMetrics/actions/workflows/gradle-build.yml)
Metrics platform build for *brawl.tf*. Acts as a wrapper ontop of the existing InfluxDB Java driver and includes common flux queries.

## Setup

```java
InfluxDBClientOptions clientOptions = InfluxDBClientOptions.builder()
  .url("http://localhost:8086")
  .authenticateToken("your-token".toCharArray())
  .org("your-org")
  .build();

// Initialize ApolloMetrics using the created client options
ApolloMetrics.init(clientOptions);
```

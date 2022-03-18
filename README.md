# Hawk Service

![workflow](https://github.com/PrivacyEngineering/hawk-service/actions/workflows/main.yml/badge.svg)

The Hawk Service is a service exposing an HTTP / REST API written in Kotlin / Spring Boot. It serves
as a managing component for Hawk Core and Hawk Release managing anything about the `Usage`
, `Mapping` and `Field` entities. Because of its stateless design, it can be horizontally scaled
requiring a robust enough Postgres Database in the backend.

For API
reference: [SwaggerHub](https://app.swaggerhub.com/apis-docs/TUB-CNPE-TB/transparency-log-service/1.1.1)

## Hawk Core Operate

Uses the `/api/usage[/batch]` endpoints for inserting Usage request and combining them into inside
the database layer.

## Hawk Core Monitoring (Configuration)

The [Configuration Dashboard](https://github.com/PrivacyEngineering/hawk-core-monitor) uses a
variety of different endpoint to add, modify and delete Mappings / Endpoints.

## Hawk Core Monitoring Grafana

The Hawk Service implements the endpoints for
the [Grafana JSON Plugin](https://grafana.com/grafana/plugins/simpod-json-datasource/).

The following metrics / targets are exported:

| Name                              | Type        | Description                                                                    |
|-----------------------------------|-------------|--------------------------------------------------------------------------------|
| service_requests_table            | table       | Count of the requests, grouped by endpoint host.                               |
| service_initiator_requests_table  | table       | Count of requests, grouped by endpoint host and initiator host                 |
| endpoint_requests_table           | table       | Count of requests, grouped by endpoint id                                      |
| endpoint_initiator_requests_table | table       | Count of requests, grouped by endpoint id, initiator host                      |
| requests_time                     | time-series | Count of requests with optional endpoint as payload `endpoint`                 |
| field_requests                    | table       | Count of requests by field, optional selection of fields with payload `fields` |
| field_endpoints                   | table       | All endpoints in which a field is mapped with payload `field`                  |
| field_requests_time               | time-series | Count of requests accumulated by selected fields passed with payload `fields`  |
| endpoints                         | variable    | List of tracked endpoints                                                      |
| fields                            | variable    | List of created fields                                                         |
| unmapped_endpoints                | variable    | List of endpoints with no mapping                                              |

## Hawk Release

When the Spring Profile `flagger-canary` is active and the current environment is a Kubernetes
environment this services uses
the [Fabric8 Kubernetes Client](https://github.com/fabric8io/kubernetes-client) to watch for new
[Flagger Canary Releases](https://github.com/fluxcd/flagger). Recent and active releases are
available at the `/api/release/*` endpoints.

Using [Micrometer](https://github.com/micrometer-metrics/micrometer) and Spring Boot Actuator this
service also exposes the following metrics for all services participating in an ongoing release as
Prometheus Metrics:

```
hawk.<service-name>.count
hawk.<service-name>.mapped.count
hawk.<service-name>.unmapped.count
hawk.<service-name>.unmapped.ratio
```

The metrics are useful for Flagger.

## Installation

For the Hawk Service to run you need to its mandatory to have a PostgreSQL Database connected. With
smaller workloads Postgres itself is fine, for bigger workloads you might need some technologies
like [YugabyteDB](https://github.com/yugabyte/yugabyte-db). Just pass the following environment
variables:

```properties
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost/hawk
SPRING_DATASOURCE_USERNAME=xxxx
SPRING_DATASOURCE_PASSWORD=xxxx
```

Profiles can be activated using the following environment variable.

```
SPRING_PROFILES_ACTIVE=flagger-canary,test-data
```

By default, the service starts on port 8080. Pass the env `SERVER_PORT` to change that.

To install via. Docker run the following command and pass the environment variables after using you
need:

```
docker run -p 8080:8080 -e ENV1=1 ENV2=2 p4skal/hawk-service
```

## Mapping / Field import from JSON

Although we recommend to create Mappings / Fields with
the [Hawk Core Monitor UI](https://github.com/PrivacyEngineering/hawk-core-monitor), when using IaaC
you may want to specify the fields / mappings directly. On startup, this service will sync the
updates. You have many possibilities to provide such entities, as they are provided
via [Spring External Configuration](). The following things are described via. Environment variables.
If you want to use Properties, JSON, YAML etc., see there.
```dotenv
FIELDS_JSON='[
    {
        "name": "user.email",
        "description": "E-Mail address of the User",
        "personalData": false,
        "specialCategoryPersonalData": false,
        "legalBases": [
            {
                "reference": "GDPR1", 
                "description": ""
            }
        ]
        "legalRequirement": false,
        "contractualRegulation": false,
        "obligationToProvide": false,
        "consequences": ""
    }
]'
MAPPINGS_JSON='[
    {
        endpointId: "http:GET:user-service:/api/users"
        fields: [
            {
                field: "user.email",
                side: "SERVER",
                phase: "RESPONSE",
                namespace: "body",
                format: "json",
                path: "$.[*].email"
            }
        ]
    }
]'
```
It is also possible insert those values one by one by using:
```dotenv
MAPPINGS_MAPPINGS_0_ENDPOINT_ID='http:GET:user-service:/api/users'
MAPPINGS_MAPPINGS_0_FIELDS_0_FIELD='user.email'
MAPPINGS_MAPPINGS_0_FIELDS_0_SIDE='...'
FIELDS_FIELDS_0_NAME='user.email'
```

## Random Usage Generation

The Service features a limited Usage generation for Test data. Activate the profile `test-data` for
that.



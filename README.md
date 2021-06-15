# Property Service

> code name: `property-service`.

A micro-service for managing property info.

## Framework used

- Spring Boot 2.3
- MyBatis 3.5

## Environment (dependency)

- Java 8+ (runtime)
- MySQL 5.7+ (service = hellodb:3306)
  - database table name prefix: `ppy_`.
  - db user: `ppy_dbuser`.
  - schema files: `/db`.
- service:
  - auth-service@1.0.x (service = auth-service:8080)
  
## Development Setup

- maven

## Configuration

> this project use spring-boot based app config,
> the config file is defined in `/src/main/resources/application.yaml`

Some key configuration parameter:

- `property-service.options.enable-debug-endpoint`: if true, expose debug-use(high risk) endpoint.
- `property-service.options.accept-expired-auth-token`: if true, auth token expiry time will be ignored.
- `property-service.options.disable-permission-check`: if true, all endpoints is opened to everyone.
- `auth-service.jwt.public-key-PEM`: public key (in PEM) for verifying JWT token.

## Debugging

```
mvn spring-boot:run
```

## Building

```
mvn package
```

## Running (production)

```
java -jar target/property-service-1.0.0.jar 
```

## Entry points

> detail refer to `/doc/property-service.raml`

## Health checking

```
curl 'http://127.0.0.1:8083/api/about'
```

# Docker support

## building docker image

- after building the app (maven), run this:

```sh
docker build -t property-service:1.0 .
```

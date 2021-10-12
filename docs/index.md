## Reservation application

### Git link
```shell
git clone git@github.com:StratoxEnterprises/tyden-inovaci-workshop.git
```

### Deployment model

![Image](deployment_model.png)

**A**:
  - DEV environment
    - Kafka: **reservationdevkafka**
    - Topic: **new-reservation**
  - PROD environment
    - Kafka: **reservationprodkafka**
    - Topic: **new-reservation**

**B**:
  - DEV environment
    - Url: **https://train-details-reservation-app-dev.trials.codenow.com**
  - PROD environment
    - Url: **https://train-details-reservation-app-prod.trials.codenow.com**

### Code snippets

#### Reservation warehouse entity

```java
TBD
```

#### Reservation storage

```java
TBD
```

#### Reservation storage configuration

```yaml
TBD
```

#### Kafka topic subscriber

```java
TBD
```

#### Kafka subscriber configuration

```yaml
TBD
```

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

#### Model

##### ReservationWarehouseEntity

```java
@Entity
@Table(name = "reservation_warehouse")
public class ReservationWarehouseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservation_warehouse_id_sequence"
    )
    @SequenceGenerator(
            name = "reservation_warehouse_id_sequence",
            sequenceName = "reservation_warehouse_id_sequence",
            allocationSize = 1
    )
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String seatId;
    private String trainId;
    private String trainName;
    private Integer trainSeats;
    private Date date;

    public ReservationWarehouseEntity() {
    }

    public ReservationWarehouseEntity(String firstName, String lastName, String email, String seatId, String trainId, String trainName, Integer trainSeats, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.seatId = seatId;
        this.trainId = trainId;
        this.trainName = trainName;
        this.trainSeats = trainSeats;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public Integer getTrainSeats() {
        return trainSeats;
    }

    public void setTrainSeats(Integer trainSeats) {
        this.trainSeats = trainSeats;
    }
}

```

##### TrainDetail

```java
public class TrainDetail {

    private String name;
    private Integer seats;

    public TrainDetail() {
    }

    public TrainDetail(String name, Integer seats) {
        this.name = name;
        this.seats = seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
```

##### NewReservationDTO

```yaml
public class NewReservationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String seatId;
    private String trainId;
    private Date date;

    public NewReservationDTO() {
    }

    public NewReservationDTO(String firstName, String lastName, String email, String seatId, String trainId, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.seatId = seatId;
        this.trainId = trainId;
        this.date = date;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
```

#### Kafka integration

##### NewReservationConsumerConfig

```java
@EnableKafka
@Configuration
public class NewReservationConsumerConfig {

    @Value("${spring.kafka.reservation.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.reservation.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, NewReservationDTO> newReservationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        return new DefaultKafkaConsumerFactory<>(props,new StringDeserializer(),
                new JsonDeserializer<>(NewReservationDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, NewReservationDTO> newReservationKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, NewReservationDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(newReservationConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

}
```

##### NewReservationConsumer

```java
@Service
public class NewReservationConsumer {

    private final ReservationWarehouseService reservationWarehouseService;

    private Logger log = LoggerFactory.getLogger(NewReservationConsumer.class);

    public NewReservationConsumer(ReservationWarehouseService reservationWarehouseService) {
        this.reservationWarehouseService = reservationWarehouseService;
    }

    // listen to new messages(reservations) from kafka
    // process reservation
    @KafkaListener(topics = "${spring.kafka.reservation.topic.new-reservation}", containerFactory="newReservationKafkaListenerContainerFactory")
    public void reservationListener(@Payload NewReservationDTO newReservationDTO, Acknowledgment ack) {
        log.info("New reservation was received.");
        try {
            reservationWarehouseService.processReservation(newReservationDTO);
            ack.acknowledge();
        } catch (InterruptedException e) {
            ack.nack(1000);
        }
    }
}
```

#### Repository

##### ReservationWarehouseRepository

```java
@Repository
public interface ReservationWarehouseRepository extends CrudRepository<ReservationWarehouseEntity, Integer> {
}
```

#### Business logic implementation

##### ReservationWarehouseService

```java
public interface ReservationWarehouseService {
    void processReservation(NewReservationDTO newReservationDTO) throws InterruptedException;
}
```

##### reservationWarehouseServiceImpl

```java
@Service
public class reservationWarehouseServiceImpl implements ReservationWarehouseService {

    private Logger log = LoggerFactory.getLogger(reservationWarehouseServiceImpl.class);

    private final ReservationWarehouseRepository reservationWarehouseRepository;

    private RestTemplate restTemplate;

    @Value("${trainDetailService.url}")
    private String trainDetailServiceUrl;

    @Value("${sleepConstant}")
    private int sleepConstatnt;


    public reservationWarehouseServiceImpl(ReservationWarehouseRepository reservationWarehouseRepository){
        this.reservationWarehouseRepository = reservationWarehouseRepository;
        this.restTemplate = new RestTemplateBuilder().build();
    }

    @Override
    public void processReservation(NewReservationDTO newReservationDTO) throws InterruptedException {

        TrainDetail trainDetail = restTemplate.getForObject(trainDetailServiceUrl + "/train/{trainId}", TrainDetail.class, newReservationDTO.getTrainId());

        reservationWarehouseRepository.save(
                new ReservationWarehouseEntity(
                        newReservationDTO.getFirstName(),
                        newReservationDTO.getLastName(),
                        newReservationDTO.getEmail(),
                        newReservationDTO.getSeatId(),
                        newReservationDTO.getTrainId(),
                        trainDetail.getName(),
                        trainDetail.getSeats(),
                        newReservationDTO.getDate()
                ));

        Thread.sleep(sleepConstatnt);
    }
}
```

#### Configuration

##### application.yaml

```yaml
spring:
  ...
  kafka:
    reservation:
      bootstrap-servers: ${KAFKA_RESERVATION_BOOTSTRAP_SERVERS}
      consumer:
        group-id: omi-id
      topic:
        new-reservation: new-reservation
  datasource:
    url: ${DB_WAREHOUSE_JDBC_URL}
    username: ${DB_WAREHOUSE_USERNAME}
    password: ${DB_WAREHOUSE_PASSWORD}
    driver-class-name: org.postgresql.Driver
    validation-query: SELECT 1
    remove-abandoned: true
    log-abandoned: true
    default-auto-commit: false
    test-on-borrow: true

trainDetailService:
  url: TBD

sleepConstant: 1000
```

##### flyway.conf

```
flyway.url=${DB_WAREHOUSE_JDBC_URL}
flyway.user=${DB_WAREHOUSE_USERNAME}
flyway.password=${DB_WAREHOUSE_PASSWORD}
```

##### Flyway migration script

```sql
CREATE TABLE reservation_warehouse
(
    id            VARCHAR PRIMARY KEY,
    first_name    VARCHAR,
    last_name     VARCHAR,
    email         VARCHAR,
    seat_id       VARCHAR,
    train_id      VARCHAR,
    train_name    VARCHAR,
    train_seats   INTEGER,
    date          DATE
);

CREATE SEQUENCE reservation_warehouse_id_sequence START 1;
```

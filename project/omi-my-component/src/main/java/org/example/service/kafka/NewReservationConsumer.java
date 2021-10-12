package org.example.service.kafka;

import org.example.service.model.NewReservationDTO;
import org.example.service.service.ReservationWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

// kafka reservation consumer
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

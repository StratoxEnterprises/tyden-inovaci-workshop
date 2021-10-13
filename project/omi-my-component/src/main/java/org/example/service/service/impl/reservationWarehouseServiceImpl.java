package org.example.service.service.impl;

import org.example.service.client.model.TrainDetail;
import org.example.service.entity.ReservationWarehouseEntity;
import org.example.service.model.NewReservationDTO;
import org.example.service.repository.ReservationWarehouseRepository;
import org.example.service.service.ReservationWarehouseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        log.info("Call train detail service ...");
        TrainDetail trainDetail = restTemplate.getForObject(trainDetailServiceUrl + "/train/{trainId}", TrainDetail.class, newReservationDTO.getTrainId());

        log.info("Store warehouse data ...");
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

        log.info("Data was processed.");

        Thread.sleep(sleepConstatnt);

    }

}

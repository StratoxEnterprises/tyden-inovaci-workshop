package org.example.service.service;

import org.example.service.model.NewReservationDTO;

public interface ReservationWarehouseService {
    void processReservation(NewReservationDTO newReservationDTO) throws InterruptedException;
}

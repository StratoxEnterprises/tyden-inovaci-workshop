package org.example.service.repository;

import org.example.service.entity.ReservationWarehouseEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationWarehouseRepository extends CrudRepository<ReservationWarehouseEntity, Integer> {
}


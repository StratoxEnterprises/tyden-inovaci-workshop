package org.example.service.entity;

import javax.persistence.*;
import java.util.Date;

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

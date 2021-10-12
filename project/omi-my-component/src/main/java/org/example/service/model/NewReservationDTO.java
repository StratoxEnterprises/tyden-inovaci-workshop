package org.example.service.model;

import java.util.Date;

// template of a reservation
// POJO
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
}

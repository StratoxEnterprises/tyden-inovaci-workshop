package org.example.service.client.model;

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

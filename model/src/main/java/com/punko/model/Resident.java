package com.punko.model;

import java.time.LocalDate;

public class Resident {

    private Integer residentId;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate arrivalTime;

    private LocalDate departureTime;

    private Integer apartmentId;

    public Resident() {
    }

//constructor?

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
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

    public LocalDate getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "residentId=" + residentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                ", apartmentId=" + apartmentId +
                '}';
    }
}

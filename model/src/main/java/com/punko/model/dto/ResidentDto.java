package com.punko.model.dto;

import java.time.LocalDate;

public class ResidentDto {

    private Integer residentId;

    private String firstName;

    private String lastName;

    private String email;

    private Long avgTime;

    public ResidentDto() {
    }

    public ResidentDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

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

    public Long getAvgTime() {
        return avgTime;
    }


    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    @Override
    public String toString() {
        return "ResidentDto{" +
                "residentId=" + residentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", avgTime=" + avgTime +
                '}';
    }
}

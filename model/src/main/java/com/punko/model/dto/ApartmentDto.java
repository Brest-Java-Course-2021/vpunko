package com.punko.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApartmentDto {

    private Integer apartmentId;

    private Integer apartmentNumber;

    private String apartmentClass;

    //should be number?
    private Long avgTime;

    public ApartmentDto() {
    }

    public ApartmentDto(Integer apartmentNumber, String apartmentClass) {
        this.apartmentNumber = apartmentNumber;
        this.apartmentClass = apartmentClass;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

        public Long getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(Long avgTime) {
        this.avgTime = avgTime;
    }

    @Override
    public String toString() {
        return "ApartmentDto{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                ", avgTime=" + avgTime +
                '}';
    }
}

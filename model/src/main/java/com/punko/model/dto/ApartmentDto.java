package com.punko.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ApartmentDto {

    private Integer apartmentId;

    private Integer apartmentNumber;

    private String apartmentClass;

    //should be number?
//    private LocalDate avgTime;
//    private BigDecimal avgTime;
    private LocalDate maxDepartureTime;

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

    public LocalDate getMaxDepartureTime() {
        return maxDepartureTime;
    }

    public void setMaxDepartureTime(LocalDate maxDepartureTime) {
        this.maxDepartureTime = maxDepartureTime;
    }

    //    public BigDecimal getAvgTime() {
//        return avgTime;
//    }
//
//    public void setAvgTime(BigDecimal avgTime) {
//        this.avgTime = avgTime;
//    }

    //    public LocalDate getAvgTime() {
//        return avgTime;
//    }
//
//    public void setAvgTime(LocalDate avgTime) {
//        this.avgTime = avgTime;
//    }

    @Override
    public String toString() {
        return "ApartmentDto{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                ", avgTime=" + maxDepartureTime +
                '}';
    }
}

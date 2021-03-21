package com.punko.model;

import java.util.Objects;

public class Apartment {

    private Integer apartmentId;

    private Integer apartmentNumber;

//    private Integer amountPlaces;

    private String apartmentClass;

    public Apartment() {
    }

    public Apartment(Integer apartmentNumber, String apartmentClass) {
        this.apartmentNumber = apartmentNumber;
//        this.amountPlaces = amountPlaces;
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

//    public Integer getAmountPlaces() {
//        return amountPlaces;
//    }
//
//    public void setAmountPlaces(Integer amountPlaces) {
//        this.amountPlaces = amountPlaces;
//    }

    public String getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return Objects.equals(apartmentId, apartment.apartmentId) && Objects.equals(apartmentNumber, apartment.apartmentNumber) && Objects.equals(apartmentClass, apartment.apartmentClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apartmentId, apartmentNumber, apartmentClass);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                '}';
    }
}
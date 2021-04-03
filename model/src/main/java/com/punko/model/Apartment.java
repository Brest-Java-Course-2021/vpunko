package com.punko.model;


import javax.validation.constraints.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static com.punko.model.constants.ApartmentClassConst.*;

public class Apartment {

    private Integer apartmentId;

    @Min(value = 1, message = "Apartment number should be more than 0")
    @Max(value = 1000, message = "Apartment number should be less than 1001")
    @NotNull(message = "Apartment number is a required field")
    private Integer apartmentNumber;

    @NotBlank(message = "Apartment class is a required field")
    private String apartmentClass;

    private Map<String, String> classes;

    public Apartment() {
    }

    public Apartment(Integer apartmentNumber, String apartmentClass) {
        this.apartmentNumber = apartmentNumber;
        this.apartmentClass = apartmentClass;
        classes = new LinkedHashMap<>();
        classes.put(LUXURIOUS, "LUXURIOUS");
        classes.put(MEDIUM, "MEDIUM");
        classes.put(CHEAP, "CHEAP");
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

    public Map<String, String> getClasses() {
        return classes;
    }

    public void setClasses(Map<String, String> classes) {
        this.classes = classes;
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
package com.punko.model.dto;

public class ApartmentDto {

    private Integer apartmentId;

    private Integer apartmentNumber;

    private String apartmentClass;

    /**
     * Time between ArrivalTime and DepartureTime
     */
    private Long avgDifferenceBetweenTime;

    public ApartmentDto() {
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

    public Long getAvgDifferenceBetweenTime() {
        return avgDifferenceBetweenTime;
    }

    public void setAvgDifferenceBetweenTime(Long avgDifferenceBetweenTime) {
        this.avgDifferenceBetweenTime = avgDifferenceBetweenTime;
    }

    @Override
    public String toString() {
        return "ApartmentDto{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentClass='" + apartmentClass + '\'' +
                ", avgTime=" + avgDifferenceBetweenTime +
                '}';
    }
}

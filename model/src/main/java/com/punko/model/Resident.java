package com.punko.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.punko.model.validation.CheckDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@CheckDate(message = "Arrival time should be before than Departure time")
public class Resident {

    private Integer residentId;

    @NotBlank(message = "First name is a required field")
    @Size(min = 2, max = 20, message = "First name should be min 2, max 20 symbols")
    private String firstName;

    @NotBlank(message = "Last name is a required field")
    @Size(min = 2, max = 20, message = "Last name should be min 2, max 20 symbols")
    private String lastName;

    @NotBlank(message = "Email name is a required field")
    @Size(min = 2, max = 50, message = "Email name should be min 2, max 50 symbols")
    @Email(message = "use correct email")
    private String email;

    @NotNull(message = "arrival time is a required field")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate arrivalTime;

    @NotNull(message = "departure time is a required field")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate departureTime;

    @Min(value = 1, message = "Apartment number is a required field")
    private Integer apartmentNumber;

    public Resident() {
    }

    public Resident(String firstName, String lastName, String email, LocalDate arrivalTime, LocalDate departureTime, Integer apartmentNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.apartmentNumber = apartmentNumber;
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

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentId) {
        this.apartmentNumber = apartmentId;
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
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident resident = (Resident) o;
        return residentId.equals(resident.residentId) && firstName.equals(resident.firstName) && lastName.equals(resident.lastName) && email.equals(resident.email) && arrivalTime.equals(resident.arrivalTime) && departureTime.equals(resident.departureTime) && Objects.equals(apartmentNumber, resident.apartmentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(residentId, firstName, lastName, email, arrivalTime, departureTime, apartmentNumber);
    }
}

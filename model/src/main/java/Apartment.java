
public class Apartment {

    private Integer apartmentId;

    private Integer apartmentNumber;

    private Integer amountPlaces;

    private String apartmentClass;

    public Apartment() {
    }

    public Apartment(Integer apartmentNumber, Integer amountPlaces, String apartmentClass) {
        this.apartmentNumber = apartmentNumber;
        this.amountPlaces = amountPlaces;
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

    public Integer getAmountPlaces() {
        return amountPlaces;
    }

    public void setAmountPlaces(Integer amountPlaces) {
        this.amountPlaces = amountPlaces;
    }

    public String getApartmentClass() {
        return apartmentClass;
    }

    public void setApartmentClass(String apartmentClass) {
        this.apartmentClass = apartmentClass;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "apartmentId=" + apartmentId +
                ", apartmentNumber=" + apartmentNumber +
                ", amountPlaces=" + amountPlaces +
                ", apartmentClass='" + apartmentClass + '\'' +
                '}';
    }
}

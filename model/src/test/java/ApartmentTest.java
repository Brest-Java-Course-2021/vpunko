import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApartmentTest {

    @Test
    public void getApartmentNumberConstructor() {
        Apartment apartment = new Apartment(101, 3, "Medium");
        Assertions.assertEquals(101, apartment.getApartmentNumber());
    }

    @Test
    public void getApartmentClassSetter() {
        Apartment apartment = new Apartment();
        apartment.setApartmentClass("Luxury");
        Assertions.assertEquals("Luxury", apartment.getApartmentClass());
    }
}

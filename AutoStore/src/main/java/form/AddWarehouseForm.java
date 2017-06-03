package form;

import org.hibernate.validator.constraints.NotEmpty;


public class AddWarehouseForm {

    @NotEmpty
    private String city;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotEmpty

    private String address;
}

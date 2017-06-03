package util;

import form.AddWarehouseForm;
import model.Warehouse;


public class AddWarehouseFormToWarehouse {

    public static Warehouse transform(AddWarehouseForm form){
        Warehouse warehouse = new Warehouse();
        warehouse.setCity(form.getCity());
        warehouse.setAddress(form.getAddress());
        return warehouse;
    }
}

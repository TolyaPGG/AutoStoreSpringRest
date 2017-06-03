package service;

import form.AddWarehouseForm;
import model.Warehouse;

import java.util.List;


public interface WarehouseService {

    void saveNewWarehouse(AddWarehouseForm form);
    List<Warehouse> getAllWarehouses();
    Warehouse getOneById(long id);
}

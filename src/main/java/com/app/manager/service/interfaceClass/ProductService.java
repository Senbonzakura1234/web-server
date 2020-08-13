package com.app.manager.service.interfaceClass;

import com.app.manager.entity.Product;
import com.app.manager.model.returnResult.JsonResult;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    JsonResult add(Product product);
    JsonResult sell(String id, int amount);
}

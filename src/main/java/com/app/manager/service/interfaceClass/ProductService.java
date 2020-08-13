package com.app.manager.service.interfaceClass;

import com.app.manager.entity.Product;
import com.app.manager.model.midware_model.ListProduct;
import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.returnResult.JsonResult;

import java.util.Optional;

public interface ProductService {
    Optional<ProductModel> get(String id);
    ListProduct getAll();
    JsonResult add(Product product);
    JsonResult sell(String id, int amount);
}

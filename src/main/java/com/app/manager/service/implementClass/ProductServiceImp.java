package com.app.manager.service.implementClass;

import com.app.manager.entity.Product;
import com.app.manager.model.midware_model.ListProduct;
import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.returnResult.JsonResult;
import com.app.manager.repository.ProductRepository;
import com.app.manager.service.interfaceClass.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImp implements ProductService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    ProductRepository productRepository;

    @Override
    public Optional<ProductModel> get(String id) {
        var product = productRepository.findById(id);
        if(product.isEmpty()) return Optional.empty();
        return Optional.of(ProductModel.castToModel(product.get()));
    }

    @Override
    public ListProduct getAll() {
        var products = (List<Product>) productRepository.findAll();
        List<ProductModel> productModels = new ArrayList<>();
        for (var product: products
             ) {
            productModels.add(ProductModel.castToModel(product));
        }
        var listProduct = new ListProduct();
        listProduct.setProductModels(productModels);
        return listProduct;
    }

    @Override
    public JsonResult add(Product product) {
        try {
            productRepository.save(product);
            return new JsonResult(true, "add product success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false,
                    "add product failed. Reason: " + e.getMessage()
                    + ". Caused by: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public JsonResult sell(String id, int amount) {
        try {
            var productGet = productRepository.findById(id);
            if (productGet.isEmpty())
                return new JsonResult(false,
                        "product not found", HttpStatus.NOT_FOUND);
            var product = productGet.get();
            var current = product.getQuantity();
            if(current < amount)
                return new JsonResult(false,
                        "product not enough to sell", HttpStatus.INTERNAL_SERVER_ERROR);
            product.setQuantity(current - amount);
            productRepository.save(product);
            return new JsonResult(true, "sell success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(false,
                    "sell failed. Reason: " + e.getMessage()
                            + ". Caused by: " + e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

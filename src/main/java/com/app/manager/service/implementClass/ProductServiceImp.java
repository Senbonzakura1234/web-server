package com.app.manager.service.implementClass;

import com.app.manager.entity.Product;
import com.app.manager.model.returnResult.JsonResult;
import com.app.manager.repository.ProductRepository;
import com.app.manager.service.interfaceClass.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return (List<Product>) productRepository.findAll();
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

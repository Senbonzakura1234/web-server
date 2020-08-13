package com.app.manager.controller;

import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.midware_model.SellModel;
import com.app.manager.service.interfaceClass.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    ProductService productService;

    @GetMapping(value = {"/api/product", "/api/product/index"}, produces = "application/json")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = {"/api/product/detail"}, produces = "application/json")
    public ResponseEntity<?> get(@RequestParam(value = "id") String id){
        var product = productService.get(id);
        if(product.isEmpty()) return ResponseEntity.badRequest().body("not found");
        return ResponseEntity.ok(product.get());
    }

    @PostMapping(value = "/api/product/add",
            consumes = "application/xml", produces = "application/json")
    public ResponseEntity<?> add(@Valid @RequestBody ProductModel productModel, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        var result = productService.add(ProductModel.castToEntity(productModel));
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PostMapping(value = "/api/product/sell",
            consumes = "application/xml", produces = "application/json")
    public ResponseEntity<?> sell(@Valid @RequestBody SellModel sellModel, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        var result = productService.sell(sellModel.getId(), sellModel.getAmount());
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}

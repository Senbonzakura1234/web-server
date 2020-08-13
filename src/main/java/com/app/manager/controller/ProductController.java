package com.app.manager.controller;

import com.app.manager.model.midware_model.ProductModel;
import com.app.manager.model.midware_model.SellModel;
import com.app.manager.service.interfaceClass.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductController {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    ProductService productService;

    @GetMapping({"/api/product", "/api/product/index"})
    public ResponseEntity<?> index(){
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("/api/product/add")
    public ResponseEntity<?> add(@Valid @RequestBody ProductModel productModel, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        var result = productService.add(ProductModel.castToEntity(productModel));
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }

    @PostMapping("/api/product/sell")
    public ResponseEntity<?> sell(@Valid @RequestBody SellModel sellModel, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.badRequest().body(errors.getAllErrors());
        var result = productService.sell(sellModel.getId(), sellModel.getAmount());
        return result.isSuccess() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }
}

package com.example.lambda.controller

import com.example.lambda.service.ProductsService
import org.springframework.stereotype.Component

@Component
class ProductsController(
    private val productsService: ProductsService
) {
    fun find(id: String) = productsService.findProduct(id)
}
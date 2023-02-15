package com.example.lambda

import org.springframework.stereotype.Component

@Component
class ProductsController(
    private val productsService: ProductsService
) {
    fun find(id: String) = productsService.findProduct(id)
}
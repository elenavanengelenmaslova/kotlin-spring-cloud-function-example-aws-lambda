package com.example.lambda

import com.example.lambda.model.Product
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.Key

@Service
class ProductsService(private val productTable: DynamoDbAsyncTable<Product>) {
    fun findProduct(id: String): Product? {
        return productTable.getItem(
            Key
                .builder()
                .partitionValue(id)
                .build()
        ).get()
    }
}
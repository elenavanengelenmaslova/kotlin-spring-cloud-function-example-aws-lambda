package com.example.lambda.service

import com.example.lambda.KotlinLambdaConfiguration.Companion.table
import com.example.lambda.model.Product
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.Key

@Service
class ProductsService(){//private val productTable: DynamoDbAsyncTable<Product>) {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun findProduct(id: String): Product? {
        logger.info("about to get item with id $id")
        val item = table.getItem(
            Key
                .builder()
                .partitionValue(id)
                .build()
        ).get()
        logger.info("item retrieved!")
        return item
    }
}
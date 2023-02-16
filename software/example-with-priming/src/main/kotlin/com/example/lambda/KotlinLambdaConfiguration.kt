package com.example.lambda

import com.example.lambda.controller.ProductsController
import com.example.lambda.model.Product
import com.example.lambda.model.ProductRequest
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbClient

@Configuration
class KotlinLambdaConfiguration {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @Bean
    fun handleRequest(productsController: ProductsController): (Message<ProductRequest>) -> Product? {
        return {
            logger.info("Getting product with id ${it.payload.id}")
            productsController.find(it.payload.id)
        }
    }

    @Bean
    fun productTable(): DynamoDbTable<Product> {
        val schema = TableSchema.fromClass(Product::class.java)

        val dynamoDbAsyncClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(
                DynamoDbClient.builder()
                    .region(Region.EU_WEST_1)
                    .build()
            ).build()

        return dynamoDbAsyncClient.table(
            Product.TABLE_NAME,
            schema
        )
    }

}
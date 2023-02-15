package com.example.lambda

import nl.vintik.sample.model.Product
import nl.vintik.sample.model.ProductRequest
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

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
    fun productTable(): DynamoDbAsyncTable<Product> {
        val schema = TableSchema.fromClass(Product::class.java)

        val dynamoDbAsyncClient = DynamoDbEnhancedAsyncClient.builder()
            .dynamoDbClient(
                DynamoDbAsyncClient.builder()
                    .region(Region.EU_WEST_1)
                    .build()
            ).build()

        return dynamoDbAsyncClient.table(
            Product.TABLE_NAME,
            schema
        )
    }
}
package com.example.lambda.model

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey

@DynamoDbBean
data class Product(
    @get:DynamoDbPartitionKey
    var id: String = "",
    var name: String = "",
    var price: Float = 0.0F
) {

    companion object {
        const val TABLE_NAME = "Products-Spring-Cloud-Function-Example"
    }

}
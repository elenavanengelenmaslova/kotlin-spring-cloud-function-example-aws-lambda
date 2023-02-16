package com.example.lambda

import com.example.lambda.controller.ProductsController
import com.example.lambda.model.Product
import com.example.lambda.model.ProductRequest
import com.example.lambda.service.ProductsService
import org.crac.Context
import org.crac.Core
import org.crac.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders

@Configuration
class PrimingResource(
    private val requestHandler: (Message<ProductRequest>) -> Product?,
    private val productsController: ProductsController
) : Resource {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        Core.getGlobalContext().register(this)
    }

    override fun beforeCheckpoint(context: Context<out Resource>?) {
        logger.info("started beforeCheckpoint hook")
        kotlin.runCatching {
            productsController.find("i dont exist")
        }.onFailure { logger.error(it.message) }
//        runCatching {
//            requestHandler.invoke(object: Message<ProductRequest>{
//                override fun getPayload() = ProductRequest("1")
//                override fun getHeaders(): MessageHeaders = MessageHeaders(emptyMap())
//            })
//        }
        logger.info("finished beforeCheckpoint hook")
    }

    override fun afterRestore(context: Context<out Resource>?) {
        logger.info("afterRestore hook")
    }
}
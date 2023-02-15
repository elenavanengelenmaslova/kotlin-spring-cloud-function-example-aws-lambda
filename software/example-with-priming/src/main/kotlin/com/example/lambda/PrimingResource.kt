package com.example.lambda

import com.example.lambda.model.Product
import com.example.lambda.model.ProductRequest
import org.crac.Context
import org.crac.Core
import org.crac.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.Message
import org.springframework.messaging.MessageHeaders

@Configuration
class PrimingResource(private val requestHandler: (Message<ProductRequest>) -> Product?) : Resource {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        //register priming
        Core.getGlobalContext().register(this)
    }

    override fun beforeCheckpoint(context: Context<out Resource>?) {
        logger.info("beforeCheckpoint hook")
        runCatching {
            requestHandler.invoke(object: Message<ProductRequest>{
                override fun getPayload() = ProductRequest("i dont exist")
                override fun getHeaders(): MessageHeaders = MessageHeaders(emptyMap())
            })
        }
    }

    override fun afterRestore(context: Context<out Resource>?) {
        logger.info("afterRestore hook")
    }
}
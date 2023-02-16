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
        logger.info("start register priming")
        Core.getGlobalContext().register(this)
        logger.info("finished register priming")
    }

    override fun beforeCheckpoint(context: Context<out Resource>?) {
        logger.info("beforeCheckpoint hook")
        runCatching {
            requestHandler.invoke(object: Message<ProductRequest>{
                override fun getPayload() = ProductRequest("i dont exist")
                override fun getHeaders(): MessageHeaders = MessageHeaders(emptyMap())
            })
        }
        logger.info("finished beforeCheckpoint hook")
    }

    override fun afterRestore(context: Context<out Resource>?) {
        logger.info("afterRestore hook")
        runCatching {
            requestHandler.invoke(object: Message<ProductRequest>{
                override fun getPayload() = ProductRequest("i dont exist")
                override fun getHeaders(): MessageHeaders = MessageHeaders(emptyMap())
            })
        }
        logger.info("finished afterRestore hook")
    }
}
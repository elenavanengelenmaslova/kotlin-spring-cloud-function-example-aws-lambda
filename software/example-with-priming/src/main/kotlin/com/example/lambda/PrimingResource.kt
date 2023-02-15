package com.example.lambda

import com.example.lambda.controller.ProductsController
import org.crac.Context
import org.crac.Core
import org.crac.Resource
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration

@Configuration
class PrimingResource(private val productsController: ProductsController) : Resource {
    private val logger = LoggerFactory.getLogger(this::class.java)

    init {
        //register priming
        Core.getGlobalContext().register(this)
    }

    override fun beforeCheckpoint(context: Context<out Resource>?) {
        logger.info("beforeCheckpoint hook")
        runCatching {
            productsController.find("i dont exist")
        }
    }

    override fun afterRestore(context: Context<out Resource>?) {
        logger.info("afterRestore hook")
    }
}
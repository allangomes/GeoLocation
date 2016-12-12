package com.allangomes.routes.config

import com.allangomes.routes.route.Route
//import com.allangomes.routes.route.internal.DefaultRouteRepository
//import com.allangomes.routes.route.internal.RouteRepository
import com.allangomes.routes.services.IServiceMap
import com.allangomes.routes.services.google.GoogleService
import com.google.gson.GsonBuilder
import com.google.maps.GeoApiContext
import org.springframework.beans.factory.BeanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository
import org.springframework.data.mongodb.core.convert.*
import org.springframework.data.mongodb.core.mapping.MongoMappingContext
import java.io.Serializable

/**
* Created by allangomes on 08/12/16.
*/

@Configuration
open class AppConfig {
    @Bean
    open fun mappingMongoConverter(factory: MongoDbFactory, context: MongoMappingContext, beanFactory: BeanFactory): MappingMongoConverter {
        val mappingConverter = MappingMongoConverter(DefaultDbRefResolver(factory), context)
        mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions::class.java))
        mappingConverter.typeMapper = DefaultMongoTypeMapper(null)
        return mappingConverter
    }

    @Bean
    open fun gsonBuilder(): GsonBuilder {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    }

    @Bean
    open fun geoApiContext(): GeoApiContext {
        return GeoApiContext().setApiKey("AIzaSyD3SzSDh5H50qaw4WcvoDUh591Yghg5Fzs")
    }

    @Bean
    open fun serviceMap(geoApiContext: GeoApiContext): IServiceMap {
        return GoogleService(geoApiContext)
    }

//    @Bean
//    open fun <T, ID: Serializable> repositories(): MongoRepository<T, ID> {
//        return SimpleMongoRepository<T, ID>(null, null)
//    }
//
//    @Bean
//    open fun routeRepository(mongoOperations: MongoOperations, base: MongoRepository<Route, String>): RouteRepository {
//        return DefaultRouteRepository(mongoOperations, base)
//    }

}
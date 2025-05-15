package org.prography.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

@Configuration
class ScrapThreadPoolConfig {
    @Bean(name = ["mainScrapExecutor"])
    fun mainScrapExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 3
        executor.maxPoolSize = 3
        executor.queueCapacity = 10000
        executor.setThreadNamePrefix("main-feign-exec-")
        return executor
    }

    @Bean(name = ["naverScrapExecutor"])
    fun naverScrapExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 1
        executor.maxPoolSize = 1
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("naver-feign-exec-")
        return executor
    }

    @Bean(name = ["kakaoScrapExecutor"])
    fun kakaoScrapExecutor(): ThreadPoolTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 3
        executor.maxPoolSize = 3
        executor.queueCapacity = 100
        executor.setThreadNamePrefix("kakao-feign-exec-")
        return executor
    }

    @Bean(name = ["callbackExecutor"])
    fun callbackExecutor(): ThreadPoolTaskExecutor =
        ThreadPoolTaskExecutor().apply {
            corePoolSize = 2
            maxPoolSize = 5
            queueCapacity = 500
            setThreadNamePrefix("callback-exec-")
            initialize()
        }
}

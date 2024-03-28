/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.nacos.property.refresher.examples.controller;

import io.github.photowey.nacos.property.refresher.annotation.NacosDynamicRefreshScope;
import io.github.photowey.nacos.property.refresher.examples.core.domain.dto.DynamicValuesDTO;
import io.github.photowey.nacos.property.refresher.examples.core.domain.result.ApiResult;
import io.github.photowey.nacos.property.refresher.examples.property.AppProperties;
import io.github.photowey.nacos.property.refresher.examples.property.HelloProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code ScopeApiController}
 *
 * @author photowey
 * @date 2024/03/29
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/scope")
@NacosDynamicRefreshScope
public class ScopeApiController {

    public static String DYNAMIC_KEY = "io.github.photowey.dynamic.property.cache.loader";
    public static String STATIC_KEY = "io.github.photowey.static.property.cache.loader";

    @Autowired
    private Environment environment;

    @Value("${io.github.photowey.dynamic.property.cache.loader}")
    private String dynamicLoader;

    @Value("${io.github.photowey.static.property.cache.loader}")
    private String staticLoader;

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private HelloProperties helloProperties;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * http://localhost:9527/api/v1/scope/static/get/dataid/dev
     *
     * @return {@link DynamicValuesDTO}
     */
    @GetMapping("/static/get/dataid/dev")
    public ApiResult<DynamicValuesDTO> dev() {
        DynamicValuesDTO dto = DynamicValuesDTO.builder()
                .value(this.staticLoader)
                .environment(this.tryAcquireLoaderFromEnvironment(STATIC_KEY))
                .property(this.helloProperties.getCache().getLoader())
                .ctxProperty(this.applicationContext.getBean(HelloProperties.class).getCache().getLoader())
                .build();

        return ApiResult.ok(dto);
    }

    /**
     * http://localhost:9527/api/v1/scope/dynamic/get/dataid/app
     *
     * @return {@link DynamicValuesDTO}
     */
    @GetMapping("/dynamic/get/dataid/app")
    public ApiResult<DynamicValuesDTO> app() {
        DynamicValuesDTO dto = DynamicValuesDTO.builder()
                .value(this.dynamicLoader)
                .environment(this.tryAcquireLoaderFromEnvironment(DYNAMIC_KEY))
                .property(this.appProperties.getCache().getLoader())
                .ctxProperty(this.applicationContext.getBean(AppProperties.class).getCache().getLoader())
                .build();

        return ApiResult.ok(dto);
    }

    private String tryAcquireLoaderFromEnvironment(String key) {
        return this.environment.getProperty(key);
    }
}
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
package io.github.photowey.nacos.property.refresher.examples.listener;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.shaded.com.google.common.collect.Lists;
import io.github.photowey.nacos.property.refresher.listener.AbstractNacosDynamicRefreshListener;

import java.util.Collection;
import java.util.List;

/**
 * {@code HelloDynamicNacosConfigListener}
 *
 * @author photowey
 * @date 2024/03/29
 * @since 1.0.0
 */
public class HelloDynamicNacosConfigListener extends AbstractNacosDynamicRefreshListener {

    private static final List<String> DYNAMIC_DATA_IDS = Lists.newArrayList(
            "{}-app"
    );

    @Override
    public void registerListener(Collection<ConfigService> configServices) {
        for (ConfigService configService : configServices) {
            DYNAMIC_DATA_IDS.forEach(dataIdTemplate -> this.addTemplateListener(configService, dataIdTemplate));
        }
    }
}

/*
 * Copyright 2016, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.querydsl.codegen;

import com.querydsl.codegen.utils.StringUtils;

import javax.lang.model.SourceVersion;
import java.util.function.Function;

/**
 * Default variable name generation strategy which un-capitalizes the first letter of the class name.
 *
 */
public final class DefaultVariableNameFunction implements Function<EntityType, String> {

    public static final DefaultVariableNameFunction INSTANCE = new DefaultVariableNameFunction();

    @Override
    public String apply(EntityType entity) {
        String simpleName = entity.getInnerType().getSimpleName();
        if (simpleName.contains(".")) {
            simpleName = simpleName.substring(simpleName.indexOf(".") + 1);
        }
        String uncapSimpleName = StringUtils.uncapitalize(simpleName);
        if (SourceVersion.isKeyword(uncapSimpleName)) {
            uncapSimpleName = uncapSimpleName + "$";
        }
        return uncapSimpleName;
    }
}

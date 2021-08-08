/*
 * Copyright 2015, The Querydsl Team (http://www.querydsl.com/team)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.querydsl.apt;

import com.querydsl.codegen.*;
import com.querydsl.codegen.utils.CodeWriter;
import com.querydsl.codegen.utils.model.SimpleType;
import com.querydsl.codegen.utils.model.Type;
import com.querydsl.codegen.utils.model.Types;
import com.querydsl.core.types.NamePath;

import javax.inject.Inject;
import java.io.IOException;

import static com.querydsl.codegen.utils.Symbols.NEW;


public class DefaultNameClassSerializer implements NameClassSerializer {

    private final TypeMappings typeMappings;

    @Inject
    public DefaultNameClassSerializer(TypeMappings typeMappings) {
        this.typeMappings = typeMappings;
    }

    @Override
    public void serialize(EntityType model, SerializerConfig config, CodeWriter writer) throws IOException {
        intro(model, config, writer);

        // properties
        serializeProperties(model, config, writer);

        outro(model, writer);
    }

    protected void intro(EntityType model, SerializerConfig config,
                         CodeWriter writer) throws IOException {
        introPackage(writer, model);

        writer.nl();

        introJavadoc(writer, model);
        introClassHeader(writer, model);

        if (config.createDefaultVariable()) {
            introDefaultInstance(writer, model, config.defaultVariableName());
        }
    }

    protected void introPackage(CodeWriter writer, EntityType model) throws IOException {
        Type queryType = getNameClassType(model);
        if (!queryType.getPackageName().isEmpty()) {
            writer.packageDecl(queryType.getPackageName());
        }
    }

    public SimpleType getPropertyType(Property property) {
        Type type = property.getType();
        if (type == null) {
            return null;
        }
        else {
            return getNameClassType(property.getType());
        }
    }

    public SimpleType getNameClassType(Type model) {
        return (SimpleType) typeMappings.getPathType(model, null, true);
    }

    /**
     * The nameClass extend {@link NamePath}
     * @param model
     * @return
     */
    public SimpleType getNamePathType(EntityType model) {
        Class<NamePath> clzz = NamePath.class;
        return new SimpleType(
                clzz.getName(),
                clzz.getPackage().getName(),
                clzz.getSimpleName(),
                model
        );
    }

    protected void introJavadoc(CodeWriter writer, EntityType model) throws IOException {
        Type queryType = getNameClassType(model);
        writer.javadoc(queryType.getSimpleName() + " is a name class for " + model.getSimpleName());
    }

    protected void introClassHeader(CodeWriter writer, EntityType model) throws IOException {
        Type queryType = getNameClassType(model);

        writer.beginClass(queryType, getNamePathType(model));

        long serialVersionUID = model.getFullName().hashCode();
        writer.privateStaticFinal(Types.LONG_P, "serialVersionUID", serialVersionUID + "L");
    }

    protected void introDefaultInstance(CodeWriter writer, EntityType model, String defaultName) throws IOException {
        String simpleName = !defaultName.isEmpty() ? defaultName : model.getModifiedSimpleName();
        Type queryType = getNameClassType(model);
        writer.publicStaticFinal(queryType, simpleName, NEW + queryType.getSimpleName() + "(null, null)");
    }

    protected void serializeProperties(EntityType model,  SerializerConfig config,
                                       CodeWriter writer) throws IOException {
        for (Property property : model.getProperties()) {
            customField(model, property, config, writer);
        }
    }

    protected void customField(EntityType model, Property field, SerializerConfig config,
                               CodeWriter writer) throws IOException {
        Type queryType = getPropertyType(field);

        if (queryType == null) {
            String value = "buildPath(\"" + field.getInits().get(0) + "\")";
            writer.publicFinal(
                    new SimpleType("String"),
                    field.getEscapedName(), value);
        }
        else {
            String value = NEW + writer.getRawName(queryType) + "(\"" + field.getInits().get(0) + "\", this)";
            writer.publicFinal(queryType, field.getEscapedName(), value);
        }
    }

    protected void outro(EntityType model, CodeWriter writer) throws IOException {
        writer.end();
    }
}

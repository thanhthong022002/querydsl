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
package com.querydsl.maven;

import com.querydsl.codegen.AnnotationHelper;
import com.querydsl.codegen.GenericExporter;
import com.querydsl.codegen.PropertyHandling;
import com.querydsl.codegen.TypeMappings;

import javax.persistence.*;
import java.lang.reflect.InvocationTargetException;

/**
 * {@code JPAExporterMojo} calls {@link GenericExporter} using the classpath of the module
 *
 * @goal jpa-export
 * @requiresDependencyResolution test
 * @author tiwe
 */
public class JPAExporterMojo extends AbstractExporterMojo {

    /**
     * annotation helpers for exporter
     *
     * @parameter
     */
    private String[] annotationHelpers;
    /**
     * custom typeMappings class
     *
     * @parameter
     */
    private String typeMappingsClass;

    @Override
    protected void configure(GenericExporter exporter) {
        super.configure(exporter);
        exporter.setEmbeddableAnnotation(Embeddable.class);
        exporter.setEmbeddedAnnotation(Embedded.class);
        exporter.setEntityAnnotation(Entity.class);
        exporter.setSkipAnnotation(Transient.class);
        exporter.setSupertypeAnnotation(MappedSuperclass.class);
        exporter.setPropertyHandling(PropertyHandling.JPA);

        // AnnotationHelpers to process specific JPA annotations
        exporter.addAnnotationHelper(JPATemporalAnnotationHelper.INSTANCE);

        if (this.typeMappingsClass != null) {
            try {
                exporter.setTypeMappingsClass(
                        (Class<? extends TypeMappings>) Class.forName(typeMappingsClass)
                );
            } catch (ClassNotFoundException e) {
                getLog()
                        .error("Unknown typeMappings: " + typeMappingsClass);
            }
        }

        // add custom annotation helper
        if (annotationHelpers != null && annotationHelpers.length > 0) {
            for (String annotationHelper : annotationHelpers) {
                try {
                    Class<?> clazz = Class.forName(annotationHelper);
                    if (AnnotationHelper.class.isAssignableFrom(clazz)) {
                        AnnotationHelper obj = (AnnotationHelper) clazz.getDeclaredConstructor().newInstance();
                        exporter.addAnnotationHelper(obj);
                        getLog().info("Register annotation helper: " + annotationHelper);
                    } else {
                        getLog().error("Clazz " + annotationHelper +
                                " is not a AnnotationHelper");
                    }
                } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    getLog().error("Can't init helper: " + e.getMessage());
                    return;
                }
            }
        }
    }

    public void setAnnotationHelpers(String[] annotationHelpers) {
        this.annotationHelpers = annotationHelpers;
    }

    public void setTypeMappingsClass(String typeMappingsClass) {
        this.typeMappingsClass = typeMappingsClass;
    }
}

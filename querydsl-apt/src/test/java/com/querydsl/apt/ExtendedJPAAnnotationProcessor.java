package com.querydsl.apt;

import com.querydsl.apt.jpa.JPAAnnotationProcessor;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.TypeMappings;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.persistence.*;
import java.lang.annotation.Annotation;

@SupportedAnnotationTypes({"com.querydsl.core.annotations.*", "javax.persistence.*"})
public class ExtendedJPAAnnotationProcessor extends JPAAnnotationProcessor {

    @Override
    protected Configuration createConfiguration(RoundEnvironment roundEnv) {
        Class<? extends Annotation> entity = Entity.class;
        Class<? extends Annotation> superType = MappedSuperclass.class;
        Class<? extends Annotation> embeddable = Embeddable.class;
        Class<? extends Annotation> embedded = Embedded.class;
        Class<? extends Annotation> skip = Transient.class;
        CodegenModule codegenModule = new CodegenModule();
        codegenModule.bind(TypeMappings.class, HibernateTypeMappings.class);
        return new ExtendedJPAConfiguration(roundEnv, this.processingEnv, entity, superType, embeddable, embedded, skip, codegenModule);
    }
}

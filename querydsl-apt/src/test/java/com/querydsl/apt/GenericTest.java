package com.querydsl.apt;

import com.querydsl.apt.jpa.JPAAnnotationProcessor;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GenericTest extends AbstractProcessorTest {

    @Test
    public void test() throws IOException {
        List<String> classes = Collections.singletonList("src/test/java/com/querydsl/apt/domain/Generic7Test.java");
        process(QuerydslAnnotationProcessor.class, classes,"GenericTest");
    }

    @Test
    public void test2() throws IOException {
        List<String> classes = Collections.singletonList("src/test/java/com/querydsl/apt/domain/Generic9Test.java");
        process(JPAAnnotationProcessor.class, classes,"GenericTest2");
    }

    @Test
    public void test3() throws IOException {
        List<String> classes = Collections.singletonList(
//                "src/test/java/com/querydsl/apt/domain/type/BrandContact.java"
                "src/test/java/com/querydsl/apt/domain/type/Action.java"
        );
        process(ExtendedJPAAnnotationProcessor.class, classes,"GenericTest3");
    }

}

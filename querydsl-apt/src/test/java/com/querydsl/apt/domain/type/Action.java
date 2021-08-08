package com.querydsl.apt.domain.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.NameClass;

@NameClass
public class Action {

    @JsonProperty("your_name")
    private String name;

    private ActionDetail1 detail1;

    @JsonProperty("detail_2")
    private ActionDetail2 detail2;

    @JsonProperty("detail_3")
    private ActionDetail3 detail3;

    @NameClass
    public static class ActionDetail1 {

        @JsonProperty("detail_attr_1")
        private String detail1;
    }

    @NameClass
    public static final class ActionDetail3 extends ActionDetail1 {

        private String detail3;
    }

}

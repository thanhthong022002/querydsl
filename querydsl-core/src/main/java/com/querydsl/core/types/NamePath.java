package com.querydsl.core.types;

public class NamePath<T> {

    private final String name;
    private final NamePath<?> parent;

    public NamePath(String name, NamePath<?> parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public NamePath getParent() {
        return parent;
    }

    public String path() {
        return parent == null ? name : parent.path() + "." + name;
    }

    public String buildPath(String leaf) {
        String path = path();
        return path == null ? leaf : path + "." + leaf;
    }

    @Override
    public String toString() {
        return path();
    }
}

package com.codercise.list.core;

public interface List<D> {

    void append(D data);

    void insertFirst(D data);

    void insert(Long pos, D data);

    D delete(Long pos);

    Long delete(D data);

    D deleteFirst();

    D deleteLast();

    D get(Long pos);

    Long search(D data);

    void set(Long pos, D data);

    boolean isEmpty();

    Long size();

    default void validatePosition(Long pos) {
        if (pos == null || pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException(
                    String.format("Position %s is out of bounds for list size %s", pos, size()));
        }
    }
}

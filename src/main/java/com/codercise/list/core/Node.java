package com.codercise.list.core;

import lombok.Data;
import lombok.NonNull;

@Data
class Node<D extends Comparable<D>> {
    D data;
    Node<D> next;

    public Node(@NonNull D data, @NonNull Node<D> next) {
        this.data = data;
        this.next = next;
    }

    public Node(@NonNull D data) {
        this.data = data;
    }
}
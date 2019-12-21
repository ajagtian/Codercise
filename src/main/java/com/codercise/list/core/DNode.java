package com.codercise.list.core;

import lombok.Data;
import lombok.NonNull;

@Data
public class DNode<D> {
    D data;
    DNode<D> left;
    DNode<D> right;

    public DNode(@NonNull D data, @NonNull DNode<D> left, @NonNull DNode<D> right) {
        this.data = data;
        this.left = left;
        this.right = right;
    }

    public DNode(@NonNull D data) {
        this.data = data;
    }
}

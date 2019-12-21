package com.codercise.list.core;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = PRIVATE)
public class DLinkedList<D> implements List<D> {

    DNode<D> head;
    DNode<D> tail;
    Long size;

    public DLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0L;
    }

    /**
     * O(1)
     */
    @Override
    public void append(D data) {

        DNode<D> nodeToAppend = new DNode<>(data);

        if (tail != null) {
            nodeToAppend.setLeft(tail);
            tail.setRight(nodeToAppend);
        }

        tail = nodeToAppend;

        if (head == null) {
            head = nodeToAppend;
        }
        size++;
    }

    /**
     * O(1)
     */
    @Override
    public void insertFirst(D data) {

        DNode<D> nodeToInsert = new DNode<>(data);

        if (head != null) {
            nodeToInsert.setRight(head);
            head.setLeft(nodeToInsert);
        }

        head = nodeToInsert;

        if (tail == null) {
            tail = nodeToInsert;
        }

        size++;
    }

    /**
     * O(n/2)
     */
    @Override
    public void insert(Long pos, D data) {

        if (pos == 0) {
            insertFirst(data);
            return;
        }

        validatePosition(pos);

        DNode<D> nodeToInsert = new DNode<>(data);
        DNode<D> insertAt = getNode(pos);

        nodeToInsert.setLeft(insertAt.getLeft());
        insertAt.getLeft().setRight(nodeToInsert);

        nodeToInsert.setRight(insertAt);
        insertAt.setLeft(nodeToInsert);
        size++;
    }

    /**
     * O(n/2)
     */
    @Override
    public D delete(Long pos) {
        validatePosition(pos);

        if (pos == 0) {
            return deleteFirst();
        }

        if (pos == size - 1) {
            return deleteLast();
        }

        DNode<D> nodeToDelete = getNode(pos);

        nodeToDelete.getLeft().setRight(nodeToDelete.getRight());
        nodeToDelete.getRight().setLeft(nodeToDelete.getLeft());
        nodeToDelete.setLeft(null);
        nodeToDelete.setRight(null);

        size--;
        return nodeToDelete.getData();
    }

    public Long delete(D data) {

        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        Long pos = search(data);

        if (pos >= size) {
            throw new IndexOutOfBoundsException("Not found");
        } else {
            delete(pos);
        }

        return pos;
    }

    public Long search(D data) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to search");
        }

        DNode<D> currentNode = head;
        Long pos = 0L;

        while (currentNode != null && !currentNode.getData().equals(data)) {
            currentNode = currentNode.getRight();
            pos++;
        }

        return pos;
    }

    /**
     * O(1)
     */
    @Override
    public D deleteFirst() {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        DNode<D> nodeToDelete = head;

        head = head.getRight();

        if (head != null) {
            head.setLeft(null);
        }

        if (nodeToDelete == tail) {
            tail = tail.getLeft();
        }

        nodeToDelete.setRight(null);
        size--;
        return nodeToDelete.getData();
    }

    /**
     * O(1)
     */
    @Override
    public D deleteLast() {

        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        DNode<D> nodeToDelete = tail;

        tail = tail.getLeft();

        if (tail != null) {
            tail.setRight(null);
        }

        if (nodeToDelete == head) {
            head = head.getRight();
        }

        nodeToDelete.setLeft(null);
        size--;
        return nodeToDelete.getData();
    }

    /**
     * O(n/2)
     */
    @Override
    public D get(Long pos) {
        validatePosition(pos);
        return getNode(pos).getData();
    }

    /**
     * O(n/2)
     */
    @Override
    public void set(Long pos, D data) {
        validatePosition(pos);
        getNode(pos).setData(data);
    }

    /**
     * O(1)
     */
    @Override
    public boolean isEmpty() {
        return size == null || size == 0;
    }

    /**
     * O(n/2)
     */
    private DNode<D> getNode(Long pos) {
        validatePosition(pos);

        DNode<D> currentNode;
        long i;
        if (pos < size - pos - 1) {
            currentNode = head();
            i = 0;
            while (i != pos) {
                currentNode = currentNode.getRight();
                ++i;
            }
        } else {
            currentNode = tail();
            i = size - 1;
            while (i != pos) {
                currentNode = currentNode.getLeft();
                --i;
            }
        }
        return currentNode;
    }

    /**
     * O(n)
     */
    @Override
    public String toString() {
        return String.format("%s%n%s", toStringLtr(), toStringRtl());
    }

    private String toStringLtr() {
        StringBuilder listAsString = new StringBuilder();
        listAsString.append("<")
                .append(size())
                .append(">")
                .append("{")
                .append(Optional.ofNullable(head()).map(DNode::getData).orElse(null))
                .append("}")
                .append("[ ");
        DNode<D> currentNode = head();
        while (currentNode != null) {
            listAsString.append(currentNode.getData());
            if (currentNode.getRight() != null) {
                listAsString.append(" -> ");
            }
            currentNode = currentNode.getRight();
        }
        listAsString.append(" ]")
                .append("{")
                .append(Optional.ofNullable(tail()).map(DNode::getData).orElse(null))
                .append("}");
        return listAsString.toString();
    }

    private String toStringRtl() {
        StringBuilder listAsString = new StringBuilder();
        listAsString.append("<")
                .append(size())
                .append(">")
                .append("{")
                .append(Optional.ofNullable(tail()).map(DNode::getData).orElse(null))
                .append("}")
                .append("[ ");
        DNode<D> currentNode = tail();
        while (currentNode != null) {
            listAsString.append(currentNode.getData());
            if (currentNode.getLeft() != null) {
                listAsString.append(" -> ");
            }
            currentNode = currentNode.getLeft();
        }
        listAsString.append(" ]")
                .append("{")
                .append(Optional.ofNullable(head()).map(DNode::getData).orElse(null))
                .append("}");
        return listAsString.toString();
    }
}

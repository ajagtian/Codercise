package com.codercise.list.core;

import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Accessors(fluent = true)
@FieldDefaults(level = PRIVATE)
public class LinkedList<D extends Comparable<D>> implements List<D> {

    Node<D> head;
    Node<D> tail;
    Long size;

    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0L;
    }

    /**
     * O(1)
     */
    @Override
    public void append(D data) {
        Node<D> nodeToAppend = new Node<>(data);

        if (isEmpty()) {
            head = nodeToAppend;
        } else {
            tail.setNext(nodeToAppend);
        }
        tail = nodeToAppend;
        size++;
    }

    /**
     * O(1)
     */
    @Override
    public void insertFirst(D data) {
        Node<D> nodeToInsert = new Node<>(data);

        if (isEmpty()) {
            tail = nodeToInsert;
        }

        nodeToInsert.setNext(head);
        head = nodeToInsert;

        size++;
    }

    /**
     * O(n)
     */
    @Override
    public void insert(Long pos, D data) {

        if (pos == 0) {
            insertFirst(data);
            return;
        }

        validatePosition(pos);

        Node<D> nodeToInsert = new Node<>(data);
        Node<D> insertAfter = getNode(pos - 1);
        nodeToInsert.setNext(insertAfter.getNext());
        insertAfter.setNext(nodeToInsert);

        size++;
    }

    /**
     * O(n)
     */
    @Override
    public void set(Long pos, D data) {
        validatePosition(pos);

        Node<D> nodeToMutate = getNode(pos);
        nodeToMutate.setData(data);
    }

    /**
     * O(n)
     */
    @Override
    public D get(Long pos) {
        validatePosition(pos);
        return getNode(pos).getData();
    }

    /**
     * O(1)
     */
    @Override
    public D deleteFirst() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        Node<D> nodeToDelete = head;
        head = head.getNext();
        nodeToDelete.setNext(null);

        size--;

        if (isEmpty()) {
            tail = null;
        }
        return nodeToDelete.getData();
    }

    /**
     * O(n)
     */
    @Override
    public D deleteLast() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        if (size == 1) {
            deleteFirst();
        }

        Node<D> deleteAfter = getNode(size - 2);
        Node<D> nodeToDelete = tail;
        deleteAfter.setNext(null);
        tail = deleteAfter;

        size--;
        return nodeToDelete.getData();
    }

    /**
     * O(n)
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

        Node<D> deleteAfter = getNode(pos - 1);
        Node<D> nodeToDelete = deleteAfter.getNext();

        deleteAfter.setNext(nodeToDelete.getNext());
        nodeToDelete.setNext(null);
        size--;
        return nodeToDelete.getData();
    }

    public Long delete(D data) {

        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to delete");
        }

        Long position = search(data);
        if (position >= size) {
            throw new IndexOutOfBoundsException("Not found");
        } else {
            delete(position);
        }
        return position;
    }

    public Long search(D data) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Nothing to search");
        }

        Node<D> currentNode = head;
        long pos = 0L;

        while (currentNode != null && !currentNode.getData().equals(data)) {
            currentNode = currentNode.getNext();
            pos++;
        }

        return pos;
    }

    /**
     * O(1)
     */
    public boolean isEmpty() {
        return size == null || size == 0;
    }

    /**
     * O(n)
     */
    @Override
    public String toString() {
        StringBuilder listAsString = new StringBuilder();
        listAsString.append("<")
                .append(size())
                .append(">")
                .append("{")
                .append(Optional.ofNullable(head()).map(Node::getData).orElse(null))
                .append("}")
                .append("[ ");
        Node<D> currentNode = head();
        while (currentNode != null) {
            listAsString.append(currentNode.getData());
            if (currentNode.getNext() != null) {
                listAsString.append(" -> ");
            }
            currentNode = currentNode.getNext();
        }
        listAsString.append(" ]")
                .append("{")
                .append(Optional.ofNullable(tail()).map(Node::getData).orElse(null))
                .append("}");
        return listAsString.toString();
    }

    /**
     * O(n)
     */
    private Node<D> getNode(Long pos) {
        validatePosition(pos);

        Node<D> currentNode = head();
        int i = 0;
        while (i != pos) {
            currentNode = currentNode.getNext();
            ++i;
        }

        return currentNode;
    }
}

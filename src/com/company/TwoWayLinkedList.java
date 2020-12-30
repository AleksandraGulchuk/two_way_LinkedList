package com.company;

import java.util.Iterator;
import java.util.function.Consumer;

public class TwoWayLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;


    public int size() {
        return size;
    }

    public void add(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        ++size;
    }

    public T get(int index) {
        chekIndex(index);
        Node<T> current = getNodeByIndex(index);
        return current.value;
    }

    public void set(int index, T value) {
        chekIndex(index);
        Node<T> current = getNodeByIndex(index);
        current.value = value;
    }

    public boolean contains(T value) {
        if (size == 0) return false;
        for (T element : this) {
            if (element.equals(value)) return true;
        }
        return false;
    }

    public boolean remove(T value) {
        if (size == 0) return false;
        for (Node<T> current = head; current != null; current = current.next) {
            if (current.value.equals(value)) {
                if (current.prev == null) {
                    current.next.prev = null;
                    head = current.next;
                } else if (current.next == null) {
                    current.prev.next = null;
                    tail = current.prev;
                } else {
                    current.next.prev = current.prev;
                    current.prev.next = current.next;
                }
                --size;
                return true;
            }
        }
        return false;
    }

    public T remove(int index) {
        if (size == 0) return null;
        Node<T> current = getNodeByIndex(index);
        if (index == 0) {
            current.next.prev = null;
            head = current.next;
        } else if (index == size - 1) {
            current.prev.next = null;
            tail = current.prev;
        } else {
            current.next.prev = current.prev;
            current.prev.next = current.next;
        }
        --size;
        return current.value;
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        for (Node<T> current = head; current != null; current = current.next) {
            consumer.accept(current.value);
        }
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            TwoWayLinkedList.Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.next;
                return value;
            }
        };
    }

    public Iterator<T> reversIterator() {
        return new Iterator<T>() {
            TwoWayLinkedList.Node<T> current = tail;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.value;
                current = current.prev;
                return value;
            }
        };
    }

    private Node<T> getNodeByIndex(int index) {
        chekIndex(index);
        Node<T> current;
        if (index <= size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private void chekIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private static class Node<T> {
        T value;
        Node<T> next;
        Node<T> prev;

        public Node(T value) {
            this.value = value;
        }
    }

}

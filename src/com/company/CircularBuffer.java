package com.company;

import java.util.Iterator;

/**
 * Коллекция, реализующая FIFO и циклический буфер.
 *
 * @param <E> тип элементов
 */
public class CircularBuffer<E> implements ICircularBuffer<E> {
    private int size = 0, head;
    private final int capacity;
    private final E[] arr;

    public CircularBuffer(int capacity) {
        arr = (E[]) new Object[capacity];
        head = capacity / 2;
        this.capacity = capacity;
    }

    /**
     * Возвращает количество элементов в буфере.
     *
     * @return размер
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Возвращает вместимость буфера.
     *
     * @return вместимость
     */
    public int getCapacity() { return capacity; }

    /**
     * Проверяет, заполнен ли буфер
     *
     * @return  true, если буфер полностью заполнен
     *          false в противном случае
     */
    public boolean isFull() {
        return size == capacity;
    }

    /**
     * Проверяет, пуст ли буфер
     *
     * @return  true, если буфер пуст
     *          false в противном случае
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Добавляет элемент в конец буфера
     * Затирает начало буфера в случае, если он заполнен
     *
     * @param item элемент, который нужно добавить в конец буфера
     */
    @Override
    public void add(E item) {
        if (isFull()) {
            arr[head] = item;
            head = (head + 1) % capacity;
            return;
        }

        arr[(head + size++) % capacity] = item;
    }

    /**
     * Возвращает и удаляет элемент из начала буфера
     *
     * @return элемент или {@code null}, если буфер пуст
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            return null;
        }

        E resElem = arr[head];
        head = (head + 1) % capacity;
        --size;
        return resElem;
    }

    /**
     * Возвращает (но не удаляет) элемент из начала буфера
     *
     * @return Элемент или {@code null}, если буфер пуст
     */
    @Override
    public E peek() {
        return isEmpty() ? null : arr[head];
    }

    /**
     * Возвращает строку с внутренним представлением циклического буфера в виде массива
     *
     * Часть буфера                                    Символ
     * Начало буфера (head)                             '['
     * Конец буфера ((head + size - 1) % capacity)      ']'
     * Незаполненные ячейки массива                     '-'
     *
     * @return Элемент или {@code null}, если буфер пуст
     */
    public String toStringShowingStructure() {
        if (isEmpty()) {
            return "Циклический буфер пуст";
        }

        StringBuilder sB = new StringBuilder();
        int tail = (head + size - 1) % capacity;

        if (head <= tail) {
            sB.append(" - ".repeat(Math.max(0, head))).append("[ ");

            for (int i = head; i <= tail; ++i) {
                sB.append(arr[i]).append(" ");
            }

            sB.append("]").append(" - ".repeat(Math.max(0, capacity - (tail + 1))));
        } else {
            for (int i = 0; i <= tail; ++i) {
                sB.append(" ").append(arr[i]);
            }

            sB.append(" ]").append(" - ".repeat(Math.max(0, head - (tail + 1)))).append("[ ");

            for (int i = head; i < capacity; ++i) {
                sB.append(arr[i]).append(" ");
            }
        }

        return sB.toString();
    }

    /**
     * Реализация интерфейса Iterable<E>
     *
     * @return  итератор для возможности использования буфера в for-each циклах
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int _curOffset = 0;

            @Override
            public boolean hasNext() {
                return _curOffset < size;
            }

            @Override
            public E next() {
                return arr[(head + _curOffset++) % capacity];
            }
        };
    }
}
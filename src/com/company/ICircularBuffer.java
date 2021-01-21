package com.company;

/**
 * Коллекция, реализующая FIFO и циклический буфер
 *
 * @param <E> тип элементов
 */
public interface ICircularBuffer<E> extends Iterable<E> {
    /**
     * Возвращает количество элементов в буфере
     *
     * @return количество элементов в буфере
     */
    int getSize();

    /**
     * Добавляет элемент в конец буфера
     * Затирает начало буфера в случае, если он заполнен
     *
     * @param item элемент, который нужно добавить в конец буфера
     */
    void add(E item);

    /**
    * Возвращает и удаляет элемент из начала буфера
    *
    * @return элемент или {@code null}, если буфер пуст
    */
    E pop();

    /**
    * Возвращает (но не удаляет) элемент из начала буфера
    *
    * @return Элемент или {@code null}, если буфер пуст
    */
    E peek();
}

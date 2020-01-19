package ru.otus.homework;
import java.util.*;

public class DIYarrayList<T> implements List<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elementData;
    private int size;
    private final Object[] EMPTY_ARRAY = {};
    public DIYarrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        size=0;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public boolean contains(Object o) {
        unsupport();
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new DIYarrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        unsupport();
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size==elementData.length) grow(size+1);
        elementData[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        unsupport();
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        unsupport();
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        unsupport();
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        unsupport();
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        unsupport();
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        unsupport();
        return false;
    }

    @Override
    public void clear() {
        unsupport();

    }

    @Override
    public T get(int index) {
        return (T)elementData[index];
    }

    @Override
    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = (T)elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        unsupport();

    }

    @Override
    public T remove(int index) {
        unsupport();
        return null;
    }

    @Override
    public int indexOf(Object o) {
        unsupport();
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        unsupport();
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new DIYarrayListListIterator();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        unsupport();
        return null;
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementData.length;
        if (oldCapacity > 0 ) {
            int newCapacity = oldCapacity*2;
            elementData = Arrays.copyOf(elementData, newCapacity);
        } else {
            elementData = new Object[Math.max(DEFAULT_CAPACITY, minCapacity)];
        }
    }
    private void unsupport(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        throw new UnsupportedOperationException(methodName+ " is unsupported");
    }
    // iterator
    private class DIYarrayListIterator implements Iterator<T> {
        private int corsor;
        @Override
        public boolean hasNext() {
            return corsor<size;
        }
        @Override
        public T next() {
            return get(corsor++);
        }
    }

    private class DIYarrayListListIterator implements ListIterator<T> {
        private int corsor;
        private int lastRet=-1;
        @Override
        public boolean hasNext() {
            return corsor<size;
        }
        @Override
        public T next() {
            lastRet=corsor;
            return get(corsor++);
        }
        @Override
        public boolean hasPrevious() {
            return corsor >0;
        }

        @Override
        public T previous() {
            lastRet=--corsor;
            return get(corsor);
        }

        @Override
        public int nextIndex() {
            return corsor;
        }

        @Override
        public int previousIndex() {
            return corsor-1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
        @Override
        public void set(T t) {
            DIYarrayList.this.set(lastRet,t);
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException();
        }
    }
}

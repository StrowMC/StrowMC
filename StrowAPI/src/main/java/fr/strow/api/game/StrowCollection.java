package fr.strow.api.game;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class StrowCollection<T> implements Iterable<T> {

    protected final List<T> collection;

    public StrowCollection(List<T> collection) {
        this.collection = collection;
    }

    public Filter<T> filter() {
        return new Filter<>(collection);
    }

    public T get(int index) {
        return collection.get(index);
    }

    public int size() {
        return collection.size();
    }

    public List<T> sorted(Comparator<T> comparator, int n) {
        return collection.stream()
                .sorted(comparator)
                .limit(n)
                .collect(Collectors.toList());
    }

    public List<T> asList() {
        return Collections.unmodifiableList(collection);
    }

    @Override
    public Iterator<T> iterator() {
        return new AbstractCollectionIterator();
    }

    class AbstractCollectionIterator implements Iterator<T> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size();
        }

        @Override
        public T next() {
            return get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static class Filter<T> {

        protected Stream<T> stream;

        public Filter(Collection<T> collection) {
            stream = collection.stream();
        }

        public Filter<T> add(Predicate<T> predicate) {
            stream = stream.filter(predicate);

            return this;
        }

        public Optional<T> get() {
            return stream.findAny();
        }

        public boolean findAny() {
            return stream.count() > 0;
        }

        public List<T> getList() {
            return stream.collect(Collectors.toList());
        }
    }
}

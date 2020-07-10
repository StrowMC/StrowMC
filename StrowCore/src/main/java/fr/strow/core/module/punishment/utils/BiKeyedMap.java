package fr.strow.core.module.punishment.utils;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class BiKeyedMap<K, S, V> {

    private final List<K> firstKeys = new ArrayList<>();
    private final List<S> secondKeys = new ArrayList<>();
    private final List<V> values = new ArrayList<>();

    public void bind(K firstKey, S secondKey, V value) {
        firstKeys.add(firstKey);
        secondKeys.add(secondKey);
        values.add(value);
    }

    @SuppressWarnings("unchecked")
    public <T> void remove(T key) {
        Class<T> type = (Class<T>) ((ParameterizedType) key.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];

        int index;

        if (type.isInstance(((ParameterizedType) firstKeys.getClass().getGenericSuperclass()).getActualTypeArguments()[0])) {
            index = firstKeys.indexOf((K) key);
        } else if (type.isInstance(((ParameterizedType) firstKeys.getClass().getGenericSuperclass()).getActualTypeArguments()[1])) {
            index = secondKeys.indexOf((S) key);
        } else {
            throw new IllegalArgumentException();
        }

        firstKeys.remove(index);
        secondKeys.remove(index);
        values.remove(index);
    }

    @SuppressWarnings("unchecked")
    public <T> V get(T key) {
        Class<T> type = (Class<T>) ((ParameterizedType) key.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];

        if (type.isInstance(((ParameterizedType) firstKeys.getClass().getGenericSuperclass()).getActualTypeArguments()[0])) {
            return values.get(firstKeys.indexOf((K) key));
        } else if (type.isInstance(((ParameterizedType) firstKeys.getClass().getGenericSuperclass()).getActualTypeArguments()[1])) {
            return values.get(secondKeys.indexOf((S) key));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<V> values() {
        return values;
    }
}

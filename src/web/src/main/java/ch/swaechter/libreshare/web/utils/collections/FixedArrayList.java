package ch.swaechter.libreshare.web.utils.collections;

import java.util.ArrayList;

/**
 * Provide a list with a fixed internal array that behaves like an evicting queue/ring buffer (Although is not
 * implemented as ring buffer but shifts the internal array).
 *
 * @param <T> Generic list type
 * @author Simon Wächter
 */
public class FixedArrayList<T> extends ArrayList<T> {

    /**
     * Fixed size of the list.
     */
    private final int size;

    /**
     * Create a new list with a fixed size. Behaves like an evicting queue/ring buffer
     *
     * @param size Fixed array size
     */
    public FixedArrayList(int size) {
        this.size = size;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(T element) {
        boolean hasChanged = super.add(element);
        if (size() > size) {
            removeRange(0, size() - size);
        }
        return hasChanged;
    }
}

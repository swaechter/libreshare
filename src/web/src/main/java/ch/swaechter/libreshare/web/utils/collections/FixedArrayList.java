package ch.swaechter.libreshare.web.utils.collections;

import java.util.ArrayList;

public class FixedArrayList<T> extends ArrayList<T> {

    private final int size;

    public FixedArrayList(int size) {
        this.size = size;
    }

    @Override
    public boolean add(T element) {
        boolean hasChanged = super.add(element);
        if (size() > size) {
            removeRange(0, size() - size);
        }
        return hasChanged;
    }
}

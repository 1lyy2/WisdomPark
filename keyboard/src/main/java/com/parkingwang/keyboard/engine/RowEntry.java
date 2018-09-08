package com.parkingwang.keyboard.engine;


import java.util.ArrayList;
import java.util.Collection;

/**
 * @author 陈哈哈 (yoojiachen@gmail.com)
 */
final public class RowEntry extends ArrayList<KeyEntry> {

    public RowEntry(int initialCapacity) {
        super(initialCapacity);
    }

    public RowEntry() {
    }

    public RowEntry( Collection<? extends KeyEntry> c) {
        super(c);
    }
}

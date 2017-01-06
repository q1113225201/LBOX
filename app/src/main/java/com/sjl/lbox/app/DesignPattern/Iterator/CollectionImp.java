package com.sjl.lbox.app.DesignPattern.Iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * CollectionImp
 *
 * @author SJL
 * @date 2017/1/6
 */
//收集齐实现类
public class CollectionImp implements Collection {
    private List<String> list;

    public CollectionImp() {
        list = new ArrayList<String>();
    }

    @Override
    public Iterator iterator() {
        return new IteratorImp(this);
    }

    @Override
    public Object get(int index) {
        return list.get(index);
    }

    @Override
    public void add(Object object) {
        list.add(object.toString());
    }

    @Override
    public int size() {
        return list.size();
    }
}

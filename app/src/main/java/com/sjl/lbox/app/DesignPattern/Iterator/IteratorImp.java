package com.sjl.lbox.app.DesignPattern.Iterator;

/**
 * IteratorImp
 *
 * @author SJL
 * @date 2017/1/6
 */
//迭代器实现类
public class IteratorImp implements Iterator {
    private Collection collection;
    private int pos = -1;

    public IteratorImp(Collection collection) {
        this.collection = collection;
    }

    @Override
    public Object next() {
        if(pos<=collection.size()){
            pos++;
        }
        return collection.get(pos);
    }

    @Override
    public boolean hasNext() {
        return pos<collection.size()-1;
    }
}

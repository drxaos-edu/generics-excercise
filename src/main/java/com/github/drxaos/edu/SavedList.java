package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;


public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private ArrayList<E> savedList = new ArrayList<E>();
    private File _file;

    public SavedList(File file) {

    }

    public void reload() throws FileOperationException {


    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    private void save() {

    }
}


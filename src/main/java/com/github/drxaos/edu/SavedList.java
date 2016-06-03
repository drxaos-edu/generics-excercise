package com.github.drxaos.edu;

import java.io.*;

import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private ArrayList<E> savedList = new ArrayList<E>();
    private File _file;

    public SavedList(File file) {
        if (file.exists()) {
            _file = file;
            reload();
        } else {
            _file = new File(file.toString());
        }
    }

    public void reload() throws FileOperationException {
        if (!_file.exists()) savedList.clear();
        try {
            FileInputStream fis = new FileInputStream(_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            savedList = (ArrayList<E>) ois.readObject();
            ois.close();
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public E get(int index) {
        return savedList.get(index);
    }

    @Override
    public E set(int index, E element) {
        savedList.set(index, element);
        save();
        return element;
    }

    @Override
    public int size() {
        return savedList.size();
    }

    @Override
    public void add(int index, E element) {
        savedList.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E elem = savedList.get(index);
        savedList.remove(index);
        save();
        return elem;
    }

    private void save() {
        try {
            FileOutputStream fos = new FileOutputStream(_file);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(savedList);
            fos.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private ArrayList<E> items = new ArrayList<E>();
    private File file;

    public SavedList(File file) {
        if (file.exists()) {
            this.file = file;
            reload();
        } else {
            this.file = new File(file.getAbsolutePath());
        }
    }

    public void reload() throws FileOperationException {
        if (!file.exists())
            items.clear();
        else {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                items = (ArrayList<E>) objectInputStream.readObject();
                objectInputStream.close();
            } catch (Exception e) {
                e.toString();
            }
        }
    }

    public void save() throws FileOperationException {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(items);
            objectOutputStream.close();
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public E get(int index) {
        if (!isIndexValid(index))
            return null;
        return items.get(index);
    }

    @Override
    public E set(int index, E element) {
        if (!isIndexValid(index))
            throw new IllegalArgumentException("invalid index");
        items.set(index, element);
        save();
        return element;
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public void add(int index, E element) {
        items.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        if (!isIndexValid(index))
            throw new IllegalArgumentException("invalid index");
        E ans = items.remove(index);
        save();
        return ans;
    }

    private boolean isIndexValid(int index) {
        return (index < 0 || index > size() - 1);
    }
}

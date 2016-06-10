package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private File file;
    private ArrayList<E> content;

    public SavedList(File file) {
        this.file = file;

        if (file == null)
            file = new File("./list.dat");

        reload();
    }

    public void reload() throws FileOperationException {
        if (!file.exists()) {
            content = new ArrayList<E>();
            return;
        }

        try {
            FileInputStream stream = new FileInputStream(file);
            ObjectInputStream obj = new ObjectInputStream(stream);
            content = (ArrayList<E>) obj.readObject();
            obj.close();
            stream.close();
        } catch (Exception e) {
            System.out.println("Error with reading: " + e);
        }
    }

    public void save() {
        try {
            FileOutputStream stream = new FileOutputStream(file);
            ObjectOutputStream obj = new ObjectOutputStream(stream);
            obj.writeObject(content);
            obj.close();
            stream.close();
        } catch (Exception e) {
            System.out.println("Error with saving: " + e);
        }
    }

    @Override
    public E get(int index) {
        if (content.isEmpty())
            return null;
        else
            return content.get(index);

    }

    @Override
    public E set(int index, E element) {
        E res = content.set(index, element);
        save();
        return res;
    }

    @Override
    public int size() {
        return content.size();
    }

    @Override
    public void add(int index, E element) {
        content.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E res = content.remove(index);
        save();
        return res;
    }

}

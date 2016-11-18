package com.github.drxaos.edu;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.io.*;


public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {
    private File file = null;
    private List list = null;



    public SavedList(File file) {
        this.list = new ArrayList();
        if (!file.exists()) {
            try {
                file.createNewFile();
                this.file = file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            this.file = file;
            this.reload();
        }
    }

    public void reload() throws FileOperationException {
        this.list.clear();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(this.file.getName());
            ois = new ObjectInputStream(fis);
            this.list = (List) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public E get(int index) {
        return (E) this.list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E oldObj = this.get(index);
        this.list.set(index, element);
        this.rewrite();
        return oldObj;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void add(int index, E element) {
        this.list.add(index, element);
        this.rewrite();
    }

    @Override
    public E remove(int index) {
        E obj = (E) this.list.remove(index);
        this.rewrite();
        return obj;
    }



    public void rewrite() throws FileOperationException{
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(this.file.getName());
            oos = new ObjectOutputStream(fos);
            oos.writeObject(this.list);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

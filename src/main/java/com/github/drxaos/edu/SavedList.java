package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {
    private ArrayList<E> arr;
    private File file;
    public SavedList(File file) {
        if (file==null){
            this.file = new File("list.dat");
        }
        else{
            this.file = file;
            reload();
        }
    }

    public void reload() throws FileOperationException {
        arr = new ArrayList<E>();
        if (!file.exists()) return;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            arr = (ArrayList<E>)ois.readObject();
            ois.close();
        }
        catch (Exception e){
            throw new FileOperationException(e);
        }
    }

    @Override
    public E get(int index) {
        return arr.get(index);
    }

    @Override
    public E set(int index, E element)  {
        E tmp = arr.set(index, element);
        save();
        return tmp;
    }

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public void add(int index, E element) {
        arr.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E tmp = arr.remove(index);
        save();
        return tmp;
    }

    private void save(){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(arr);
            oos.close();
        } catch (Exception e) {}
    }
}

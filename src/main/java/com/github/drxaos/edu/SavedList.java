package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.ArrayList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private ArrayList<E> array;
    private File file;

    public SavedList(File file) {
        this.file = file;

        if (file == null)
            file = new File("./list.dat");
        reload();
    }

    public void reload() throws FileOperationException {
        if (!file.exists()){
            array = new ArrayList<E>();
            return;
        }

        array = new ArrayList<E>();
        try {
            FileInputStream inputFile = new FileInputStream(file);
            ObjectInputStream inputObject = new ObjectInputStream(inputFile);
            array = (ArrayList<E>)inputObject.readObject();
            inputObject.close();

        } catch (Exception e) {
            throw new FileOperationException(e);
        }
    }

    @Override
    public E get(int index) {
        if(array.isEmpty())
            return  null;
        else return  array.get(index);
    }

    @Override
    public E set(int index, E element) {
        E item = array.set(index, element);
        save();
        return item;
    }

    @Override
    public int size() {
        return  array.size();
    }

    @Override
    public void add(int index, E element) {
        array.add(index, element);
        save();
    }

    private void save(){
        try {
            FileOutputStream outputFile = new FileOutputStream(file);
            ObjectOutputStream outputObject = new ObjectOutputStream(outputFile);
            outputObject.writeObject(array);
            outputObject.close();
        } catch (Exception e) {}
    }

    @Override
    public E remove(int index) {
        E item = array.remove(index);
        save();
        return  item;
    }
}

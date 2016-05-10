package com.github.drxaos.edu;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {
    
    private File file;
    private ArrayList<E> list;
    
    public SavedList(File file) {
        if(file == null){
            this.file = new File("SavedList.dat");
            list = new ArrayList<E>();
        }
        else{
            this.file = file;
            reload();
        }
    }

    public void reload() throws FileOperationException {
        list = new ArrayList<E>();
        try {
            ObjectInputStream objectInputStream = 
                    new ObjectInputStream(new FileInputStream(file));
            list = (ArrayList<E>) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch(Exception e){}
    }
    
    private void updateFile(){
        try {
            ObjectOutputStream objectOutputStream = 
                    new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(list);
            objectOutputStream.close();
        }
        catch(Exception e){}
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        E tmp = list.set(index, element);
        updateFile();
        return tmp;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        updateFile();
    }

    @Override
    public E remove(int index) {
        E tmp = list.remove(index);
        updateFile();
        return tmp;
    }
}

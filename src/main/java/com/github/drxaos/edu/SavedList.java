package com.github.drxaos.edu;

import java.io.*;
import java.util.AbstractList;
import java.util.LinkedList;

public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private File file;
    private LinkedList<E> list = new LinkedList<E>();

    public SavedList(File file) {
        if(file == null){
            this.file = new File("savedlist.dat");
        }
        else {
            this.file = file;
            reload();
        }
    }

    public void reload() throws FileOperationException {
        list = new LinkedList<E>();
        try{
            ObjectInputStream inStream = new ObjectInputStream(new FileInputStream(this.file));
            list = (LinkedList<E>)inStream.readObject();
            inStream.close();
        }
        catch(Exception ex){}
    }

    private void save(){
        try{
            ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
            outStream.writeObject(list);
            outStream.close();
        }
        catch(Exception ex){}
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        list.set(index, element);
        save();
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        save();
    }

    @Override
    public E remove(int index) {
        E result = list.remove(index);
        save();
        return result;
    }
}

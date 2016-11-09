package com.github.drxaos.edu;

import java.io.*;
import java.util.ArrayList;
import java.util.AbstractList;



public class SavedList<E extends Serializable> extends AbstractList<E> implements Reloadable {

    private ArrayList<E> savedList = new ArrayList<E>();
 	private File fileInst;

    public SavedList(File fileInst) {
        if (fileInst.exists()) {
            this.fileInst = fileInst;
            reload();
        } else {
            this.fileInst = new File(fileInst.toString());
        }
    } // construct

    public void reload() throws FileOperationException {
        if (!fileInst.exists())
                savedList.clear();
        try {
            FileInputStream fileInstis = new FileInputStream(fileInst);
            ObjectInputStream objis = new ObjectInputStream(fileInstis);
            savedList = (ArrayList<E>) objis.readObject();
            objis.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public E get(int index) {
        return savedList.get(index);
    }

    @Override
    public E set(int index, E element) {
        savedList.set(index,element);
        save();
        return element;
    }

    @Override
    public int size() { return savedList.size(); }

    @Override
    public void add(int index, E element) {
        savedList.add(index,element);
        save();
    }

    @Override
    public E remove(int index) {
        E element = savedList.get(index);
        savedList.remove(index);
        save();
        return element;
    }

    private void save() {
        		try {
            			FileOutputStream fileInstos = new FileOutputStream(fileInst);
            			ObjectOutputStream objos = new ObjectOutputStream(fileInstos);
            			objos.writeObject(savedList);
            			fileInstos.close();
            		} catch (Exception ex) {
            			System.out.println(ex.getMessage());
            		}
        	} // save file
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * Clase Frame, guarda la información de un frame y su process
 * @author Equipo4
 */

public class Frame{
    private int size; // size del frame
    private int processID;  // id del process dentro del frame
    private int pageNumber;  // número de página
    
    //Constructor de la clase Frame
    public Frame(){
        this.size = 8;
        this.processID = -1;
        this.pageNumber = 0;
    }
    
    //Getters
    public int getSize(){
        return this.size;
    }
    
    public int getProcessID(){
        return this.processID;
    }
    
    public int getPageNumber(){
        return this.pageNumber;
    }
    
    //Setters
    public void setSize(int size){
        this.size = size;
    }
    
    public void setProcessID(int processID){
        this.processID = processID;
    }
    
    public void setPageNumber(int pageNumber){
        this.pageNumber = pageNumber;
    }
};
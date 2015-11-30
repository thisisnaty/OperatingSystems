/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * Clase Marco, guarda la información de un marco y su proceso
 * @author Equipo4
 */

public class Marco{
    private int tamaño; // tamaño del marco
    private int idProceso;  // id del proceso dentro del marco
    private int numPagina;  // número de página
    
    //Constructor de la clase Marco
    public Marco(){
        this.tamaño = 8;
        this.idProceso = -1;
        this.numPagina = 0;
    }
    
    //Getters
    public int getTamaño(){
        return this.tamaño;
    }
    
    public int getIDProceso(){
        return this.idProceso;
    }
    
    public int getNumPagina(){
        return this.numPagina;
    }
    
    
    //Setters
    public void setTamaño(int tamaño){
        this.tamaño = tamaño;
    }
    
    public void setIDProceso(int idProceso){
        this.idProceso = idProceso;
    }
    
    public void setNumPagina(int numPagina){
        this.numPagina = numPagina;
    }
};
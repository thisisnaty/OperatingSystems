/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Calendar;

/**
 * Process
 La clase <code>Process</code> guarda toda la información de un proceso, es un
 * simulador de una PCB.
 * @author Equipo4
 */
public class Process {
    private int id;     // Es el id del proceso.
    private int pageNumber;     // Número de páginas que ocupa el proceso.
    private int size;     // Tamaño del proceso.
    // Arreglo de las páginas ocupadas por el proceso.
    private Page[] arrPages;    
    private Calendar arrivalTime;     // Tiempo de llegada del proceso.
    private long turnaround;    // Tiempo de turnaround del proceso.
    private Calendar endTime; // Tiempo de terminación del proceso.
    
    public Process(int id, int size) {
        this.id = id;
        this.size = size;
        if(this.size%8 != 0) {
            this.pageNumber = size/8 + 1;
        }
        else {
            this.pageNumber = size/8;
        }
        this.arrPages = new Page[pageNumber];
        this.arrivalTime = Calendar.getInstance();
        this.arrivalTime.getTime();
        this.turnaround = 0;
        this.endTime = null;
        
    }
    
    // Metodos para obtener los datos del proceso
    public long getTurnaround() {
        if(endTime != null)
        {
            return (endTime.getTimeInMillis() - arrivalTime.getTimeInMillis());
        }
        return 0;
    }
    
    public Calendar getTerminacion() {
        return endTime;
    }
    
    public int getId() {
        return id;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public int getSize() {
        return size;
    }
    
    public Page[] getArrPages() {
        return arrPages;
    }
    
    public Calendar getArrivalTime() {
        return arrivalTime;
    }
    
    // Metodos para modificar los datos del proceso
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }         
    
}

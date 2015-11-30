/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Calendar;

/**
 *
 * @author Equipo4
 */
public class Proceso {
    private int id;     // Es el id del proceso.
    private int numPaginas;     // Número de páginas que ocupa el proceso.
    private int tamaño;     // Tamaño del proceso.
    // Arreglo de las páginas ocupadas por el proceso.
    private Pagina[] arrPaginas;    
    private Calendar tiempoLlegada;     // Tiempo de llegada del proceso.
    private long turnaround;    // Tiempo de turnaround del proceso.
    private Calendar tiempoTerminacion; // Tiempo de terminación del proceso.
    
    public Proceso(int id, int tamaño) {
        this.id = id;
        this.tamaño = tamaño;
        if(this.tamaño%8 != 0) {
            this.numPaginas = tamaño/8 + 1;
        }
        else {
            this.numPaginas = tamaño/8;
        }
        this.arrPaginas = new Pagina[numPaginas];
        this.tiempoLlegada = Calendar.getInstance();
        this.tiempoLlegada.getTime();
        this.turnaround = 0;
        this.tiempoTerminacion = null;
        
    }
    
    // Metodos para obtener los datos del proceso
    public long getTurnaround() {
        if(tiempoTerminacion != null)
        {
            return (tiempoTerminacion.getTimeInMillis() - tiempoLlegada.getTimeInMillis());
        }
        return 0;
    }
    
    public Calendar getTerminacion() {
        return tiempoTerminacion;
    }
    
    public int getId() {
        return id;
    }
    
    public int getNumPaginas() {
        return numPaginas;
    }
    
    public int getTamaño() {
        return tamaño;
    }
    
    public Pagina[] getArrPaginas() {
        return arrPaginas;
    }
    
    public Calendar getTiempoLlegada() {
        return tiempoLlegada;
    }
    
    // Metodos para modificar los datos del proceso
    public void setNumPaginas(int numPaginas) {
        this.numPaginas = numPaginas;
    }
    
    public void setTerminacion(Calendar terminacion) {
        this.tiempoTerminacion = terminacion;
    }         
    
}

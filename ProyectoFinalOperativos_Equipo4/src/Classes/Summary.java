/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * Summary
 * La clase <code>Summary</code> guarda toda la información que se imprime en el
 * reporte final con la instrucción F.
 * @author Equipo 4
 */
class Summary {
    // Procesos terminados en el conjunto de instrucciones.
    int terminatedProcesses; 
    // Cantidad de fallos de página en el conjunto de instrucciones.
    int pageFaults;  
    // Cantidad de swaps in de página en el conjunto de instrucciones.
    int swapsIn;    
    // Cantidad de swaps out de página en el conjunto de instrucciones.
    int swapsOut;
    // Tiempo de turnaround total de todos los procesos.
    long totalTurnaround;
    // Tiempo de turnaround promedio de los procesos.
    double averageTurnaround;
    
    public Summary() {
        // Se inicializan todas las variables en 0.
        this.terminatedProcesses = 0;
        this.pageFaults = 0;
        this.swapsIn = 0;
        this.swapsOut = 0;
        this.totalTurnaround = 0;
        this.averageTurnaround = 0;
    }
    
    // Métodos para accesar las variables.
    public int getTerminatedProcesses() {
        return terminatedProcesses;
    }
    
    public int getPageFaults() {
        return pageFaults;
    }
    
    public int getSwapsIn() {
        return swapsIn;
    }
    
    public int getSwapsOut() {
        return swapsOut;
    }
    
    public long getTotalTurnaround() {
        return totalTurnaround;
    }
    
    public double getAverageTurnaround() {
        if (terminatedProcesses > 0) {
            return totalTurnaround/terminatedProcesses;
        }
        return 0;
    }
    
    // Métodos para modificar las variables.
    
    // Agrega 1 a los procesos terminados.
    public void updateTerminatedProcesses() {
        terminatedProcesses += 1;
    }
    
    public void updatePageFaults() {
        pageFaults += 1;
    }
    
    public void updateSwapsIn() {
        swapsIn += 1;
    }
    
    public void updateSwapsOut() {
        swapsOut += 1;
    }
    
    public void updateTotalTurnaround(long turnaround) {
        totalTurnaround += turnaround;
    }
    
}

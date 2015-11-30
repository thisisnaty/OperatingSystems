/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 *
 * @author Equipo4
 */
public class EventHandler {
    private Frame[] mainMemory;
    private Frame[] secondaryMemory;
    
    public EventHandler() {
        mainMemory = new Frame[256];
        secondaryMemory = new Frame[512];
        
        for (int i = 0; i < 256; i++) {
            mainMemory[i] = new Frame();
            secondaryMemory[i] = new Frame();
        }
        
        for (int i = 256; i < 512; i++) {
            secondaryMemory[i] = new Frame();
        }
    }
    
    // Método freeSpace que librea un proceso de memoria.
    // Recibe de parámetros el processId, el summary y la lista de procesos.
    // El método calcula el turnaround y muestra los marcos liberados.
    public void freeSpace(int pId, Summary summary, 
            LinkedList<Process> lklProcess) {
        System.out.println("Liberar");
        
        boolean exists = false;     // Variable que guarda si el proceso existe.
        
        // Checa que el proceso exista y obtiene el tiempo de llegada.
        for (Process process : lklProcess) {
            if (process.getId() == pId && process.getTurnaround() == 0) {
                exists = true;
            }
        }
        
        // Si el proceso existe, se libera.
        if (exists) {
            Calendar arrivalTime = null;    // El tiempo de lelgada del proceso.
            Process p = null;   // El proceso
            int processIndex = 0;       // El índice del proceso.
            
            // Saca el tiempo de llegada y el index.
            for (Process process : lklProcess) {
                if (process.getId() == pId) {
                    arrivalTime = process.getArrivalTime();
                    processIndex = lklProcess.indexOf(process);
                }
            }            
            
            // Calcular el tiempo actual.
            Calendar terminationTime = Calendar.getInstance();
            terminationTime.getTime();
            
            // Calcula y guarda el turnaround.
            long turnaround = (terminationTime.getTimeInMillis() - 
                    arrivalTime.getTimeInMillis());
            lklProcess.get(processIndex).setEndTime(terminationTime);
            summary.updateTerminatedProcesses();
            summary.updateTotalTurnaround(turnaround);
            
            // Revisa las localidades de memoria principal y secundaria y borra
            // el proceso que tenga el pID
            System.out.print("Se liberaron los marcos: ");
            for (int i = 0; i < 256; i++) {
                if (mainMemory[i].getProcessID() == pId) {
                    mainMemory[i].setProcessID(-1);
                    System.out.print(i + " ");
                }
                
                if (secondaryMemory[i].getProcessID() == pId) {
                    secondaryMemory[i].setProcessID(-1);
                }
                
                System.out.println("que ocupaba el proceso" + pId);  
            }
            
            for (int i = 256; i < 512; i++) {
                if (secondaryMemory[i].getProcessID() == pId) {
                    secondaryMemory[i].setProcessID(-1);
                }
            } 
        }
    }
    
    // Método que muestra los datos del summary después de un conjunto de
    // instrucciones.
    // Recibe la lista de procesos y el resumen.
    public void end(LinkedList<Process> lklProcess, Summary summary) {
        // Revisa los procesos que no terminaron y muestra el error.
        for (Process process : lklProcess) {
            if (process.getEndTime() == null) {
                System.out.println("ERROR. El proceso " + process.getId() +
                        " se quedó cargado en memoria.");
            }
        }
        
        // Imprime los datos del summary.
        System.out.println("Fin. REPORTE DE SALIDA");
        for (Process process : lklProcess) {
            if (process.getEndTime() != null) {
                System.out.println("Turnaround del proceso " + process.getId() +
                        " = " + process.getTurnaround() + "ms");
            }
        }   
        System.out.println("Procesos terminados: " + 
                summary.getTerminatedProcesses());
        System.out.println("Page faults: "+ summary.getPageFaults());
        System.out.println("Swap ins: " + summary.getSwapsIn());
        System.out.println("Swap outs: "+ summary.getSwapsOut());
        System.out.println("Turnaround promedio: " + 
                summary.getAverageTurnaround());
    }
}

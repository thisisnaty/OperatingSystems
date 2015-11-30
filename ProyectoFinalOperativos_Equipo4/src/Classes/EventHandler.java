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
import java.util.Queue;
/**
 *
 * @author Equipo4
 */
public class EventHandler {
    // Arreglo de marcos de página en memoria principal.
    private Frame[] mainMemory;
    // Arreglo de marcos de página en memoria secundaria.
    private Frame[] secondaryMemory;
    // Queue para manejar FIFO.
    private Queue<Integer> mainMemoryQueue;
    private Queue<Integer> secondaryMemoryQueue;
    // Arreglo en posicion 0 espacios libres en memoria principal, indice 1 
    // libres en memoria secundaria
    private Integer[] frameAvailability;
    private Summary report;
    
    public EventHandler() {
        mainMemory = new Frame[256];
        secondaryMemory = new Frame[512];
        mainMemoryQueue = new LinkedList<Integer>();
        secondaryMemoryQueue = new LinkedList<Integer>();
        frameAvailability = new Integer[2];
        frameAvailability[0] = 0;
        frameAvailability[1] = 0;
        for (int i = 0; i < 256; i++) {
            mainMemory[i] = new Frame();
            secondaryMemory[i] = new Frame();
        }
        
        for (int i = 256; i < 512; i++) {
            secondaryMemory[i] = new Frame();
        }
    }

    public List<Integer> freeSpace (int spaceNeeded, List<Integer> memoryFrameAvailability, int type, 
            Queue<Integer> tempQueue) {
        int frameNumber = 0;
        for (int i = 0; i < spaceNeeded; i++) {
            frameNumber = tempQueue.poll();
            memoryFrameAvailability.add(frameNumber);
            frameAvailability[type]++;
        }
        return memoryFrameAvailability;
    }
    
    public void moveToSecondaryMemory (List<Integer> mainMemoryFrameAvailability, Process p, int spaceNeeded) {
        List<Integer> secondaryMemoryFrameAvailability = new ArrayList<Integer>();
        boolean fitsInSecondaryMemory;
        int processID, pageNumber, frameNumber;
        for (int i = 0; i < 512 && frameAvailability[1] != spaceNeeded; i++) {
                if (secondaryMemory[i].getProcessID() == -1) {
                    frameAvailability[1]++;
                    secondaryMemoryFrameAvailability.add(i);
                }
        }
        fitsInSecondaryMemory = (spaceNeeded <= frameAvailability[1]);
        if (!fitsInSecondaryMemory) {
            secondaryMemoryFrameAvailability = freeSpace (spaceNeeded, secondaryMemoryFrameAvailability, 1,
                    secondaryMemoryQueue);
        }
        for (int i = 0; i < frameAvailability[0]; i++) {
            report.swapsOut++;
            frameNumber = mainMemoryFrameAvailability.get(i);
            processID = mainMemory[frameNumber].getProcessID();
            pageNumber = mainMemory[frameNumber].getPageNumber();
            secondaryMemory[frameNumber].setProcessID(processID);
            secondaryMemory[frameNumber].setPageNumber(pageNumber);
            secondaryMemoryQueue.add(frameNumber);
        }
    }
    
    public void load (Process p, Summary r) {
        report = r;
        boolean fitsInMainMemory = false;
        frameAvailability[0] = 0;
        frameAvailability[1] = 0;
        
        List<Integer> mainMemoryFrameAvailability = new ArrayList<>();
        
        if (isLoaded(p)) {
            System.out.println("Este proceso ya está cargado en memoria");
            System.out.println();
            return;
        }
        else {
            //guardar los marcos libres
            for (int i = 0; i < 256 && frameAvailability[0] != p.getPageNumber(); i++) {
                if (mainMemory[i].getProcessID() == -1) {
                    frameAvailability[0]++;
                    mainMemoryFrameAvailability.add(i);
                }
            }
            
            System.out.println("Se usaron los siguientes marcos de página: ");
            
            fitsInMainMemory = (p.getPageNumber() <= frameAvailability[0]);
            
            if (!fitsInMainMemory) {
                //liberar espacio
                mainMemoryFrameAvailability = freeSpace(p.getPageNumber()-frameAvailability[0], mainMemoryFrameAvailability, 0, 
                        mainMemoryQueue);
                moveToSecondaryMemory(mainMemoryFrameAvailability, p, p.getPageNumber()-frameAvailability[0]);
            }
            //solo se carga en memoria
            int frameNumber = 0;
            int pageNumber = 0;
            
            for (int i = 0; i < frameAvailability[0]; i++) {
                report.swapsIn++;
                frameNumber = mainMemoryFrameAvailability.get(i);
                mainMemory[frameNumber].setProcessID(p.getId());
                mainMemory[frameNumber].setPageNumber(pageNumber);
                
                System.out.print(frameNumber + "\t \t");
                pageNumber++;
                mainMemoryQueue.add(frameNumber);
                if (i % 5 == 0) {
                    System.out.println();
                }
            }
            
            System.out.println();
            
            mainMemoryFrameAvailability.clear();
            System.out.println();
        }
    }
    
    public boolean isLoaded (Process p) {
        
        int pageNum = p.getSize()/8;
        if (p.getSize() % 8 != 0) {
            pageNum++;
        }
        
        p.setPageNumber(pageNum);
        
        for (int i = 0; i < 256; i++) {
            if (mainMemory[i].getProcessID() == p.getId() ||
                    secondaryMemory[i].getProcessID() == p.getId()) {
                return true;
            }
        }
        
        for (int i = 256; i < 512; i++) {
            if (secondaryMemory[i].getProcessID() == p.getId()) {
                return true;
            }
        }
        
        return false;
    }
    
    // Método removeProcess que libera un proceso de memoria.
    // Recibe de parámetros el processId, el summary y la lista de procesos.
    // El método calcula el turnaround y muestra los marcos liberados.
    public void removeProcess(int pID, Summary summary,
            LinkedList<Process> processList) {
        System.out.println("Liberar");
        boolean exists = false;     // Variable que guarda si el proceso existe.
        
        // Checa que el proceso exista
        for (Process process : processList) {
            if (process.getId() == pID && process.getTurnaround() == 0) {
                exists = true;
            }
        }

        // Si el proceso existe, se libera.
        if (exists) {
            System.out.println("Liberar Proceso " + pID);
            
            // Tiempo de llegada
            Calendar arrivalTime = null;
            // Proceso temporal
            Process p = null;
            // Index in list
            int processIndex = 0;
            
            // Saca el tiempo de llegada y el index.
            for (Process process : processList) {
                if (process.getId() == pID) {
                    arrivalTime = process.getArrivalTime();
                    // Guardar indice
                    processIndex = processList.indexOf(process);
                }
            }
            
            // Get actual time
            Calendar terminationTime = Calendar.getInstance();
            terminationTime.getTime();
            
            // Turnaround calculations
            long turnaround = (terminationTime.getTimeInMillis() -
                    arrivalTime.getTimeInMillis());
            
            processList.get(processIndex).setEndTime(terminationTime);
            summary.updateTerminatedProcesses();
            summary.updateTotalTurnaround(turnaround);
            processList.get(processIndex).getTurnaround();
            
            int countP = 0;
            int countS = 0;
            
            // Revisa memorias y borra proceso con pID
            System.out.println("Se liberaron los marcos de memoria principal: ");
            for (int i = 0; i < 256; i++) {
                if (mainMemory[i].getProcessID() == pID) {
                    countP++;
                    mainMemory[i].setProcessID(-1);
                    System.out.print(i + "\t \t");
                    if (i % 5 == 0) {
                        System.out.println();
                    }
                }
            }
            
            if (countP == 0) {
                System.out.println("Memoria principal no afectada en liberación");
            }
            
            System.out.println();
            
            System.out.println("Se liberaron los marcos de memoria secundaria: ");
            for (int i = 0; i < 512; i++) {
                if (secondaryMemory[i].getProcessID() == pID) {
                    countS++;
                    secondaryMemory[i].setProcessID(-1);
                    System.out.print(i + "\t \t");
                    if (i % 5 == 0) {
                        System.out.println();
                    }
                }
            }
            
            if (countS == 0) {
                System.out.println("Memoria secundaria no afectada en liberación");
            }
            
             System.out.println();
             System.out.println("*Que ocupaba el proceso " + pID);
             System.out.println();
        }
        report = summary;
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
    
    public void access(int address, int pID, boolean bitMod, LinkedList<Process>
            processList, Summary summary) {
        int pageNumber = address/8;
        boolean pageFound = false;
        boolean pageFoundInSecondaryM;
        //checar si esta cargada en mi memoria
        //pID && pageNumber en algun marco de memoria REAL
        for(int i = 0; i < 256; i++){
            if(mainMemory[i].getProcessID() == pID && mainMemory[i].getPageNumber() == pageNumber){
                System.out.println("Direccion virtual: " + address);
                System.out.println("Direccion real: " + pageNumber + i*8);
                pageFound = true;
            }
        }
        
        //si no lo encuentra, buscar en memoria secundaria
        if(!pageFound){
            for(int i = 0; i < 512; i++){
                if(secondaryMemory[i].getProcessID() == pID && secondaryMemory[i].getPageNumber() == pageNumber){
                    pageFoundInSecondaryM = true;
                    //Checar si hay espacio libre en la real
                    //Si si, pasarlo
                    //Si no, hacer espacio y pasarlo
                }
            }
        }
    }
    
}

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
    private LinkedList<Integer> mainMemoryQueue;
    private LinkedList<Integer> secondaryMemoryQueue;
    // Arreglo en posicion 0 espacios libres en memoria principal, indice 1
    // libres en memoria secundaria
    private Integer[] frameAvailability;
    private Summary report;
    private LinkedList<Integer> mainMemoryFrameAvailability;
    private LinkedList<Integer> secondaryMemoryFrameAvailability;
    
    public EventHandler() {
        secondaryMemoryFrameAvailability = new LinkedList<Integer>();
        mainMemoryFrameAvailability = new LinkedList<Integer>();
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

    public void moveToSecondaryMemory (Process p, int spaceToMove) {
        int processID, pageNumber, frameNumber;
        if (!hasEnoughSpace(spaceToMove, 1, secondaryMemory)) {
            //si no hay espacio, hace espacio con freeSpace
            freeSpace (spaceToMove,
                    secondaryMemoryFrameAvailability, 1,
                    secondaryMemoryQueue);
        }
        
        for (int i = 0; i < spaceToMove && 
                mainMemory[mainMemoryFrameAvailability.peekLast()].getProcessID() != -1; i++) {
            
            report.swapsOut++;
            frameNumber = mainMemoryFrameAvailability.pollLast();
            mainMemoryFrameAvailability.addFirst(frameNumber);
            
            processID = mainMemory[frameNumber].getProcessID();
            pageNumber = mainMemory[frameNumber].getPageNumber();
            mainMemory[frameNumber].setProcessID(-1);
            
            frameNumber = secondaryMemoryFrameAvailability.pollFirst();
            secondaryMemory[frameNumber].setProcessID(processID);
            secondaryMemory[frameNumber].setPageNumber(pageNumber);
            secondaryMemoryQueue.add(frameNumber);
        }
    }
    
    /**
     * Mueve una pagina de memoria secundaria a principal
     * 
     * @param SM_pageNumber Es el numero de pagina del proceso
     * @param SM_processID Es el id del proceso
     * @param SM_frame Es el frame de memoria secundaria donde está la pagina
     */
    public void movePageToPrimaryMemory(int SM_pageNumber, int SM_processID, int SM_frame){
        //checa que haya un marco libre en memoria principal, 
        //se actualiza la lista de libres en principal
        boolean fitsInMM = hasEnoughSpace(1, 0, mainMemory);
        int MM_pageNumber, MM_processID, MM_frame;
        
        //si no hay espacio, hacer swaps
        if(!fitsInMM){
            //guardo los valores de la pagina actualmente en memoria principal
            MM_frame = mainMemoryQueue.poll();
            MM_pageNumber = mainMemory[MM_frame].getPageNumber();
            MM_processID = mainMemory[MM_frame].getProcessID();
            
            //actualizo los valores por los de la memoria secundaria
            mainMemory[MM_frame].setPageNumber(SM_pageNumber);
            mainMemory[MM_frame].setProcessID(SM_processID);
            
            //actualizo valores por los de la memoria principal
            secondaryMemory[SM_frame].setPageNumber(MM_pageNumber);
            secondaryMemory[SM_frame].setProcessID(MM_processID);
            
            mainMemoryQueue.push(MM_frame);
            //secondaryMemoryQueue.push(SM_frame); //buscar y sacar este frame??
            
            report.swapsOut++;
        }
        //si hay espacio, solo mover a principal
        else{
            MM_frame = mainMemoryFrameAvailability.peek();
            
            //actualizo los valores por los de la memoria secundaria
            mainMemory[MM_frame].setPageNumber(SM_pageNumber);
            mainMemory[MM_frame].setProcessID(SM_processID);
            
            //se borran datos de pagina del marco en la memoria secundaria
            secondaryMemory[SM_frame].setPageNumber(0);
            secondaryMemory[SM_frame].setProcessID(-1);
            
            mainMemoryQueue.push(MM_frame);
            
            //quitar secondary de queue?
        }
        
        report.swapsIn++;
    }
    
    //sobreescribe main memories availability
    //type = 0 para principal
    //type = 1 para secundaria
    public boolean hasEnoughSpace (int spaceNeeded, int type,
            Frame[] frameArray) {
        LinkedList<Integer> memoryFrameAvailability = new LinkedList<Integer>();
        int max;
        
        //tipo = 0 para principal
        if (type == 0) {
            max = 256;
        }
        //tipo = 1 para secundaria
        else {
            max = 512;
        }
        
        //itera hasta pasarse de marcos o llenar la demanda de libres
        for (int i = 0; i < max && frameAvailability[type] != spaceNeeded; i++) {
            if (frameArray[i].getProcessID() == -1) {
                frameAvailability[type]++;
                memoryFrameAvailability.add(i);
            }
        }
        
        //copia los libres a la lista correspondiente
        if (type == 0) {
            mainMemoryFrameAvailability = memoryFrameAvailability;
        }
        else {
            secondaryMemoryFrameAvailability = memoryFrameAvailability;
        }
        return (frameAvailability[type] >= spaceNeeded);
    }
    
    // Método que muestra los datos del summary después de un conjunto de
    // instrucciones.
    // Recibe la lista de procesos y el resumen.
    public void end(LinkedList<Process> processList, Summary summary) {
        // Revisa los procesos que no terminaron y muestra el error.
        System.out.println("******************************************");
        for (Process process : processList) {
            if (process.getEndTime() == null) {
                System.out.println("Proceso " + process.getId() +
                        " cargado en memoria.");
            }
            
            else {
                System.out.println("Turnaround de proceso " + process.getId() +
                        ": " + process.getTurnaround() + "ms");
            }
        }
        
        System.out.println("Procesos terminados: " +
                summary.getTerminatedProcesses());
        System.out.println("Page faults: "+ summary.getPageFaults());
        System.out.println("Swap ins: " + summary.getSwapsIn());
        System.out.println("Swap outs: "+ summary.getSwapsOut());
        System.out.println("Turnaround promedio: " +
                summary.getAverageTurnaround());
        System.out.println("******************************************");
        System.out.println();
    }
    
    public boolean access(int address, int pID, boolean bitMod, LinkedList<Process>
            processList, Summary summary) {
        int pageNumber = address/8;
        frameAvailability[0] = 0;
        frameAvailability[1] = 0;
        
        //innecesario, o cambiar a que regrese index y su tipo de memoria
        if (!isLoaded(pID)) {
            return false;
        }
        
        boolean pageFound = false;
        boolean pageFoundInSecondaryM = false;
        System.out.println();
        System.out.println("******************************************");
        //checar si esta cargada en memoria principal
        for(int i = 0; i < 256; i++){
            if(mainMemory[i].getProcessID() == pID && 
                    mainMemory[i].getPageNumber() == pageNumber){
                System.out.println("Dirección virtual: " + address);
                System.out.println("Dirección real: " + pageNumber + i*8);
                System.out.println("******************************************");
                //pageFound = true;
                return true;
            }
        }
        
        //si no lo encuentra, buscar en memoria secundaria
        if(!pageFound){
            for(int i = 0; i < 512; i++){
                //lo encuentra
                if(secondaryMemory[i].getProcessID() == pID && 
                        secondaryMemory[i].getPageNumber() == pageNumber){
                    
                    //pasa de secundaria a principal
                    movePageToPrimaryMemory(pageNumber, pID, i);
                    
                    System.out.println("Dirección virtual: " + address);
                    System.out.println("Dirección real: " + pageNumber + i*8);
                    System.out.println("******************************************");
                    //pageFoundInSecondaryM = true;
                    return true;
                }
            }
        }
        
        if(!pageFoundInSecondaryM){
            System.out.println("La página " + pageNumber + " no está cargada");
        }
        return false;
    }
    
    // Método removeProcess que libera un proceso de memoria.
    // Recibe de parámetros el processId, el summary y la lista de procesos.
    // El método calcula el turnaround y muestra los marcos liberados.
    public void removeProcess(int pID, Summary summary,
            LinkedList<Process> processList) {
        boolean exists = false;     // Variable que guarda si el proceso existe.
        
        // Checa que el proceso exista
        for (Process process : processList) {
            if (process.getId() == pID && !process.getEnd()) {
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
            processList.get(processIndex).setEnd(true);
            
            int countP = 0;
            int countS = 0;
            
            // Revisa memorias y borra proceso con pID
            System.out.println("******************************************");
            System.out.println("Marcos liberados de memoria principal: ");
            for (int i = 0; i < 256; i++) {
                if (mainMemory[i].getProcessID() == pID) {
                    countP++;
                    mainMemory[i].setProcessID(-1);
                    
                    // Encontrar y borrar en queue
                    mainMemoryQueue.remove((Object)i);
                    
                    System.out.print(i + "\t \t");
                    if ((i+1) % 5 == 0) {
                        System.out.println();
                    }
                }
            }
            
            if (countP == 0) {
                System.out.println("SIN MODIFICACIÓN");
            }
            
            else {
                System.out.println();
            }
            
            System.out.println("******************************************");
            System.out.println("Marcos liberados de memoria secundaria: ");
            for (int i = 0; i < 512; i++) {
                if (secondaryMemory[i].getProcessID() == pID) {
                    countS++;
                    secondaryMemory[i].setProcessID(-1);
                    
                    // Encontrar y borrar en queue
                    secondaryMemoryQueue.remove((Object)i);
                    
                    System.out.print(i + "\t \t");
                    if ((i+1) % 5 == 0) {
                        System.out.println();
                    }
                }
            }
            
            if (countS == 0) {
                System.out.println("SIN MODIFICACIÓN");
            }
            
            else {
                System.out.println();
            }
            
            System.out.println("******************************************");
            System.out.println();
            
        }
        
        else {
            System.out.println("******************************************");
            System.out.println("No existe el proceso " + pID + " en memoria");
            System.out.println("******************************************");
            System.out.println();
            
        }
        report = summary;
    }
    
    public boolean isLoaded (int pID) {
        
        for (int i = 0; i < 256; i++) {
            if (mainMemory[i].getProcessID() == pID ||
                    secondaryMemory[i].getProcessID() == pID) {
                return true;
            }
        }
        
        for (int i = 256; i < 512; i++) {
            if (secondaryMemory[i].getProcessID() == pID) {
                return true;
            }
        }
        
        return false;
    }
    
    public void freeSpace (int spaceNeeded, LinkedList<Integer> memoryFrameAvailability, int type,
            Queue<Integer> tempQueue) {
        int frameNumber = 0;
        for (int i = 0; i < spaceNeeded; i++) {
            frameNumber = tempQueue.poll();
            memoryFrameAvailability.add(frameNumber);
            frameAvailability[type]++;
        }
        if (type == 0) {
            mainMemoryFrameAvailability = memoryFrameAvailability;
        }
        else {
            secondaryMemoryFrameAvailability = memoryFrameAvailability;
        }
    }
    
    public boolean load (Process p, Summary r) {
        report = r;
        boolean fitsInMainMemory = false;
        frameAvailability[0] = 0;
        frameAvailability[1] = 0;
        int pageNum = p.getSize()/8;
        if (p.getSize() % 8 != 0) {
            pageNum++;
        }
        p.setPageNumber(pageNum);
        
        if (isLoaded(p.getId())) {
            
            // Checa si el proceso ya existe
            System.out.println("******************************************");
            System.out.println("Este proceso ya está cargado en memoria");
            System.out.println("******************************************");
            System.out.println();
            return false;
        }
        else {
            
            
            System.out.println("******************************************");
            System.out.println("Se usaron los siguientes marcos de página: ");

            if (!hasEnoughSpace (p.getPageNumber(), 0, mainMemory)) {
                int tmp = frameAvailability[0];
                //liberar espacio
                freeSpace(p.getPageNumber()-frameAvailability[0],
                        mainMemoryFrameAvailability, 0, mainMemoryQueue);
                moveToSecondaryMemory(p, p.getPageNumber()-tmp);
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
                if ((i+1) % 5 == 0) {
                    System.out.println();
                }
            }
            
            System.out.println();
            System.out.println("******************************************");
            
            mainMemoryFrameAvailability.clear();
            System.out.println();
        }
        return true;
    }
}

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
    
    public void load(Process p, Summary report) {
        boolean fitsInMainMemory = false;
        int frameAvailability = 0; 
        List<Integer> mainMemoryFrameAvailability = new ArrayList<Integer>();
        List<Integer> secondaryMemoryFrameAvailability = new ArrayList<Integer>();
        
        if (isLoaded(p)) {
            System.out.println("Este proceso ya está cargado en memoria");
        }
        else {
            //cargarlo en memoria
            for (int i = 0; i < 256 && frameAvailability != p.getPageNumber(); i++) {
                if (mainMemory[i].getProcessID() == -1) {
                    frameAvailability++;
                    mainMemoryFrameAvailability.add(i);
                }
            }
            
            System.out.println("Se usaron los siguientes marcos de página: ");
            
            fitsInMainMemory = (p.getPageNumber() <= frameAvailability);
                        
            if (!fitsInMainMemory) {
                //swap usando FIFO
                
            }
            //solo se carga en memoria
                int frameNumber = 0;
                int pageNumber = 0;
                
                for (int i = 0; i < frameAvailability; i++) {
                    frameNumber = mainMemoryFrameAvailability.get(i);
                    mainMemory[frameNumber].setProcessID(p.getId());
                    mainMemory[frameNumber].setPageNumber(pageNumber);
                    System.out.println(frameNumber + " ");
                    pageNumber++;
                }
                
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
        
        boolean isLoaded = false;
        
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
}

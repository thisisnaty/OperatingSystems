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
}

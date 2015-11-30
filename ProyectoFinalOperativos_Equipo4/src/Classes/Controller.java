/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.util.LinkedList;
import java.util.Scanner;

/**
 *
 * @author Equipo4
 */
public class Controller {

    // Contiene memorias y hace handling de comandos ACCESAR, AGREGAR, etc.
    EventHandler handler;
    // Contiene procesos como queue
    LinkedList<Integer> procesos; 
    
    // Nombre de archivo
    String fileName;
    // Scanner de archivo
    Scanner scan;
    // ID de proceso entrante
    Integer pID;
    // Tamaño del proceso
    Integer pSize;
    // Comando en archivo
    String lineaComando;
    // Caracter de comando
    char charComando;
    // Direccion asignada al proceso
    int direccion;
    // Datos se guardan para resumen del final
    Resumen resumen;
    
    public Controller(String fName) {
        // Asigna valores default
        this.handler = new EventHandler();
        this.procesos = new LinkedList();
        
        this.fileName = fName;
        this.pID = -1;
        this.pSize = 0;
        this.lineaComando = "";
        this.charComando = '\0';
        this.direccion = 0;
        
        this.resumen = new Resumen();
    }

    public void leerArchivo() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

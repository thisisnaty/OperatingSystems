/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.io.File;
import java.io.IOException;
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
    LinkedList<Integer> processList; 
    
    // Nombre de archivo
    String fileName;
    // Scanner de archivo
    Scanner scan;
    // ID de proceso entrante
    Integer pID;
    // Tamaño del proceso
    Integer pSize;
    // Comando en archivo
    String commandLine;
    // Caracter de comando
    char charCommand;
    // Direccion asignada al proceso
    int address;
    // Datos se guardan para resumen del final
    Summary summary;
    
    // Controller constructor
    public Controller(String fName) {
        // Asigna valores default
        this.handler = new EventHandler();
        this.processList = new LinkedList();
        
        this.fileName = fName;
        this.pID = -1;
        this.pSize = 0;
        this.commandLine = "";
        this.charCommand = '\0';
        this.address = 0;
        
        this.summary = new Resumen();
    }

    //lee archivo de pruebas, valida inputs
    public void leerArchivo() {
        // Variable boleana en pasado
        boolean fileRead = false;
        
        // Leer archivo
        try {
            scan = new Scanner(new File(fileName));
            fileRead = true;
        } 
        
        catch (IOException e) {
            System.out.println("Archivo no existe");
        }
        
        //Si fue leido exitosamente
        if (fileRead) {
            // Leer linea
            commandLine = scan.nextLine();
            commandLine = commandLine.trim();
            
            // Leer archivo hasta que sea E
            while (!commandLine.equals("E")) {
                
            }
            
        }
    }
    
}

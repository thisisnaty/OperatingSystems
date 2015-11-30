/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
    LinkedList<Process> processList; 
    
    // Nombre de archivo
    String fileName;
    // Scanner de archivo
    Scanner scan;
    // ID de proceso entrante
    Integer pID;
    // Tamaño del proceso
    Integer pSize;
    // Comando en archivo
    String[] commandLine;
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
        this.commandLine = null;
        this.charCommand = '\0';
        this.address = 0;
        
        this.summary = new Summary();
    }

    //lee archivo de pruebas, valida inputs
    public void leerArchivo() {
        // Variable boleana en pasado
        boolean fileRead = false;
        String line;
        
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
            line = scan.nextLine();
            line = line.trim();
            
            // Leer archivo hasta que sea E
            while (!line.equals("E")) {
                // Leer primera palabra
                commandLine = line.split(" ");
                charCommand = commandLine[0].charAt(0);
                
                switch(charCommand) {
                    // Agregar proceso
                    case 'P': 
                        if(validateP(line)) {
                            System.out.println("Instrucción a procesar:");
                            System.out.println(line);
                            System.out.println();
                            createP();
                        }
                    break;
                        
                    // Accesar proceso    
                    case 'A': 
                        if(validateA(line)) {
                            System.out.println("Instrucción a procesar:");
                            System.out.println(line);
                            System.out.println();
                            createA();
                        }
                    break;
                        
                    // Liberar proceso
                    case 'L':
                        if(validateL(line)) {
                            System.out.println("Instrucción a procesar:");
                            System.out.println(line);
                            System.out.println();
                            createL();
                        }
                    break;
                        
                    // Fin de acciones    
                    case 'F':
                        System.out.println("Instrucción a procesar:");
                        System.out.println(line);
                        System.out.println();
                        createF();
                    break;
                        
                    // Terminar aplicacion    
                    case 'E':
                        System.out.println("Instrucción a procesar:");
                        System.out.println(line);
                        System.out.println();
                        System.out.println("Terminar pruebas");
                        System.exit(0);
                    break;
                        
                    default:
                        System.out.println("Instrucción a procesar:");
                        System.out.println(line);
                        System.out.println();
                        System.out.println("Linea de pruebas invalida:");
                        System.out.println(line);
                    break;
                        
                }
                
                // Read new line
                line = scan.nextLine();
                line = line.trim();
            }
            
            // Finish reading
            scan.close();
        }
    }

    // Crea nuevo evento de agregar proceso a memoria
    private void createP() {
        // Se crea proceso
        Process newProcess = new Process(pID, pSize);
        // Se carga al handler
        handler.load(newProcess, summary);
        // Se carga a la lista
        processList.add(newProcess);
    }

    private boolean validateP(String line) {
        // Palabras sin espacio, checar sintaxis
        if (line.trim().split("\\s+").length != 3) {
            // Error de sintaxis
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pSize = Integer.parseInt(commandLine[1]);
            // Checar tamaño de letra
            if (pSize > 2048) {
                System.out.println("Tamaño excede espacio en memoria (2048):");
                System.out.println(commandLine[1]);
                return false;
            }
        } 
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pID = Integer.parseInt(commandLine[2]);
        } 
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            return false;
        }
        
        // Bulletproof input
        return true;
    }
    
    private void createA() {

    }
    
    private boolean validateA(String line) {
        return true;
    }
    
    private void createL() {

    }
    
    private boolean validateL(String line) {
        return true;
    }
    
    private void createF() {

    }
    
}

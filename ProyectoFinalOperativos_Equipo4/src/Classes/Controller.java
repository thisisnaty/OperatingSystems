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
    // Modified bit
    boolean bitMod;
    
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
            
            if (!line.equals("")) {
                
            }
            
            // Leer archivo hasta que sea E
            while (!line.equals("E")) {
                
                if (!line.equals("")) {
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
                                createA();
                                System.out.println();                                
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
                            
                        default:
                            System.out.println("***************************");
                            System.out.println("Linea de pruebas invalida:");
                            System.out.println(line);
                            System.out.println("***************************");
                            System.out.println();
                            break;
                            
                    }
                }
                
                // Read new line
                line = scan.nextLine();
                line = line.trim();
            }
            
            System.out.println("Instrucción a procesar:");
            System.out.println(line);
            System.out.println();
            System.out.println("***************************");
            System.out.println("Terminar pruebas");
            System.out.println("***************************");
            System.exit(0);
            
            // Finish reading
            scan.close();
        }
    }
    
    // Crea nuevo evento de agregar proceso a memoria
    private void createP() {
        // Se crea proceso
        Process newProcess = new Process(pID, pSize);
        // Se carga al handler
        if(handler.load(newProcess, summary)) {
            // Se carga a la lista
            processList.add(newProcess);
        }
    }
    
    // Validar file input de P
    private boolean validateP(String line) {
        // Palabras sin espacio, checar sintaxis
        if (line.trim().split("\\s+").length != 3) {
            // Error de sintaxis
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pSize = Integer.parseInt(commandLine[1]);
            // Checar tamaño de letra
            if (pSize > 2048) {
                System.out.println("******************************************");
                System.out.println("Tamaño excede espacio en memoria (2048):");
                System.out.println(commandLine[1]);
                System.out.println("Error de linea:");
                System.out.println(line);
                System.out.println("******************************************");
                System.out.println();
                return false;
            }
        }
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pID = Integer.parseInt(commandLine[2]);
        }
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Bulletproof input
        return true;
    }
    
    // Crear nuevo evento de accesar datos
    private void createA() {
        // Accesar proceso
        handler.access(address, pID, bitMod, processList, summary);
    }
    
    // Validar file input de A
    private boolean validateA(String line) {
        // Palabras sin espacio, checar sintaxis
        if (line.trim().split("\\s+").length != 4) {
            // Error de sintaxis
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            address = Integer.parseInt(commandLine[1]);
        }
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pID = Integer.parseInt(commandLine[2]);
        }
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Validate bit
        switch (commandLine[3]) {
            case "0":
                bitMod = false;
                break;
            case "1":
                bitMod = true;
                break;
            default:
                System.out.println("***************************");
                System.out.println("Error de sintaxis de linea:");
                System.out.println(line);
                System.out.println("***************************");
                System.out.println();
                return false;
        }
        
        // Bulletproof input
        return true;
    }
    
    // Crear nuevo evento de liberar proceso
    private void createL() {
        // Liberar proceso
        handler.removeProcess(pID, summary, processList);
    }
    
    // Validar file input de L
    private boolean validateL(String line) {
        // Palabras sin espacio, checar sintaxis
        if (line.trim().split("\\s+").length != 2) {
            // Error de sintaxis
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        // Checar si palabras son numeros o letras
        try {
            pID = Integer.parseInt(commandLine[1]);
        }
        
        catch(NumberFormatException e) {
            //se imprime el tipo de error
            System.out.println("***************************");
            System.out.println("Error de sintaxis de linea:");
            System.out.println(line);
            System.out.println("***************************");
            System.out.println();
            return false;
        }
        
        return true;
    }
    
    // Nuevo evento de Fin de lectura de datos agrupados
    private void createF() {
        // Finish
        handler.end(processList, summary);
        // Reinicia variables
        summary = new Summary();
        handler = new EventHandler();
        processList = new LinkedList();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
import Classes.*;

/**
 *
 * @author Equipo4
 */
public class ProyectoFinalOperativos_Equipo4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //Lee archivo de prueba
        Controller control = new Controller("Archivo.txt");
        control.leerArchivo();
    }
    
}

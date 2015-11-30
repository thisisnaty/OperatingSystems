/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * Clase Pagina, guarda la información de una página.
 * @author Equipo4
 */
public class Pagina {
    private int marco;  // El número de marco en donde se encuentra la página.
    private boolean bitResidencia; // El bit de residencia.
    
    public Pagina() {
        bitResidencia = false;
    }
    
    // Métodos para accesar la información de la página.
    public int getMarco() {
        return marco;
    }

    public boolean getBitResidencia() {
        return bitResidencia;
    }
    
    // Métodos para modificar la información de la página.
    public void setMarco(int marco) {
        this.marco = marco;
    }
        
    public void setBitResidencia(boolean bitResidencia) {
        this.bitResidencia = bitResidencia;
    }
    

}

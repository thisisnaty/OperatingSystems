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
    
    public void setMarco(int marco) {
        this.marco = marco;
    }
    
    public int getMarco() {
        return marco;
    }
    
    public void setBitResidencia(boolean bitResidencia) {
        this.bitResidencia = bitResidencia;
    }
    
    public boolean getBitResidencia() {
        return bitResidencia;
    }
}

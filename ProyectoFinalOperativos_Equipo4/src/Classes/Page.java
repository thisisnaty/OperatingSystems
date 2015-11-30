/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 * Page
 * Class <code>Page</code>, stores page information
 * @author Team4
 */
public class Page {
    private int frame;  // El número de frame en donde se encuentra la página.
    private boolean residenceBit; // El bit de residencia.
    
    public Page() {
        residenceBit = false;
    }
    
    // Métodos para accesar la información de la página.
    public int getFrame() {
        return frame;
    }

    public boolean getResidenceBit() {
        return residenceBit;
    }
    
    // Métodos para modificar la información de la página.
    public void setFrame(int frame) {
        this.frame = frame;
    }
        
    public void setResidenceBit(boolean residenceBit) {
        this.residenceBit = residenceBit;
    }
}

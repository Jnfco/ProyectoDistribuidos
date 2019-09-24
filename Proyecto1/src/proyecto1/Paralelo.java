/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.File;

/**
 *
 * @author jnfco
 */
public class Paralelo
{
    
    private int DIM;
    private int ancho;
    private int alto;
    private File entrada;
    private File salidaErosion;
    private File salidaDilatacion;
    
    public Paralelo(int DIM,int alto,int ancho,File lectura,File salidaErosion,File salidaDilatacion)
    {
        
        this.DIM=DIM;
        this.ancho=ancho;
        this.alto=alto;
        this.entrada=entrada;
        this.salidaErosion=salidaErosion;
        this.salidaDilatacion=salidaDilatacion;
        
        
    }
}

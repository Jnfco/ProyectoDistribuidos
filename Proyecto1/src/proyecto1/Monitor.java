/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

/**
 *
 * @author jnfco
 */
public class Monitor
{
    private int [][]matrizResultado;

    public Monitor(int [][]matrizResultado)
    {
        this.matrizResultado = matrizResultado;
    }
    
    public void clonar(int [][]matrizOrigen)
    {
        matrizResultado=matrizOrigen;
    }
    public int [][] getMatrizResultado()
    {
        return matrizResultado;
    }
    
}

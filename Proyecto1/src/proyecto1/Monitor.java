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
    private int [][]matrizResultadoErosion;
    private int [][]matrizResultadoDilatacion;
    int id;

    
    public Monitor(int id,int [][]matrizResultadoErosion , int [][]matrizResultadoDilatacion)
    {
        this.matrizResultadoErosion = matrizResultadoErosion;
        this.matrizResultadoDilatacion = matrizResultadoDilatacion;
        this.id=id;
    }
    
    public void clonarErosion(int [][]matrizOrigen)
    {
        matrizResultadoErosion=matrizOrigen;
    }
    
    public void clonarDilatacion(int[][]matrizOrigen)
    {
        matrizResultadoDilatacion=matrizOrigen;
    }
    
    public int [][] getMatrizResultadoErosion()
    {
        return matrizResultadoErosion;
    }
    
    public int [][]getMatrizResultadoDilatacion()
    {
        return matrizResultadoDilatacion;
    }
    public int getId()
    {
        return id;
    }
    
}

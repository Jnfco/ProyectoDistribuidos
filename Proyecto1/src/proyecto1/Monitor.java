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

    /**
     * Esta clase se usa para pasarse la matriz dentro de los hilos
     * ya que asi se puede llamar en la clase proyecto1 que es la principal
     * se le pasa una matriz para la erosion y dilatacion
     */
    public Monitor(int [][]matrizResultadoErosion , int [][]matrizResultadoDilatacion)
    {
        this.matrizResultadoErosion = matrizResultadoErosion;
        this.matrizResultadoDilatacion = matrizResultadoDilatacion;
    }
    
    /**
     * Este metodo sirve para copiar la matriz de erosion que está en el hilo
     * para que en la clase principal se pueda devolver correctamente
     */
    public void clonarErosion(int [][]matrizOrigen)
    {
        matrizResultadoErosion=matrizOrigen;
    }
    
    /**
     * Este metodo sirve para copiar la matriz de dilatacion que está en el hilo
     * para que en la clase principal se pueda devolver correctamente
     */
    public void clonarDilatacion(int[][]matrizOrigen)
    {
        matrizResultadoDilatacion=matrizOrigen;
    }
    
    /**
     * este metodo es el que se utiliza en la clase proyecto1 , el cual retorna la matriz de erosion resultante del hilo
     * @return 
     */
    public int [][] getMatrizResultadoErosion()
    {
        return matrizResultadoErosion;
    }
    
    public int [][]getMatrizResultadoDilatacion()
    {
        return matrizResultadoDilatacion;
    }
    
}

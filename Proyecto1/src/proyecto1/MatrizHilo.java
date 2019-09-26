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
public class MatrizHilo implements Runnable
{

    private int matrizOrigen[][];
    private int anchoMatriz;
    private int matrizErosion[][];
    private int matrizDilatacion[][];
    private int filaInicio;
    private int filaFin;
    private int matrizHilo[][];
    private Monitor monitor;
    private String modo;

    public MatrizHilo(int[][] matrizOrigen, int anchoMatriz, int[][] matrizErosion, int[][] matrizDilatacion, int filaInicio, int filaFin, Monitor monitor, String modo)
    {
        this.matrizOrigen = matrizOrigen;
        this.anchoMatriz = anchoMatriz;
        this.matrizErosion = matrizErosion;
        this.matrizDilatacion = matrizDilatacion;
        this.filaInicio = filaInicio;
        this.filaFin = filaFin;
        this.monitor = monitor;
        this.modo = modo;
    }

    @Override
    public synchronized void run()
    {
        System.out.println("Trabajando hilo " + Thread.currentThread().getId() + "...");

        if (modo.equals("Erosion"))
        {
            //algoritmo de erosion 
            for (int i = filaInicio + 1; i < filaFin - 1; i++)
            {
                for (int j = 1; j < anchoMatriz - 1; j++)
                {
                    int min = 255;
                    int k[] = new int[5];
                    k[0] = matrizOrigen[i][j - 1];
                    k[1] = matrizOrigen[i - 1][j];
                    k[2] = matrizOrigen[i][j];
                    k[3] = matrizOrigen[i][j + 1];
                    k[4] = matrizOrigen[i + 1][j];
                    int l;

                    for (l = 0; l < 5; l++)
                    {
                        if (k[l] < min)
                        {
                            min = k[l];
                        }
                    }
                    matrizErosion[i][j] = min;
                    //System.out.print(matrizErosion[i][j]);
                }
            }
            //Ahora clonamos la matriz para pasarla al monitor devuelta
            monitor.clonarErosion(matrizErosion);
        }

        if (modo.equals("Dilatacion"))
        {
            //Algoritmo de dilatacion 
            for (int i = filaInicio + 1; i < filaFin - 1; i++)
            {
                for (int j = 1; j < anchoMatriz; j++)
                {
                    int max = 0;
                    int k[] = new int[5];
                    k[0] = matrizOrigen[i][j - 1];
                    k[1] = matrizOrigen[i - 1][j];
                    k[2] = matrizOrigen[i][j];
                    k[3] = matrizOrigen[i][j + 1];
                    k[4] = matrizOrigen[i + 1][j];
                    for (int l = 0; l < 5; l++)
                    {
                        if (k[l] > max)
                        {
                            max = k[l];
                        }
                    }
                    matrizDilatacion[i][j] = max;

                }
            }
            monitor.clonarDilatacion(matrizDilatacion);

            System.out.println("Hilo " + Thread.currentThread().getId() + " terminado.");
        }

    }

}

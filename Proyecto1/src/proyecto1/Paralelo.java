/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author jnfco
 */
public class Paralelo
{

    private int DIM;
    private int ancho;
    private int alto;
    private String cabecera;
    private File entrada;
    private File salidaErosion;
    private File salidaDilatacion;
    private String modo;

    public Paralelo(int DIM, int alto, int ancho, int[][] matriz, String cabecera, String modo) throws FileNotFoundException, IOException
    {

        this.DIM = DIM;
        this.ancho = ancho;
        this.alto = alto;
        this.cabecera = cabecera;
        this.modo = modo;

        this.hola(this.DIM, this.ancho, this.alto, matriz, cabecera);
    }

    public void hola(
            int DIM,
            int ancho,
            int alto,
            int[][] matriz,
            String cabecera) throws FileNotFoundException, IOException
    {

        int[][] matrizErosion = new int[alto + 1][ancho + 1];//Matriz que se crea para los datos de la matriz Erosionada
        int[][] matrizDilatacion = new int[alto + 1][ancho + 1];//Matriz que se cera para los datos de la matriz dilatada

        //Creamos un monitor que recibe la matriz resultado en el constructor para despues devolverla como resuelta por el hilo
        Monitor monitor = new Monitor(matrizErosion, matrizDilatacion);
        Monitor monitor2 = new Monitor(matrizErosion, matrizDilatacion);
        Monitor monitor3 = new Monitor(matrizErosion, matrizDilatacion);

        //Para los hilos se van a tomar distintas posiciones de la matriz origen, se divide en 3 para cada una aproximadamente
        //Luego en cada hilo se procesa desde una posicion distinta la matriz origen.
        Thread h1 = new Thread(new MatrizHilo(matriz, ancho, matrizErosion, matrizDilatacion, 0, ((alto / 3) + 1), monitor, modo));//hilo 1 para la primera matriz separada
        Thread h2 = new Thread(new MatrizHilo(matriz, ancho, matrizErosion, matrizDilatacion, ((alto / 3) - 1), ((2 * alto / 3) + 1), monitor2, modo));//hilo 2 para la segunda matriz separada
        Thread h3 = new Thread(new MatrizHilo(matriz, ancho, matrizErosion, matrizDilatacion, ((2 * alto / 3) - 1), alto, monitor3, modo));//hilo 3 para la tercera matriz separada

        h1.start();
        h2.start();
        h3.start();

        if (modo.equals("Erosion"))
        {
            File erosion = new File("erosionHilo.pgm"); //Archivo de salida con el algoritmo de erosion
            FileOutputStream f2 = new FileOutputStream(erosion);//Stream de salida para el archivo de erosion
            DataOutputStream dos = new DataOutputStream(f2);//Stream de salida para los datos del archivo de erosion en bytes

            matrizErosion = monitor.getMatrizResultadoErosion();// matriz para el algoritmo de erosion resultante del hilo, se obtiene desde el monitor 

            dos.writeBytes(cabecera);

            //Acá se escribe en el archivo la primera parte de la matriz  resultande con la erosion, falta juntar con las otras 2
            for (int i = 0; i < (alto / 3); i++) // escribo el resultado de la primera matriz menos la última fila
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos.writeByte(matrizErosion[i][j]);
                }
            }
            // segunda parte de la matriz (originada por el hilo h2)
            matrizErosion = monitor2.getMatrizResultadoErosion();
            for (int i = (alto / 3); i < (2 * alto / 3); i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos.writeByte(matrizErosion[i][j]);
                }
            }
            // tercera parte
            matrizErosion = monitor3.getMatrizResultadoErosion();
            for (int i = (2 * alto / 3); i < alto; i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos.writeByte(matrizErosion[i][j]);
                }
            }
        }

        if (modo.equals("Dilatacion"))
        {

            File dilatacion = new File("dilatacionHilo.pgm");//Archivo de salida con el algoritmo de dilatacion
            FileOutputStream f3 = new FileOutputStream(dilatacion);//Stream de salida para el archivo de dilatacion
            DataOutputStream dos2 = new DataOutputStream(f3);//Stream de salida para los datos del archivo de dilatacion en bytes 
            //Algoritmo de dilatacion Primera matriz
            //Aca se pasa la matriz de dilatacion resultante que se obtiene 
            //del hilo con el monitor, es solo la primera parte, falta combinar las otras 2
            matrizDilatacion = monitor.getMatrizResultadoDilatacion();

            dos2.writeBytes(cabecera);

            //Aqui solo estamos guardando los elementos de la primera de 3 partes de la matriz resultado, falta generar las otroas y combinarlas
            for (int i = 0; i < (alto / 3); i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos2.writeByte(matrizDilatacion[i][j]);
                }
            }
            // segunda parte
            matrizDilatacion = monitor2.getMatrizResultadoDilatacion();
            for (int i = alto / 3; i < (2 * alto / 3); i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos2.writeByte(matrizDilatacion[i][j]);
                }
            }
            // tercera parte
            matrizDilatacion = monitor3.getMatrizResultadoDilatacion();
            for (int i = (2 * alto / 3); i < alto; i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos2.writeByte(matrizDilatacion[i][j]);
                }
            }
        }

    }
}

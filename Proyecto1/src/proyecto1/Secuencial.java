/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta clase realiza la ejecucion de los algoritmos de forma secuencial
 *
 * @author jnfco
 */
public class Secuencial
{

    private byte c, c1, c2;
    private int fila, colu, i, j, gris;
    private byte dibu[][] = new byte[1000][1000], otra[][] = new byte[1000][1000];
    private FileInputStream l;
    private File lectura;
    private File salida;
    private File salida2;
    private int alto;
    private int ancho;
    private int DIM;
    private String cabecera;
    private int[][] matriz;
    private int[][] matrizR;
    private int[][] matrizR2;
    private String modo;

    public Secuencial(int DIM, int alto, int ancho, int[][] matriz, String cabecera, String modo) throws IOException
    {
        this.DIM = DIM;
        this.alto = alto;
        this.ancho = ancho;
        this.matriz = matriz;
        this.cabecera = cabecera;
        this.matrizR = new int[this.alto + 1][this.ancho + 1];
        this.matrizR2 = new int[this.alto + 1][this.ancho + 1];
        this.modo = modo;

        System.out.println("Trabajando algoritmo secuencial...");

        if (modo.equals("Erosion"))
        {
            File erosion = new File("erosionSec.pgm"); //Archivo de salida con el algoritmo de erosion
            FileOutputStream f2 = new FileOutputStream(erosion);//Stream de salida para el archivo de erosion
            DataOutputStream dos = new DataOutputStream(f2);//Stream de salida para los datos del archivo de erosion en bytes
            //algoritmo de erosion 
            for (int i = 1; i < alto - 1; i++)
            {
                for (int j = 1; j < ancho - 1; j++)
                {
                    int min = 255;
                    int k[] = new int[5];
                    k[0] = matriz[i][j - 1];
                    k[1] = matriz[i - 1][j];
                    k[2] = matriz[i][j];
                    k[3] = matriz[i][j + 1];
                    k[4] = matriz[i + 1][j];
                    int l;
                    for (l = 0; l < 5; l++)
                    {
                        if (k[l] < min)
                        {
                            min = k[l];
                        }
                    }
                    matrizR[i][j] = min;
                }
            }

            i = 0;
            String linea = null;

//        dos.writeBytes("P5\n");
//        dos.writeBytes("# Creado por juan abello \n");
//        dos.writeBytes("839 346\n");
            dos.writeBytes(cabecera);

            for (int i = 0; i < alto; i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos.writeByte(matrizR[i][j]);
                }
                //System.out.println();
            }
        }
        if (modo.equals("Dilatacion"))
        {
            File dilatacion = new File("dilatacionSec.pgm");//Archivo de salida con el algoritmo de dilatacion
            FileOutputStream f3 = new FileOutputStream(dilatacion);//Stream de salida para el archivo de dilatacion
            DataOutputStream dos2 = new DataOutputStream(f3);//Stream de salida para los datos del archivo de dilatacion en bytes 
            //Algoritmo de Dilatación
            for (int i = 1; i < alto - 1; i++)
            {
                for (int j = 1; j < ancho; j++)
                {
                    int max = 0;
                    int k[] = new int[5];
                    k[0] = matriz[i][j - 1];
                    k[1] = matriz[i - 1][j];
                    k[2] = matriz[i][j];
                    k[3] = matriz[i][j + 1];
                    k[4] = matriz[i + 1][j];
                    for (int l = 0; l < 5; l++)
                    {
                        if (k[l] > max)
                        {
                            max = k[l];
                        }
                    }
                    matrizR2[i][j] = max;
                }
            }
            dos2.writeBytes(cabecera);

            for (int i = 0; i < alto; i++)
            {
                for (int j = 0; j < ancho; j++)
                {
                    dos2.writeByte(matrizR2[i][j]);
                }
            }

            System.out.println("Algoritmo secuencial terminado.");
        }

    }
}

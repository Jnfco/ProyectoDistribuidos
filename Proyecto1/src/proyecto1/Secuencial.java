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
 *Esta clase la dejé por que es la que funciona sin hilos y deja el archivo de salida correcto, para probar el programa solo con la salida sin hilos
 * hay que comentar en  proyecto1 todo el codigo y solo crear una instancia de esta clase Lectura archivo, ahi sale un archivo que se llama erosionBuena.pgm ese es el que toma 
 * bien el convertidor de pgm, y puedes usarlo para comparar los bytes del archivo de salida con los que salen de los archivos con hilos, en la linea 6086 por ahi, empieza la matriz
 * 2 asi que ahi puedes comparar entre los 2 archivos.
 * @author jnfco
 */
public class Secuencial
{

    private byte c, c1, c2;
    private int fila, colu, i, j, gris;
    private byte dibu[][] = new byte[1000][1000], otra[][] = new byte[1000][1000];
    private FileInputStream l;
    private File lectura;
    private int alto;
    private int ancho;
    private int DIM;
    private DataInputStream dis;
    private int [][]matriz;
    private int [][]matrizErosion;
    private int [][]matrizDilatacion;
    

    public Secuencial(int[][] matriz,int[][] matrizErosion,int [][] matrizDilatacion,int DIM,int alto,int ancho,File lectura,DataInputStream dis) throws IOException
    {

        
        this.DIM=DIM;
        this.alto=alto;
        this.ancho=ancho;
        this.lectura=lectura;
        this.dis=dis;
        this.matriz=matriz;
        this.matrizErosion=matrizErosion;
        this.matrizDilatacion=matrizDilatacion;
        
        
        
        
        File erosionSecuencial=new File("erosionSecuencial.pgm");
        File dilatacionSecuencial=new File("dilatacionSecuencial.pgm");
        
        FileOutputStream f = new FileOutputStream(erosionSecuencial);//Stream de salida para el archivo de erosion
        FileOutputStream f2 = new FileOutputStream(dilatacionSecuencial);//Stream de salida para el archivo de dilatacion
        
        DataOutputStream dos = new DataOutputStream(f);//Stream de salida para los datos del archivo de erosion en bytes
        DataOutputStream dos2 = new DataOutputStream(f2);//Stream de salida para los datos del archivo de dilatacion en bytes 
        
        
        
       
        //algoritmo de erosion 
        
        for (int i = 1; i < alto - 1; i++)
        {
            for (int j = 1; j < ancho- 1; j++)
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
                matrizErosion[i][j] = min;
            }
        }

        i = 0;
        String linea=null;
        
            dos.writeChars("P5\n");
            dos.writeChars("Creado por juan abello \n");
            dos.writeChars("839 346\n");
            dos.writeChars("255\n");
        

        for (int i = 0; i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {

                dos.write(matrizErosion[i][j]);
                
            }
            System.out.println();
        }
        
        //Algoritmo de Dilatación
        for (int i = 1; i < alto-1; i++)
        {
            for (int j = 1; j < ancho; j++)
            {
                int max=0;
                int k[] = new int[5];
                k[0]=matriz[i][j-1];
                k[1]=matriz[i-1][j];
                k[2]=matriz[i][j];
                k[3]=matriz[i][j+1];
                k[4]=matriz[i+1][j];
                for (int l = 0; l < 5; l++)
                {
                   if(k[l]>max)
                   {
                       max=k[l];
                   }
                }
                matrizDilatacion[i][j]=max;
            }
        }
        dos2.writeChars("P5\n");
        dos2.writeChars("Creado por juan abello \n");
        dos2.writeChars("839 346\n");
        dos2.writeChars("255\n");
        

        for (int i = 0; i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {

                dos2.writeByte(matrizDilatacion[i][j]);
                
            }
        }

    }
    

}

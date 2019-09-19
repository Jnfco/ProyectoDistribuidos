/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jnfco
 */
public class Proyecto1
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
    {

        
        File original = new File("imgNueva.pgm");//Archivo original en pgm
        File erosion = new File("erosionE.pgm"); //Archivo de salida con el algoritmo de erosion
        File dilatacion = new File("dilatacionE.pgm");//Archivo de salida con el algoritmo de dilatacion

        //LecturaArchivo l = new LecturaArchivo();
        FileInputStream f = new FileInputStream(original); //Stream de entrada para leer  el archivo original
        FileOutputStream f2 = new FileOutputStream(erosion);//Stream de salida para el archivo de erosion
        FileOutputStream f3 = new FileOutputStream(dilatacion);//Stream de salida para el archivo de dilatacion

        //Aqui se crea el scanner para leer el archivo original
        //luego se saltan 4 lineas que corresponden al encabezado del archivo, esto no se 
        //tomará en cuenta para el algoritmo asi que por eso se omiten
        Scanner scan = new Scanner(f);
        scan.nextLine();
        scan.nextLine();
        scan.nextLine();
        scan.nextLine();

        f.close();

        BufferedReader b = new BufferedReader(new InputStreamReader(f));
        f = new FileInputStream(original);
        DataInputStream dis = new DataInputStream(f); // Stream de entrada para los datos del archivo original, para leer en bytes
        DataOutputStream dos = new DataOutputStream(f2);//Stream de salida para los datos del archivo de erosion en bytes
        DataOutputStream dos2 = new DataOutputStream(f3);//Stream de salida para los datos del archivo de dilatacion en bytes 

        int lineas = 4;
        while (lineas > 0)
        {
            char c;
            do
            {
                c = (char) (dis.readUnsignedByte());
                //System.out.println("Caracter: " + c);
            } while (c != '\n');
            lineas--;
        }
        int[][] matriz = new int[1000][1000];//Matriz que se crea para ingresar los datos del archivo original 
        int[][] matrizErosion = new int[1000][1000];//Matriz que se crea para los datos de la matriz Erosionada
        int[][] matrizDilatacion = new int[1000][1000];//Matriz que se cera para los datos de la matriz dilatada

        
        //Creamos un monitor que recibe la matriz resultado en el constructor para despues devolverla como resuelta por el hilo
        Monitor monitor =new Monitor(matrizErosion);
        Monitor monitor2 =new Monitor(matrizDilatacion);
        
        //Aqui se llena la matriz con los datos transformados en bytes del archivo original de 346x839
        for (int i = 0; i < 346; i++)
        {
            for (int j = 0; j < 839; j++)
            {

                matriz[i][j] = dis.readUnsignedByte();
            }

        }
        //Para los hilos se van a tomar distintas posiciones de la matriz origen, se divide en 3 para cada una aproximadamente
        //Luego en cada hilo se procesa desde una posicion distinta la matriz origen.
        Thread h1= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,0,281, monitor)); //hilo 1 para la primera matriz separada, se toma desde la columna 0 al 281
        Thread h2= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,280,561, monitor));//hilo 2 para la segunda matriz separada , se toma desde la columna 280 hasta el 561
        Thread h3= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,560,839, monitor));//hilo 3 para la tercera matriz separada, se toma desde la columna 560 hasta el 839
        
        h1.start();
        //h2.start();
        //h3.start();
        
        matrizErosion=monitor.getMatrizResultado();// matriz para el algoritmo de erosion resultante del hilo, se obtiene desde el monitor 
        
            dos.writeBytes("P5\n");
            dos.writeBytes("Creado por juan abello \n");
            dos.writeBytes("839 346\n");
            dos.writeBytes("255\n");
                
            
        for (int i = 0; i < 116; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos.writeByte(matrizErosion[i][j]);
            }
            System.out.println();
        }
        
        //Algoritmo de dilatacion
        matrizErosion=monitor.getMatrizResultado();
        
        
            dos.writeBytes("P5\n");
            dos.writeBytes("Creado por juan abello \n");
            dos.writeBytes("839 346\n");
            dos.writeBytes("255\n");
            

        for (int i = 0; i < 116; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos.writeByte(matrizErosion[i][j]);
                
            }
            System.out.println();
        }
        
        
        //h1.wait();
        

        
        
    }
}

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
    
    private static int DIM = 1000;
    private static int ancho;
    private static int alto;
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
    {
        
        
        File original = new File("imgNueva.pgm");//Archivo original en pgm
       //Archivo de salida con el algoritmo de dilatacion
        

        //LecturaArchivo l = new LecturaArchivo();
        FileInputStream f = new FileInputStream(original); //Stream de entrada para leer  el archivo original
       
      

        //Aqui se crea el scanner para leer el archivo original
        //luego se saltan 4 lineas que corresponden al encabezado del archivo, esto no se 
        //tomará en cuenta para el algoritmo asi que por eso se omiten
        Scanner scan = new Scanner(f);
        scan.nextLine();
        scan.nextLine();
        // en la siguiente línea obtenemos las dimensiones de la imagen
        // en acho y alto
        String[] dimensiones = scan.nextLine().split(" ");
        ancho = Integer.parseInt(dimensiones[0]);
        alto = Integer.parseInt(dimensiones[1]);
        scan.nextLine();
        //scan.nextLine();

        f.close();

        BufferedReader b = new BufferedReader(new InputStreamReader(f));
        f = new FileInputStream(original);
        DataInputStream dis = new DataInputStream(f); // Stream de entrada para los datos del archivo original, para leer en bytes
       
        
        
        
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
        
        int[][] matriz = new int[DIM][DIM];
        int[][] matrizR = new int[DIM][DIM];
        int[][] matrizR2 = new int[DIM][DIM];
        
        for (int i = 0; i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                
                matriz[i][j] = dis.readUnsignedByte();
                System.out.print(matriz[i][j]);
                
                
              
            }
           
        }
        
        Secuencial s= new Secuencial (matriz,matrizR,matrizR2,DIM,alto,ancho,original,dis);
        Paralelo p = new Paralelo(matriz,matrizR,matrizR2,DIM,alto,ancho,original,dis);
        
       
        
       
    }
}

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
    
    private static int DIM = 10000;
    private static int ancho;
    private static int alto;
    private static int grises;
    private static String modo;
    private static int opcion;
    private static boolean esParalelo;
    private static int estructura;
   
    
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException
    {
        esParalelo=false;
        Scanner sc= new Scanner(System.in);
        System.out.println("Ingrese nombre del archivo de origen con su extensión, "
                + "ejemplo: archivo.pgm (el archivo debe estar en la carpeta raíz del proyecto).");
        String nombreArchivo=sc.nextLine();
        
        do
        {
            System.out.println("Elija el metodo de resolución del algoritmo seleccionado:");
            System.out.println("1) Secuencial");
            System.out.println("2) Paralelo");
            opcion=sc.nextInt();
        } while (opcion < 1 || opcion > 2);
        
        
        
        if(opcion ==1)
        {
            esParalelo=false;
            do
            {
                System.out.println("Seleccione el elemento estructural para la aplicación del algoritmo"
                    + "(Consulte el enunciado para ver el orden en que están las estructuras): ");
                System.out.println("0) Normal");
                System.out.println("1) Estructura 1");
                System.out.println("2) Estructura 2");
                System.out.println("3) Estructura 3");
                System.out.println("4) Estructura 4");
                System.out.println("5) Estructura 6");
            
                estructura = sc.nextInt();
            } while (estructura < 0 || estructura > 6);
            
        }
        if(opcion==2)
        {
            esParalelo=true;
        }
        
        
        do
        {
            System.out.println("Escoga una de los siguientes algoritmos para la imagen:");
            System.out.println("1) Erosión");
            System.out.println("2) Dilatación");
            opcion=sc.nextInt();
        } while (opcion < 1 || opcion > 2);
        
        
        if(opcion == 1)
        {
            modo="Erosion";
        }
        if(opcion ==2)
        {
            modo="Dilatacion";
        }
        
        
        System.out.println("En proceso....");
        
        File original = new File(nombreArchivo);//Archivo original en pgm

        //LecturaArchivo l = new LecturaArchivo();
        FileInputStream f = new FileInputStream(original); //Stream de entrada para leer  el archivo original

        //Aqui se crea el scanner para leer el archivo original
        //luego se saltan 4 lineas que corresponden al encabezado del archivo, esto no se 
        //tomará en cuenta para el algoritmo asi que por eso se omiten
        Scanner scan = new Scanner(f);
        String[] linea = scan.nextLine().split(" ");
        int contCabecera = 3;
        while(linea.length < 100) // suponiendo que cualquier comentario será de menos de 100 palabras
        {
            if(linea[0].charAt(0) != '#' && linea.length == 2)
            {
                // linea que contiene las dimensiones
                ancho = Integer.parseInt(linea[0]);
                alto = Integer.parseInt(linea[1]);
            }
            if(!linea[0].equals("P5") && linea.length == 1)
            {
                grises = Integer.parseInt(linea[0]);
                break;
            }
            if(linea[0].charAt(0) == '#')
            {
                contCabecera++;
            }
            linea = scan.nextLine().split(" ");
        }

        f.close();

        BufferedReader b = new BufferedReader(new InputStreamReader(f));
        f = new FileInputStream(original);
        DataInputStream dis = new DataInputStream(f); // Stream de entrada para los datos del archivo original, para leer en bytes
        
        int lineas = contCabecera;
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
        
        int[][] matriz = new int[alto+1][ancho+1];
        int[][] matrizR = new int[alto+1][ancho+1];
        int[][] matrizR2 = new int[alto+1][ancho+1];
        
        for (int i = 0; i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                matriz[i][j] = dis.readUnsignedByte();
            }
        }        
        
        String cabecera = "P5\n" + ancho + " " + alto + "\n" + grises + "\n";
        System.out.println("Modo: "+modo);
        if(esParalelo==true)
        {
            Paralelo p = new Paralelo(DIM,alto,ancho, matriz, cabecera,modo);
        }
        else
        {
            Secuencial s = new Secuencial (DIM, alto, ancho, matriz, cabecera, modo, estructura); 
        }
    }
}

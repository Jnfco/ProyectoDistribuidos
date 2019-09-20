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
        
        
        //LecturaArchivo l = new LecturaArchivo();

        
        File original = new File("imgNueva.pgm");//Archivo original en pgm
        File erosion = new File("erosionHilo.pgm"); //Archivo de salida con el algoritmo de erosion
        File dilatacion = new File("dilatacionHilo.pgm");//Archivo de salida con el algoritmo de dilatacion

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
        Monitor monitor =new Monitor(matrizErosion,matrizDilatacion);
        Monitor monitor2 =new Monitor(matrizErosion,matrizDilatacion);
        Monitor monitor3 =new Monitor(matrizErosion,matrizDilatacion);
        
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
        Thread h1= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,0,117, monitor)); //hilo 1 para la primera matriz separada, se toma desde la fila 0 al 117(desde el 116 es la fila que pertenecerá despues a la otra matriz)
        Thread h2= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,116,233, monitor2));//hilo 2 para la segunda matriz separada, se toma desde la fila 116 al 233(desde el 232 es la fila que pertenecerá despues a la otra matriz)
        Thread h3= new Thread(new MatrizHilo(matriz,matrizErosion,matrizDilatacion,232,346, monitor3));//hilo 3 para la tercera matriz separada, se toma desde la fila 232 al 346 
        
        h1.start();
        
        
        matrizErosion=monitor.getMatrizResultadoErosion();// matriz para el algoritmo de erosion resultante del hilo, se obtiene desde el monitor 
        
        //El encabezado solo va en la primera porcion de la salida , lo demas deberian ser los elementos de las matrices combinadas 
            dos.writeBytes("P5\n");
            dos.writeBytes("Creado por juan abello \n");
            dos.writeBytes("839 346\n");
            dos.writeBytes("255\n");
                
            
            //Acá se escribe en el archivo la primera parte de la matriz  resultande con la erosion, falta juntar con las otras 2
        for (int i = 0; i < 114; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos.writeByte(matrizErosion[i][j]);
                
            }
           
        }
        
        
         //Algoritmo de dilatacion Primera matriz
        matrizDilatacion=monitor.getMatrizResultadoDilatacion(); //Aca se pasa la matriz de dilatacion resultante que se obtiene del hilo con el monitor, es solo la primera parte, falta combinar las otras 2
        
        
            dos2.writeBytes("P5\n");
            dos2.writeBytes("Creado por juan abello \n");
            dos2.writeBytes("839 346\n");
            dos2.writeBytes("255\n");
            

            //Aqui solo estamos guardando los elementos de la primera de 3 partes de la matriz resultado, falta generar las otroas y combinarlas
        for (int i = 0; i < 114; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos2.writeByte(matrizDilatacion[i][j]);
                
            }
            //System.out.println();
        }
        
        h1.join();
        h2.start();
        //Comenzamos el hilo 2 para la segunda parte de la matriz de erosion y dilatacion
        
        h2.join();
        //Aca se juntará la segunda parte de la matriz con la primera en el archivo
       
         matrizErosion=monitor2.getMatrizResultadoErosion();
         
          for (int i = 116; i < 231; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos.writeByte(matrizErosion[i][j]);
                //System.out.print(matrizErosion[i][j]);
                
            }
            
           
        }
          //Dilatacion para juntar la guardar la segunda parte de la matriz en el archivo
          for (int i = 116; i < 231; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos2.writeByte(matrizDilatacion[i][j]);
                
            }
            //System.out.println();
        }
          
         
          
        //Iniciamos el hilo 3 y final, para las  partes finales de las matrices de erosion y dilatacion
        h3.start();
         //Acá se juntara la tercera parte de la matriz  de erosion con la segunda en el archivo
        
           matrizErosion=monitor3.getMatrizResultadoErosion();
         
          for (int i = 231; i < 346; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos.writeByte(matrizErosion[i][j]);
                
            }
           
        }
          //Aca se junta la tercera parte de la matriz de dilatacion con la segunda en el archivo
          for (int i = 231; i < 346; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                dos2.writeByte(matrizDilatacion[i][j]);
                
            }
        }
          
         h3.join(); 
          
        
       
        
        
        

        
        
    }
}

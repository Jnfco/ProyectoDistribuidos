/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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
    private File entrada;
    private File salidaErosion;
    private File salidaDilatacion;
    private DataInputStream dis;
    private int[][]matriz;
    private int[][]matrizErosion;
    private int[][]matrizDilatacion;
    
    public Paralelo(int [][]matriz,int[][] matrizErosion,int[][] matrizDilatacion,int DIM,int alto,int ancho,File lectura,DataInputStream dis) throws IOException
    {
        
        this.DIM=DIM;
        this.ancho=ancho;
        this.alto=alto;
        this.entrada=entrada;
        this.salidaErosion=salidaErosion;
        this.salidaDilatacion=salidaDilatacion;
        this.dis=dis;
        this.matriz=matriz;
        this.matrizErosion=matrizErosion;
        this.matrizDilatacion=matrizDilatacion;
        
        
        File erosion = new File("erosionHilo.pgm"); //Archivo de salida con el algoritmo de erosion
        File dilatacion = new File("dilatacionHilo.pgm");
        FileOutputStream f =new FileOutputStream(erosion);
        FileOutputStream f2 =new FileOutputStream(dilatacion);
        
        DataOutputStream dos=new DataOutputStream(f);
        DataOutputStream dos2=new DataOutputStream(f2);
       
        //Creamos un monitor que recibe la matriz resultado en el constructor para despues devolverla como resuelta por el hilo
        Monitor monitor =new Monitor(matrizErosion,matrizDilatacion);
        Monitor monitor2 =new Monitor(matrizErosion,matrizDilatacion);
        Monitor monitor3 =new Monitor(matrizErosion,matrizDilatacion);
        
        
        //Para los hilos se van a tomar distintas posiciones de la matriz origen, se divide en 3 para cada una aproximadamente
        //Luego en cada hilo se procesa desde una posicion distinta la matriz origen.
        Thread h1= new Thread(new MatrizHilo(matriz,ancho,matrizErosion,matrizDilatacion,0,((alto/3) + 1), monitor));//0-116 //hilo 1 para la primera matriz separada, se toma desde la fila 0 al 117(desde el 116 es la fila que pertenecerá despues a la otra matriz)
        Thread h2= new Thread(new MatrizHilo(matriz,ancho,matrizErosion,matrizDilatacion,((alto/3) - 1),((2*alto/3) + 1), monitor2));//114-231//hilo 2 para la segunda matriz separada, se toma desde la fila 116 al 233(desde el 232 es la fila que pertenecerá despues a la otra matriz)
        Thread h3= new Thread(new MatrizHilo(matriz,ancho,matrizErosion,matrizDilatacion,((2*alto/3) - 1),alto, monitor3));//229-346 //hilo 3 para la tercera matriz separada, se toma desde la fila 232 al 346 
        
        h1.start();
        h2.start();
        h3.start();
        
        
        matrizErosion=monitor.getMatrizResultadoErosion();// matriz para el algoritmo de erosion resultante del hilo, se obtiene desde el monitor 
        
        //El encabezado solo va en la primera porcion de la salida , lo demas deberian ser los elementos de las matrices combinadas 
        dos.writeChars("P5\n");
        dos.writeChars("# Creado por juan abello \n");
        dos.writeChars("839 346\n");
        dos.writeChars("255\n");
                
            
        //Acá se escribe en el archivo la primera parte de la matriz  resultande con la erosion, falta juntar con las otras 2
        for (int i = 0; i < (alto/3); i++) // escribo el resultado de la primera matriz menos la última fila
        {
            for (int j = 0; j < ancho; j++)
            {
                dos.write(matrizErosion[i][j]);
            }
        }
        // segunda parte de la matriz (originada por el hilo h2)
        matrizErosion=monitor2.getMatrizResultadoErosion();
        for(int i = (alto/3); i < (2*alto/3); i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                dos.write(matrizErosion[i][j]);
            }
        }
        // tercera parte
        matrizErosion=monitor3.getMatrizResultadoErosion();
        for (int i = (2*alto/3); i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                dos.write(matrizErosion[i][j]);
            }
        }
        
        
        //Algoritmo de dilatacion Primera matriz
        //Aca se pasa la matriz de dilatacion resultante que se obtiene 
        //del hilo con el monitor, es solo la primera parte, falta combinar las otras 2
        matrizDilatacion=monitor.getMatrizResultadoDilatacion(); 
        
        
        dos2.writeChars("P5\n");
        dos2.writeChars("# Creado por juan abello \n");
        dos2.writeChars("839 346\n");
        dos2.writeChars("255\n");
            

        //Aqui solo estamos guardando los elementos de la primera de 3 partes de la matriz resultado, falta generar las otroas y combinarlas
        for (int i = 0; i < (alto/3); i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                dos2.write(matrizDilatacion[i][j]);
            }
            //System.out.println();
        }
        // segunda parte
        matrizDilatacion=monitor2.getMatrizResultadoDilatacion(); 
        for (int i = alto/3; i < (2*alto/3); i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                dos2.write(matrizDilatacion[i][j]);
            }
        }
        // tercera parte
        matrizDilatacion=monitor3.getMatrizResultadoDilatacion(); 
        for (int i = (2*alto/3); i < alto; i++)
        {
            for (int j = 0; j < ancho; j++)
            {
                dos2.write(matrizDilatacion[i][j]);
            }
        }
        
    }
}

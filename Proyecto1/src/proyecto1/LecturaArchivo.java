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
 *
 * @author jnfco
 */
public class LecturaArchivo
{

    private byte c, c1, c2;
    private int fila, colu, i, j, gris;
    private byte dibu[][] = new byte[1000][1000], otra[][] = new byte[1000][1000];
    private FileInputStream l;

    public LecturaArchivo() throws IOException
    {

        File lectura = new File("imgNueva.pgm");
        File salida = new File("erosionE.pgm");
        
        //BufferedInputStream stream = new BufferedInputStream(new FileInputStream(lectura));
        FileInputStream f = new FileInputStream(lectura);
        FileOutputStream f2 = new FileOutputStream(salida);
        Scanner scan = new Scanner(f);
        scan.nextLine();
        scan.nextLine();
        scan.nextLine();
        scan.nextLine();

       f.close();

        BufferedReader b = new BufferedReader(new InputStreamReader(f));
        f = new FileInputStream(lectura);
        DataInputStream dis = new DataInputStream(f);
        DataOutputStream dos = new DataOutputStream(f2);
        int lineas = 4;
        while (lineas > 0)
        {
            char c;
            do
            {
                c = (char) (dis.readUnsignedByte());
                System.out.println("Caracter: "+c);
            } while (c != '\n');
            lineas--;
        }
        int[][] matriz = new int[1000][1000];
        int[][] matrizR = new int[1000][1000];
        
        for (int i = 0; i < 346; i++)
        {
            for (int j = 0; j < 839; j++)
            {
                
                matriz[i][j] = dis.readUnsignedByte();
                //System.out.println("Matriz: "+matriz[i][j]);
                
                
                //System.out.print(matriz[i][j]);
            }
            //System.out.println();
        }
        //algoritmo de erosion 
        
        for (int i = 1; i < 346 - 1; i++)
        {
            for (int j = 1; j < 839 - 1; j++)
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
        String linea=null;
        
            dos.writeBytes("P5\n");
            dos.writeBytes("Creado por juan abello \n");
            dos.writeBytes("839 346\n");
            dos.writeBytes("255\n");
        

        for (int i = 0; i < 346; i++)
        {
            for (int j = 0; j < 839; j++)
            {

                dos.writeByte(matrizR[i][j]);
                
            }
            System.out.println();
        }

    }
    

}

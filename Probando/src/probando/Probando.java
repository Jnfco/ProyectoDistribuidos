/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probando;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author jnfco
 */
public class Probando
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException
    {
        File f = new File("imgNueva.pgm");
        File f2 = new File("imgNueva2.pgm");
        PGMIO p = new PGMIO();
       
        
        int[][]matrizR=p.read(f);
        
        for(int i=0;i<346;i++)
        {
            for(int j=0;j<838;j++)
            {
                System.out.print(matrizR[i][j]);
            }
        }
         for (int i = 1; i < 346 - 1; i++)
        {
            for (int j = 1; j < 838 - 1; j++)
            {
                int min = 255;
                int k[] = new int[5];
                k[0] = matrizR[i][j - 1];
                k[1] = matrizR[i - 1][j];
                k[2] = matrizR[i][j];
                k[3] = matrizR[i][j + 1];
                k[4] = matrizR[i + 1][j];
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
        
       p.write(matrizR, f2, 255);
    }
    
}

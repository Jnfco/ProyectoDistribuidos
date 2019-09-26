
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class lectura{
        
        
        
    public static void main(String[] args) throws IOException{
         
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bienvenido");
        System.out.println("Para empezar ingrese nombre del archivo(no olvidar .pgm): ");
	String archivo = scanner.nextLine();
        File lectura = new File(archivo);
	
        int ancho = 0;
        int alto = 0;
        int grises = 0;
        
        File salida = new File("erosionSuflo.pgm");
	File salida2 = new File("dilatacionSuflo.pgm");
	FileInputStream f = new FileInputStream(lectura);
	FileOutputStream f2 = new FileOutputStream(salida);
	FileOutputStream f3 = new FileOutputStream(salida2);
	Scanner scan = new Scanner(f);
        
        String[] leerDatos = scan.nextLine().split(" ");
	int contador = 3;
        while (leerDatos.length < 50){
            if (leerDatos[0].charAt(0) != '#' && leerDatos.length == 2){
                ancho = Integer.parseInt(leerDatos[0]);
                System.out.println(ancho);
                alto = Integer.parseInt(leerDatos[1]);
                System.out.println(alto);
            }
            if (!leerDatos[0].equals("P5") && leerDatos.length == 1){
                grises = Integer.parseInt(leerDatos[0]);
                System.out.println(grises);
                break;
            }
            if (leerDatos[0].charAt(0) == '#'){
                contador++;
            }
            leerDatos = scan.nextLine().split(" ");
        }
	f.close();
        
        String gris = Integer.toString(grises);
        String anchoS = Integer.toString(ancho);
        String altoS = Integer.toString(alto);
	
        f = new FileInputStream(lectura);
        
	DataInputStream dis = new DataInputStream(f);
	DataOutputStream dos = new DataOutputStream(f2);
	DataOutputStream des = new DataOutputStream(f3);
	int lineas = contador;
	System.out.println(lineas);
        while (lineas > 0){
		char c;
		do{
			c = (char) (dis.readUnsignedByte());
		} while(c != '\n');
		lineas --;
	}
	int[][] matriz = new int[alto+1][ancho+1];
	int[][] matrizR = new int[alto+1][ancho+1];
        int[][] matrizR2 = new int[alto+1][ancho+1];
	
        for (int i = 0; i < alto; i++){
		for (int j = 0; j < ancho; j++){
			matriz[i][j] = dis.readUnsignedByte();
                        
		}
	}
	//erosion
	for (int i = 1; i < alto -1; i++){
		for (int j = 1; j < ancho -1; j++){
			int min = grises;
			int k[] = new int[5];
			k[0] = matriz[i][j-1];
			k[1] = matriz[i-1][j];
			k[2] = matriz[i][j];
			k[3] = matriz[i][j+1];
			k[4] = matriz[i+1][j];
			int l;
			for (l = 0; l < 5; l++){
				if (k[l] < min){
					min = k[l];
				}
			}
			matrizR[i][j] = min;
		}
	}
        dos.writeBytes("P5\n");
	dos.writeBytes("# Creado por Pedro Gonzalez \n");
	dos.writeBytes(anchoS +" "+ altoS+"\n");
	dos.writeBytes(gris+"/n");
	
        for (int i = 0; i < alto; i++){
		for (int j = 0; j < ancho; j++){
			dos.writeByte(matrizR[i][j]);
		}
		//System.out.println();
	}
	//dilatacion 
	for (int i = 1; i < alto -1; i++){
		for (int j = 1; j < ancho -1; j++){
			int max = 0;
			int k[] = new int[5];
			k[0] = matriz[i][j-1];
			k[1] = matriz[i-1][j];
			k[2] = matriz[i][j];
			k[3] = matriz[i][j+1];
			k[4] = matriz[i+1][j];
			int l;
			for (l = 0; l < 5; l++){
				if (k[l] > max){
					max = k[l];
				}
			}
			matrizR2[i][j] = max;
		}
	}
	des.writeBytes("P5\n");
	des.writeBytes("# Creado por Pedro Gonzalez \n");
	des.writeBytes(anchoS+" "+altoS+"\n");
	des.writeBytes(gris+"\n");
	for (int i = 0; i < alto; i++){
		for (int j = 0; j < ancho; j++){
			des.writeByte(matrizR2[i][j]);
		}
		//System.out.println();
	}

    }	
} 
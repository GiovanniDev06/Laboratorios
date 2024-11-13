package lab17.fp2;

import java.util.*;
public class Mapa {
    private static final int FILAS=10,COLUMNAS=10;
    private static Ejercito[][] mapaEjercitos=new Ejercito[FILAS][COLUMNAS];
    private static Soldado[][] mapaSoldados=new Soldado[FILAS][COLUMNAS];

    public static Ejercito[][] getMapaEjercitos() {
        return mapaEjercitos;
    }

    public static Soldado[][] getMapaSoldados() {
        return mapaSoldados;
    }
    
    public static void crearMapaEjercito(Ejercito[] reino1,Ejercito[] reino2){
        Random r=new Random();
        int fil,col;
        for (int i=0;i<FILAS;i++){
            for (int j=0;j<COLUMNAS;j++){
                mapaEjercitos[i][j]=null;
            }
        }
        for (int i=0;i<reino1.length;i++){
            do{
                fil=r.nextInt(10);
                col=r.nextInt(10);
            }while(mapaEjercitos[fil][col] != null);
            mapaEjercitos[fil][col]=reino1[i];
            reino1[i].setFil(fil);
            reino1[i].setCol(col);
        }
        for (int i=0;i<reino2.length;i++){
            do{
                fil=r.nextInt(10);
                col=r.nextInt(10);
            }while(mapaEjercitos[fil][col] != null);
            mapaEjercitos[fil][col]=reino2[i];
            reino2[i].setFil(fil);
            reino2[i].setCol(col);
        }
    }

    public static void crearMapaSoldado(Ejercito ejercito1,Ejercito ejercito2){
        Random r=new Random();
        int fil,col;
        for (int i=0;i<FILAS;i++){
            for (int j=0;j<COLUMNAS;j++){
                mapaSoldados[i][j]=null;
            }
        }
        for (int i=0;i<ejercito1.getEjercito().length;i++){
            do{
                fil=r.nextInt(10);
                col=r.nextInt(10);
            }while(mapaSoldados[fil][col] != null);
            mapaSoldados[fil][col]=ejercito1.getEjercito()[i];
            ejercito1.getEjercito()[i].setFil(fil);
            ejercito1.getEjercito()[i].setCol(col);
        }
        for (int i=0;i<ejercito2.getEjercito().length;i++){
            do{
                fil=r.nextInt(10);
                col=r.nextInt(10);
            }while(mapaSoldados[fil][col] != null);
            mapaSoldados[fil][col]=ejercito2.getEjercito()[i];
            ejercito2.getEjercito()[i].setFil(fil);
            ejercito2.getEjercito()[i].setCol(col);
        }
    }
    
}

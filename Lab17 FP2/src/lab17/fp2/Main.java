package lab17.fp2;

import java.util.*;
public class Main {
    public static void main(String[] args){
        guerraReinos();
    }
    public static String reinos(){
        Random r=new Random();
        switch (r.nextInt(5)+1){
            case 1:
                return "Inglaterra";
            case 2:
                return "Francia";
            case 3:
                return "Sacro Imperio";
            case 4:
                return "Castillo - Aragon";
            case 5:
                return "Moros";
            default:
                return "Incaico";
        }
    }
    
    public static void ganadorBatalla(Ejercito[] reino1,Ejercito[] reino2){
        if (Ejercito.contadorEjercito(reino1) > Ejercito.contadorEjercito(reino2)){
            System.out.println("El ejercito 1 Ha ganado");
            mostrarReino(reino1);
        } else{
            System.out.println("El ejercito 2 Ha ganado");
            mostrarReino(reino2);
        }
    }
    
    private static void guerraReinos(){
        String nombreReino1,nombreReino2;
        Ejercito[] reino1,reino2; 
        Random r=new Random();
        int cantidadEjercitos1=r.nextInt(10)+1,cantidadEjercitos2=r.nextInt(10)+1;
        int cantidadSoldados1=r.nextInt(10)+1,cantidadSoldados2=r.nextInt(10)+1;
        reino1=new Ejercito[cantidadEjercitos1];
        reino2=new Ejercito[cantidadEjercitos2];
        do{
            nombreReino1=reinos();
            nombreReino2=reinos();
        } while (nombreReino1.equals(nombreReino2));
        System.out.println("Se enfrentara : " + nombreReino1 + " contra " + nombreReino2);
        if (nombreReino1.equals("Castillo - Aragon"))
            nombreReino1="CastAra";
        else if (nombreReino1.equals("Sacro Imperio"))
            nombreReino1="SacImp";
        if (nombreReino2.equals("Castillo - Aragon"))
            nombreReino2="CastAra";
        else if (nombreReino2.equals("Sacro Imperio"))
            nombreReino2="SacImp";
        for (int i=0;i<cantidadEjercitos1;i++){
            reino1[i]=new Ejercito(cantidadSoldados1,1);
            reino1[i].setNombre(nombreReino1 + i + 'X' + 1);
            for (int j=0;j<reino1[i].getEjercito().length;j++)
                reino1[i].getEjercito()[j].setNumReino(1);
        }
        for (int i=0;i<cantidadEjercitos2;i++){
            reino2[i]=new Ejercito(cantidadSoldados2,2);
            reino2[i].setNombre(nombreReino2 + i + 'X' + 2);
            for (int j=0;j<reino2[i].getEjercito().length;j++)
                reino2[i].getEjercito()[j].setNumReino(2);
        }
        Mapa.crearMapaEjercito(reino1,reino2);
        Ejercito.mostrarMapa();
        while (Ejercito.contadorEjercito(reino1) != 0 && Ejercito.contadorEjercito(reino2) != 0){
            Ejercito.turnoJugador(Ejercito.elegirEjercito(reino1));
            Ejercito.turnoJugador(Ejercito.elegirEjercito(reino2));
        }
        ganadorBatalla(reino1,reino2);
    }
    
    public static void mostrarReino(Ejercito[] reino){
        for (Ejercito ejercito : reino){
            if (!(ejercito == null)){
                if (ejercito.getVive())
                System.out.println("\n" + ejercito.toString());
            }
        }
        System.out.println("\n--------------------------------------------------------");
    }
}

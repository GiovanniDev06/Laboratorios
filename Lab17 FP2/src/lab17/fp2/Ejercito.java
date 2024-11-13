package lab17.fp2;

import java.util.*;
public class Ejercito {
    private Soldado[] ejercito;
    private String nombre;
    private int numEjercito,numReino,fil,col;
    private boolean vive;

    public Ejercito(int numSoldados,int numReino) {
        this.ejercito = crearEjercito(numSoldados,numReino);
        this.numReino=numReino;
        this.vive=true;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre() {
        return nombre;
    }

    public Soldado[] getEjercito() {
        return ejercito;
    }

    public int getFil() {
        return fil;
    }

    public void setFil(int fil) {
        this.fil = fil;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNumReino() {
        return numReino;
    }

    public void setNumReino(int numReino) {
        this.numReino = numReino;
    }

    public boolean getVive() {
        return vive;
    }

    public void setVive(boolean vive) {
        this.vive = vive;
    }
    
    private Soldado[] crearEjercito(int numSoldados,int numReino){
        int cantSoldados=0;
        Soldado[] ejercito=new Soldado[numSoldados];
        for (int i=0;i<numSoldados;i++){
            String nombre="Soldado"+cantSoldados+'X'+numReino;
            cantSoldados++;
            ejercito[i]=new Soldado(nombre);
        }
        return ejercito;
    }
    
    public static Ejercito elegirEjercito(Ejercito[] reino){
        Scanner sc=new Scanner(System.in);
        int elegido;
        for (int i=1;i<=reino.length;i++){
            if (reino[i-1] != null){
                if (reino[i-1].getVive())
                    System.out.println(i+ ". " + reino[i-1].getNombre());
                else
                    System.out.println(i+ ". " + reino[i-1].getNombre() + "(Muerto)");
            }
        }
        do{
            do{
                System.out.print("Ingrese ejercito con cual jugar: ");
                elegido=sc.nextInt()-1;
            } while (elegido < 0 || elegido >= reino.length);
        } while (!reino[elegido].getVive());
        return reino[elegido];
    }
    
    public static void turnoJugador(Ejercito ejercito) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Es el turno de " + ejercito.getNombre() + " en posicion (" 
                + ejercito.getFil() + ", " + ejercito.getCol() + ")");
        System.out.println("Ingrese la direccion para mover el ejercito "
                + "(arriba, abajo, izquierda, derecha): ");
        String direccion = scanner.nextLine();
        moverEjercito(ejercito, direccion);
        mostrarMapa();
    }
    
    private static void moverEjercito(Ejercito ejercito, String direccion) {
            int nuevaFila = ejercito.getFil();
            int nuevaColumna = ejercito.getCol();

            // Ajustar la posición en función de la dirección
            switch (direccion.toLowerCase()) {
                case "arriba":
                    nuevaFila -= 1;
                    break;
                case "abajo":
                    nuevaFila += 1;
                    break;
                case "izquierda":
                    nuevaColumna -= 1;
                    break;
                case "derecha":
                    nuevaColumna += 1;
                    break;
                default:
                    System.out.println("Direccion invalida. Usa 'arriba', 'abajo', 'izquierda' o 'derecha'.");
                    return;
            }

            // Verificar si la nueva posición está dentro de los límites del tablero
            if (nuevaFila < 0 || nuevaFila >= 10 || nuevaColumna < 0 || nuevaColumna >= 10) {
                System.out.println("Movimiento fuera de los limites del tablero. Intenta otra direccion.");
                return;
            }

            // Realizar el movimiento o batalla si la posición está ocupada
            Ejercito ejercitoEnPosicion = Mapa.getMapaEjercitos()[nuevaFila][nuevaColumna];
            if (ejercitoEnPosicion == null) {
                // Posición vacía, realiza el movimiento
                Mapa.getMapaEjercitos()[ejercito.getFil()][ejercito.getCol()] = null; // Libera posición actual
                ejercito.setFil(nuevaFila);
                ejercito.setCol(nuevaColumna);
                Mapa.getMapaEjercitos()[nuevaFila][nuevaColumna] = ejercito; // Mueve a la nueva posición
                System.out.println(ejercito.getNombre() + " se ha movido a (" + nuevaFila + ", " + nuevaColumna + ").");
            } else if (ejercitoEnPosicion.getNumReino() != ejercito.getNumReino()) {
                    iniciarBatalla(ejercito, ejercitoEnPosicion, nuevaFila, nuevaColumna);
            } else {
                System.out.println("Ya hay un soldado aliado en esa posicion. Intenta otra direccion.");
            }
    }
    
    private static void iniciarBatalla(Ejercito atacante, Ejercito enemigo, int newFila, int newColumna){
        System.out.println("Se enfrenta" + atacante.getNombre() + " VS " + enemigo.getNombre());
        Soldado.jugarSoldados(atacante, enemigo);
        if (Soldado.contadorSoldado(atacante) == 0){
            System.out.println("El ejercito enemigo gana! ");
            atacante.setVive(false);
            Mapa.getMapaEjercitos()[atacante.getFil()][atacante.getCol()]=null;
            Mapa.getMapaEjercitos()[enemigo.getFil()][enemigo.getCol()]=null;
            Mapa.getMapaEjercitos()[newFila][newColumna]=enemigo;
        } else if (Soldado.contadorSoldado(enemigo) == 0){
            System.out.println("El ejercito atacante gana! ");
            enemigo.setVive(false);
            Mapa.getMapaEjercitos()[enemigo.getFil()][enemigo.getCol()]=null;
            Mapa.getMapaEjercitos()[atacante.getFil()][atacante.getCol()]=null;
            Mapa.getMapaEjercitos()[newFila][newColumna]=atacante;
        }
    }
    
    public static void mostrarEjercito(Ejercito ejercito){
        for (Soldado soldado : ejercito.getEjercito()){
            if (!(soldado == null)){
                if (soldado.getVive())
                System.out.println("\n" + soldado.toString());
            }
        }
        System.out.println("\n--------------------------------------------------------");
    }
    
    public static int contadorEjercito(Ejercito[] reino){
        int contador=0;
        for (int i=0;i<reino.length;i++){
            if (reino[i] != null){
                if (reino[i].getVive())
                    contador++;
            }
        }
        return contador;
    }
    
    public static void mostrarMapa(){
        for (int i=0;i<Mapa.getMapaEjercitos().length;i++){
            System.out.println("---------------------------------------------------"
                    + "-----------------------------------------------------------");
            for (int j=0;j<Mapa.getMapaEjercitos()[i].length;j++)
                if (j < Mapa.getMapaEjercitos()[i].length-1){
                    if (Mapa.getMapaEjercitos()[i][j] == null)
                        System.out.print("          |");
                    else
                        System.out.print(Mapa.getMapaEjercitos()[i][j].getNombre() + "|");
                    }
                    else if (j == Mapa.getMapaEjercitos()[i].length-1){
                        if (Mapa.getMapaEjercitos()[i][j] == null)
                            System.out.println("          |");
                        else
                        System.out.println(Mapa.getMapaEjercitos()[i][j].getNombre());
                    }
                }
        System.out.println("------------------------------------------------------"
                    + "--------------------------------------------------------\n");
    }
    
    public static void ordenarInsercionEjercitos(Ejercito ejercito) {
        for (int i = 1; i < ejercito.getEjercito().length; i++) {
                if (ejercito.getEjercito()[i] != null){
                Soldado soldado = ejercito.getEjercito()[i];
                int j = i - 1;
                while (j >= 0 && ejercito.getEjercito()[j].getVidaActual() < soldado.getVidaActual()) {
                    ejercito.getEjercito()[j + 1]= ejercito.getEjercito()[j];
                    j--;
                }
                ejercito.getEjercito()[j + 1]= soldado;
            }
        }
    }
    
    public static void ordenarBurbujaEjercitos(Ejercito ejercito) {
        for (int i = 0; i < ejercito.getEjercito().length - 1; i++) {
            if (ejercito.getEjercito()[i] != null){
                for (int j = 0; j < ejercito.getEjercito().length - 1 - i; j++) {
                    if (ejercito.getEjercito()[j] != null && ejercito.getEjercito()[j+1] != null){
                        if (ejercito.getEjercito()[j].getVidaActual() < ejercito.getEjercito()[j + 1].getVidaActual()) {
                            Soldado temp = ejercito.getEjercito()[j];
                            ejercito.getEjercito()[j] = ejercito.getEjercito()[j + 1];
                            ejercito.getEjercito()[j + 1] = temp;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Ejercito{" + "ejercito=" + ejercito + ", nombre=" + nombre + 
                ", numEjercito=" + numEjercito + ", numReino=" + numReino + ", vive=" 
                + vive + '}';
    }
    
    
}

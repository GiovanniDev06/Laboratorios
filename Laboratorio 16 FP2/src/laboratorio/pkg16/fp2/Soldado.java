package laboratorio.pkg16.fp2;

import java.util.*;
public class Soldado {
    private String nombre,actitud;
    private int nivelAtaque,nivelDefensa,nivelVida,vidaActual,velocidad,
            fil,col,numEjercito,numReino;
    private static int numSoldados; 
    private boolean vive;
    
    public Soldado(String nombre) {
        Random r=new Random();
        this.nombre = nombre;
        this.actitud = "normal";
        this.nivelVida=r.nextInt(5)+1;
        this.nivelDefensa=r.nextInt(5)+1;
        this.nivelAtaque=r.nextInt(5)+1;
        this.vidaActual=this.nivelVida;
        this.vive=true;
    }
    //Metodos get y set
    
    public String getActitud() {
        return actitud;
    }

    public void setActitud(String actitud) {
        this.actitud = actitud;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNivelAtaque() {
        return nivelAtaque;
    }

    public boolean getVive() {
        return vive;
    }

    public void setVive(boolean vive) {
        this.vive = vive;
    }

    public int getFil() {
        return fil;
    }

    public Soldado setFil(int fil) {
        this.fil = fil;
        return this;
    }

    public int getCol() {
        return col;
    }

    public Soldado setCol(int col) {
        this.col = col;
        return this;
    }

    public int getNumEjercito() {
        return numEjercito;
    }

    public void setNumEjercito(int numEjercito) {
        this.numEjercito = numEjercito;
    }

    public int getNumReino() {
        return numReino;
    }

    public void setNumReino(int numReino) {
        this.numReino = numReino;
    }
    
    //metodos soldado
    
    public void atacar(){
        this.actitud="ofensivo";
        avanzar();
    }
    
    public void avanzar(){
        this.velocidad++;
    }
    
    public void defender(){
        this.actitud="defensivo";
        if (vidaActual < nivelVida)
            vidaActual++;
        else
            System.out.println("No se puede aumentar más vida de la que tiene!");
        this.velocidad=0;
    }
    
    public void huir(){
        this.actitud="fuga";
        this.velocidad += 2;
    }
    
    public void retroceder(){
        if (this.velocidad > 0){
            defender();
        }
        if (this.velocidad == 0){
            velocidad--;
        }
    }
    
    public void morir(){
        if (this.getVidaActual() <= 0)
            this.vive=false;
    }
    
    public void serAtacado(Soldado soldadoEnemigo) {
        Random r=new Random();
        int prob=this.getVidaActual() + soldadoEnemigo.getVidaActual();
        if (this.getVidaActual() > soldadoEnemigo.getVidaActual()){
            if (r.nextInt(prob)+1 <= this.getVidaActual()){
                soldadoEnemigo.vidaActual=0;
                soldadoEnemigo.morir();
            }
            else{
                this.vidaActual=0;
                this.morir();
            }
        } else{
            if (r.nextInt(prob)+1 <= soldadoEnemigo.getVidaActual()){
                this.vidaActual=0;
                this.morir();
            }
            else{
                soldadoEnemigo.vidaActual=0;
                soldadoEnemigo.morir();
            }
        }
    }
    
    public static void ganadorBatalla(Ejercito ejercito1,Ejercito ejercito2){
        if (contadorSoldado(ejercito1) > contadorSoldado(ejercito2)){
            System.out.println("El ejercito 1 Ha ganado");
            Ejercito.mostrarEjercito(ejercito1);
        } else{
            System.out.println("El ejercito 2 Ha ganado");
            Ejercito.mostrarEjercito(ejercito1);
        }
    }
    
    public static int contadorSoldado(Ejercito ejercito){
        int contador=0;
        for (int i=0;i<ejercito.getEjercito().length;i++){
            if (ejercito.getEjercito()[i] != null){
                if (ejercito.getEjercito()[i].getVive())
                    contador++;
            }
        }
        return contador;
    }
    
    public static void turnoJugador(Soldado soldado) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Es el turno de " + soldado.getNombre() + " en posicion (" 
                + soldado.getFil() + ", " + soldado.getCol() + ")");
        System.out.println("Ingrese la direccion para mover el soldado "
                + "(arriba, abajo, izquierda, derecha): ");
        String direccion = scanner.nextLine();
        moverSoldado(soldado, direccion);
        Soldado.mostrarMapa();
    }
    
    public static Soldado elegirSoldado(Ejercito ejercito){
        Scanner sc=new Scanner(System.in);
        int elegido;
        for (int i=1;i<=ejercito.getEjercito().length;i++){
            if (ejercito.getEjercito()[i-1] != null){
                if (ejercito.getEjercito()[i-1].getVive())
                    System.out.println(i+ ". " + ejercito.getEjercito()[i-1].getNombre());
                else
                    System.out.println(i+ ". " + ejercito.getEjercito()[i-1].getNombre() + "(Muerto)");
            }
        }
        do{
            do{
                System.out.print("Ingrese soldado con cual jugar: ");
                elegido=sc.nextInt()-1;
            } while (elegido < 0 || elegido >= ejercito.getEjercito().length);
        } while (!ejercito.getEjercito()[elegido].getVive());
        return ejercito.getEjercito()[elegido];
    }
    
    private static void moverSoldado(Soldado soldado, String direccion) {
            int nuevaFila = soldado.getFil();
            int nuevaColumna = soldado.getCol();

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
            Soldado soldadoEnPosicion = Mapa.getMapaSoldados()[nuevaFila][nuevaColumna];
            if (soldadoEnPosicion == null) {
                // Posición vacía, realiza el movimiento
                Mapa.getMapaSoldados()[soldado.getFil()][soldado.getCol()] = null; // Libera posición actual
                soldado.setFil(nuevaFila);
                soldado.setCol(nuevaColumna);
                soldado.avanzar();
                Mapa.getMapaSoldados()[nuevaFila][nuevaColumna] = soldado; // Mueve a la nueva posición
                System.out.println(soldado.getNombre() + " se ha movido a (" + nuevaFila + ", " + nuevaColumna + ").");
            } else if (soldadoEnPosicion.getNumReino() != soldado.getNumReino()) {
                    iniciarBatalla(soldado, soldadoEnPosicion, nuevaFila, nuevaColumna);
            } else {
                System.out.println("Ya hay un soldado aliado en esa posicion. Intenta otra direccion.");
            }
    }
    
    private static void iniciarBatalla(Soldado atacante,Soldado enemigo,int newFila,int newColumna){
        System.out.println("Se enfrenta" + atacante.getNombre() + " VS " + enemigo.getNombre());
        enemigo.serAtacado(atacante);
        if (atacante.getVive())
            System.out.println("El soldado " + enemigo.getNombre() + "ha muerto");
        else
            System.out.println("El soldado " + atacante.getNombre() + "ha muerto");
        
        if (atacante.getVidaActual() <= 0) {
            System.out.println(atacante.getNombre() + " ha muerto en la batalla.");
            Mapa.getMapaSoldados()[atacante.getFil()][atacante.getCol()]=null;
            Mapa.getMapaSoldados()[enemigo.getFil()][enemigo.getCol()]=null;
            Mapa.getMapaSoldados()[newFila][newColumna]=enemigo;
        } else if (enemigo.getVidaActual() <= 0) {
            System.out.println(enemigo.getNombre() + " ha muerto en la batalla.");
            Mapa.getMapaSoldados()[atacante.getFil()][atacante.getCol()]=null;
            Mapa.getMapaSoldados()[enemigo.getFil()][enemigo.getCol()]=null;
            atacante.setFil(enemigo.getFil()).setCol(enemigo.getCol());
            Mapa.getMapaSoldados()[newFila][newColumna]=atacante;
        }
    }
    
    public static void mostrarMapa(){
        for (int i=0;i<Mapa.getMapaEjercitos().length;i++){
            System.out.println("---------------------------------------------------"
                    + "-----------------------------------------------------------");
            for (int j=0;j<Mapa.getMapaEjercitos()[i].length;j++)
                if (j < Mapa.getMapaSoldados()[i].length-1){
                    if (Mapa.getMapaSoldados()[i][j] == null)
                        System.out.print("          |");
                    else
                        System.out.print(Mapa.getMapaSoldados()[i][j].getNombre() + "|");
                    }
                    else if (j == Mapa.getMapaSoldados()[i].length-1){
                        if (Mapa.getMapaSoldados()[i][j] == null)
                            System.out.println("          |");
                        else
                        System.out.println(Mapa.getMapaSoldados()[i][j].getNombre());
                    }
                }
        System.out.println("------------------------------------------------------"
                    + "--------------------------------------------------------\n");
    }
    
    public static void jugarSoldados(Ejercito ejercito1,Ejercito ejercito2){
        Mapa.crearMapaSoldado(ejercito1,ejercito2);
        mostrarMapa();
        Scanner sc=new Scanner(System.in);
            Ejercito.ordenarInsercionEjercitos(ejercito1);
            Ejercito.ordenarBurbujaEjercitos(ejercito2);
            System.out.println("\nEjercito 1: ");
            Ejercito.mostrarEjercito(ejercito1);
            System.out.println("\nEjercito 2: ");
            Ejercito.mostrarEjercito(ejercito2);
            while (Soldado.contadorSoldado(ejercito1) > 0 && Soldado.contadorSoldado(ejercito2) > 0){
                System.out.println("Elija su soldado para pelear: ");
                Soldado.turnoJugador(Soldado.elegirSoldado(ejercito1));
                if (Soldado.contadorSoldado(ejercito1) == 0 || Soldado.contadorSoldado(ejercito2) == 0)
                    break;
                Soldado.turnoJugador(Soldado.elegirSoldado(ejercito2));
                if (Soldado.contadorSoldado(ejercito1) == 0 || Soldado.contadorSoldado(ejercito2) == 0)
                    break;
            }
        Soldado.ganadorBatalla(ejercito1,ejercito2);
    }
    
    public String toString() {
        return nombre + ":\nNivelAtaque=" + nivelAtaque + ", nivelDefensa=" + nivelDefensa 
                + ", nivelVida=" + nivelVida + ", velocidad=" + velocidad + 
                ",\nEjercito=" + numEjercito + ", vidaActual=" + vidaActual+ ", actitud=" 
                + actitud + ", vive=" + vive;
    }
}

import java.util.Scanner;

public class Main {
    public static Scanner ler = new Scanner(System.in);

    public static void Tabuleiro(){

    }

    public static void MenuIniciar(){
        System.out.println("Deseja jogar contra o PC (1) ou uma pessoa (2)? ");
        int jogador = ler.nextInt();
        if(jogador == 2){
            System.out.println("Deseja continuar contra outro jogador? (S/N)");
            String opcJogador = ler.next().toLowerCase();
            if(opcJogador.equals("n"))
                MenuIniciar();
            else if(opcJogador.equals("s"))
                Jogadores();
        }
        else if (jogador == 1)
            System.out.println("Maquina");
    }

    public static void Jogadores(){
        System.out.println("Nome do primeiro jogador: ");
        String nomeJ1 = ler.next().toLowerCase();
        System.out.println("Nome do segundo jogador: ");
        String nomeJ2 = ler.next().toLowerCase();

        System.out.println(nomeJ1+ " VS " +nomeJ2);
    }
    public static void main(String[] args) {
        System.out.println("BEM VINDO!");
        System.out.println("Batalha Naval!");
        MenuIniciar();
    }
}
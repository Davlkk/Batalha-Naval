import java.util.Scanner;
import java.util.Random;

public class Main {
    public static Scanner ler = new Scanner(System.in);
    public static Random aleatorio = new Random();

    public static void main(String[] args) {
        System.out.println("BEM VINDO!");
        System.out.println("----------------------");
        System.out.println("Batalha Naval!");
        System.out.println();

        menuIniciar();
    }

    public static void menuIniciar() {
        System.out.print("Deseja jogar contra o PC (1) ou uma pessoa (2)? ");
        int jogador = ler.nextInt();

        if (jogador == 2) {
            System.out.print("Deseja continuar contra outro jogador? (S/N) ");
            char opcJogador = ler.next().toLowerCase().charAt(0);
            if (opcJogador == 'n')
                menuIniciar();
            else if (opcJogador == 's')
                jogadores();
            else{
                System.out.println("Erro! Tente novamente.");
                menuIniciar();
            }
        }
        else if (jogador == 1)
            System.out.println("falta parte."); //aqui vai o método para jogar com a máquina
        else{
            System.out.println("Opção inválida! Tente novamente.");
            menuIniciar();
        }
    }

    //métodos dos jogadores
    public static void jogadores() {
        System.out.print("Nome do primeiro jogador: ");
        String nomeJ1 = ler.next().toLowerCase();

        System.out.print("Nome do segundo jogador: ");
        String nomeJ2 = ler.next().toLowerCase();

        System.out.println("\n" + nomeJ1 + " VS " + nomeJ2 + "!!\n");

        alocarJogadores(nomeJ1);

        System.out.println("Troque de jogador. Agora é a vez de " + nomeJ2 + "!\n");

        alocarJogadores(nomeJ2);

        System.out.println("Barcos alocados!");
    }

    private static void alocarJogadores(String nome){
        System.out.print(nome+", deseja colocar os barcos manualmente (1) ou adiciona-los automaticamente (2)? ");
        int alocarJogador = ler.nextInt();

        if(alocarJogador == 1){
            System.out.print(nome+", deseja continuar com a alocação manual? (S/N) ");
            char opcAlocar = ler.next().toLowerCase().charAt(0);
            if(opcAlocar == 'n')
                alocarJogadores(nome);
            else if (opcAlocar == 's'){
                System.out.println("Aloque seus barcos " + nome + ": ");
                iniciarTabuleiro();
                exibirTabuleiro(false);
                alocarBarcos();
            }
            else{
                System.out.println("Opção Inválida Tente Novamente.");
            }
        }

        else if (alocarJogador == 2) {
            System.out.print(nome+", deseja continuar com a alocação automática? (S/N) ");
            char opcAlocar = ler.next().toLowerCase().charAt(0);
            if(opcAlocar == 'n')
                alocarJogadores(nome);
            else if (opcAlocar == 's'){
                System.out.println("Alocando automaticamente...");
                iniciarTabuleiro();
                alocarBarcosAutomaticamente();
                exibirTabuleiro(false);
            }
        }
    }

    //métodos do tabuleiro
    public static int tamanho = 11;
    public static final char navio = 'N';
    public static final char agua = '~';
    public static char[][] tabuleiro = new char[tamanho][tamanho];

    public static void iniciarTabuleiro() {
        for (int l = 0; l < tamanho; l++) {
            for (int c = 0; c < tamanho; c++) {
                tabuleiro[l][c] = agua;
            }
        }
    }

    public static void exibirTabuleiro(boolean esconderNavios) {
        System.out.print("  ");
        for (int l = 1; l < tamanho; l++){
            System.out.printf("%02d ", l);
        }
        System.out.println();

        for (int l = 1; l < tamanho; l++) {
            System.out.print((char)('A' + l - 1) + "  ");
            for (int c = 1; c < tamanho; c++) {
                char simbolo = tabuleiro[l][c];
                if (esconderNavios && simbolo == navio) {
                    System.out.print("~  ");
                } else {
                    System.out.print(simbolo + "  ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean podeAlocar(int linha, int coluna, int tamanhoBarco, char direcao) {
        //confirmação de posição, se tiver tudo de acordo, segue o código, senão retorna ao início.
        if (linha < 1 || linha >= tamanho || coluna < 1 || coluna >= tamanho)
            return false;

        if (direcao == 'H') {
            if (coluna + tamanhoBarco - 1 >= tamanho)
                return false;
            for (int c = 0; c < tamanhoBarco; c++) {
                if (tabuleiro[linha][coluna + c] != agua)
                    return false;
            }
        } else if (direcao == 'V') {
            if (linha + tamanhoBarco - 1 >= tamanho)
                return false;
            for (int l = 0; l < tamanhoBarco; l++) {
                if (tabuleiro[linha + l][coluna] != agua)
                    return false;
            }
        } else {
            return false;
        }

        return true;
    }

    public static void alocarBarcos() {
        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int i = 0; i < tamanhos.length; i++) {
            int tamanhoBarco = tamanhos[i];
            boolean alocado = false;

            while (!alocado) {
                System.out.println("\nAlocando navio de tamanho " + tamanhoBarco);
                System.out.print("Informe a linha inicial (A a J): ");
                int linha = ler.nextInt();

                System.out.print("Informe a coluna inicial (1 a 10): ");
                int coluna = ler.nextInt();

                System.out.print("Direção (H para horizontal, V para vertical): ");
                char direcao = ler.next().toUpperCase().charAt(0);

                if (podeAlocar(linha, coluna, tamanhoBarco, direcao)) {
                    for (int j = 0; j < tamanhoBarco; j++) {
                        if (direcao == 'H') {
                            tabuleiro[linha][coluna + j] = navio;
                        } else {
                            tabuleiro[linha + j][coluna] = navio;
                        }
                    }
                    alocado = true;
                    exibirTabuleiro(false);
                } else {
                    System.out.println("Posição inválida. Fora do tabuleiro ou espaço ocupado. Tente novamente.");
                }
            }
        }
    }

    public static void alocarBarcosAutomaticamente() {
        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int tamanhoNavio : tamanhos) {
            boolean alocado = false;

            while (!alocado) {
                int linha = aleatorio.nextInt(10) + 1;
                int coluna = aleatorio.nextInt(10) + 1;
                char direcao = aleatorio.nextBoolean() ? 'H' : 'V';

                if (podeAlocar(linha, coluna, tamanhoNavio, direcao)) {
                    for (int j = 0; j < tamanhoNavio; j++) {
                        if (direcao == 'H') {
                            tabuleiro[linha][coluna + j] = navio;
                        } else {
                            tabuleiro[linha + j][coluna] = navio;
                        }
                    }
                    alocado = true;
                }
            }
        }
    }
}
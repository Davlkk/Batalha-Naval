import java.util.Scanner;
import java.util.Random;


public class Main {
    public static Scanner ler = new Scanner(System.in);
    public static Random aleatorio = new Random();

    public static int tamanho = 11;
    public static final char navio = 'N';
    public static final char agua = '~';
    public static final char acerto = 'X';
    public static final char erro = 'O';

    public static char[][] tabuleiroJogador1 = new char[tamanho][tamanho];
    public static char[][] tabuleiroJogador2 = new char[tamanho][tamanho];

    public static int[][][] naviosJogador1;
    public static int[][][] naviosJogador2;

    public static int[] segmentosNaviosJogador1;
    public static int[] segmentosNaviosJogador2;

    public static void iniciarTabuleiro(char[][] tabuleiro) {
        for (int l = 0; l < tamanho; l++) {
            for (int c = 0; c < tamanho; c++) {
                tabuleiro[l][c] = agua;
            }
        }
    }

    public static void exibirTabuleiro(char[][] tabuleiro, boolean esconderNavios) {
        System.out.print("  ");
        for (int l = 1; l < tamanho; l++) {
            System.out.printf("%02d ", l);
        }
        System.out.println();

        for (int l = 1; l < tamanho; l++) {
            System.out.print((char) ('A' + l - 1) + "  ");
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
    }

    public static void menuIniciar() {
        System.out.print("Deseja jogar contra o PC (1) ou uma pessoa (2)? ");
        int jogador = ler.nextInt();

        if (jogador == 2) {
            System.out.print("Deseja continuar contra outro jogador? (S/N) ");
            char opcJogador = ler.next().toLowerCase().charAt(0);

            if (opcJogador == 'n') {
                System.out.println("Reiniciando o jogo...");
                menuIniciar();
            }
            else if (opcJogador == 's') {
                jogadores();
            }
            else {
                System.out.println("Erro! Tente novamente.");
                menuIniciar();
            }
        }


        else if (jogador == 1) {
            System.out.print("Deseja continuar contra a máquina? (S/N) ");
            char opcJogador = ler.next().toLowerCase().charAt(0);

            if (opcJogador == 'n') {
                System.out.println("Reiniciando o jogo...");
                menuIniciar();
            }
            else if (opcJogador == 's') {
                jogar();
            }
            else {
                System.out.println("Erro! Tente novamente.");
                menuIniciar();
            }
        }

        else {
            System.out.println("Opção inválida! Tente novamente.");
            menuIniciar();
        }
    }

    public static void jogadores() {
        iniciarTabuleiro(tabuleiroJogador1);
        iniciarTabuleiro(tabuleiroJogador2);

        System.out.print("Nome do primeiro jogador: ");
        String nomeJ1 = ler.next();

        System.out.print("Nome do segundo jogador: ");
        String nomeJ2 = ler.next();

        System.out.println("\n" + nomeJ1 + " VS " + nomeJ2 + "!!\n");

        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        naviosJogador1 = new int[tamanhos.length][][];
        naviosJogador2 = new int[tamanhos.length][][];
        segmentosNaviosJogador1 = new int[tamanhos.length];
        segmentosNaviosJogador2 = new int[tamanhos.length];

        for (int i = 0; i < tamanhos.length; i++) {
            segmentosNaviosJogador1[i] = tamanhos[i];
            segmentosNaviosJogador2[i] = tamanhos[i];
        }

        alocarJogadores(nomeJ1, tabuleiroJogador1, naviosJogador1, tamanhos);

        System.out.println("Troque de jogador. Agora é a vez de " + nomeJ2 + "!\n");

        alocarJogadores(nomeJ2, tabuleiroJogador2, naviosJogador2, tamanhos);

        System.out.println("Barcos alocados!");

        boolean jogoEmAndamento = true;

        while (jogoEmAndamento) {
            boolean continuarJogandoJ1 = true;
            while (continuarJogandoJ1 && jogoEmAndamento) {
                continuarJogandoJ1 = turnoJogador(nomeJ1, tabuleiroJogador2, naviosJogador2, segmentosNaviosJogador2);
                jogoEmAndamento = !verificarFimDeJogo(tabuleiroJogador2);

                if (!jogoEmAndamento) {
                    System.out.println(nomeJ1 + " venceu!");
                }
            }

            if (jogoEmAndamento) {
                boolean continuarJogandoJ2 = true;
                while (continuarJogandoJ2 && jogoEmAndamento) {
                    continuarJogandoJ2 = turnoJogador(nomeJ2, tabuleiroJogador1, naviosJogador1, segmentosNaviosJogador1);
                    jogoEmAndamento = !verificarFimDeJogo(tabuleiroJogador1);


                    if (!jogoEmAndamento) {
                        System.out.println(nomeJ2 + " venceu!");
                    }
                }
            }
        }
    }

    private static void alocarJogadores(String nome, char[][] tabuleiro, int[][][] naviosJogador, int[] tamanhos) {
        System.out.print(nome + ", deseja colocar os barcos manualmente (1) ou adiciona-los automaticamente (2)? ");
        int alocarJogador = ler.nextInt();

        if (alocarJogador == 1) {
            System.out.print(nome + ", deseja continuar com a alocação manual? (S/N) ");
            char opcAlocar = ler.next().toLowerCase().charAt(0);
            if (opcAlocar == 'n')
                alocarJogadores(nome, tabuleiro, naviosJogador, tamanhos);
            else if (opcAlocar == 's') {
                System.out.println("Aloque seus barcos " + nome + ": ");
                exibirTabuleiro(tabuleiro, false);
                alocarBarcos(tabuleiro, naviosJogador, tamanhos);
            }
            else {
                System.out.println("Opção Inválida Tente Novamente.");
                alocarJogadores(nome, tabuleiro, naviosJogador, tamanhos);
            }
        }

        else if (alocarJogador == 2) {
            System.out.print(nome + ", deseja continuar com a alocação automática? (S/N) ");
            char opcAlocar = ler.next().toLowerCase().charAt(0);
            if (opcAlocar == 'n')
                alocarJogadores(nome, tabuleiro, naviosJogador, tamanhos);
            else if (opcAlocar == 's') {
                System.out.println("\n Alocando automaticamente... \n");
                alocarBarcosAutomaticamente(tabuleiro, naviosJogador, tamanhos);
                exibirTabuleiro(tabuleiro, false);
            }
            else {
                System.out.println("Opção Inválida Tente Novamente.");
                alocarJogadores(nome, tabuleiro, naviosJogador, tamanhos);
            }
        }

        else {
            System.out.println("Opção Inválida Tente Novamente.");
            alocarJogadores(nome, tabuleiro, naviosJogador, tamanhos);
        }
    }

    public static boolean podeAlocar(char[][] tabuleiro, int linha, int coluna, int tamanhoBarco, char direcao) {
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

    public static void alocarBarcos(char[][] tabuleiro, int[][][] naviosJogador, int[] tamanhos) {
        for (int i = 0; i < tamanhos.length; i++) {
            int tamanhoBarco = tamanhos[i];
            boolean alocado = false;

            while (!alocado) {
                System.out.println("\nAlocando navio de tamanho " + tamanhoBarco);
                System.out.print("Informe a linha inicial (A a J): ");
                char linhaChar = ler.next().toUpperCase().charAt(0);
                int linha = linhaChar - 'A' + 1;

                System.out.print("Informe a coluna inicial (1 a 10): ");
                int coluna = ler.nextInt();

                System.out.print("Direção (H para horizontal, V para vertical): ");
                char direcao = ler.next().toUpperCase().charAt(0);

                if (podeAlocar(tabuleiro, linha, coluna, tamanhoBarco, direcao)) {
                    naviosJogador[i] = new int[tamanhoBarco][2];

                    for (int j = 0; j < tamanhoBarco; j++) {
                        if (direcao == 'H') {
                            tabuleiro[linha][coluna + j] = navio;
                            naviosJogador[i][j][0] = linha;
                            naviosJogador[i][j][1] = coluna + j;
                        } else {
                            tabuleiro[linha + j][coluna] = navio;
                            naviosJogador[i][j][0] = linha + j;
                            naviosJogador[i][j][1] = coluna;
                        }
                    }

                    alocado = true;
                    exibirTabuleiro(tabuleiro, false);
                } else {
                    System.out.println("Posição inválida. Fora do tabuleiro ou espaço ocupado. Tente novamente.");
                }
            }
        }
    }

    public static void alocarBarcosAutomaticamente(char[][] tabuleiro, int[][][] naviosJogador, int[] tamanhos) {
        for (int i = 0; i < tamanhos.length; i++) {
            int tamanhoNavio = tamanhos[i];
            boolean alocado = false;

            while (!alocado) {
                int linha = aleatorio.nextInt(10) + 1;
                int coluna = aleatorio.nextInt(10) + 1;
                char direcao = aleatorio.nextBoolean() ? 'H' : 'V';

                if (podeAlocar(tabuleiro, linha, coluna, tamanhoNavio, direcao)) {
                    naviosJogador[i] = new int[tamanhoNavio][2];

                    for (int j = 0; j < tamanhoNavio; j++) {
                        if (direcao == 'H') {
                            tabuleiro[linha][coluna + j] = navio;
                            naviosJogador[i][j][0] = linha;
                            naviosJogador[i][j][1] = coluna + j;
                        }
                        else {
                            tabuleiro[linha + j][coluna] = navio;
                            naviosJogador[i][j][0] = linha + j;
                            naviosJogador[i][j][1] = coluna;
                        }
                    }
                    alocado = true;
                }
            }
        }
    }

    public static boolean turnoJogador(String nome, char[][] tabuleiroOponente, int[][][] naviosOponente, int[] segmentosNaviosOponente) {
        System.out.println(nome + ", é sua vez de atacar!");
        System.out.print("Informe a linha para atacar (A a J): ");
        char linhaChar = ler.next().toUpperCase().charAt(0);
        int linha = linhaChar - 'A' + 1;

        System.out.print("Informe a coluna para atacar (1 a 10): ");
        int coluna = ler.nextInt();

        if (linha < 1 || linha > 10 || coluna < 1 || coluna > 10) {
            System.out.println("Posição inválida! Fora do tabuleiro. Tente novamente.");
            return turnoJogador(nome, tabuleiroOponente, naviosOponente, segmentosNaviosOponente);
        }

        if (tabuleiroOponente[linha][coluna] == acerto || tabuleiroOponente[linha][coluna] == erro) {
            System.out.println("Você já atacou esta posição! Tente novamente.");
            return turnoJogador(nome, tabuleiroOponente, naviosOponente, segmentosNaviosOponente);
        }

        int[] resultado = atacar(tabuleiroOponente, linha, coluna, naviosOponente, segmentosNaviosOponente);
        boolean acertou = resultado[0] == 1;
        boolean navioDestruido = resultado[1] == 1;

        exibirTabuleiro(tabuleiroOponente, true);

        if (acertou) {
            System.out.println("Você acertou um navio!");
            if (navioDestruido) {
                System.out.println("Você destruiu um navio! A vez passa para o outro jogador.");
                return false;
            }
            else {
                System.out.println("Você tem direito a outro ataque!");
                return true;
            }
        }

        else {
            System.out.println("Você errou! A vez passa para o outro jogador.");
            return false;
        }
    }

    public static boolean turnoComputador(char[][] tabuleiroJogador, int[][][] naviosJogador, int[] segmentosNaviosJogador) {
        System.out.println("Computador está jogando...");

        while (true) {
            int linha = aleatorio.nextInt(10) + 1;
            int coluna = aleatorio.nextInt(10) + 1;

            char alvo = tabuleiroJogador[linha][coluna];

            if (alvo != acerto && alvo != erro) {
                System.out.println("O computador atacou a posição " + (char)('A' + linha - 1) + coluna);
                int[] resultado = atacar(tabuleiroJogador, linha, coluna, naviosJogador, segmentosNaviosJogador);

                boolean acertou = resultado[0] == 1;
                boolean navioDestruido = resultado[1] == 1;

                exibirTabuleiro(tabuleiroJogador, false);

                if (acertou) {
                    System.out.println("O computador acertou um navio!");
                    if (navioDestruido) {
                        System.out.println("O computador destruiu um navio! A vez passa para você.");
                        return false;
                    } else {
                        System.out.println("O computador tem direito a outro ataque!");
                        return true;
                    }
                } else {
                    System.out.println("O computador errou! A vez passa para você.");
                    return false;
                }
            }
        }
    }

    public static int[] atacar(char[][] tabuleiro, int linha, int coluna, int[][][] navios, int[] segmentosNavios) {
        int[] resultado = new int[2];

        if (tabuleiro[linha][coluna] == navio) {
            tabuleiro[linha][coluna] = acerto;
            resultado[0] = 1;

            int navioAtingido = -1;

            for (int i = 0; i < navios.length; i++) {
                boolean encontrou = false;
                for (int j = 0; j < navios[i].length && !encontrou; j++) {
                    if (navios[i][j][0] == linha && navios[i][j][1] == coluna) {
                        navioAtingido = i;
                        encontrou = true;
                    }
                }
            }
            if (navioAtingido != -1) {
                segmentosNavios[navioAtingido]--;
                if (segmentosNavios[navioAtingido] == 0) {
                    resultado[1] = 1;
                }
            }
        }

        else {
            tabuleiro[linha][coluna] = erro;
            resultado[0] = 0;
        }

        return resultado;
    }

    public static boolean verificarFimDeJogo(char[][] tabuleiro) {
        for (int l = 1; l < tamanho; l++) {
            for (int c = 1; c < tamanho; c++) {
                if (tabuleiro[l][c] == navio) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void jogar() {
        iniciarTabuleiro(tabuleiroJogador1);
        iniciarTabuleiro(tabuleiroJogador2);

        int[] tamanhos = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
        naviosJogador1 = new int[tamanhos.length][][];
        naviosJogador2 = new int[tamanhos.length][][];
        segmentosNaviosJogador1 = new int[tamanhos.length];
        segmentosNaviosJogador2 = new int[tamanhos.length];

        for (int i = 0; i < tamanhos.length; i++) {
            segmentosNaviosJogador1[i] = tamanhos[i];
            segmentosNaviosJogador2[i] = tamanhos[i];
        }

        System.out.println("Jogador 1 (Você):");
        alocarJogadores("Jogador 1", tabuleiroJogador1, naviosJogador1, tamanhos);

        System.out.println("\nComputador está posicionando seus navios... \n");
        alocarBarcosAutomaticamente(tabuleiroJogador2, naviosJogador2, tamanhos);

        System.out.println("Tabuleiro do Computador (navios ocultos):");
        exibirTabuleiro(tabuleiroJogador2, true);

        boolean jogoEmAndamento = true;
        while (jogoEmAndamento) {
            boolean continuarJogando = true;

            while (continuarJogando && jogoEmAndamento) {
                continuarJogando = turnoJogador("Jogador 1", tabuleiroJogador2, naviosJogador2, segmentosNaviosJogador2);
                if (verificarFimDeJogo(tabuleiroJogador2)) {
                    System.out.println("Jogador 1 venceu!");
                    jogoEmAndamento = false;
                    continuarJogando = false;
                }
            }
            if (jogoEmAndamento) {
                continuarJogando = true;
                while (continuarJogando && jogoEmAndamento) {
                    continuarJogando = turnoComputador(tabuleiroJogador1, naviosJogador1, segmentosNaviosJogador1);
                    if (verificarFimDeJogo(tabuleiroJogador1)) {
                        System.out.println("Computador venceu!");
                        jogoEmAndamento = false;
                        continuarJogando = false;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("BEM VINDO!");
        System.out.println("----------------------");
        System.out.println("Batalha Naval!");
        System.out.println();

        menuIniciar();
    }
}
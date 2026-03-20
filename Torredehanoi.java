public class Torredehanoi {

    private int[] elementos;
    private int topo;
    public static int contadorMovimentos = 0;
    private String nome;

    public Torredehanoi(int capacidade, String nome) {
        elementos = new int[capacidade];
        topo = -1;
        this.nome = nome;
    }

    // Adicionar discos ao ultimo elemento da lista
    public void push(int valor) {
        if (topo == elementos.length - 1) {
            System.out.println("Torre cheia, retire");
        }
        elementos[++topo] = valor;
    }
    
    // Verificar se está vazio
    public boolean isEmpty() {
        return topo == -1;
    }

    // Remover o ultimo disco
    public int pop() {
        if (isEmpty()) {
            System.out.println("Pilha vazia");
        }
        return elementos[topo--];
    }
    
    // Tamanho do disco
    public int peek() {
        if (isEmpty()) {
            System.out.println("Pilha vazia");
        }
        return elementos[topo];
    }
    
    // Mostrar lista
    public void mostrar() {
        if (isEmpty()) {
            return;
        }
        for (int i = topo; i >= 0; i--){
            System.out.println(elementos[i]);
        }
    }



    public static void moverDisco(Torredehanoi origem, Torredehanoi destino) {
        // Se a torre de destino estiver vazia, qualquer disco pode entrar

        if(destino.isEmpty()){
            int temp = origem.pop(); 
            destino.push(temp);
            System.out.println("Move disco " + temp + " de " + origem.nome + " -> " + destino.nome);
        }

        // Verificação de que o disco que chega tem que ser menor que o que já está lá
        else if (origem.peek() < destino.peek()) {

            int temp = origem.pop();
            destino.push(temp);
            System.out.println("Move disco " + temp + " de " + origem.nome + " -> " + destino.nome);

        } else {

            // Caso tente colocar um disco maior sobre um meno
            System.out.println("Movimento inválido: disco maior sobre disco menor");
        }

        // cada movimento feito, incrementa ao contador de movimentos
        contadorMovimentos++;
    }



    public static void HanoiPilhas(int n, Torredehanoi torreOrigem, Torredehanoi torreDestino, Torredehanoi torreSuporte) {

        // Se houver apenas 1 disco, basta movê-lo direto para o destino

        if(n == 1){
            moverDisco(torreOrigem, torreDestino);
            return;
        }

        // Caso Recursivo:
        if (n > 1){
            // movendo os discos da origem para a auxiliar usando o destino como suporte

            HanoiPilhas(n-1, torreOrigem, torreSuporte, torreDestino);

            // movendo disco maior da origem o que restou para o destino definitivo

            moverDisco(torreOrigem, torreDestino);

            // movendo os discos que estavam na auxiliar para o destino usando a origem como suporte
            HanoiPilhas(n-1, torreSuporte, torreDestino, torreOrigem);
        }
    }

    public static void main(String[] args) {
        // Definindo o número de discos
        int numeroDiscos = 5;
        Torredehanoi torre1 = new Torredehanoi(numeroDiscos, "A");
        Torredehanoi torre2 = new Torredehanoi(numeroDiscos, "B");
        Torredehanoi torre3 = new Torredehanoi(numeroDiscos, "C");

        // Preenchendo a Torre 1, do maior para o menor
        for (int i = numeroDiscos; i > 0; i--) {
            torre1.push(i);
        }

        System.out.println("=== INICIO ===");
        System.out.print("Torre 1: "); torre1.mostrar();

        System.out.println("\nIniciando resolução...");

        // Contando o tempo
        long tempoInicial = System.nanoTime();


        // Chamada do algoritmo
        HanoiPilhas(numeroDiscos, torre1, torre2, torre3);


        // terminando o tempo 
        long tempoFinal = System.nanoTime();

        // Calculando o tempo final de execucao
        long tempoDeExecucao = tempoFinal - tempoInicial;

        System.out.println("\n=== FINAL ===");
        System.out.println("Torre 1 (Origem) vazia? " + torre1.isEmpty());
        System.out.println("Torre 2 (Destino) finalizada:");
        torre2.mostrar();


        System.out.println("\n=== RELATÓRIO ===");
        System.out.println("Quantidade de movimentos realizados: " + contadorMovimentos);
        System.out.println("Tempo de execução: " + tempoDeExecucao + " nanossegundos");
        System.out.println("Complexidade de Tempo: O(2^n) - Exponencial");
        System.out.println("(2^n - 1) para 10 discos: 1023 movimentos.");
    }
}
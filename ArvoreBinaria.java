import java.util.Scanner;
import java.util.Random;

class Node {
    int valor;
    Node esquerda;
    Node direita;

    public Node(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }
}

public class ArvoreBinaria {
    private static Node inserir(Node raiz, int numero) {
        if (raiz == null) {
            return new Node(numero);
        }
        if (numero >= raiz.valor) {
            raiz.direita = inserir(raiz.direita, numero);
        } else {
            raiz.esquerda = inserir(raiz.esquerda, numero);
        }
        return raiz;
    }

    private static void imprimirEmOrdem(Node raiz) {
        if (raiz != null) {
            imprimirEmOrdem(raiz.esquerda);
            System.out.print(raiz.valor + " ");
            imprimirEmOrdem(raiz.direita);
        }
    }

    private static void imprimirPreOrdem(Node raiz) {
        if (raiz != null) {
            System.out.print(raiz.valor + " ");
            imprimirPreOrdem(raiz.esquerda);
            imprimirPreOrdem(raiz.direita);
        }
    }
    
    private static void imprimirPosOrdem(Node raiz) {
        if (raiz != null) {
            imprimirPosOrdem(raiz.esquerda);
            imprimirPosOrdem(raiz.direita);
            System.out.print(raiz.valor + " ");
        }
    }

    private static Node removerMenor(Node raiz) {
        if (raiz == null) {
            return null;
        }
        if (raiz.esquerda == null) {
            return raiz.direita;
        }
        raiz.esquerda = removerMenor(raiz.esquerda);
        return raiz;
    }

    private static Node removerMaior(Node raiz) {
        if (raiz == null) {
            return null;
        }
        if (raiz.direita == null) {
            return raiz.esquerda;
        }
        raiz.direita = removerMaior(raiz.direita);
        return raiz;
    }

    private static Node remover(Node raiz, int numero) {
        if (raiz == null) {
            return null;
        }
        if (numero == raiz.valor) {
            if (raiz.esquerda == null) {
                return raiz.direita;
            } else if (raiz.direita == null) {
                return raiz.esquerda;
            }
            raiz.valor = encontrarMenor(raiz.direita).valor;
            raiz.direita = removerMenor(raiz.direita);
        } else if (numero < raiz.valor) {
            raiz.esquerda = remover(raiz.esquerda, numero);
        } else {
            raiz.direita = remover(raiz.direita, numero);
        }
        return raiz;
    }

    private static Node encontrarMenor(Node raiz) {
        if (raiz == null) {
            return null;
        }
        while (raiz.esquerda != null) {
            raiz = raiz.esquerda;
        }
        return raiz;
    }

    private static void busca(Node raiz, int numero) {
        if (raiz != null) {
            if (raiz.valor == numero) {
                System.out.println("Elemento encontrado!");
                return;
            }
            busca(raiz.esquerda, numero);
            busca(raiz.direita, numero);
        }
    }

    public static void main(String[] args) {
        Scanner dígito = new Scanner(System.in);
        Random random = new Random();

        Node raiz = null;

        System.out.println("Digite quantos elementos deseja ter na árvore: ");
        int quantidade = dígito.nextInt();

        long tempoCriacao = System.currentTimeMillis();

        for (int i = 0; i < quantidade; i++) {
            int numero = random.nextInt(quantidade * 2);
            raiz = inserir(raiz, numero);
        }

        long tempoCriacaoFim = System.currentTimeMillis();
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore criada em " + tempoCriacaoFinal + " milissegundos.");

        System.out.println("\nImprimindo em ordem:");
        imprimirEmOrdem(raiz);

        System.out.println("\nDigite um número para inserir na árvore: ");
        int numeroInserir = dígito.nextInt();
        
        long tempoInsercaoInicio = System.currentTimeMillis();
        raiz = inserir(raiz, numeroInserir);
        long tempoInsercaoFim = System.currentTimeMillis();
        long tempoInsercaoFinal = tempoInsercaoFim - tempoInsercaoInicio;
        
        System.out.println("Imprimindo em ordem após inserir " + numeroInserir + ":");
        imprimirEmOrdem(raiz);
        System.out.println("Tempo de inserção: " + tempoInsercaoFinal + " milissegundos.");
        
        System.out.println("\nRemovendo o menor elemento:");
        long tempoRemocaoMenorInicio = System.currentTimeMillis();
        raiz = removerMenor(raiz);
        long tempoRemocaoMenorFim = System.currentTimeMillis();
        long tempoRemocaoMenorFinal = tempoRemocaoMenorFim - tempoRemocaoMenorInicio;
        System.out.println("Imprimindo em ordem após a remoção do menor elemento:");
        imprimirEmOrdem(raiz);
        System.out.println("Tempo de remoção do menor elemento: " + tempoRemocaoMenorFinal + " milissegundos.");

        System.out.println("\nRemovendo o maior elemento:");
        long tempoRemocaoMaiorInicio = System.currentTimeMillis();
        raiz = removerMaior(raiz);
        long tempoRemocaoMaiorFim = System.currentTimeMillis();
        long tempoRemocaoMaiorFinal = tempoRemocaoMaiorFim - tempoRemocaoMaiorInicio;
        System.out.println("Imprimindo em ordem após a remoção do maior elemento:");
        imprimirEmOrdem(raiz);
        System.out.println("Tempo de remoção do maior elemento: " + tempoRemocaoMaiorFinal + " milissegundos.");

        System.out.println("\nDigite um número para remover na árvore: ");
        int numeroRemover = dígito.nextInt();
        long tempoRemocaoInicio = System.currentTimeMillis();
        raiz = remover(raiz, numeroRemover);
        long tempoRemocaoFim = System.currentTimeMillis();
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocaoInicio;
        System.out.println("Imprimindo em ordem após a remoção do elemento " + numeroRemover + ":");
        imprimirEmOrdem(raiz);
        System.out.println("Tempo de remoção do elemento " + numeroRemover + ": " + tempoRemocaoFinal + " milissegundos.");

        System.out.println("\nDigite um número para buscar na árvore: ");
        int numeroBuscar = dígito.nextInt();
        long tempoBuscaInicio = System.currentTimeMillis();
        busca(raiz, numeroBuscar);
        long tempoBuscaFim = System.currentTimeMillis();
        long tempoBuscaFinal = tempoBuscaFim - tempoBuscaInicio;
        System.out.println("Tempo de busca do elemento " + numeroBuscar + ": " + tempoBuscaFinal + " milissegundos.");

        System.out.println("\nImprimindo em pré-ordem:");
        imprimirPreOrdem(raiz);

        System.out.println("\nImprimindo em pós-ordem:");
        imprimirPosOrdem(raiz);

        dígito.close();
    }
}

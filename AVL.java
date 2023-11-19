import java.util.Scanner;
import java.util.Random;

class Node {
    public int valor;
    public Node esquerda;
    public Node direita;

    public Node(int valor) {
        this.valor = valor;
        this.esquerda = null;
        this.direita = null;
    }
}

public class AVL {
    public static Node inserir(Node raiz, int numero) {
        if (raiz == null) {
            return new Node(numero);
        }
        if (numero >= raiz.valor) {
            raiz.direita = inserir(raiz.direita, numero);
        } else if (numero < raiz.valor) {
            raiz.esquerda = inserir(raiz.esquerda, numero);
        } else {
            return raiz;
        }

        int balanceamento = calcularAltura(raiz);

        if (balanceamento < -1) {
            System.out.println("A subarvore à direita é maior do que a esquerda");
            if (numero > raiz.direita.valor) {
                // Rotação à esquerda
                return rotacaoEsquerda(raiz);
            } else {
                // Rotação dupla à esquerda
                raiz.direita = rotacaoDireita(raiz.direita);
                return rotacaoEsquerda(raiz);
            }
        } else if (balanceamento > 1) {
            System.out.println("A subarvore à esquerda é maior do que a direita");
            if (numero < raiz.esquerda.valor) {
                // Rotação á direita
                return rotacaoDireita(raiz);
            } else {
                // Rotação dupla à direita
                raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
                return rotacaoDireita(raiz);
            }
        }

        return raiz;
    }

    public static void imprimirEmOrdem(Node raiz) {
        if (raiz != null) {
            imprimirEmOrdem(raiz.esquerda);
            System.out.print(raiz.valor + " ");
            imprimirEmOrdem(raiz.direita);
        }
    }

    public static void imprimirPreOrdem(Node raiz) {
        if (raiz != null) {
            System.out.print(raiz.valor + " ");
            imprimirPreOrdem(raiz.esquerda);
            imprimirPreOrdem(raiz.direita);
        }
    }

    public static void imprimirPosOrdem(Node raiz) {
        if (raiz != null) {
            imprimirPosOrdem(raiz.esquerda);
            imprimirPosOrdem(raiz.direita);
            System.out.print(raiz.valor + " ");
        }
    }

    public static int calcularAltura(Node raiz) {
        if (raiz == null) {
            return 0;
        }
        int alturaEsquerda = encontrarPosicao(raiz.esquerda);
        int alturaDireita = encontrarPosicao(raiz.direita);

        return alturaEsquerda - alturaDireita;
    }

    public static Node rotacaoDireita(Node raiz) {
        if (raiz != null && raiz.esquerda != null) {
            Node novaRaiz = raiz.esquerda;
            Node temp = novaRaiz.direita;
            novaRaiz.direita = raiz;
            raiz.esquerda = temp;
            return novaRaiz;
        }
        return raiz;
    }

    public static Node rotacaoEsquerda(Node raiz) {
        if (raiz != null && raiz.direita != null) {
            Node novaRaiz = raiz.direita;
            Node temp = novaRaiz.esquerda;
            novaRaiz.esquerda = raiz;
            raiz.direita = temp;
            return novaRaiz;
        }
        return raiz;
    }

    public static int encontrarPosicao(Node raiz) {
        if (raiz == null) {
            return 0;
        } else {
            int alturaEsquerda = encontrarPosicao(raiz.esquerda);
            int alturaDireita = encontrarPosicao(raiz.direita);

            if (alturaEsquerda > alturaDireita) {
                return alturaEsquerda + 1;
            } else {
                return alturaDireita + 1;
            }
        }
    }

    public static Node encontrarMaior(Node raiz) {
        if (raiz == null) {
            return null;
        }

        while (raiz.direita != null) {
            raiz = raiz.direita;
        }

        return raiz;
    }

    public static Node encontrarMenor(Node raiz) {
        if (raiz == null) {
            return null;
        }

        while (raiz.esquerda != null) {
            raiz = raiz.esquerda;
        }

        return raiz;
    }

    public static boolean busca(Node raiz, int elemento) {
        if (raiz != null) {
            if (raiz.valor == elemento) {
                return true;
            }
    
            if (busca(raiz.esquerda, elemento) || busca(raiz.direita, elemento)) {
                return true;
            }
        }
    
        return false;
    }
    

    public static Node removerElemento(Node raiz, int elemento) {
        if (raiz == null) {
            return raiz; 
        }

        // Verifica se o elemento a ser removido é menor que o valor na raiz
        if (elemento < raiz.valor) {
            raiz.esquerda = removerElemento(raiz.esquerda, elemento);
        }
        // Verifica se o elemento a ser removido é maior que o valor na raiz
        else if (elemento > raiz.valor) {
            raiz.direita = removerElemento(raiz.direita, elemento);
        }
        // Caso o elemento seja igual ao valor na raiz (nó a ser removido)
        else {
            // Verificar se o nó possui um ou nenhum filho
            if (raiz.esquerda == null || raiz.direita == null) {
                // Inicializa uma variável temporária para armazenar uma referência ao filho do nó atual, se ele tiver apenas um filho
                Node temp = null;
                // Seleciona o filho não nulo (ou nulo, se não houver filho)
                if (temp == raiz.esquerda) { // Se esquerda for null, da getDireita
                    temp = raiz.direita;
                } else {
                    temp = raiz.esquerda;
                }

                // Se não tem filhos, apenas retorna o filho (pode ser nulo)
                if (temp == null) {
                    raiz = null;
                } else {
                    // Caso com um filho, copia o conteúdo do filho
                    raiz = temp;
                }
            }
            // 2 filhos
            else {
                // Encontra o sucessor em ordem na subárvore direita
                Node temp = encontrarMenor(raiz.direita);

                // Copia o conteúdo do sucessor em ordem para este nó
                raiz.valor = temp.valor;

                // Remover o sucessor em ordem
                raiz.direita = removerElemento(raiz.direita, temp.valor);
            }
        }

        // Se a árvore tinha apenas um nó, retorne
        if (raiz == null) {
            return raiz;
        }

        int balanceamento = calcularAltura(raiz);

        if (balanceamento > 1) {
            if (raiz.valor > raiz.esquerda.valor) {
                // Rotação à direita simples
                return rotacaoDireita(raiz);
            } else {
                // Rotação à esquerda seguida por rotação à direita
                raiz.esquerda = rotacaoEsquerda(raiz.esquerda);
                return rotacaoDireita(raiz);
            }
        }

        if (balanceamento < -1) {
            if (raiz.valor < raiz.direita.valor) {
                // Rotação à esquerda simples
                return rotacaoEsquerda(raiz);
            } else {
                // Rotação à direita seguida por rotação à esquerda
                raiz.direita = rotacaoDireita(raiz.direita);
                return rotacaoEsquerda(raiz);
            }
        }

        return raiz;
    }


    public static void main(String[] args) {
        Scanner dígito = new Scanner(System.in);
        Random random = new Random();

        Node raiz = null;

        System.out.println("Digite quantos elementos deseja ter na árvore AVL: ");
        int quantidade = dígito.nextInt();

        long tempoCriacao = System.currentTimeMillis();

        for (int i = 0; i < quantidade; i++) {
            int numero = random.nextInt(quantidade);
            raiz = inserir(raiz, numero);
        }

        long tempoCriacaoFim = System.currentTimeMillis();
        long tempoCriacaoFinal = tempoCriacaoFim - tempoCriacao;

        System.out.println("Árvore AVL criada em " + tempoCriacaoFinal + " milissegundos.");

        imprimirPreOrdem(raiz);

        System.out.println("\nDigite um número para adicionar: ");
        int adicionar = dígito.nextInt();

        long tempoInsercao = System.currentTimeMillis();

        raiz = inserir(raiz, adicionar);

        long tempoInsercaoFim = System.currentTimeMillis();
        long tempoInsercaoFinal = tempoInsercaoFim - tempoInsercao;

        System.out.println("Tempo de inserção: " + tempoInsercaoFinal + " milissegundos");

        System.out.println("\nDigite um número para remover: ");
        int remover = dígito.nextInt();

        long tempoRemocao = System.currentTimeMillis();

        raiz = removerElemento(raiz, remover);

        long tempoRemocaoFim = System.currentTimeMillis();
        long tempoRemocaoFinal = tempoRemocaoFim - tempoRemocao;

        System.out.println("Tempo de remoção: " + tempoRemocaoFinal + " milissegundos");

        imprimirPreOrdem(raiz);

        System.out.println("\nDigite um número para buscar: ");
        int buscar = dígito.nextInt();

        long tempoBusca = System.currentTimeMillis();

        boolean encontrado = busca(raiz, buscar);

        long tempoBuscaFim = System.currentTimeMillis();
        long tempoBuscaFinal = tempoBuscaFim - tempoBusca;

        if (encontrado) {
            System.out.println("Número encontrado");
        } else {
            System.out.println("O número não foi encontrado");
        }

        System.out.println("Tempo de busca: " + tempoBuscaFinal + " milissegundos.");
    }
}

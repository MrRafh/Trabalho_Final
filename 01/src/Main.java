import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    private Grafo grafo;
    private JFrame frame;
    private JTextArea outputArea;

    public Main() {
        grafo = new Grafo();
        frame = new JFrame("Gerenciamento de Grafos");
        outputArea = new JTextArea(15, 40);
        outputArea.setEditable(false);
    }

    public void iniciar() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 2));

        adicionarBotoes(panel);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        frame.pack();
        frame.setVisible(true);
    }

    private void adicionarBotoes(JPanel panel) {
        JButton btnAdicionarVertice = new JButton("Adicionar Vértice");
        btnAdicionarVertice.addActionListener(e -> adicionarVertice());

        JButton btnAdicionarAresta = new JButton("Adicionar Aresta");
        btnAdicionarAresta.addActionListener(e -> adicionarAresta());

        JButton btnRemoverVertice = new JButton("Remover Vértice");
        btnRemoverVertice.addActionListener(e -> removerVertice());

        JButton btnRemoverAresta = new JButton("Remover Aresta");
        btnRemoverAresta.addActionListener(e -> removerAresta());

        JButton btnVisualizarGrafo = new JButton("Visualizar Grafo");
        btnVisualizarGrafo.addActionListener(e -> visualizarGrafo());

        JButton btnGrauVertice = new JButton("Grau de um Vértice");
        btnGrauVertice.addActionListener(e -> grauVertice());

        JButton btnVerificarConexo = new JButton("Verificar Grafo Conexo");
        btnVerificarConexo.addActionListener(e -> verificarConexo());

        JButton btnMatrizAdjacencia = new JButton("Converter para Matriz de Adjacência");
        btnMatrizAdjacencia.addActionListener(e -> converterParaMatrizAdjacencia());

        JButton btnBFS = new JButton("Caminhamento em Amplitude (BFS)");
        btnBFS.addActionListener(e -> bfs());

        JButton btnDFS = new JButton("Caminhamento em Profundidade (DFS)");
        btnDFS.addActionListener(e -> dfs());

        JButton btnDijkstra = new JButton("Caminho Mínimo (Dijkstra)");
        btnDijkstra.addActionListener(e -> dijkstra());

        JButton btnPrimMST = new JButton("Árvore Geradora Mínima (Prim)");
        btnPrimMST.addActionListener(e -> primMST());

        panel.add(btnAdicionarVertice);
        panel.add(btnAdicionarAresta);
        panel.add(btnRemoverVertice);
        panel.add(btnRemoverAresta);
        panel.add(btnVisualizarGrafo);
        panel.add(btnGrauVertice);
        panel.add(btnVerificarConexo);
        panel.add(btnMatrizAdjacencia);
        panel.add(btnBFS);
        panel.add(btnDFS);
        panel.add(btnDijkstra);
        panel.add(btnPrimMST);
    }

    private void adicionarVertice() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice:");
        if (vertice != null) {
            grafo.adicionarVertice(vertice);
            outputArea.append("Vértice " + vertice + " adicionado.\n");
        }
    }

    private void adicionarAresta() {
        String vertice1 = JOptionPane.showInputDialog(frame, "Digite o primeiro vértice:");
        String vertice2 = JOptionPane.showInputDialog(frame, "Digite o segundo vértice:");
        int peso = Integer.parseInt(JOptionPane.showInputDialog(frame, "Digite o peso da aresta:"));

        if (vertice1 != null && vertice2 != null) {
            grafo.adicionarAresta(vertice1, vertice2, peso);
            outputArea.append("Aresta adicionada entre " + vertice1 + " e " + vertice2 + " com peso " + peso + ".\n");
        }
    }

    private void removerVertice() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice a ser removido:");
        if (vertice != null) {
            grafo.removerVertice(vertice);
            outputArea.append("Vértice " + vertice + " removido.\n");
        }
    }

    private void removerAresta() {
        String vertice1 = JOptionPane.showInputDialog(frame, "Digite o primeiro vértice:");
        String vertice2 = JOptionPane.showInputDialog(frame, "Digite o segundo vértice:");

        if (vertice1 != null && vertice2 != null) {
            grafo.removerAresta(vertice1, vertice2);
            outputArea.append("Aresta removida entre " + vertice1 + " e " + vertice2 + ".\n");
        }
    }

    private void visualizarGrafo() {
        JFrame frameGrafo = new JFrame("Visualização do Grafo");
        frameGrafo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameGrafo.setSize(400, 400);

        GrafoPanel grafoPanel = new GrafoPanel(grafo);
        frameGrafo.add(grafoPanel);

        frameGrafo.setVisible(true);
    }

    private void grauVertice() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice:");
        if (vertice != null) {
            int grau = grafo.grauVertice(vertice);
            outputArea.append("Grau do vértice " + vertice + ": " + grau + "\n");
        }
    }

    private void verificarConexo() {
        boolean conexo = grafo.ehConexo();
        outputArea.append("O grafo é conexo? " + (conexo ? "Sim" : "Não") + "\n");
    }

    private void converterParaMatrizAdjacencia() {
        int[][] matrizAdjacencia = grafo.paraMatrizAdjacencia();
        outputArea.append("Matriz de Adjacência:\n");
        for (int[] linha : matrizAdjacencia) {
            outputArea.append(Arrays.toString(linha) + "\n");
        }
    }

    private void bfs() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice inicial:");
        if (vertice != null) {
            outputArea.append("Caminhamento em Amplitude (BFS) a partir do vértice " + vertice + ":\n");
            grafo.bfs(vertice);
        }
    }

    private void dfs() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice inicial:");
        if (vertice != null) {
            outputArea.append("Caminhamento em Profundidade (DFS) a partir do vértice " + vertice + ":\n");
            grafo.dfs(vertice);
        }
    }

    private void dijkstra() {
        String vertice = JOptionPane.showInputDialog(frame, "Digite o vértice inicial:");
        if (vertice != null) {
            Map<String, Integer> distancias = grafo.dijkstra(vertice);
            outputArea.append("Distâncias mínimas a partir do vértice " + vertice + ":\n");
            for (Map.Entry<String, Integer> entrada : distancias.entrySet()) {
                outputArea.append(entrada.getKey() + ": " + entrada.getValue() + "\n");
            }
        }
    }

    private void primMST() {
        List<Aresta> mst = grafo.primMST();
        outputArea.append("Árvore Geradora Mínima (Prim):\n");
        for (Aresta aresta : mst) {
            outputArea.append("Aresta de " + aresta.getDestino() + " com peso " + aresta.getPeso() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().iniciar());
    }
}

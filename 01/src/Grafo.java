import java.util.*;

class Grafo {
    private Map<String, List<Aresta>> listaAdjacencias;

    public Grafo() {
        this.listaAdjacencias = new HashMap<>();
    }

    public void adicionarVertice(String vertice) {
        listaAdjacencias.putIfAbsent(vertice, new ArrayList<>());
    }

    public void adicionarAresta(String vertice1, String vertice2, int peso) {
        listaAdjacencias.putIfAbsent(vertice1, new ArrayList<>());
        listaAdjacencias.putIfAbsent(vertice2, new ArrayList<>());
        listaAdjacencias.get(vertice1).add(new Aresta(vertice2, peso));
        listaAdjacencias.get(vertice2).add(new Aresta(vertice1, peso)); // Para grafo não-direcionado
    }

    public void removerVertice(String vertice) {
        listaAdjacencias.values().forEach(e -> e.removeIf(aresta -> aresta.getDestino().equals(vertice)));
        listaAdjacencias.remove(vertice);
    }

    public void removerAresta(String vertice1, String vertice2) {
        List<Aresta> eV1 = listaAdjacencias.get(vertice1);
        List<Aresta> eV2 = listaAdjacencias.get(vertice2);
        if (eV1 != null) eV1.removeIf(aresta -> aresta.getDestino().equals(vertice2));
        if (eV2 != null) eV2.removeIf(aresta -> aresta.getDestino().equals(vertice1));
    }

    public void imprimirGrafo() {
        for (String vertice : listaAdjacencias.keySet()) {
            System.out.print(vertice + " -> ");
            for (Aresta aresta : listaAdjacencias.get(vertice)) {
                System.out.print(aresta.getDestino() + "(" + aresta.getPeso() + ") ");
            }
            System.out.println();
        }
    }

    public int grauVertice(String vertice) {
        return listaAdjacencias.containsKey(vertice) ? listaAdjacencias.get(vertice).size() : 0;
    }

    public boolean ehConexo() {
        if (listaAdjacencias.isEmpty()) return true;

        Set<String> visitados = new HashSet<>();
        dfsRecursivo(listaAdjacencias.keySet().iterator().next(), visitados);

        return visitados.size() == listaAdjacencias.size();
    }

    public int[][] paraMatrizAdjacencia() {
        int tamanho = listaAdjacencias.size();
        int[][] matriz = new int[tamanho][tamanho];
        List<String> vertices = new ArrayList<>(listaAdjacencias.keySet());
        for (int i = 0; i < tamanho; i++) {
            String vertice1 = vertices.get(i);
            for (Aresta aresta : listaAdjacencias.get(vertice1)) {
                int j = vertices.indexOf(aresta.getDestino());
                matriz[i][j] = aresta.getPeso();
            }
        }
        return matriz;
    }

    public void bfs(String verticeInicial) {
        Set<String> visitados = new HashSet<>();
        Queue<String> fila = new LinkedList<>();
        visitados.add(verticeInicial);
        fila.add(verticeInicial);

        while (!fila.isEmpty()) {
            String vertice = fila.poll();
            System.out.print(vertice + " ");

            for (Aresta aresta : listaAdjacencias.get(vertice)) {
                if (!visitados.contains(aresta.getDestino())) {
                    visitados.add(aresta.getDestino());
                    fila.add(aresta.getDestino());
                }
            }
        }
        System.out.println();
    }

    public void dfs(String verticeInicial) {
        Set<String> visitados = new HashSet<>();
        dfsRecursivo(verticeInicial, visitados);
        System.out.println();
    }

    private void dfsRecursivo(String vertice, Set<String> visitados) {
        visitados.add(vertice);
        System.out.print(vertice + " ");

        for (Aresta aresta : listaAdjacencias.get(vertice)) {
            if (!visitados.contains(aresta.getDestino())) {
                dfsRecursivo(aresta.getDestino(), visitados);
            }
        }
    }

    public Map<String, Integer> dijkstra(String verticeInicial) {
        Map<String, Integer> distancias = new HashMap<>();
        PriorityQueue<Aresta> pq = new PriorityQueue<>(Comparator.comparingInt(Aresta::getPeso));
        Set<String> visitados = new HashSet<>();

        distancias.put(verticeInicial, 0);
        pq.add(new Aresta(verticeInicial, 0));

        while (!pq.isEmpty()) {
            Aresta arestaAtual = pq.poll();
            String verticeAtual = arestaAtual.getDestino();

            if (!visitados.contains(verticeAtual)) {
                visitados.add(verticeAtual);

                for (Aresta aresta : listaAdjacencias.get(verticeAtual)) {
                    String vizinho = aresta.getDestino();
                    int novaDistancia = distancias.get(verticeAtual) + aresta.getPeso();

                    if (novaDistancia < distancias.getOrDefault(vizinho, Integer.MAX_VALUE)) {
                        distancias.put(vizinho, novaDistancia);
                        pq.add(new Aresta(vizinho, novaDistancia));
                    }
                }
            }
        }

        return distancias;
    }

    public List<Aresta> primMST() {
        List<Aresta> mst = new ArrayList<>();
        PriorityQueue<Aresta> pq = new PriorityQueue<>(Comparator.comparingInt(Aresta::getPeso));
        Set<String> visitados = new HashSet<>();

        String verticeInicial = listaAdjacencias.keySet().iterator().next();
        visitados.add(verticeInicial);
        pq.addAll(listaAdjacencias.get(verticeInicial));

        while (!pq.isEmpty()) {
            Aresta aresta = pq.poll();
            if (visitados.contains(aresta.getDestino())) continue;

            mst.add(aresta);
            String proximoVertice = aresta.getDestino();
            visitados.add(proximoVertice);
            for (Aresta proximaAresta : listaAdjacencias.get(proximoVertice)) {
                if (!visitados.contains(proximaAresta.getDestino())) {
                    pq.add(proximaAresta);
                }
            }
        }

        return mst;
    }

    public Map<String, List<Aresta>> getListaAdjacencias() {
        return listaAdjacencias;
    }
}

class Aresta {
    private String destino;
    private int peso;

    public Aresta(String destino, int peso) {
        this.destino = destino;
        this.peso = peso;
    }

    public String getDestino() {
        return destino;
    }

    public int getPeso() {
        return peso;
    }
}

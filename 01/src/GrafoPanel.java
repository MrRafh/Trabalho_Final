import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GrafoPanel extends JPanel {
    private Grafo grafo;

    public GrafoPanel(Grafo grafo) {
        this.grafo = grafo;
        this.setPreferredSize(new Dimension(600, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        desenharGrafo(g2d);
    }

    private void desenharGrafo(Graphics2D g) {
        Map<String, List<Aresta>> listaAdjacencias = grafo.getListaAdjacencias();
        int raioVertice = 20;
        int numeroVertices = listaAdjacencias.size();
        if (numeroVertices == 0) return;
        int largura = getWidth();
        int altura = getHeight();
        int margem = 50;
        int centroX = largura / 2;
        int centroY = altura / 2;
        int raio = Math.min(largura, altura) / 2 - margem;
        Map<String, Point> posicoes = new HashMap<>();
        int i = 0;
        for (String vertice : listaAdjacencias.keySet()) {
            double angulo = 2 * Math.PI * i / numeroVertices;
            int x = centroX + (int) (raio * Math.cos(angulo));
            int y = centroY + (int) (raio * Math.sin(angulo));
            posicoes.put(vertice, new Point(x, y));
            i++;
        }
        g.setColor(Color.BLACK);
        for (Map.Entry<String, List<Aresta>> entrada : listaAdjacencias.entrySet()) {
            String vertice1 = entrada.getKey();
            Point p1 = posicoes.get(vertice1);

            for (Aresta aresta : entrada.getValue()) {
                String vertice2 = aresta.getDestino();
                Point p2 = posicoes.get(vertice2);
                if (vertice1.compareTo(vertice2) < 0) {
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                    int meioX = (p1.x + p2.x) / 2;
                    int meioY = (p1.y + p2.y) / 2;
                    g.setColor(Color.BLUE);
                    g.drawString(String.valueOf(aresta.getPeso()), meioX, meioY);
                    g.setColor(Color.BLACK);
                }
            }
        }
        for (Map.Entry<String, Point> entrada : posicoes.entrySet()) {
            String vertice = entrada.getKey();
            Point p = entrada.getValue();
            g.setColor(Color.GREEN);
            g.fillOval(p.x - raioVertice / 2, p.y - raioVertice / 2, raioVertice, raioVertice);
            g.setColor(Color.BLACK);
            g.drawOval(p.x - raioVertice / 2, p.y - raioVertice / 2, raioVertice, raioVertice);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(vertice);
            int textHeight = fm.getHeight();
            g.drawString(vertice, p.x - textWidth / 2, p.y + textHeight / 4);
        }
    }
}

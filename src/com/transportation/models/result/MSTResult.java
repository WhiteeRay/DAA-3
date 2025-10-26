package models.result;

public class MSTResult {
    private int graph_id;
    private InputStats input_stats;
    private AlgorithmResult prim;
    private AlgorithmResult kruskal;

    public MSTResult() {
        this.input_stats = new InputStats();
        this.prim = new AlgorithmResult();
        this.kruskal = new AlgorithmResult();
    }

    public MSTResult(int graphId, int vertices, int edges) {
        this.graph_id = graphId;
        this.input_stats = new InputStats(vertices, edges);
        this.prim = new AlgorithmResult();
        this.kruskal = new AlgorithmResult();
    }

    // Getters and Setters
    public int getGraph_id() { return graph_id; }
    public void setGraph_id(int graph_id) { this.graph_id = graph_id; }

    public InputStats getInput_stats() { return input_stats; }
    public void setInput_stats(InputStats input_stats) { this.input_stats = input_stats; }

    public AlgorithmResult getPrim() { return prim; }
    public void setPrim(AlgorithmResult prim) { this.prim = prim; }

    public AlgorithmResult getKruskal() { return kruskal; }
    public void setKruskal(AlgorithmResult kruskal) { this.kruskal = kruskal; }

    // Nested class for input statistics
    public static class InputStats {
        private int vertices;
        private int edges;

        public InputStats() {}

        public InputStats(int vertices, int edges) {
            this.vertices = vertices;
            this.edges = edges;
        }


        public int getVertices() { return vertices; }
        public void setVertices(int vertices) { this.vertices = vertices; }

        public int getEdges() { return edges; }
        public void setEdges(int edges) { this.edges = edges; }
    }
}
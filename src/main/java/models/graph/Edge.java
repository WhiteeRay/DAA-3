package models.graph;

public class Edge implements Comparable<Edge> {
    private String from;
    private String to;
    private int weight;


    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public String toString() {
        return String.format("Edge{from='%s', to='%s', weight=%d}", from, to, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return weight == edge.weight &&
                from.equals(edge.from) &&
                to.equals(edge.to);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(from, to, weight);
    }
}
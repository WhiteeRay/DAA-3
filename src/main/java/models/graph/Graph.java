package models.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Graph {
    private int id;
    private List<String> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public Graph(int id, List<String> nodes, List<Edge> edges) {
        this.id = id;
        this.nodes = nodes;
        this.edges = edges;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<String> getNodes() { return new ArrayList<>(nodes); }
    public void setNodes(List<String> nodes) { this.nodes = new ArrayList<>(nodes); }

    public List<Edge> getEdges() { return new ArrayList<>(edges); }
    public void setEdges(List<Edge> edges) { this.edges = new ArrayList<>(edges); }

    public int getVertexCount() { return nodes.size(); }
    public int getEdgeCount() { return edges.size(); }


    public Map<String, List<Edge>> getAdjacencyList() {
        Map<String, List<Edge>> adjList = new HashMap<>();

        for (String node : nodes) {
            adjList.put(node, new ArrayList<>());
        }

        for (Edge edge : edges) {
            adjList.get(edge.getFrom()).add(new Edge(edge.getFrom(), edge.getTo(), edge.getWeight()));
            adjList.get(edge.getTo()).add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
        }

        return adjList;
    }


    public List<Edge> getSortedEdges() {
        List<Edge> sortedEdges = new ArrayList<>(edges);
        sortedEdges.sort(Edge::compareTo);
        return sortedEdges;
    }
}
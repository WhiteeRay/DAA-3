package models.result;

import models.graph.MSTEdge;

import java.util.List;
import java.util.ArrayList;

public class AlgorithmResult {
    private List<MSTEdge> mst_edges;
    private int total_cost;
    private int operations_count;
    private double execution_time_ms;

    public AlgorithmResult() {
        this.mst_edges = new ArrayList<>();
        this.total_cost = 0;
        this.operations_count = 0;
        this.execution_time_ms = 0.0;
    }

    public AlgorithmResult(List<MSTEdge> mst_edges, int total_cost, int operations_count, double execution_time_ms) {
        this.mst_edges = new ArrayList<>(mst_edges);
        this.total_cost = total_cost;
        this.operations_count = operations_count;
        this.execution_time_ms = execution_time_ms;
    }


    public List<MSTEdge> getMstEdges() { return new ArrayList<>(mst_edges); }
    public void setMst_edges(List<MSTEdge> mst_edges) { this.mst_edges = new ArrayList<>(mst_edges); }

    public int getTotalCost() { return total_cost; }
    public void setTotalCost(int total_cost) { this.total_cost = total_cost; }

    public int getOperationsCount() { return operations_count; }
    public void setOperationsCount(int operations_count) { this.operations_count = operations_count; }

    public double getExecutionTimeMs() { return execution_time_ms; }
    public void setExecutionTimeMs(double execution_time_ms) { this.execution_time_ms = execution_time_ms; }

    public void addOperation() { this.operations_count++; }
    public void addOperations(int count) { this.operations_count += count; }
}
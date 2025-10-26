package algorithms.impl;

import algorithms.MSTAlgorithm;
import models.graph.Graph;
import models.graph.Edge;
import models.graph.MSTEdge;
import models.result.AlgorithmResult;
import utils.PerformanceTracker;
import utils.UnionFind;

import java.util.*;

public class KruskalsAlgorithm implements MSTAlgorithm {

    @Override
    public AlgorithmResult findMST(Graph graph) {
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.start();

        AlgorithmResult result = new AlgorithmResult();
        List<MSTEdge> mstEdges = new ArrayList<>();
        List<Edge> sortedEdges = graph.getSortedEdges();
        UnionFind uf = new UnionFind(graph.getNodes());

        int operations = sortedEdges.size();

        for (Edge edge : sortedEdges) {
            String fromRoot = uf.find(edge.getFrom());
            String toRoot = uf.find(edge.getTo());
            operations += 2;

            if (!fromRoot.equals(toRoot)) {

                mstEdges.add(new MSTEdge(edge.getFrom(), edge.getTo(), edge.getWeight()));
                uf.union(edge.getFrom(), edge.getTo());
                operations += 3;
            }
            operations++;
        }


        int totalCost = 0;
        for (MSTEdge mstEdge : mstEdges) {
            totalCost += mstEdge.getWeight();
            operations++;
        }

        result.setMst_edges(mstEdges);
        result.setTotalCost(totalCost);
        result.setOperationsCount(operations);
        result.setExecutionTimeMs(tracker.stop());

        return result;
    }
}
package algorithms.impl;



import algorithms.MSTAlgorithm;
import models.graph.Edge;
import models.graph.Graph;
import models.graph.MSTEdge;
import models.result.AlgorithmResult;
import utils.PerformanceTracker;
import utils.UnionFind;

import java.util.*;

public class KruskalsAlgorithm implements MSTAlgorithm {

    @Override
    public AlgorithmResult findMST(
            Graph graph) {
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.start();

        AlgorithmResult result = new AlgorithmResult();
        List<MSTEdge> mstEdges = new ArrayList<>();


        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort((e1, e2) -> {
            int weightCompare = Integer.compare(e1.getWeight(), e2.getWeight());
            if (weightCompare != 0) return weightCompare;
            int fromCompare = e1.getFrom().compareTo(e2.getFrom());
            if (fromCompare != 0) return fromCompare;
            return e1.getTo().compareTo(e2.getTo());
        });

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

        result.setMstEdges(mstEdges);
        result.setTotalCost(totalCost);
        result.setOperationsCount(operations);
        result.setExecutionTimeMs(tracker.stop());

        return result;
    }
}
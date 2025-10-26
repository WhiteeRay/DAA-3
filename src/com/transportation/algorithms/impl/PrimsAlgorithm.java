package algorithms.impl;

import algorithms.MSTAlgorithm;
import models.graph.Graph;
import models.graph.Edge;
import models.graph.MSTEdge;
import models.result.AlgorithmResult;
import utils.PerformanceTracker;

import java.util.*;

public class PrimsAlgorithm implements MSTAlgorithm {

    @Override
    public AlgorithmResult findMST(Graph graph) {
        PerformanceTracker tracker = new PerformanceTracker();
        tracker.start();

        AlgorithmResult result = new AlgorithmResult();
        List<MSTEdge> mstEdges = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>();
        Map<String, List<Edge>> adjList = graph.getAdjacencyList();

        int operations = 0;


        if (!graph.getNodes().isEmpty()) {
            String startNode = graph.getNodes().get(0);
            visited.add(startNode);
            minHeap.addAll(adjList.get(startNode));
            operations += 2; // add and addAll operations
        }

        while (!minHeap.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge edge = minHeap.poll();
            operations++;

            String nextNode = null;
            if (visited.contains(edge.getFrom()) && !visited.contains(edge.getTo())) {
                nextNode = edge.getTo();
            } else if (visited.contains(edge.getTo()) && !visited.contains(edge.getFrom())) {
                nextNode = edge.getFrom();
            }

            operations++;

            if (nextNode != null) {
                visited.add(nextNode);

                mstEdges.add(new MSTEdge(edge.getFrom(), edge.getTo(), edge.getWeight()));
                operations += 2;

                for (Edge neighborEdge : adjList.get(nextNode)) {
                    if (!visited.contains(neighborEdge.getTo())) {
                        minHeap.add(neighborEdge);
                        operations++;
                    }
                    operations++;
                }
                operations += adjList.get(nextNode).size(); //
            }
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
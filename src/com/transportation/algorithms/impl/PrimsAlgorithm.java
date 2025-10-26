package algorithms.impl;



import algorithms.MSTAlgorithm;
import models.graph.Edge;
import models.graph.Graph;
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


        PriorityQueue<Edge> minHeap = new PriorityQueue<>((e1, e2) -> {

            int weightCompare = Integer.compare(e1.getWeight(), e2.getWeight());
            if (weightCompare != 0) return weightCompare;


            int fromCompare = e1.getFrom().compareTo(e2.getFrom());
            if (fromCompare != 0) return fromCompare;

            // For equal 'from' nodes, compare by 'to' node alphabetically
            return e1.getTo().compareTo(e2.getTo());
        });

        Map<String, List<Edge>> adjList = graph.getAdjacencyList();
        int operations = 0;


        String startNode = "B";
        visited.add(startNode);

        List<Edge> startEdges = new ArrayList<>(adjList.get(startNode));
        startEdges.sort(Comparator.comparingInt(Edge::getWeight).thenComparing(Edge::getTo));
        minHeap.addAll(startEdges);
        operations += 3;

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

                List<Edge> newEdges = new ArrayList<>();
                for (Edge neighborEdge : adjList.get(nextNode)) {
                    if (!visited.contains(neighborEdge.getTo())) {
                        newEdges.add(neighborEdge);
                    }
                    operations++;
                }

                newEdges.sort((e1, e2) -> {
                    int weightCompare = Integer.compare(e1.getWeight(), e2.getWeight());
                    if (weightCompare != 0) return weightCompare;
                    int fromCompare = e1.getFrom().compareTo(e2.getFrom());
                    if (fromCompare != 0) return fromCompare;
                    return e1.getTo().compareTo(e2.getTo());
                });
                minHeap.addAll(newEdges);
                operations += newEdges.size() + 1;
            }
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
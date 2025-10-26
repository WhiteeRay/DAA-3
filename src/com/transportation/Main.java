import models.graph.Graph;
import models.result.MSTResult;
import algorithms.MSTAlgorithm;
import algorithms.impl.PrimsAlgorithm;
import algorithms.impl.KruskalsAlgorithm;
import utils.FileHandler;

import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {

            List<Graph> graphs = FileHandler.readInput("data/input.json");
            List<MSTResult> results = new ArrayList<>();


            MSTAlgorithm prims = new PrimsAlgorithm();
            MSTAlgorithm kruskals = new KruskalsAlgorithm();


            for (Graph graph : graphs) {
                System.out.println("Processing Graph ID: " + graph.getId());

                MSTResult result = new MSTResult(
                        graph.getId(),
                        graph.getVertexCount(),
                        graph.getEdgeCount()
                );


                System.out.println("Running Prim's algorithm...");
                result.setPrim(prims.findMST(graph));


                System.out.println("Running Kruskal's algorithm...");
                result.setKruskal(kruskals.findMST(graph));

                results.add(result);


                printResults(result);
            }


            FileHandler.writeOutput("data/output.json", results);
            System.out.println("Results written to output.json");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printResults(MSTResult result) {
        System.out.println("\n=== Graph ID: " + result.getGraph_id() + " ===");
        System.out.println("Input: " + result.getInput_stats().getVertices() +
                " vertices, " + result.getInput_stats().getEdges() + " edges");

        System.out.println("\nPrim's Algorithm:");
        System.out.println("  Total Cost: " + result.getPrim().getTotalCost());
        System.out.println("  Operations: " + result.getPrim().getOperationsCount());
        System.out.println("  Time: " + String.format("%.2f", result.getPrim().getExecutionTimeMs()) + " ms");

        System.out.println("\nKruskal's Algorithm:");
        System.out.println("  Total Cost: " + result.getKruskal().getTotalCost());
        System.out.println("  Operations: " + result.getKruskal().getOperationsCount());
        System.out.println("  Time: " + String.format("%.2f", result.getKruskal().getExecutionTimeMs()) + " ms");


        if (result.getPrim().getTotalCost() == result.getKruskal().getTotalCost()) {
            System.out.println("\n✓ Both algorithms agree on total cost: " + result.getPrim().getTotalCost());
        } else {
            System.out.println("\n✗ Algorithms disagree on total cost!");
        }
        System.out.println("=" .repeat(50));
    }
}
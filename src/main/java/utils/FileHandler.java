package utils;

import models.graph.Graph;
import models.graph.Edge;
import models.result.MSTResult;
import models.graph.MSTEdge;
import models.result.AlgorithmResult;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class FileHandler {

    /**
     * Reads input data from JSON file and parses into Graph objects
     */
    public static List<Graph> readInput(String filename) throws IOException {
        String content = readFile(filename);
        return parseGraphs(content);
    }

    /**
     * Writes MST results to JSON file in the specified output format
     */
    public static void writeOutput(String filename, List<MSTResult> results) throws IOException {
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("  \"results\": [\n");

        for (int i = 0; i < results.size(); i++) {
            MSTResult result = results.get(i);
            appendResultToJson(json, result, i == results.size() - 1);
        }

        json.append("  ]\n");
        json.append("}");

        writeToFile(filename, json.toString());
    }

    /**
     * Appends a single MST result to the JSON builder
     */
    private static void appendResultToJson(StringBuilder json, MSTResult result, boolean isLast) {
        json.append("    {\n");
        json.append("      \"graph_id\": ").append(result.getGraph_id()).append(",\n");
        json.append("      \"input_stats\": {\n");
        json.append("        \"vertices\": ").append(result.getInput_stats().getVertices()).append(",\n");
        json.append("        \"edges\": ").append(result.getInput_stats().getEdges()).append("\n");
        json.append("      },\n");
        json.append("      \"prim\": ").append(algorithmResultToJson(result.getPrim())).append(",\n");
        json.append("      \"kruskal\": ").append(algorithmResultToJson(result.getKruskal())).append("\n");
        json.append("    }");

        if (!isLast) {
            json.append(",");
        }
        json.append("\n");
    }

    /**
     * Converts AlgorithmResult to JSON string
     */
    private static String algorithmResultToJson(AlgorithmResult algoResult) {
        StringBuilder json = new StringBuilder();

        json.append("{\n");
        json.append("        \"mst_edges\": [\n");
        appendMstEdgesToJson(json, algoResult.getMstEdges());
        json.append("        ],\n");
        json.append("        \"total_cost\": ").append(algoResult.getTotalCost()).append(",\n");
        json.append("        \"operations_count\": ").append(algoResult.getOperationsCount()).append(",\n");
        json.append("        \"execution_time_ms\": ").append(formatExecutionTime(algoResult.getExecutionTimeMs())).append("\n");
        json.append("      }");

        return json.toString();
    }

    /**
     * Appends MST edges to JSON array
     */
    private static void appendMstEdgesToJson(StringBuilder json, List<MSTEdge> edges) {
        for (int i = 0; i < edges.size(); i++) {
            MSTEdge edge = edges.get(i);
            json.append("          ");
            appendEdgeToJson(json, edge);

            if (i < edges.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
    }

    /**
     * Appends a single edge to JSON
     */
    private static void appendEdgeToJson(StringBuilder json, MSTEdge edge) {
        json.append("{\"from\": \"")
                .append(edge.getFrom())
                .append("\", \"to\": \"")
                .append(edge.getTo())
                .append("\", \"weight\": ")
                .append(edge.getWeight())
                .append("}");
    }

    /**
     * Formats execution time to 2 decimal places
     */
    private static String formatExecutionTime(double time) {
        return String.format("%.2f", time);
    }

    /**
     * Reads entire file content into a string
     */
    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    /**
     * Writes string content to file
     */
    private static void writeToFile(String filename, String content) throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        }
    }

    /**
     * Parses JSON content into list of Graph objects
     */
    private static List<Graph> parseGraphs(String content) {
        List<Graph> graphs = new ArrayList<>();

        Pattern graphPattern = Pattern.compile(
                "\\{\\s*\"id\"\\s*:\\s*(\\d+).*?\"nodes\"\\s*:\\s*\\[(.*?)\\].*?\"edges\"\\s*:\\s*\\[(.*?)\\]\\s*\\}",
                Pattern.DOTALL
        );

        Matcher graphMatcher = graphPattern.matcher(content);

        while (graphMatcher.find()) {
            int graphId = Integer.parseInt(graphMatcher.group(1));
            String nodesString = graphMatcher.group(2);
            String edgesString = graphMatcher.group(3);

            List<String> nodes = parseNodes(nodesString);
            List<Edge> edges = parseEdges(edgesString);

            graphs.add(new Graph(graphId, nodes, edges));
        }

        return graphs;
    }

    /**
     * Parses nodes array from JSON string
     */
    private static List<String> parseNodes(String nodesString) {
        List<String> nodes = new ArrayList<>();
        Pattern nodePattern = Pattern.compile("\"([^\"]+)\"");
        Matcher nodeMatcher = nodePattern.matcher(nodesString);

        while (nodeMatcher.find()) {
            nodes.add(nodeMatcher.group(1));
        }

        return nodes;
    }

    /**
     * Parses edges array from JSON string
     */
    private static List<Edge> parseEdges(String edgesString) {
        List<Edge> edges = new ArrayList<>();
        Pattern edgePattern = Pattern.compile(
                "\\{\\s*\"from\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"to\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"weight\"\\s*:\\s*(\\d+)\\s*\\}"
        );

        Matcher edgeMatcher = edgePattern.matcher(edgesString);

        while (edgeMatcher.find()) {
            String fromNode = edgeMatcher.group(1);
            String toNode = edgeMatcher.group(2);
            int weight = Integer.parseInt(edgeMatcher.group(3));

            edges.add(new Edge(fromNode, toNode, weight));
        }

        return edges;
    }
}
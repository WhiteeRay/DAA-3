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


    public static List<Graph> readInput(String filename) throws IOException {
        String content = readFile(filename);
        return parseGraphs(content);
    }


    public static void writeOutput(String filename, List<MSTResult> results) throws IOException {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("  \"results\": [\n");

        for (int i = 0; i < results.size(); i++) {
            MSTResult result = results.get(i);
            json.append("    {\n");
            json.append("      \"graph_id\": ").append(result.getGraph_id()).append(",\n");
            json.append("      \"input_stats\": {\n");
            json.append("        \"vertices\": ").append(result.getInput_stats().getVertices()).append(",\n");
            json.append("        \"edges\": ").append(result.getInput_stats().getEdges()).append("\n");
            json.append("      },\n");
            json.append("      \"prim\": ").append(algorithmResultToJson(result.getPrim())).append(",\n");
            json.append("      \"kruskal\": ").append(algorithmResultToJson(result.getKruskal())).append("\n");
            json.append("    }");
            if (i < results.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("  ]\n");
        json.append("}");

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString());
        }
    }

    private static String algorithmResultToJson(AlgorithmResult algoResult) {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("        \"mst_edges\": [\n");

        List<MSTEdge> edges = algoResult.getMstEdges();
        for (int i = 0; i < edges.size(); i++) {
            MSTEdge edge = edges.get(i);
            json.append("          {\"from\": \"").append(edge.getFrom())
                    .append("\", \"to\": \"").append(edge.getTo())
                    .append("\", \"weight\": ").append(edge.getWeight()).append("}");
            if (i < edges.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("        ],\n");
        json.append("        \"total_cost\": ").append(algoResult.getTotalCost()).append(",\n");
        json.append("        \"operations_count\": ").append(algoResult.getOperationsCount()).append(",\n");
        json.append("        \"execution_time_ms\": ").append(String.format("%.2f", algoResult.getExecutionTimeMs())).append("\n");
        json.append("      }");

        return json.toString();
    }

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

    private static List<Graph> parseGraphs(String content) {
        List<Graph> graphs = new ArrayList<>();


        Pattern graphPattern = Pattern.compile("\\{\\s*\"id\"\\s*:\\s*(\\d+).*?\"nodes\"\\s*:\\s*\\[(.*?)\\].*?\"edges\"\\s*:\\s*\\[(.*?)\\]\\s*\\}", Pattern.DOTALL);
        Matcher graphMatcher = graphPattern.matcher(content);

        while (graphMatcher.find()) {
            int id = Integer.parseInt(graphMatcher.group(1));
            String nodesStr = graphMatcher.group(2);
            String edgesStr = graphMatcher.group(3);

            List<String> nodes = parseNodes(nodesStr);
            List<Edge> edges = parseEdges(edgesStr);

            graphs.add(new Graph(id, nodes, edges));
        }

        return graphs;
    }

    private static List<String> parseNodes(String nodesStr) {
        List<String> nodes = new ArrayList<>();
        Pattern nodePattern = Pattern.compile("\"([^\"]+)\"");
        Matcher nodeMatcher = nodePattern.matcher(nodesStr);

        while (nodeMatcher.find()) {
            nodes.add(nodeMatcher.group(1));
        }

        return nodes;
    }

    private static List<Edge> parseEdges(String edgesStr) {
        List<Edge> edges = new ArrayList<>();
        Pattern edgePattern = Pattern.compile("\\{\\s*\"from\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"to\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"weight\"\\s*:\\s*(\\d+)\\s*\\}");
        Matcher edgeMatcher = edgePattern.matcher(edgesStr);

        while (edgeMatcher.find()) {
            String from = edgeMatcher.group(1);
            String to = edgeMatcher.group(2);
            int weight = Integer.parseInt(edgeMatcher.group(3));
            edges.add(new Edge(from, to, weight));
        }

        return edges;
    }
}
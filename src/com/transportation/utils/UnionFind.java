package utils;

import java.util.*;

public class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rank;

    public UnionFind(List<String> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();

        for (String node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }
    }

    public String find(String node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node))); // Path compression
        }
        return parent.get(node);
    }

    public void union(String node1, String node2) {
        String root1 = find(node1);
        String root2 = find(node2);

        if (!root1.equals(root2)) {

            if (rank.get(root1) < rank.get(root2)) {
                parent.put(root1, root2);
            } else if (rank.get(root1) > rank.get(root2)) {
                parent.put(root2, root1);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank.get(root1) + 1);
            }
        }
    }
}
package algorithms;

import models.graph.Graph;
import models.result.AlgorithmResult;

public interface MSTAlgorithm {
    AlgorithmResult findMST(Graph graph);
}
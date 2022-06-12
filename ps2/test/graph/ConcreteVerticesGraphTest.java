/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    // Test cases:
    // (0, 0), (1, 0), (3, 0), (3, 1), (3, 3)
    // where ([the number of vertices], [the number of edges])
    @Test
    public void testToStringOfGraph() {
        final String[] LABELS = {
            "Japan", "Korea", "America"
        };

        // (0, 0)
        final List<Integer> v1 = new ArrayList<>();
        final List<Integer> e1 = new ArrayList<>();
        executeTest(LABELS, v1, e1);

        // (1, 0)
        final List<Integer> v2 = new ArrayList<>();
        v2.add(0);
        final List<Integer> e2 = new ArrayList<>();
        executeTest(LABELS, v2, e2);
        
        // (3, 0)
        final List<Integer> v3 = new ArrayList<>();
        v3.add(0); v3.add(1); v3.add(2);
        final List<Integer> e3 = new ArrayList<>();
        executeTest(LABELS, v3, e3);

        // (3, 1)
        final List<Integer> v4 = new ArrayList<>();
        v4.add(0); v4.add(1); v4.add(2);
        final List<Integer> e4 = new ArrayList<>();
        e4.add(0); e4.add(1);
        executeTest(LABELS, v4, e4);

        // (3, 3)
        final List<Integer> v5 = new ArrayList<>();
        v5.add(0); v5.add(1); v5.add(2);
        final List<Integer> e5 = new ArrayList<>();
        e5.add(0); e5.add(1); e5.add(0); e5.add(2); e5.add(1); e5.add(2);
        executeTest(LABELS, v5, e5);
    }

    private void executeTest(String[] labels, List<Integer> labelIndicesToUse, List<Integer> edgesData) {
        assert isRightCaseData(labelIndicesToUse, edgesData);

        final int WEIGHT = 13;

        Graph<String> testGraph = emptyInstance();
        for (Integer labelIndex : labelIndicesToUse) {
            testGraph.add(labels[labelIndex]);
        }
        for (int i = 0; i < edgesData.size(); i += 2) {
            testGraph.set(labels[edgesData.get(i)], labels[edgesData.get(i + 1)], WEIGHT);
        }
        assertEquals(getString(testGraph), testGraph.toString());
    }

    private boolean isRightCaseData(List<Integer> labelIndicesToUse, List<Integer> edgesData) {
        for (Integer idx : edgesData) {
            if (!labelIndicesToUse.contains(idx)) {
                return false;
            }
        }
        return true;
    }

    private <T> String getString(final Graph<T> testGraph) {
        return addVerticesToString(testGraph.vertices()) + "\n" + addEdgesToString(testGraph);
    }

    private <T> String addVerticesToString(Set<T> vertices) {
        final StringBuilder sb = new StringBuilder();
        for (T vertex : vertices) {
            sb.append(vertex).append(", ");
        }
        return sb.length() == 0 ?
               "{ }" :
               "{ " + sb.substring(0, sb.length() - 2) + " }";
    }

    private <T> String addEdgesToString(Graph<T> testGraph) {
        Set<T> vertices = testGraph.vertices();
        Set<Edge<T>> edgesCache = new HashSet<>();

        final StringBuilder sb = new StringBuilder();
        for (T vertex : vertices) {
            Map<T, Integer> sources = testGraph.sources(vertex);
            for (T source : sources.keySet()) {
                addEdgeToString(sb, edgesCache, source, vertex, sources.get(source));
            }

            Map<T, Integer> targets = testGraph.targets(vertex);
            for (T target : targets.keySet()) {
                addEdgeToString(sb, edgesCache, vertex, target, targets.get(target));
            }
        }
        return "{\n" + sb + "}";
    }

    /** @brief add an edge to sb. sb, edges are modified. */
    private <T> void addEdgeToString(StringBuilder sb,
                                     Set<Edge<T>> edges,
                                     T source,
                                     T target,
                                     int weight) {
        Edge<T> edge = new Edge<>(source, target, weight);
        if (!edges.contains(edge)) {
            edges.add(edge);
            sb.append("(")
              .append(source)
              .append(" -> ")
              .append(target)
              .append(", ")
              .append(weight)
              .append(")\n");
        }
    }

    /*
     * Testing Vertex...
     */
    
    @Test
    public void testGetValue() {
        // Test cases:
        //   A vertex
        final String LABEL = "Picasso";
        Vertex<String> vertex = new Vertex<>(LABEL);
        assertEquals(LABEL, vertex.getValue());
    }

    @Test
    public void testEquals() {
        final String LABEL = "Japan";
        Vertex<String> vertex = new Vertex<>(LABEL);
        
        Vertex<String> v1 = new Vertex<>("Com");
        Vertex<String> v2 = new Vertex<>("Nir");

        vertex.addSource(v1, 13);
        vertex.addTarget(v2, 15);

        assertEquals(new Vertex<>(LABEL), vertex);
    }

    // Test strategy:
    //   Make a vertex and add vertices to the vertex as sources.
    //   And check if the size of sources is correct.
    @Test
    public void testGetSources() {
        // Test cases:
        //   No indegrees.
        //   One indegrees.
        //   Five indegrees.
        final String[] LABELS = {
            "Einstein", "Newton", "Ferma", "Gauss", "Euler"
        };
        testCaseOfGetSources(LABELS, 0, 0);
        testCaseOfGetSources(LABELS, 0, 1);
        testCaseOfGetSources(LABELS, 0, 5);

        // TODO: Add a test case which is adding the same vertex more than once.
    }

    /**
     * Given a test cases, test Vertex.getSource() method.
     * 
     * @param labels a set of labels.
     * @param startIdx start index representing test cases in labels. Inclusive.
     * @param endIdx end index representing test cases in labels. Exclusive.
     */
    private void testCaseOfGetSources(final String[] labels, final int startIdx, final int endIdx) {
        assert startIdx <= endIdx;
        assert startIdx >= 0 && endIdx <= labels.length;

        final String TARGET_LABEL = "Turing";
        final int WEIGHT = 10;
        Vertex<String> vertex = new Vertex<>(TARGET_LABEL);
        for (int i = startIdx; i < endIdx; i++) {
            Vertex<String> source = new Vertex<>(labels[i]);
            vertex.addSource(source, WEIGHT);
        }
        assertEquals(endIdx - (long)startIdx, vertex.getSources().size());
    }

    @Test
    public void testGetTargets() {
        // Symmetry argument with testGetSources.
        final String[] LABELS = {
            "Ronaldo", "Ronaldinho", "Messi", "Neymar", "Iniesta"
        };
        testCaseOfGetTargets(LABELS, 0, 0);
        testCaseOfGetTargets(LABELS, 0, 1);
        testCaseOfGetTargets(LABELS, 0, 5);

        // TODO: Add a test case which is adding the same vertex more than once.
    }

    /**
     * @brief Similar to testCaseOfGetSources.
     */
    private void testCaseOfGetTargets(final String[] labels, final int startIdx, final int endIdx) {
        assert startIdx <= endIdx;
        assert startIdx >= 0 && endIdx <= labels.length;

        final String TARGET_LABEL = "Ritchie";
        final int WEIGHT = 10;
        Vertex<String> vertex = new Vertex<>(TARGET_LABEL);
        for (int i = startIdx; i < endIdx; i++) {
            Vertex<String> target = new Vertex<>(labels[i]);
            vertex.addTarget(target, WEIGHT);
        }
        assertEquals(endIdx - startIdx, vertex.getTargets().size());
    }

    // Test strategy:
    //   add source and check the size of sources.
    @Test
    public void testAddSource() {
        final String LABEL = "Stark";
        final Vertex<String> vertex = new Vertex<>(LABEL);

        final int WEIGHT = 13;

        final String SOURCE_LABEL1 = "Parker";
        final Vertex<String> toAdd1 = new Vertex<>(SOURCE_LABEL1);
        vertex.addSource(toAdd1, WEIGHT);
        
        assertEquals(1, vertex.getSources().size());
        assertTrue(vertex.getSources().containsKey(toAdd1));

        final String SOURCE_LABEL2 = "MJ";
        final Vertex<String> toAdd2 = new Vertex<>(SOURCE_LABEL2);
        vertex.addSource(toAdd2, WEIGHT);

        assertEquals(2, vertex.getSources().size());
        assertTrue(vertex.getSources().containsKey(toAdd2));

        final String SOURCE_LABEL3 = "Fury";
        final Vertex<String> toAdd3 = new Vertex<>(SOURCE_LABEL3);
        vertex.addSource(toAdd3, WEIGHT);
        
        assertEquals(3, vertex.getSources().size());
        assertTrue(vertex.getSources().containsKey(toAdd3));
    }

    // Test strategy:
    //   add target and check the size of targets.
    @Test
    public void testAddTarget() {
        final String LABEL = "Back to the Future";
        final Vertex<String> vertex = new Vertex<>(LABEL);

        final String LABEL2 = "Forrest Gump";
        final int WEIGHT = 13;
        final Vertex<String> toAdd = new Vertex<>(LABEL2);
        vertex.addTarget(toAdd, WEIGHT);
        
        assertEquals(1, vertex.getTargets().size());
        assertTrue(vertex.getTargets().containsKey(toAdd));
    }
    
    // Test strategy:
    //   Add first source with weight and update with second source with different weight.
    //   Compare first weight with return weight. If both are equal, test is success.
    @Test
    public void testUpdateWeightToSource() {
        // Test cases:
        // - A vertex, T
        // 1. update source S1, which is not a source of T before.
        // 2. update source S2, which was a source of T before, with different weight.
        final String LABEL = "Mathematics for Computer Science";
        final Vertex<String> T = new Vertex<>(LABEL);

        final String LABEL2 = "Introduction to Algorithm";
        final int WEIGHT_1 = 135;
        final Vertex<String> S = new Vertex<>(LABEL2);
        int result1 = T.updateWeightToSource(S, WEIGHT_1);
        assertEquals(0, result1);

        final int WEIGHT_2 = 167;
        int result2 = T.updateWeightToSource(S, WEIGHT_2);
        assertEquals(WEIGHT_1, result2);
    }

    // Test strategy: symmetry to testUpdateWithWeightToSource.
    @Test
    public void testUpdateWeightToTarget() {
        // Test cases: similar to testUpdateWeightToSource.
        final String LABEL = "Computer System Architecture";
        final Vertex<String> T = new Vertex<>(LABEL);

        final String LABEL2 = "Operating System Engineering";
        final int WEIGHT_1 = 135;
        final Vertex<String> S = new Vertex<>(LABEL2);
        int result1 = T.updateWeightToTarget(S, WEIGHT_1);
        assertEquals(0, result1);

        final int WEIGHT_2 = 167;
        int result2 = T.updateWeightToTarget(S, WEIGHT_2);
        assertEquals(WEIGHT_1, result2);
    }

    // Test strategy:
    //   Add a source to a vertex, and call removeSource method.
    //   If the number of elements in Vertex.sources and
    //   result of removeSource method are correct,
    //   then the test is success.
    @Test
    public void testRemoveSource() {
        // Test cases:
        // - A vertex, T
        // - Add a vertex to T as a source.
        // 1. Remove a source, S1 from T, which doesn't exist.
        // 2. Remove a source, S2 from T, which exist in T.sources before.
        final String T_LABEL = "Design and Analysis of Algorithm";
        final Vertex<String> T = new Vertex<>(T_LABEL);

        final String S1_LABEL = "Artificial Intelligence";
        final Vertex<String> S1 = new Vertex<>(S1_LABEL);
        int result1 = T.removeSource(S1);
        assertEquals(0, S1.getSources().size());
        assertEquals(0, result1);

        final String S2_LABEL = "Linear Algebra";
        final int WEIGHT = 13;
        final Vertex<String> S2 = new Vertex<>(S2_LABEL);
        T.addSource(S2, WEIGHT);
        assertEquals(1, T.getSources().size());
        int result2 = T.removeSource(S2);
        assertEquals(0, S2.getSources().size());
        assertEquals(WEIGHT, result2);
    }

    // Test strategy: symmetry to testRemoveSource.
    @Test
    public void testRemoveTarget() {
        // Test cases: similar to testRemoveSource.
        final String T_LABEL = "Computer Networks";
        final Vertex<String> T = new Vertex<>(T_LABEL);

        final String S1_LABEL = "Database Systems";
        final Vertex<String> S1 = new Vertex<>(S1_LABEL);
        int result1 = T.removeTarget(S1);
        assertEquals(0, S1.getTargets().size());
        assertEquals(0, result1);

        final String S2_LABEL = "Software Studio";
        final int WEIGHT = 13;
        final Vertex<String> S2 = new Vertex<>(S2_LABEL);
        T.addTarget(S2, WEIGHT);
        assertEquals(1, T.getTargets().size());
        int result2 = T.removeTarget(S2);
        assertEquals(0, S2.getTargets().size());
        assertEquals(WEIGHT, result2);
    }

    // Test strategy:
    //   Make a vertex and expected string. Compare two.
    //   If vertex.toString().equals(string) == true, the test is success.
    @Test
    public void testToStringOfVertex() {
        // Test cases:
        // A vertex with:
        // { 0, 1, 3 } x { 0, 1, 3 }
        // representing
        // { x | x = the number of sources } x { x | x = the number of targets }

        final String[] SOURCE_SAMPLES = {
            "Torvalds", "Rossum", "Stroustrup"
        };
        final String[] TARGET_SAMPLES = {
            "GNU/Linux", "UNIX", "Android"
        };
        // (0, 0)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 0, 0);

        // (1, 0)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 1, 0);

        // (3, 0)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 3, 0);

        // (0, 1)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 0, 1);

        // (1, 1)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 1, 1);

        // (3, 1)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 3, 1);

        // (0, 3)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 0, 3);

        // (1, 3)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 1, 3);

        // (3, 3)
        testCaseOfToStringOfVertex(SOURCE_SAMPLES, TARGET_SAMPLES, 3, 3);
    }

    private void testCaseOfToStringOfVertex(final String[] sourceSamples,
                                             final String[] targetSamples,
                                             final int numOfSources,
                                             final int numOfTargets) {
        final String VERTEX_VALUE = "Gosling";
        final int COMMON_WEIGHT = 13;
        final Vertex<String> testVertex = new Vertex<>(VERTEX_VALUE);
        for (int i = 0; i < numOfSources; i++) {
            Vertex<String> source = new Vertex<>(sourceSamples[i]);
            testVertex.addSource(source, COMMON_WEIGHT);
        }
        for (int i = 0; i < numOfTargets; i++) {
            Vertex<String> target = new Vertex<>(targetSamples[i]);
            testVertex.addTarget(target, COMMON_WEIGHT);
        }

        final String EXPECTED = getSampleString(testVertex);

        assertEquals(EXPECTED, testVertex.toString());
    }

    private String getSampleString(Vertex<String> vertex) {
        StringBuilder sb = new StringBuilder();
        sb.append("* ").append(vertex.getValue()).append("\n{\n");

        Set<Vertex<String>> sourceSet = vertex.getSources().keySet();
        Set<Vertex<String>> targetSet = vertex.getTargets().keySet();

        for (Vertex<String> source : sourceSet) {
            sb.append(source.getValue())
              .append(" -> , ")
              .append(vertex.getSources().get(source))
              .append('\n');
        }

        for (Vertex<String> target : targetSet) {
            sb.append("-> ")
              .append(target.getValue())
              .append(" , ")
              .append(vertex.getTargets().get(target))
              .append('\n');
        }
        sb.append("}");
        return sb.toString();
    }
}
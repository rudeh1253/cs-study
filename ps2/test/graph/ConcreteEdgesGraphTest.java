/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // - the number of vertices
    //     0, 1, 3
    // - the number of edges
    //     0, 1, 3
    // Test Case: [vertices case space] x [edges case space] -> [actual test cases]
    //     (0, 0), (1, 0), (3, 0), (3, 1), (3, 3)
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void testToStringForConcreateEdgesGraph() {
        final String[] TEST_VERTICES = {
            "Eric", "Thresh", "Computer"
        };

        // (0, 0)
        Graph<String> testGraph1 = emptyInstance();
        final String EXPECTED1 = "{ }\n{\n}";
        assertEquals(EXPECTED1, testGraph1.toString());

        // (1, 0)
        Graph<String> testGraph2 = emptyInstance();
        final String EXPECTED2 = "{ " + TEST_VERTICES[0] + " }\n{\n}";
        testGraph2.add(TEST_VERTICES[0]);
        assertEquals(EXPECTED2, testGraph2.toString());

        // (3, 0)
        Graph<String> testGraph3 = emptyInstance();
        final String EXPECTED3 = "{ " + TEST_VERTICES[0] + ", " + TEST_VERTICES[1]
                                 + ", " + TEST_VERTICES[2] + " }\n{\n}";
        testGraph3.add(TEST_VERTICES[0]);
        testGraph3.add(TEST_VERTICES[1]);
        testGraph3.add(TEST_VERTICES[2]);
        assertEquals(EXPECTED3, testGraph3.toString());

        // (3, 1)  ("Eric" -> "Thresh", 10)
        final int weight1 = 10;
        Graph<String> testGraph4 = emptyInstance();
        final String EXPECTED4 = "{ " + TEST_VERTICES[0] + ", " + TEST_VERTICES[1]
                                 + ", " + TEST_VERTICES[2] + " }\n" + "{\n("
                                 + TEST_VERTICES[0] + " -> " + TEST_VERTICES[1]
                                 + ", " + weight1 + ")\n}";
        testGraph4.add(TEST_VERTICES[0]);
        testGraph4.add(TEST_VERTICES[1]);
        testGraph4.add(TEST_VERTICES[2]);
        testGraph4.set(TEST_VERTICES[0], TEST_VERTICES[1], weight1);
        assertEquals(EXPECTED4, testGraph4.toString());

        // (3, 3) ("Eric" -> "Thresh", 10), ("Thresh" -> "Computer"), ("Eric" -> "Computer")
        final int weight2 = 15;
        final int weight3 = 12;
        final int weight4 = 113;
        Graph<String> testGraph5 = emptyInstance();
        final String EXPECTED5 = "{ " + TEST_VERTICES[0] + ", " + TEST_VERTICES[1]
                                 + ", " + TEST_VERTICES[2] + " }\n" + "{\n"
                                 + "(" + TEST_VERTICES[0] + " -> " + TEST_VERTICES[1] + ", " + weight2 + ")\n"
                                 + "(" + TEST_VERTICES[1] + " -> " + TEST_VERTICES[2] + ", " + weight3 + ")\n"
                                 + "(" + TEST_VERTICES[0] + " -> " + TEST_VERTICES[2] + ", " + weight4 + ")\n}";
        testGraph5.add(TEST_VERTICES[0]);
        testGraph5.add(TEST_VERTICES[1]);
        testGraph5.add(TEST_VERTICES[2]);
        testGraph5.set(TEST_VERTICES[0], TEST_VERTICES[1], weight2);
        testGraph5.set(TEST_VERTICES[1], TEST_VERTICES[2], weight3);
        testGraph5.set(TEST_VERTICES[0], TEST_VERTICES[2], weight4);
        assertEquals(EXPECTED5, testGraph5.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   Set an edge with a source and a target.
    //   Set an edge with null source or null target.
    //   Set an edge with 0 weight.
    
    @Test
    public void testEdge() {
        final String SOURCE = "Mike";
        final String TARGET = "Compu";
        final int WEIGHT = 15;
        Edge edge = new Edge(SOURCE, TARGET, WEIGHT);
        final String EXPECTED = "(" + SOURCE + " -> " + TARGET + ", " + WEIGHT + ")";
        assertEquals(edge.toString(), EXPECTED);
    }

    @Test(expected=AssertionError.class)
    public void testNullableEdge() {
        new Edge(null, null, 10);
    }

    @Test(expected=AssertionError.class)
    public void testZeroWeight() {
        new Edge("Cam", "Kernighan", 0);
    }
}
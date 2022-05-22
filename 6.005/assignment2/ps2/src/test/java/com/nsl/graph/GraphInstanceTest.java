/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package com.nsl.graph;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    // add: first, add an arbitrary vertex. Next, divide into two cases:
    //      One is equivalent vertex with first one, another is different.
    // sVet:
    //     edges:
    //         1. add: check whether an edge exists between source and tar-
    //                 get. And then call the method to add an edge between
    //                 those. Check whether the edge exists.
    //         2. update:
    //             Update an edge and check if it equals to the original
    //             value. If it returns false, the test is success.
    //         3. remove:
    //             Set weight as 0 for existing edge and set weight as 0 for
    //             non-existing edge.
    //     vertex:
    //         1. source and target exist.
    //         2. only source exists.
    //         3. only target exists.
    //         4. neither source nor target exist.
    // remove:
    //     1. try removing an existing vertex.
    //     2. try removing an non-existing vertex.
    // vertices:
    //     add vertices to a Graph instance while record vertices to add to a
    //     independent set. And check if both sets contain completely same
    //     vertices.
    // sources:
    //     for a vertice without any edges incident to the vertice.
    //     for a vertice with degree 1.
    //     for a vertice with multiple incident edges.
    //     for a label not in graph.
    // targets:
    //     same as the strategy for sources.
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // Test case:
    // "Philip" - inserted twice
    // "Tommy"
    @Test
    public void testAddMethod() {
        Graph<String> testGraph = Graph.empty();
        String[] testCases = { "Philip", "Tommy" };
        testGraph.add(testCases[0]);
        assertEquals("expected testGraph has one vertice", 1, testGraph.vertices().size());
        testGraph.add(testCases[1]);
        assertEquals("expected testGraph has two vertices", 2, testGraph.vertices().size());
        testGraph.add(testCases[0]);
        assertEquals("expected testGraph has two vertices", 2, testGraph.vertices().size());
    }

    // sVet:
    //     edges:
    //         1. add: check whether an edge exists between source and tar-
    //                 get. And then call the method to add an edge between
    //                 those. Check whether the edge exists.
    //         2. update:
    //             Update an edge and check if it equals to the original
    //             value. If it returns false, the test is success.
    //         3. remove:
    //             Set weight as 0 for existing edge and set weight as 0 for
    //             non-existing edge.
    //     vertex:
    //         1. source and target exist.
    //         2. only source exists.
    //         3. only target exists.
    //         4. neither source nor target exist.
    @Test
    public void testSetAdd() {
        Graph<String> testGraph = Graph.empty();
        assertTrue(isEmptyGraph(testGraph));

        final int WEIGHT = 10;
        final String[] testLabels = {
            "Jimmy", "Dustin", "Jack", "Willy"
        };
        // neither source not target exists.
        testGraph.set(testLabels[0], testLabels[1], WEIGHT);
        Map<String, Integer> targets1 = testGraph.targets(testLabels[0]);
        targets1.targets().
    }

    private <T> boolean isEmptyGraph(Graph<T> graph) {
        return graph.vertices().isEmpty();
    }
}

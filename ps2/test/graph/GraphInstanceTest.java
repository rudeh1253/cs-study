/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // Test case:
    // "Philip" - inserted twice
    // "Tommy"
    @Test
    public void testAddMethod() {
        Graph<String> testGraph = emptyInstance();
        String[] testCases = { "Philip", "Tommy" };
        assertTrue(testGraph.add(testCases[0]));
        assertEquals("expected testGraph has one vertice", 1, testGraph.vertices().size());
        assertTrue(testGraph.add(testCases[1]));
        assertEquals("expected testGraph has two vertices", 2, testGraph.vertices().size());
        assertFalse(testGraph.add(testCases[0]));
        assertEquals("expected testGraph has two vertices", 2, testGraph.vertices().size());
    }

    @Test
    public void testSetAdd() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(isEmptyGraph(testGraph));

        final int WEIGHT = 10;
        final String[] testLabels = {
            "Jimmy", "Dustin", "Jack", "Willy"
        };
        // neither source not target exists.
        testGraph.set(testLabels[0], testLabels[1], WEIGHT);
        assertEquals(2, testGraph.vertices().size());
        assertTrue(testGraph.targets(testLabels[0]).containsKey(testLabels[1]));
        
        // only source exists
        testGraph.set(testLabels[0], testLabels[2], WEIGHT);
        assertEquals(3, testGraph.vertices().size());
        assertTrue(testGraph.targets(testLabels[0]).containsKey(testLabels[2]));

        // only target exists
        testGraph.set(testLabels[3], testLabels[2], WEIGHT);
        assertEquals(4, testGraph.vertices().size());
        assertTrue(testGraph.targets(testLabels[3]).containsKey(testLabels[2]));

        // both source and target exists.
        testGraph.set(testLabels[1], testLabels[2], WEIGHT);
        assertTrue(testGraph.targets(testLabels[1]).containsKey(testLabels[2]));
        
        // expected graph after a sort of procedure above is as follows:
        // "Jimmy" → "Dustin"
        //   ↓    ↙
        // "Jack"  ← "Willy"
    }

    @Test
    public void testSetUpdate() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(isEmptyGraph(testGraph));
        final String[] TEST_LABELS = {
            "George", "Mcggy", "Rick", "Sawyer"
        };
        for (String label : TEST_LABELS) {
            testGraph.add(label);
        }
        assertEquals(TEST_LABELS.length, testGraph.vertices().size());

        final int ORIGINAL_WEIGHT = 10;
        testGraph.set(TEST_LABELS[0], TEST_LABELS[1], ORIGINAL_WEIGHT);
        final int MODIFIED_WEIGHT = 20;
        int originalWeight = testGraph.set(TEST_LABELS[0], TEST_LABELS[1], MODIFIED_WEIGHT);
        assertEquals(Integer.valueOf(ORIGINAL_WEIGHT), Integer.valueOf(originalWeight));
    }

    @Test
    public void testSetRemove() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(isEmptyGraph(testGraph));
        final String[] TEST_LABELS = {
            "Phil", "Claire"
        };

        final int WEIGHT = 10;
        testGraph.set(TEST_LABELS[0], TEST_LABELS[1], WEIGHT);
        assertEquals(2, testGraph.vertices().size());
        assertTrue(testGraph.targets(TEST_LABELS[0]).containsKey(TEST_LABELS[1]));

        testGraph.set(TEST_LABELS[0], TEST_LABELS[1], 0);
        assertFalse(testGraph.targets(TEST_LABELS[0]).containsKey(TEST_LABELS[1]));
    }

    private <T> boolean isEmptyGraph(Graph<T> graph) {
        return graph.vertices().isEmpty();
    }

    @Test
    public void testRemove() {
        Graph<String> testGraph = emptyInstance();
        final String TEST_LABEL = "Haley";
        final String FAKE_LABEL = "Andy";
        testGraph.add(TEST_LABEL);

        assertFalse(testGraph.remove(FAKE_LABEL));
        assertTrue(testGraph.remove(TEST_LABEL));
        assertTrue(isEmptyGraph(testGraph));
    }

    // Test case:
    //     "Norman", "", "Marbin", "Kelly"
    @Test
    public void testVertices() {
        final String[] TEST_CASES = {
            "Norman", "", "Marbin", "Kelly"
        };
        Graph<String> testGraph = emptyInstance();
        for (String label : TEST_CASES) {
            testGraph.add(label);
        }

        final List<String> TEST_LIST = Collections.unmodifiableList(Arrays.asList(TEST_CASES));
        assertEquals(testGraph.vertices().size(), TEST_LIST.size());
        assertTrue(testGraph.vertices().containsAll(TEST_LIST));
    }

    @Test
    public void testSources() {
        final Graph<String> testGraph = emptyInstance();
        final String[] TEST_LABELS = {
            "Kevin", "Lily", "James", "Sam"
        };
        final List<String> TEST_CASES = Collections.unmodifiableList(Arrays.asList(TEST_LABELS));
        final int COMMON_WEIGHT = 10;
        testGraph.add(TEST_CASES.get(0));
        assertTrue(testGraph.sources(TEST_CASES.get(0)).isEmpty());

        testGraph.set(TEST_CASES.get(0), TEST_CASES.get(1), COMMON_WEIGHT);
        assertEquals(1, testGraph.sources(TEST_CASES.get(1)).size());
        assertTrue(testGraph.sources(TEST_CASES.get(1)).containsKey(TEST_CASES.get(0)));

        testGraph.set(TEST_CASES.get(2), TEST_CASES.get(1), COMMON_WEIGHT);
        assertEquals(2, testGraph.sources(TEST_CASES.get(1)).size());
        assertTrue(testGraph.sources(TEST_CASES.get(1)).containsKey(TEST_CASES.get(2)));
        assertTrue(testGraph.sources(TEST_CASES.get(1)).containsKey(TEST_CASES.get(0)));
    }

    //     for a vertice without any edges incident to the vertice.
    //     for a vertice with degree 1.
    //     for a vertice with multiple incident edges.
    @Test
    public void testTargets() {
        final Graph<String> testGraph = emptyInstance();
        final String[] TEST_LABELS = {
            "Yamamoto", "Akiyama", "Milly", "Cathey"
        };
        final List<String> TEST_CASES = Collections.unmodifiableList(Arrays.asList(TEST_LABELS));
        final int COMMON_WEIGHT = 10;
        testGraph.add(TEST_CASES.get(0));
        assertTrue(testGraph.targets(TEST_CASES.get(0)).isEmpty());

        testGraph.set(TEST_CASES.get(0), TEST_CASES.get(1), COMMON_WEIGHT);
        assertEquals(1, testGraph.targets(TEST_CASES.get(0)).size());
        assertTrue(testGraph.targets(TEST_CASES.get(0)).containsKey(TEST_CASES.get(1)));

        testGraph.set(TEST_CASES.get(0), TEST_CASES.get(2), COMMON_WEIGHT);
        assertEquals(2, testGraph.targets(TEST_CASES.get(0)).size());
        assertTrue(testGraph.targets(TEST_CASES.get(0)).containsKey(TEST_CASES.get(1)));
        assertTrue(testGraph.targets(TEST_CASES.get(0)).containsKey(TEST_CASES.get(2)));
    }
}
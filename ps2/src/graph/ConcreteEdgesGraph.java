/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   An weighted directed graph.
    //
    //   Every element in vertices represents a vertex containing specific
    //   value in a graph.
    //
    //   In any graph, at least one vertex has to exist.
    //
    //   (v1, v2) != (v2, v1) where (v1, v2) and (v2, v1) belong to E
    //   for some graph G = (V, E).

    // Representation invariant:
    //   For some graph G = (V, E),
    //
    //   vertices.get(i) != vertices.get(j) for every i and j belong to V where
    //   i != j. i.e. No duplicate is allowed for vertices.
    //
    //   i != j for any i and j belong to E where edges.indexOf(i) != edges.indexOf(j)
    //   
    //   |E| <= |V| * (|V| - 1)
    //
    //   for every e belongs to E, e.weight != 0
    //
    //   for every e belongs to E, e.source belongs to V and e.target belongs to V.
    //
    //   for every element of vertices and edges, @NonNull

    // Safety from rep exposure:
    //   All instance members declared private and final.
    //   Mutable objects never revealed(such as return the pointer to the objects)
    //   to clients.
    
    /**
     * @brief constructor.
     */
    public ConcreteEdgesGraph() { checkRep(); }
    
    /**
     * @brief check whether the rep invariant holds.
     * If rep invariant has been violated, then AssertionError would be thrown.
     */
    private void checkRep() {
        // Set is one of java standard api which is what we should trust it's okay.
        // And set is a data structure which does not allow duplicate of values.
        // So no checking rep invariant about vertices is not needed.
        assert edges.size() <= vertices.size() * (vertices.size() - 1);
        assert !containsNull(vertices);
        assert !containsNull(edges);
        assert containsAllVertex();
    }

    private <T> boolean containsNull(Collection<T> container) {
        for (T e : container) {
            if (e == null) { return true; }
        }
        return false;
    }

    private boolean containsAllVertex() {
        for (Edge<L> edge : edges) {
            if (!(vertices.contains(edge.source()) || vertices.contains(edge.target()))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean add(L vertex) {
        return this.vertices.add(vertex);
    }
    
    @Override
    public int set(L source, L target, int weight) {
        vertices.add(source); vertices.add(target);
        Edge<L> newEdge = new Edge<>(source, target, weight);
        if (this.edges.contains(newEdge)) {
            edges.add(newEdge);
            return 0;
        } else {
            
        }
    }
    
    @Override
    public boolean remove(L vertex) {
        throw new RuntimeException("not implemented");
    }
    
    @Override
    public Set<L> vertices() {
        throw new RuntimeException("not implemented");
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        throw new RuntimeException("not implemented");
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        throw new RuntimeException("not implemented");
    }
    
    /**
     * Express the object as a human-readable string.
     * format:
     * e.g.
     * { [VERTEX], [VERTEX], [VERTEX] }\n
     * {\n
     * ([SOURCE] -> [TARGET])\n
     * ([SOURCE] -> [TARGET])\n
     * }
     * 
     * if empty,
     * { }\n
     * {\n
     * }
     */
    @Override
    public String toString() {
        final StringBuilder verticeBuilder = new StringBuilder();
        for (L vertex : this.vertices) {
            verticeBuilder.append(vertex)
              .append(", ");
        }
        final String VERTICES_STR = verticeBuilder.length() != 0 ?
                                    "{ " + verticeBuilder.substring(0, verticeBuilder.length() - 2) + " }\n" :
                                    "{ }";

        final StringBuilder edgesBuilder = new StringBuilder();
        for (Edge<L> e : this.edges) {
            edgesBuilder.append(e).append("\n");
        }
        final String EDGES_STR = "{\n" + edgesBuilder.toString() + "}";
        return VERTICES_STR + "\n" + EDGES_STR;
    }
}

/**
 * @brief Represent an edge between two nodes.
 * An edge is of a weighted directed graph.
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    private final L SOURCE;
    private final L TARGET;
    private final int WEIGHT;
    
    // Abstraction function:
    //   An edge from source to target.
    //   for edges (source, target), (a, b), (b, a),
    //   (a, b) and (b, a) are mapped to different abstract values.
    // Representation invariant:
    //   source != null && target != null.
    //   weight != 0
    // Safety from rep exposure:
    //   Every data declared as private, final, so that they are immutable.
    
    public Edge(final L source, final L target, int weight) {
        this.SOURCE = source;
        this.TARGET = target;
        this.WEIGHT = weight;
        checkRep();
    }
    
    private void checkRep() {
        assert this.SOURCE != null;
        assert this.TARGET != null;
        assert this.WEIGHT != 0;
    }
    
    /**
     * Get source of the edge.
     * 
     * @return source.
     */
    public String source() { return null; } // TODO

    /**
     * Get target of the edge.
     * 
     * @return target.
     */
    public String target() { return null; } // TODO
    
    /**
     * Express this object as a string.
     * ([source] -> [target], [weight])
     * 
     * @return decription of this object.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("(")
          .append(this.SOURCE)
          .append(" -> ")
          .append(this.TARGET)
          .append(", ")
          .append(this.WEIGHT)
          .append(")");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        return this.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.WEIGHT;
    }
}
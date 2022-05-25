/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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
    //
    //   The graph is irreflexive.

    // Safety from rep exposure:
    //   All instance members declared private and final.
    //   Mutable objects never revealed(such as return the pointer to the objects)
    //   to clients. Only immutable objects are returned to the clients.
    
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

    /**
     * @breif check if any container contains null.
     * @param container to check if it contains null.
     * @return true if the container contains null, otherwise false.
     */
    private <T> boolean containsNull(Collection<T> container) {
        for (T e : container) {
            if (e == null) { return true; }
        }
        return false;
    }

    private boolean containsAllVertex() {
        for (Edge<L> edge : edges) {
            if (!(vertices.contains(edge.source()) && vertices.contains(edge.target()))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean add(L vertex) {
        boolean result = this.vertices.add(vertex);
        checkRep();
        return result;
    }
    
    @Override
    public int set(L source, L target, int weight) {
        int returnValue = weight != 0 ? updateEdge(source, target, weight) : removeEdge(source, target);
        checkRep();
        return returnValue;
    }

    private int removeEdge(L source, L target) {
        Set<L> verticesSet = this.vertices();
        if (verticesSet.contains(source) && verticesSet.contains(target)) {
            Map<L, Integer> targets = this.targets(source);
            if (targets.isEmpty() || !targets.containsKey(target)) {
                return 0;
            } else {
                int idxToRemove = this.edges.indexOf(new Edge<L>(source, target, 1));
                int returnWeight = this.edges.get(idxToRemove).weight();
                this.edges.remove(idxToRemove);
                return returnWeight;
            }
        } else {
            return 0;
        }
    }

    private int updateEdge(L source, L target, int weight) {
        vertices.add(source);
        vertices.add(target);

        Edge<L> newEdge = new Edge<>(source, target, weight);
        int returnValue;
        if (this.edges.contains(newEdge)) {
            final int idx = this.edges.indexOf(newEdge);
            returnValue = this.edges.get(idx).weight();

            this.edges.remove(idx);
            this.edges.add(newEdge);
            
        } else {
            edges.add(newEdge);
            returnValue = 0;
        }
        return returnValue;
    }
    
    @Override
    public boolean remove(L vertex) {
        return this.vertices.remove(vertex);
    }
    
    @Override
    public Set<L> vertices() {
        return Collections.unmodifiableSet(this.vertices);
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        // Edge<L> is an immutable type. So returning a map object containing
        // Edge<L> objects is safe from rep exposure.
        // sourceContainer does not have to be an immutable type. Its mutability does not
        // violate rep independence.
        Map<L, Integer> sourcesContainer = new HashMap<>();
        for (Edge<L> edge : this.edges) {
            if (edge.target().equals(target)) {
                sourcesContainer.put(edge.source(), edge.weight());
            }
        }
        return sourcesContainer;
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        // Argument about rep indepence is same as the argument in sources method.
        Map<L, Integer> targetsContainer = new HashMap<>();
        for (Edge<L> edge : this.edges) {
            if (edge.source().equals(source)) {
                targetsContainer.put(edge.target(), edge.weight());
            }
        }
        return targetsContainer;
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
            verticeBuilder.append(vertex).append(", ");
        }
        final String VERTICES_STR = verticeBuilder.length() != 0 ?
                                    "{ " + verticeBuilder.substring(0, verticeBuilder.length() - 2) + " }" :
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
    //   L must be treated as an immutable type.
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
    public L source() { return this.SOURCE; }

    /**
     * Get target of the edge.
     * 
     * @return target.
     */
    public L target() { return this.TARGET; }

    /**
     * Get weight of the edge.
     * 
     * @return weight.
     */
    public int weight() { return this.WEIGHT; }
    
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

    /**
     * Determine whether two object is equivalent.
     * E: O1 x O2 -> Boolean
     * @param obj compared to this.
     * @return true if this and obj is equal based on abstracion function, otherwise, false.
     *         When obj is null, or obj is not instance of Edge<L>, also return false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }

        Edge<L> that;
        if (obj instanceof Edge) { 
            that = (Edge<L>)obj;
        } else { return false; }
        return (this.SOURCE.equals(that.source()) && this.TARGET.equals(that.target()));
    }

    @Override
    public int hashCode() {
        return this.SOURCE.hashCode() + this.TARGET.hashCode();
    }
}
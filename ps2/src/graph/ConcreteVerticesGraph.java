/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.ArrayList;
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
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represent an weighted directed graph with vertices.
    // Representation invariant:
    //   The number of vertices >= 0.
    //   (v1, v2) != (v2, v1) for some v1, v2
    //   where v1, v2 belong to V and (v1, v2), (v2, v1) belong to E
    //   for some graph G = (V, E).
    //
    //   No duplicate.
    //
    //   For some G = (V, E), 
    //   (u, v) belongs to E implies u belongs to V and v belongs to V.
    // Safety from rep exposure:
    //   this.vertices has been declared as private, and it is a constant.
    //   Vertex objects in vertices are never revealed to clients.
    
    public ConcreteVerticesGraph() {
        checkRep();
    }
    
    /**
     * @brief Check rep invariant.
     */
    private void checkRep() {
        assert !hasDuplicate();
        assert checkWhetherAdjacentVertexPointsToNonexistVertex();
    }

    private boolean hasDuplicate() {
        for (Vertex<L> vertex : this.vertices) {
            int idxOfVertex = this.vertices.indexOf(vertex);
            for (int i = idxOfVertex + 1; i < this.vertices.size(); i++) {
                if (vertex.equals(this.vertices.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkWhetherAdjacentVertexPointsToNonexistVertex() {
        for (Vertex<L> v : this.vertices) {
            for (Vertex<L> source : v.getSources().keySet()) {
                if (!this.vertices.contains(source)) {
                    return false;
                }
            }
            for (Vertex<L> target : v.getTargets().keySet()) {
                if (!this.vertices.contains(target)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean add(L vertex) {
        Vertex<L> newVertex = new Vertex<>(vertex);
        if (this.vertices.contains(newVertex)) {
            return false;
        } else {
            this.vertices.add(newVertex);
            checkRep();
            return true;
        }
    }
    
    @Override
    public int set(L source, L target, int weight) {
        Vertex<L> sourceAsVertex = new Vertex<>(source);
        Vertex<L> targetAsVertex = new Vertex<>(target);

        if (!this.vertices.contains(sourceAsVertex)) {
            this.vertices.add(sourceAsVertex);
        }
        if (!this.vertices.contains(targetAsVertex)) {
            this.vertices.add(targetAsVertex);
        }

        int result = 0;
        Vertex<L> s = this.vertices.get(this.vertices.indexOf(sourceAsVertex));
        Vertex<L> t = this.vertices.get(this.vertices.indexOf(targetAsVertex));
        if (weight == 0) {
            result = s.removeTarget(t);
            t.removeSource(s);
        } else {
            result = s.updateWeightToTarget(t, weight);
            t.updateWeightToSource(s, weight);
        }
        
        checkRep();
        return result;
    }
    
    @Override
    public boolean remove(L vertex) {
        Vertex<L> v = new Vertex<>(vertex);
        if (this.vertices.contains(v)) {
            Vertex<L> r = this.vertices.get(this.vertices.indexOf(v));
            for (Vertex<L> source : r.getSources().keySet()) {
                source.removeTarget(r);
            }
            for (Vertex<L> target : r.getTargets().keySet()) {
                target.removeSource(r);
            }
            this.vertices.remove(v);
            checkRep();
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Set<L> vertices() {
        Set<L> vertexSet = new HashSet<>();
        for (Vertex<L> vertex : this.vertices) {
            vertexSet.add(vertex.getValue());
        }
        return vertexSet;
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        return makeMap(findVertex(target).getSources());
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        return makeMap(findVertex(source).getTargets());
    }

    private Vertex<L> findVertex(L what) {
        Vertex<L> toFind = new Vertex<>(what);
        return this.vertices.get(this.vertices.indexOf(toFind));
    }

    private Map<L, Integer> makeMap(Map<Vertex<L>, Integer> orig) {
        Map<L, Integer> returnMap = new HashMap<>();
        for (Vertex<L> v : orig.keySet()) {
            returnMap.put(v.getValue(), orig.get(v));
        }
        return returnMap;
    }
    
    /**
     * @brief Get a string represent this object by a human-readable format.
     * 
     * @return a string represent this object's state.
     * - Format in the case that there exist something in this object.
     * { [vertice_1], [vertice_2], ..., [vertice_n] }\n
     * {\n
     * ([vertice_i] -> [vertice_j], [weight])\n
     * ...
     * ([vertice_k] -> [vertice_l], [weight])\n
     * }
     * - When it is empty.
     * { }\n    // for vertices
     * {\n      // for edges
     * }
     */
    @Override
    public String toString() {
        return addVerticesToString() + "\n" + addEdgesToString();
    }

    private String addVerticesToString() {
        final StringBuilder sb = new StringBuilder();
        for (L vertex : this.vertices()) {
            sb.append(vertex).append(", ");
        }
        return sb.isEmpty() ?
               "{ }" :
               "{ " + sb.substring(0, sb.length() - 2) + " }";
    }

    private String addEdgesToString() {
        final Set<L> valuesSet = this.vertices();
        final Set<Edge<L>> edgesCache = new HashSet<>();

        final StringBuilder sb = new StringBuilder();
        for (L vertexValue : valuesSet) {
            Map<L, Integer> sources = this.sources(vertexValue);
            for (L source : sources.keySet()) {
                addEdgeToString(sb, edgesCache, source, vertexValue, sources.get(source));
            }
            
            Map<L, Integer> targets = this.targets(vertexValue);
            for (L target : targets.keySet()) {
                addEdgeToString(sb, edgesCache, vertexValue, target, targets.get(target));
            }
        }
        return "{\n" + sb + "}";
    }

    private void addEdgeToString(StringBuilder sb,
                                 Set<Edge<L>> edges,
                                 L source,
                                 L target,
                                 int weight) {
        final Edge<L> edge = new Edge<>(source, target, weight);
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
}

/**
 * Representing a vertex in some graph.
 * This class also expresses incoming vertices and outgoing vertices.
 * This is a mutable type such that the list of sources and targets
 * can change. But the value the vertex represents is of a immutable type.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    private final L VALUE;

    private final Map<Vertex<L>, Integer> sources;
    private final Map<Vertex<L>, Integer> targets;
    
    // Abstraction function:
    //   A vertex of a graph.
    //   this.sources and this.targets represent adjacent vertices,
    //   sources and targets respectively.
    //   Integer objects in map object represent weight between two vertices.
    // Representation invariant:
    //   Weights != 0
    //   VALUE != null
    // Safety from rep exposure:
    //   Every members were declared as private,
    //   and VALUE is a constant.
    //   Vertex objects in this.sources and this.targets are never revealed to clients.
    
    public Vertex(L value) {
        this.VALUE = value;
        this.sources = new HashMap<>();
        this.targets = new HashMap<>();
        checkRep();
    }
    
    private void checkRep() {
        assert !containsZeroWeights(sources);
        assert !containsZeroWeights(targets);
        assert this.VALUE != null;
    }

    /**
     * @brief Check whether some of incident edges has 0 weight.
     * 
     * @param adjacentVertices checkes to vertices.
     * @return true if adjacentVertices contains edges of 0 weight, otherwise false.
     */
    private boolean containsZeroWeights(Map<Vertex<L>, Integer> adjacentVertices) {
        Set<Vertex<L>> vertices = adjacentVertices.keySet();
        for (Vertex<L> vertex : vertices) {
            if (adjacentVertices.get(vertex).equals(Integer.valueOf(0))) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get the value of this vertex.
     * 
     * @return value.
     */
    public L getValue() {
        return this.VALUE;
    }

    /**
     * Get sources.
     * 
     * @return return sources. Return object is surrounded by an immutable wrapper.
     */
    public Map<Vertex<L>, Integer> getSources() {
        return this.sources;
    }

    /**
     * Get targets.
     * 
     * @return return targets. Return object is surrounded by an immutable wrapper.
     */
    public Map<Vertex<L>, Integer> getTargets() {
        return this.targets;
    }

    /**
     * Add a adjacent vertex which is pointing this vertex.
     * 
     * @param source of the edge between [source] and [this].
     * @param weight of the edge between [source] and [this].
     * @return true if this did not have source in this.sources, otherwise false.
     */
    public boolean addSource(Vertex<L> source, int weight) {
        if (this.sources.containsKey(source)) {
            return false;
        } else {
            this.sources.put(source, weight);
            checkRep();
            return true;
        }
    }

    /**
     * Add a adjacent vertex whom this vertex is pointing.
     * 
     * @param target of the edge between [target] and [this].
     * @param weight of the edge between [target] and [this].
     * @return true if this did not have target in this.targets, otherwise false.
     */
    public boolean addTarget(Vertex<L> target, int weight) {
        if (this.targets.containsKey(target)) {
            return false;
        } else {
            this.targets.put(target, weight);
            checkRep();
            return true;
        }
    }

    /**
     * Update weight of the edge between source and this.
     * If there does not exist source in this.sources, add the vertex as a source,
     * and set weight.
     * 
     * @param source of the edge between [source] and [this].
     * @param newWeight of the edge between [source] and [this].
     * @return the weight of previous edge, 0 if there isn't edge.
     */
    public int updateWeightToSource(Vertex<L> source, int newWeight) {
        if (this.addSource(source, newWeight)) {
            return 0;
        }
        
        int returnValue = this.sources.put(source, newWeight);
        checkRep();
        return returnValue;
    }

    /**
     * Update weight of the edge between target and this.
     * If there does not exist target in this.targets, add the vertex as a target,
     * and set weight.
     * 
     * @param target of the edge between [target] and [this].
     * @param newWeight of the edge between [target] and [this].
     * @return the weight of previous edge, 0 if there isn't edge.
     */
    public int updateWeightToTarget(Vertex<L> target, int newWeight) {
        if (this.addTarget(target, newWeight)) {
            return 0;
        }

        int returnValue = this.targets.put(target, newWeight);
        checkRep();
        return returnValue;
    }

    /**
     * Remove a source from this vertex.
     * 
     * @param source to remove.
     * @return the previous weight of edge between this and source. 0 if there
     *         did not exist an edge.
     */
    public int removeSource(Vertex<L> source) {
        if (!this.sources.containsKey(source)) {
            return 0;
        }

        int returnValue = this.sources.remove(source);
        checkRep();
        return returnValue;
    }

    /**
     * Remove a target from this vertex.
     * 
     * @param target to remove.
     * @return the previous weight of edge between this and target. 0 if there
     *         did not exist an edge.
     */
    public int removeTarget(Vertex<L> target) {
        if (!this.targets.containsKey(target)) {
            return 0;
        }

        int returnValue = this.targets.remove(target);
        checkRep();
        return returnValue;
    }
    
    /**
     * @brief Get a string represent this object by a human-readable format.
     * 
     * @return formatted string.
     * - Format in the case that there exist something in this object.
     * * [VALUE.toString()]\n
     * {\n
     * [indegree_1] -> , [weight]\n
     * ...
     * [indegree_i] -> , [weight]\n
     * -> [outdegree_1] , [weight]\n
     * ...
     * -> [outdegree_j] , [weight]\n
     * }\n
     * - When its degree is 0.
     * * [VALUE.toString()]\n
     * {\n
     * }
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("* ").append(this.VALUE).append("\n{\n");

        Set<Vertex<L>> sourceSet = sources.keySet();
        Set<Vertex<L>> targetSet = targets.keySet();

        for (Vertex<L> source : sourceSet) {
            sb.append(source.getValue())
              .append(" -> , ")
              .append(sources.get(source))
              .append('\n');
        }

        for (Vertex<L> target : targetSet) {
            sb.append("-> ")
              .append(target.getValue())
              .append(" , ")
              .append(targets.get(target))
              .append('\n');
        }
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vertex)) {
            return false;
        }
        return this.getValue().equals(((Vertex<L>)obj).getValue());
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}

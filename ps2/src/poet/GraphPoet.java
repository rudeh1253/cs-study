/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Target;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //   Represent a graph which represents affinity between words.
    //   Given an input string containing words each of which is
    //   non-empty, case-insentive, of non-space characters,
    //   non-newline characeters, each word becomes a vertex of the graph,
    //   and an edge indicates adjacency between two words.
    //   For two adjacent vertices, a word w1 points to another word w2
    //   means w1 is followed by w2 in the input string.
    //   The weight of an edge indicates times of occurence of the adjacency.
    // Representation invariant:
    //   No duplicate of a vertex is allowed in the graph.
    //   The type of the value of weight is non-negative and non-zero integer.
    //   For every edge, e, in the graph, G = (V, E),
    //   |e| == 2 and v1 and v2 belong to V where v1 and v2 belong to e.
    // Safety from rep exposure:
    //   The attribute, graph, is declared as a private and final, and
    //   it will not be exposed outside by some methods such as returning
    //   the instance of that.
    //   The attribute, graph, is immutable and the types of elements in graph
    //   are also immutable.
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpusFile) throws IOException {
        final String corpus = readFile(corpusFile);
        final Queue<String> wordsContainer = parseCorpus(corpus);
        buildGraph(wordsContainer);
        checkRep();
    }

    /**
     * Read file and store the content as a string type.
     * 
     * @param corpusFile an object containing an information of file of corpus.
     * @return corpus.
     */
    private String readFile(final File corpusFile) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(corpusFile));
        final StringBuilder sb = new StringBuilder();
        String line = null; // modifable
        while ((line = br.readLine()) != null) {
            sb.append(line).append(' ');
        }
        br.close();
        return sb.toString();
    }

    /**
     * Parse corpus and store it to a queue data structure.
     * 
     * @param corpus input to make a GraphPoet.
     * @return a queue data structure containing corpus.
     *         The corpus will be split by [^a-zA-Z0-9]+
     *         Suppose a relation T: C -> Q where
     *         C indicates the input corpus and Q indicates the
     *         generated queue. cTq means a word which belongs to
     *         C is stored to Q, in the order of which appeared in C.
     *         For every word w_1 and w_2 belong to C and Q,
     *         w_1 is followed by w_2 in C implies T(w_1) has been put to Q
     *         before T(w_2) is put.
     */
    private Queue<String> parseCorpus(final String corpus) {
        final String[] parsedCorpus = corpus.split("[^a-zA-Z0-9]+");
        if (parsedCorpus.length <= 1) {
            return new LinkedList<>();
        }

        final Queue<String> wordContainer = new LinkedList<>();
        Collections.addAll(wordContainer, parsedCorpus);
        return wordContainer;
    }

    /**
     * Build a graph from a data structure containing the corpus.
     * This method modifies the state of this.graph.
     * 
     * @param corpusQueue a queue containing the corpus. Every element of queue must have been
     *                    input in order as appeared in the corpus.
     */
    private void buildGraph(Queue<String> corpusQueue) {
        String front = null;
        try {
            front = corpusQueue.remove();
        } catch (NoSuchElementException e) { return; }

        String back;
        while (!corpusQueue.isEmpty()) {
            back = corpusQueue.remove();
            final Map<String, Integer> targets = this.graph.targets(front);
            Integer weight = targets.get(back);
            setAffinityEdge(front, back, weight);
            front = back;
        }
    }

    private void setAffinityEdge(String source, String target, Integer weight) {
        if (weight == null) {
            this.graph.set(source, target, 1);
        } else {
            this.graph.set(source, target, weight + 1);
        }
    }

    /**
     * @brief Check rep invariant.
     */
    private void checkRep() {
        assert weightCheck();
    }

    private boolean weightCheck() {
        Set<String> vertices = this.graph.vertices();
        for (String s : vertices) {
            if (this.graph.targets(s).containsValue(Integer.valueOf(0))) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Generate a poem.
     * Given two paths which are 2-edge-long path and 3-edge-long path
     * with the same weight, insert 2-edge-long path as bridge words.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        final Queue<String> inputWords = getInputWordsQueue(input);
        String front;
        try {
            front = inputWords.remove();
        } catch (NoSuchElementException e) {
            return input;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(front + " ");
        while (!inputWords.isEmpty()) {
            String back = inputWords.remove();
            sb.append(getBridgeWords(front, back))
              .append(' ');
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**
     * Given two adjacent words in the input, return bridge words to insert into between
     * front and back according to the poem.
     * e.g. If there is such a path as follows, "Big" -> "brother" -> "watches" -> "you"
     * then given front as "big" and back as "you", this method will return
     * "brother watches"
     * 
     * @param front string which is start of a part of the poem.
     * @param back string which is end of a part of the poem.
     * @return a part of the poem.
     */
    private String getBridgeWords(final String front, final String back) {
        List<String> bridgeWords = getPath(front, back);
        StringBuilder sb = new StringBuilder();
        for (String word : bridgeWords) {
            sb.append(word).append(' ');
        }
        return sb.toString().equals("") ? ""
                            : sb.substring(0, sb.length() - 1);
    }

    private List<String> getPath(String start, String dest) {
        // BFS
        if (!this.graph.vertices().contains(start) || !this.graph.vertices().contains(dest)) {
            return new LinkedList<>();
        }

        Map<String, VertexOnPathInfo> pathData = new HashMap<>();
        for (String vertex : graph.vertices()) {
            pathData.put(vertex, new VertexOnPathInfo());
        }

        Queue<String> visit = new LinkedList<>();
        visit.add(start);
        final VertexOnPathInfo startVertexInfo = pathData.get(start);
        startVertexInfo.lengthOfPath = 0;
        startVertexInfo.predecessor = start;

        while (!visit.isEmpty()) { // Start bfs.
            String source = visit.remove();
            final VertexOnPathInfo sourceInfo = pathData.get(source);
            Map<String, Integer> targets = graph.targets(source);
            for (String adj : targets.keySet()) {
                final VertexOnPathInfo adjInfo = pathData.get(adj);
                if (adj.equals(dest) &&
                        adjInfo.lengthOfPath < sourceInfo.lengthOfPath) {
                    adjInfo.lengthOfPath = sourceInfo.lengthOfPath + targets.get(adj);
                    adjInfo.predecessor = source;
                } else if (adjInfo.predecessor == null) {
                    adjInfo.predecessor = source;
                    adjInfo.lengthOfPath = sourceInfo.lengthOfPath + targets.get(adj);
                } else {
                    continue;
                }
                visit.add(adj);
            }
        }

        List<String> path = new LinkedList<>();
        String predecessorVertex = pathData.get(dest).predecessor;
        Set<String> visited = new HashSet<>();
        while (!predecessorVertex.equals(start)) {
            if (visited.contains(predecessorVertex)) {
                return new LinkedList<>();
            }
            path.add(0, predecessorVertex);
            visited.add(predecessorVertex);
            VertexOnPathInfo vertexInfo = pathData.get(predecessorVertex);
            predecessorVertex = vertexInfo.predecessor;
        }
        return path;
    }

    /**
     * In dfs algorithm, we can store each vertex data of a path in this class.
     * This class will be used only in getPath method.
     */
    private class VertexOnPathInfo {
        public String predecessor = null;
        public int lengthOfPath = -1;
    }

    /**
     * Parse input string and put them all to a queue
     * 
     * @param input string to convert into a poem.
     * @return a queue containing parsed input strings.
     */
    private Queue<String> getInputWordsQueue(final String input) {
        final String[] splitStrs = input.split("[^a-zA-Z0-9]+");
        Queue<String> queue = new LinkedList<>();
        for (String str : splitStrs) {
            queue.add(str);
        }
        return queue;
    }
    
    /**
     * Generate a string describe the states of this instance,
     * making it be readable for human.
     * 
     * @return a string expressing states of this instance.
     *         
     *         u_i -> v_j, weight_ij\n
     *
     *         for every i, j where u_i and v_j belong to V
     *         where for some graph G = (V, E)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Set<String> verticesSet = this.graph.vertices();
        for (String vertex : verticesSet) {
            Map<String, Integer> t = this.graph.targets(vertex);
            Set<String> targetLabels = t.keySet();
            for (String targetLabel : targetLabels) {
                sb.append(vertex)
                  .append(" -> ")
                  .append(targetLabel)
                  .append(", ")
                  .append(t.get(targetLabel))
                  .append('\n');
            }
        }
        return sb.toString();
    }
}
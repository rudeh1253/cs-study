/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.junit.Test;

import graph.Graph;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    // TODO before ending up this assignemnt, remain this ROUTE variable to point null.
    public static final String ROUTE = "/home/pgd/cs-study/6.005/ps2/test/resources/";
    
    // Testing strategy
    //   For constructor testing, input some strings and check
    //   whether bugs exist by calling toString method and checking
    //   return string.
    //   For poem method testing, input some strings and check
    //   whether bugs exist by comparing each of the input string and
    //   corresponding desired output string.
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testConstructor() {
        // Test cases:
        // 1. ""
        // - expected:
        // none
        // 2. "stRing"
        // - expected:
        // none
        // 3. "Battle Field"
        // - expected:
        // "Battle" -1-> "Field"
        // 4. "Most men and women will grow up to love their
        // servitude and will never dream of revolution"
        // - expected:
        // "most" -1-> "men"
        // "men" -1-> "and"
        // "and" -1-> "women"
        // "women" -1-> "will"
        // "will" -1-> "grow"
        // "will" -1-> "never"
        // "grow" -1-> "up"
        // "up" -1-> "to"
        // "to" -1-> "love"
        // "love" -1-> "their"
        // "their" -1-> "servitude"
        // "servitude" -1-> "and"
        // "and" -1-> "will"
        // "never" -1-> "dream"
        // "dream" -1-> "of"
        // "of" -1-> "revolution"
        // 5. "Whenever you feel like criticizing anyone,” he
        // told me, “just remember that all the people in this world
        // have not had the advantages that you have had"
        // "whenever" -1-> "you"
        // "you" -1-> "feel"
        // "like" -1-> "criticizing"
        // "anyone" -1-> "he"
        // "he" -1-> "told"
        // "told" -1-> "me"
        // "me" -1-> "just"
        // "just" -1-> "remember"
        // "remember" -1-> "that"
        // "that" -1-> "all"
        // "all" -1-> "the"
        // "the" -1-> "people"
        // "people" -1-> "in"
        // "in" -1-> "this"
        // "this" -1-> "world"
        // "world" -1-> "have"
        // "have" -1-> "not"
        // "not" -1-> "had"
        // "had" -1-> "the"
        // "the" -1-> "advantages"
        // "advantages" -1-> "that"
        // "that" -1-> "you"
        // "you" -1-> "have"
        // "have" -1-> "had"

        final String TEST_FILE_NAME_1 = ROUTE + "test_case_1.txt";
        final String TEST_FILE_NAME_2 = ROUTE + "test_case_2.txt";
        final String TEST_FILE_NAME_3 = ROUTE + "test_case_3.txt";
        final String TEST_FILE_NAME_4 = ROUTE + "test_case_4.txt";
        final String TEST_FILE_NAME_5 = ROUTE + "test_case_5.txt";

        // 1
        try {
            System.out.println("1");
            final File testFile = new File(TEST_FILE_NAME_1);
            final GraphPoet testGraphPoet = new GraphPoet(testFile);
            supportTestConstructor(testGraphPoet, testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 2
        try {
            System.out.println("2");
            final File testFile = new File(TEST_FILE_NAME_2);
            final GraphPoet testGraphPoet = new GraphPoet(testFile);
            supportTestConstructor(testGraphPoet, testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //3
        try {
            System.out.println("3");
            final File testFile = new File(TEST_FILE_NAME_3);
            final GraphPoet testGraphPoet = new GraphPoet(testFile);
            supportTestConstructor(testGraphPoet, testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4
        try {
            System.out.println("4");
            final File testFile = new File(TEST_FILE_NAME_4);
            final GraphPoet testGraphPoet = new GraphPoet(testFile);
            supportTestConstructor(testGraphPoet, testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 5
        try {
            System.out.println("5");
            final File testFile = new File(TEST_FILE_NAME_5);
            final GraphPoet testGraphPoet = new GraphPoet(testFile);
            supportTestConstructor(testGraphPoet, testFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void supportTestConstructor(final GraphPoet testGraphPoet, final File testFile) throws IOException {

        final BufferedReader br = new BufferedReader(new FileReader(testFile));
        final Graph<String> testGraph = buildTestGraphFromFileInput(br);
        final Graph<String> actualGraph = buildActualGraphFromGraphPoetObj(testGraphPoet);
        System.out.println("Test Graph");
        System.out.println(testGraphPoet);
        System.out.println("\nActualGraph");
        System.out.println(actualGraph);
        
        final Set<String> verticesOfActualGraph = actualGraph.vertices();
        for (String vertex : verticesOfActualGraph) {
            Map<String, Integer> targetsOfAG = actualGraph.targets(vertex);
            Map<String, Integer> targetsOfTG = testGraph.targets(vertex);
            
            for (String key : targetsOfAG.keySet()) {
                Integer wa = targetsOfAG.get(key);
                Integer wt = targetsOfTG.get(key);
                assertTrue(wt != null && wa != null);
                assertEquals(wt, wa);
            }
        }
        br.close();
    }

    private Graph<String> buildActualGraphFromGraphPoetObj(final GraphPoet testGraphPoet) {
        Graph<String> actualGraph = Graph.empty();

        final String testGraphPoetAsString = testGraphPoet.toString();
        

        if (testGraphPoetAsString.equals("")) {
            return actualGraph;
        }

        String[] lines = testGraphPoetAsString.split("\n");
        for (String line : lines) {
            String[] parsedLine = line.split("[^a-zA-Z0-9]+");
            actualGraph.set(parsedLine[0], parsedLine[1], Integer.valueOf(parsedLine[2]));
        }
        return actualGraph;
    }

    private Graph<String> buildTestGraphFromFileInput(BufferedReader br)
                                            throws IOException {
        final String testString = exportString(br);
        final Queue<String> words = inputWords(testString);
        final Graph<String> outputGraph = Graph.empty();
        
        String front = words.remove();
        String back;
        while (!words.isEmpty()) {
            back = words.remove();
            Map<String, Integer> targets = outputGraph.targets(front);
            Integer weight = targets.get(back);
            if (weight == null || weight.equals(0)) {
                outputGraph.set(front, back, 1);
            } else {
                outputGraph.set(front, back, weight + 1);
            }
            front = back;
        }
        return outputGraph;
    }

    private Queue<String> inputWords(String testString) {
        String[] parsedArray = testString.split("[^a-zA-Z0-9]+");

        final Queue<String> words = new LinkedList<>();
        
        Collections.addAll(words, parsedArray);
        return words;
    }

    private String exportString(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = br.read()) != -1) {
            sb.append((char)c);
        }
        return sb.toString();
    }

    @Test
    public void testPoem() {
        // Test case:
        // - The number of words to build GraphPoet:
        // 0, 1, 2, 3, 4, 6
        // - The number of two-edge-long paths an input string contains
        // 0, 1, 2, 3
        // - An input string contains more than two-edge-long paths at least one.
        // true, false
        //
        // Composite cases:
        // (0, 0, false), (1, 0, false), (2, 0, false), (3, 0, false),
        // (3, 1, false), (4, 0, false), (4, 1, false), (4, 1, true),
        // (4, 2, false), (6, 0, false), (6, 0, true), (6, 1, false),
        // (6, 0, true), (6, 1, true), (6, 2, false), (6, 2, true)

        final String FILE_FORMAT = "test_poem_";
        final String[] TEST_FILES = new String[15];
        for (int i = 1; i <= 15; i++) {
            TEST_FILES[i - 1] = FILE_FORMAT + i + ".txt";
        }

        // (0, 0, false)
        // Build GraphPoet from: ""
        // Input string: "GNU not Unix."
        // Output string: "GNU not Unix."
        executeString(TEST_FILES[0], "GNU not Unix.", "GNU not Unix.");

        // (1, 0, false)
        // Build GraphPoet from: "GNU"
        // Input string: "GNU not Unix."
        // Output string: "GNU not Unix."
        executeString(TEST_FILES[1], "GNU not Unix.", "GNU not Unix.");

        // (2, 0, false)
        // Build GraphPoet from: "GNU is"
        // Input string: "GNU not Unix."
        // Output string: "GNU not Unix."
        executeString(TEST_FILES[2], "GNU not Unix.", "GNU not Unix.");

        // (3, 0, false)
        // Build GraphPoet from: "GNU is os"
        // Input string: "GNU not Unix."
        // Output string: "GNU not Unix."
        executeString(TEST_FILES[3], "GNU not Unix.", "GNU not Unix.");

        // (3, 1, false)
        // Build GraphPoet from: "GNU is not"
        // Input string: "GNU not Unix."
        // Output string: "GNU is not Unix."
        executeString(TEST_FILES[4], "GNU not Unix.", "GNU is not Unix.");
        
        // (4, 0, false)
        // Build GraphPoet from: "I am a boy."
        // Input string: "You are not a girl."
        // Output string: "You are not a girl."
        executeString(TEST_FILES[5], "Your are not a girl.", "You are not a girl.");

        // (4, 1, false)
        // Build GraphPoet from: "Manner makes a man."
        // Input string: "Experiment makes man."
        // Output string: "Experiment makes a man."
        executeString(TEST_FILES[6], "Experiment makes man", "Experiment makes a man");

        // (4, 0, true)
        // Build GraphPoet from: "Manner makes a man."
        // Input string: "Manner man."
        // Output string: "Manner makes a man."
        executeString(TEST_FILES[7], "Manner man.", "Manner makes a man.");

        // (4, 2, false)
        // Build GraphPoet from: "Introduction to Computer Science"
        // Input string: "Introduction computer is to Science."
        // Output string: "Introduction to computer is to computer Science."
        executeString(TEST_FILES[8], "Introduction computer is to Science."
                                   , "Introduction to computer is to computer Science.");

        // (6, 0, false)
        // Build GraphPoet from: "Introduction to Computational Thinking Data Science"
        // Input string: "The name \"Java\" came from a region which is famous for coffee."
        // Output string: "The name \"Java\" came from a region which is famous for coffee."
        executeString(TEST_FILES[9], "The name \"Java\" came from a region which is famous for coffee."
                                   , "The name \"Java\" came from a region which is famous for coffee.");

        // (6, 0, true)
        // Build GraphPoet from: "Science is a thinking with experiment"
        // Input string: "Science thinking"
        // Output string: "Science is a thinking"
        executeString(TEST_FILES[10], "Science thinking", "Science is a thinking");

        // (6, 1, false)
        // Build GraphPoet from: "Linear Algebra is pretty good course"
        // Input string: "He is good person."
        // Output string: "He is pretty good person."
        executeString(TEST_FILES[11], "He is good person.", "He is pretty good person.");

        // (6, 1, true)
        // Build GraphPoet from: "Nothing is better than you right"
        // Input string: "Nothing than you, but I'm better you"
        // Output string: "Nothing is better than you, but I'm better than you"
        executeString(TEST_FILES[12], "Nothing than you, but I'm better you", "Nothing is better than you, but I'm better than you");

        // (6, 2, false)
        // Build GraphPoet from: "Poem is maybe better than novel"
        // Input string: "Poem maybe than Desktop"
        // Output string: "Poem is maybe better than Desktop"
        executeString(TEST_FILES[13], "Poem maybe than Desktop", "Poem is maybe better than Desktop");

        // (6, 2, true)
        // Build GraphPoet from: "Java is maybe better than Python"
        // Input string: "Java maybe than C++, and maybe Python"
        // Output string: "Java is maybe better than C++, and maybe better than Python"
        executeString(TEST_FILES[14], "Java maybe than C++, and maybe Python", "Java is maybe better than C++, and maybe better than Python");
    }

    @Test
    public void testPoemWeight() {
        // A test for poems whose graph contains edges sharing a source but
        // having distinct weight.
        // Assume that there always exists an unique edge with the maximum weight.
        
        // two-edges-long
        // Build GraphPoet from: "I am trying to do something. I am trying to go somewhere.
        //                        I am wanting to make anything."
        // Input string:  "I am to be insensitive."
        // Output string: "I am trying to be insensitive."
        final String INPUT_1 = "I am to be insensitive.";
        final String OUTPUT_1 = "I am trying to be insensitive.";
        executeString("test_poem_16.txt", INPUT_1, OUTPUT_1);

        // three-edges-long
        // Build GraphPoet from: "Come to my home. Don't Come to my home. Instead,
        //                        Come from your home."
        // Input string: "Come home."
        // Output string: "Come to my home."
        final String INPUT_2 = "Come home.";
        final String OUTPUT_2 = "Come to my home.";
        executeString("test_poem_17.txt", INPUT_2, OUTPUT_2);
    }

    private void executeString(final String builderFileName, final String input, final String expectedOutput) {
        final String FOLDER = ROUTE + "test_poem_cases/";
        final File testFile = new File(FOLDER + builderFileName);
        try (final BufferedReader br = new BufferedReader(new FileReader(testFile));) {
            GraphPoet graphPoet = new GraphPoet(testFile);
            String actualOutput = graphPoet.poem(input);
            assertEquals(expectedOutput, actualOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
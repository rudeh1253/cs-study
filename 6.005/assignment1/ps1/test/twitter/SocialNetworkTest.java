/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /**
     * Test starategy:
     * 
     * - length of tweets
     * No tweet
     * One tweet
     * Several Tweets
     * 
     * - containing @-mention
     * No mention
     * One Mention
     * Several Mention
     */
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowersGraphOneObject() {
        String[] msgs = {
            "Bert is bullshit.", "Hello, @Bert I saw you.", "Fuck you @Kevin I hate you @Bert"
        };

        List<Tweet> tweets1 = new ArrayList<>();
        tweets1.add(getTweet("Ernie", msgs[0]));
        Map<String, Set<String>> followsGraph1 = SocialNetwork.guessFollowsGraph(tweets1);

        assertTrue("expected empty", followsGraph1.get("ernie").isEmpty());

        List<Tweet> tweets2 = new ArrayList<>();
        tweets2.add(getTweet("Ernie", msgs[1]));
        Map<String, Set<String>> followsGraph2 = SocialNetwork.guessFollowsGraph(tweets2);

        assertFalse("expected not empty", followsGraph2.isEmpty());
        assertTrue("expected contains key Ernie", followsGraph2.containsKey("ernie"));
        assertTrue("expected Ernie contains Bert", followsGraph2.get("ernie").contains("bert"));

        List<Tweet> tweets3 = new ArrayList<>();
        tweets3.add(getTweet("Ernie", msgs[2]));
        Map<String, Set<String>> followsGraph3 = SocialNetwork.guessFollowsGraph(tweets3);

        assertFalse("expected not empty", followsGraph3.get("ernie").isEmpty());
        assertTrue("expected contains key Ernie", followsGraph3.containsKey("ernie"));
        assertTrue("expected Ernie contains Bert", followsGraph3.get("ernie").containsAll(Arrays.asList("kevin", "bert")));
    }

    @Test
    public void testGuessFollwersGraphSeveralObjects() {
        List<Tweet> tweets = new ArrayList<>(Arrays.asList(
            getTweet("Ernie", "Come on @Newton"),
            getTweet("Ernie", "Sex on the beach with @Carthia"),
            getTweet("Ernie", "Oh my god @Newton"),
            getTweet("Ernie", "Sex! Sex!"),
            getTweet("Carthia", "I hate @Ernie")
        ));

        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(tweets);

        assertFalse("expected not empty", followsGraph.isEmpty());
        assertEquals(2, followsGraph.size());
        assertTrue("expected Ernie contains Newton, Carthia", followsGraph.get("ernie").containsAll(Arrays.asList("newton", "carthia")));
        assertTrue("expected Carthia contains Ernie", followsGraph.get("carthia").contains("ernie"));
    }

    private static int idCount = 1;

    private Tweet getTweet(String author, String msg) {
        
        return new Tweet(idCount++, author, msg, Instant.now());
    }
    
    /**
     * Test strategy:
     * 
     * Empty person
     * Every persons has no followers
     * Only one person
     * Five person
     */
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersNoFolowers() {
        Map<String, Set<String>> followersGraph = new HashMap<>();
        followersGraph.put("ernie", new HashSet<String>());
        followersGraph.put("kevin", new HashSet<String>());
        followersGraph.put("sawyer", new HashSet<String>());

        List<String> influencers = SocialNetwork.influencers(followersGraph);
        assertEquals(3, influencers.size());
        //assertTrue("order remained", influencers.get(0).equals("ernie"));
    }

    @Test
    public void testInfluencersOnePerson() {
        Map<String, Set<String>> followersGraph = new HashMap<>();
        followersGraph.put("ernie", new HashSet<String>());
        followersGraph.get("ernie").add("morgan");
        followersGraph.get("ernie").add("kevin");

        List<String> influencers = SocialNetwork.influencers(followersGraph);
        assertTrue("ernie is sole influencer", influencers.contains("ernie"));
    }

    @Test
    public void testInfluencersFivePerson() {
        Map<String, Set<String>> followersGraph = new HashMap<>();
        followersGraph.put("ernie", getFollowers("kor", "alex"));
        followersGraph.put("morgan", getFollowers("rick", "annie"));
        followersGraph.put("martin", getFollowers("tom"));
        followersGraph.put("evy", getFollowers("yamashita", "collin", "obama"));
        followersGraph.put("rick", getFollowers());

        List<String> influencers = SocialNetwork.influencers(followersGraph);

        assertEquals(5, influencers.size());
        assertTrue("evy is first", influencers.get(0).equals("evy"));
        assertTrue("rick is last", influencers.get(influencers.size() - 1).equals("rick"));
    }

    private Set<String> getFollowers(String...men) {
        Set<String> set = new HashSet<>();
        for (String man : men) {
            set.add(man);
        }

        return set;
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}

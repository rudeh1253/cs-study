/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");
    
    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    /**
     * Test strategy:
     * 
     * Partition the test cases into:
     * start == end
     * start < end
     * start > end
     * 
     * two tweets
     * three tweets
     */
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());

        Timespan timespan2 = Extract.getTimespan(Arrays.asList(tweet1, tweet1));

        assertEquals("expected start", d1, timespan2.getStart());
        assertEquals("expected end", d1, timespan2.getEnd());

        Timespan timespan3 = Extract.getTimespan(Arrays.asList(tweet2, tweet1));

        assertEquals("expected start", d1, timespan3.getStart());
        assertEquals("expected end", d2, timespan3.getEnd());
    }

    @Test
    public void testGetTimespanThreeTweets() {
        Instant d3 = Instant.parse("2016-02-17T12:00:00Z");
        Tweet addtionalTweet = new Tweet(3, "af", "abs", d3);

        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2, addtionalTweet));

        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d3, timespan.getEnd());

        Timespan timespan2 = Extract.getTimespan(Arrays.asList(tweet1, addtionalTweet, tweet2));

        assertEquals("expected start", d1, timespan2.getStart());
        assertEquals("expected end", d3, timespan2.getEnd());

        Timespan timespan3 = Extract.getTimespan(Arrays.asList(addtionalTweet, tweet1, tweet2));

        assertEquals("expected start", d1, timespan3.getStart());
        assertEquals("expected end", d3, timespan3.getEnd());
    }
    
    /**
     * Test strategy:
     * 
     * Partition the test cases into:
     *     1. A username-mention is preceded by a character valid in a Twitter username. (?@)
     *     2. No username-mention in message.
     *     3. One mention in message.
     *     4. Several mention in message. (maybe 2)
     *     5. Invalid mention.
     * 
     *     - Environment
     *     e1. A username-mention is at the beginning of message.
     *     e2. A username-mention is at the middle of message.
     *     e3. A username-mention is at the end of message.
     */
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());

        int count = 3;
        // additional test cases
        // case 1
        // e1
        assertTrue("expected empty set", getMentionedUsers(count++, "a@Name comhere").isEmpty());
        // e2
        assertTrue("expected empty set", getMentionedUsers(count++, "hello, a@World men").isEmpty());
        // e3
        assertTrue("expected empty set", getMentionedUsers(count++, "hello, a@World").isEmpty());

        // case 2 is executed before.

        final String SAMPLE_NAME1 = "@James";
        // case 3
        // e1
        Object[] case3MentionedUsers1 = getMentionedUsers(count++, SAMPLE_NAME1 + " where are you?").toArray();
        boolean case3Result1 = case3MentionedUsers1.length == 1 && case3MentionedUsers1[0].equals(SAMPLE_NAME1);
        assertTrue("expected set of length 1, contains \"James\"", case3Result1);
        // e2
        Object[] case3MentionedUsers2 = getMentionedUsers(count++, "Oh, " + SAMPLE_NAME1 + " where are you?").toArray();
        boolean case3Result2 = case3MentionedUsers2.length == 1 && case3MentionedUsers2[0].equals(SAMPLE_NAME1);
        assertTrue("expected set of length 1, contains \"James\"", case3Result2);
        // e3
        Object[] case3MentionedUsers3 = getMentionedUsers(count++, "Where are you, " + SAMPLE_NAME1).toArray();
        boolean case3Result3 = case3MentionedUsers3.length == 1 && case3MentionedUsers3[0].equals(SAMPLE_NAME1);
        assertTrue("expected set of length 1, contains \"James\"", case3Result3);

        final String SAMPLE_NAME2 = "@PIKA";
        // case4
        // (e1, e1)
        assertTrue("expected 2", getMentionedUsers(count++, SAMPLE_NAME1 + " " + SAMPLE_NAME2 + " hello").size() == 2);
        // (e1, e2)
        assertTrue("expected 2", getMentionedUsers(count++, SAMPLE_NAME1 + " hello " + SAMPLE_NAME2 + " good bye").size() == 2);
        // (e1, e3)
        assertTrue("expected 2", getMentionedUsers(count++, SAMPLE_NAME1 + "hello " + SAMPLE_NAME2).size() == 2);
        // (e2, e2)
        assertTrue("expected 2", getMentionedUsers(count++, "Hello, " + SAMPLE_NAME1 + " hi " + SAMPLE_NAME2 + " nothing").size() == 2);
        // (e2, e3)
        assertTrue("expected 2", getMentionedUsers(count++, "Hello, " + SAMPLE_NAME1 + "Good bye " + SAMPLE_NAME2).size() == 2);
        // (e3, e3)
        assertTrue("expected 2", getMentionedUsers(count++, "Hello, " + SAMPLE_NAME1 + " " + SAMPLE_NAME2).size() == 2);

        // case5
        // e1
        assertTrue("expected empty", getMentionedUsers(count++, "Hi, @,name hi").isEmpty());
        // e2
        assertTrue("expected empty", getMentionedUsers(count++, "Hi, @na,me hi").isEmpty());
        // e3
        assertTrue("expected empty", getMentionedUsers(count++, "Hi, @name, hi").isEmpty());
    }

    private Set<String> getMentionedUsers(int count, String msg) {
        final Tweet t = new Tweet(count, String.valueOf(count), msg, d1);

        return Extract.getMentionedUsers(Arrays.asList(t));
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}

/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        assert (tweets.size() >= 2);

        Instant fastest = tweets.get(0).getTimestamp();
        Instant lastest = tweets.get(0).getTimestamp();

        for (Tweet t : tweets) {
            if (fastest.isAfter(t.getTimestamp())) {
                fastest = t.getTimestamp();
            }
            if (lastest.isBefore(t.getTimestamp())) {
                lastest = t.getTimestamp();
            }
        }

        return new Timespan(fastest, lastest);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        final Set<String> mentionList = new TreeSet<>();
        assert mentionList.isEmpty();

        for (Tweet tweet : tweets) {
            String message = tweet.getText();
            addMention(mentionList, message);
        }

        return mentionList;
    }

    /**
     * Support method for getMentionedUsers() method.
     * Check message and add mention to mentionList.
     * @param mentionList will be modified
     * @param msg not won't be modified
     */
    private static void addMention(Set<String> mentionList, String msg) {
        int idxOfMention = -1;
        while ((idxOfMention = msg.indexOf("@", idxOfMention + 1)) != -1) {
            if (idxOfMention != 0 && !msg.substring(idxOfMention - 1, idxOfMention).equals(" ")) {
                continue;
            }

            int idxOfSpace = msg.indexOf(" ", idxOfMention);

            assert (idxOfMention == -1 || idxOfSpace == -1 || idxOfMention < idxOfSpace);

            if (idxOfSpace == -1) {
                if (idxOfMention != msg.length() - 1) {
                    String mention = msg.substring(idxOfMention, msg.length());
                    if (isValidMention(mention)) {
                        mentionList.add(mention);
                    }
                }
            } else if (idxOfSpace - idxOfMention > 1) {
                String mention = msg.substring(idxOfMention, idxOfSpace);
                if (isValidMention(mention)) {
                    mentionList.add(mention);
                }
            } 
        }
    }

    /**
     * Check mention if it is valid.
     */
    private static boolean isValidMention(String mention) {
        if (!mention.substring(0, 1).equals("@")) {

            return false;
        }

        for (int i = 1; i < mention.length(); i++) {
            if (!isVaildChar(mention.substring(i, i + 1))) {

                return false;
            }
        }

        return true;
    }

    private static String[] validChars = null;

    /**
     * Check character whether it is valid character for user name.
     * 
     * @param ch character to check
     * @return true if the character is valid.
     *         false if the character is invalid.
     */
    private static boolean isVaildChar(String ch) {
        if (validChars == null) {
            parseChars();
        }

        for (String c : validChars) {
            if (c.equals(ch)) {

                return true;
            }
        }

        return false;
    }

    /**
     * Parse characters valid in Twitter user name.
     */
    private static void parseChars() {
        final String validCharsString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
            + "0123456789_-";
        validChars = new String[validCharsString.length()];
        for (int i = 0; i < validCharsString.length(); i++) {
            validChars[i] = validCharsString.substring(i, i + 1);
        }
    }
}

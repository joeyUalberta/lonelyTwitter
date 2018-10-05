package ca.ualberta.cs.lonelytwitter;

/**
 * An exception that indicate the message passed is way too long
 */
public class TweetTooLongException extends Exception {

    TweetTooLongException() {
        super("The message is too long! Please keep your tweets within 140 characters.");
    }
}

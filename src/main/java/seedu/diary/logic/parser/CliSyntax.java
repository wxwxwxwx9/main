package seedu.diary.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_COMPANY = new Prefix("c/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("w/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_IS_ONLINE = new Prefix("o/");

    public static final Prefix[] ALL_PREFIXES = {
        PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_EMAIL,
        PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_IS_ONLINE
    };

}

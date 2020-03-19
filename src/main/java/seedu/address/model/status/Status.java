package seedu.address.model.status;

/**
 * Enum for the possible statuses an internship application can have.
 */
public enum Status {
    WISHLIST, APPLIED, INTERVIEW, OFFERED, REJECTED;

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be one of the following strings: "
                    + "wishlist, applied, interview, offered, rejected";

    private static final String[] validStatuses = new String[] {"wishlist", "applied", "interview",
        "offered", "rejected"};

    /**
     * Returns true is given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        String status = test.toLowerCase();
        boolean output = false;
        for (String s: validStatuses) {
            output = output || status.equals(s);
        }
        return output;
    }

}

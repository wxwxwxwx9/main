package seedu.diary.model.status;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for the possible statuses an internship application can have.
 */
public enum Status {
    WISHLIST, APPLIED, INTERVIEW, OFFERED, REJECTED, GHOSTED;

    public static final String MESSAGE_CONSTRAINTS =
        "Status should only be one of the following strings: "
            + "wishlist, applied, interview, offered, rejected, ghosted";

    private static final String[] validStatuses = new String[]{"wishlist", "applied", "interview",
        "offered", "rejected", "ghosted"};

    /**
     * Returns a list of possible Status which starts with test.
     */
    public static List<Status> possibleStatus(String test) {
        String regex = "^" + test.toUpperCase() + ".*";
        ArrayList<Status> possible = new ArrayList<>();
        for (Status s : Status.values()) {
            if (s.name().matches(regex)) {
                possible.add(s);
            }
        }
        return possible;
    }

    /**
     * Returns true is given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        String status = test.toLowerCase();
        boolean output = false;
        for (String s : validStatuses) {
            output = output || status.equals(s);
        }
        return output;
    }
}

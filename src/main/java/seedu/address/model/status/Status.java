package seedu.address.model.status;

/**
 * Enum for the possible statuses an internship application can have.
 */
public enum Status {
    ACTIVE, INACTIVE, PLAN_TO_APPLY, APPLICATION_DONE, INTERVIEW_SCHEDULED, INTERVIEW_DONE, ACCEPTED, REJECTED;

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be one of the following strings: "
                    + "active, inactive, plan to apply, plan_to_apply, application done, application_done"
                    + "interview scheduled, interview_scheduled, interview done, interview_done, accepted"
                    + "rejected";

    private static final String[] validStatuses = new String[] {"active", "inactive", "plan_to_apply",
        "application_done", "interview_scheduled", "interview_done", "accepted", "rejected"};

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

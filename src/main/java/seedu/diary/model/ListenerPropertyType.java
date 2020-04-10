package seedu.diary.model;

/**
 * Enums for the ListenerPropertyTypes to pass to addPropertyChangeListener in Model.
 */
public enum ListenerPropertyType {
    COMPARATOR("comparator"),
    PREDICATE("predicate"),
    FILTERED_INTERNSHIP_APPLICATIONS("filteredInternshipApplications"),
    DISPLAYED_INTERNSHIPS("displayedInternships"),
    VIEW_TYPE("currentView"),
    DISPLAYED_INTERNSHIP_DETAIL("displayedDetails"),
    DISPLAYED_INTERVIEWS("displayedInterviews");

    private String propertyName;

    ListenerPropertyType(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}

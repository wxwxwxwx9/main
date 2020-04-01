package seedu.address.model;

/**
 * Enums for the ListenerPropertyTypes to pass to addPropertyChangeListener in Model.
 */
public enum ListenerPropertyType {
    COMPARATOR("comparator"),
    PREDICATE("predicate"),
    FILTERED_INTERNSHIP_APPLICATIONS("filteredInternshipApplications"),
    DISPLAYED_INTERNSHIPS("displayedInternships"),
    VIEW_TYPE("currentView");

    private String propertyName;

    ListenerPropertyType(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String toString() {
        return propertyName;
    }
}

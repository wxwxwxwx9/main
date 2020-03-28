package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.internship.InternshipApplication;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class PredicateDisplayFooter extends UiPart<Region> {

    private static final String FXML = "PredicateDisplayFooter.fxml";

    @FXML
    private Label predicateLabel;

    public PredicateDisplayFooter(Logic logic) {
        super(FXML);
        predicateLabel.setText("Not Filtered.");
        updatePredicateDisplayOnChange(logic);
    }

    /**
     * Adds an event listener to update the predicateLabel upon any changes
     * in the given list of internship application.
     *
     * @param logic the Logic.
     */
    public void updatePredicateDisplayOnChange(Logic logic) {
        logic.getFilteredInternshipApplicationList().addListener((ListChangeListener<InternshipApplication>) c -> {
            while (c.next()) {
                updatePredicateDisplay(logic.getPredicateString());
            }
        });
    }

    /**
     * Computes and updates the predicateLabel.
     *
     * @param predicateStr predicate string that generates relevant display.
     */
    public void updatePredicateDisplay(String predicateStr) {
        if (predicateStr == null || predicateStr.equals("")) {
            predicateLabel.setText("Not Filtered.");
        } else {
            predicateLabel.setText("Finding: " + predicateStr);
        }
    }

}

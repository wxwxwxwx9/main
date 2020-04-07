package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Predicate;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.InternshipApplication;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class PredicateDisplayFooter extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "PredicateDisplayFooter.fxml";

    @FXML
    private Label predicateLabel;

    public PredicateDisplayFooter() {
        super(FXML);
        updatePredicateDisplay(null);
    }

    /**
     * Receives the latest changes in Comparator from internship diary.
     * Updates the relevant display accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        updatePredicateDisplay((Predicate<InternshipApplication>) e.getNewValue());
    }

    /**
     * Computes and updates the predicateLabel.
     *
     * @param predicate predicate that generates relevant display.
     */
    private void updatePredicateDisplay(Predicate<InternshipApplication> predicate) {
        if (predicate == null) {
            predicateLabel.setText("Not Filtered");
        } else {
            predicateLabel.setText("Finding: " + predicate.toString());
        }
    }

}

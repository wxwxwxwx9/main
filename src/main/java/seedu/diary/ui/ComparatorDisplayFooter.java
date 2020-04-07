package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.InternshipApplication;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class ComparatorDisplayFooter extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "ComparatorDisplayFooter.fxml";

    @FXML
    private Label comparatorLabel;

    public ComparatorDisplayFooter() {
        super(FXML);
        updateComparatorDisplay(null);
    }

    /**
     * Receives the latest changes in Comparator from internship diary.
     * Updates the relevant display accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        updateComparatorDisplay((Comparator<InternshipApplication>) e.getNewValue());
    }

    /**
     * Computes and updates the comparatorLabel.
     *
     * @param comparator comparator object that generates relevant display.
     */
    private void updateComparatorDisplay(Comparator<InternshipApplication> comparator) {
        if (comparator == null) {
            comparatorLabel.setText("Not Sorted");
        } else {
            comparatorLabel.setText("Sorted by: " + comparator.toString());
        }
    }

}

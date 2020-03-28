package seedu.address.ui;

import java.util.Comparator;
import java.util.Objects;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.Logic;
import seedu.address.model.internship.InternshipApplication;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class ComparatorDisplayFooter extends UiPart<Region> {

    private static final String FXML = "ComparatorDisplayFooter.fxml";
    private Comparator<InternshipApplication> currentComparator;

    @FXML
    private Label comparatorLabel;

    public ComparatorDisplayFooter(Logic logic) {
        super(FXML);
        Comparator<InternshipApplication> comparator = logic.getComparator();
        comparatorLabel.setText("Not Sorted.");
        updateComparatorDisplay(comparator);
        updateComparatorDisplayOnChange(logic);
    }

    /**
     * Adds an event listener to update the comparatorLabel upon any changes
     * in the given list of internship application.
     *
     * @param logic the Logic.
     */
    public void updateComparatorDisplayOnChange(Logic logic) {
        logic.getFilteredInternshipApplicationList().addListener((ListChangeListener<InternshipApplication>) c -> {
            while (c.next()) {
                updateComparatorDisplay(logic.getComparator());
            }
        });
    }

    /**
     * Computes and updates the comparatorLabel.
     *
     * @param comparator comparator object that generates relevant display.
     */
    public void updateComparatorDisplay(Comparator<InternshipApplication> comparator) {
        if (Objects.equals(comparator, currentComparator)) {
            return;
        }
        if (comparator == null) {
            comparatorLabel.setText("Not Sorted.");
        } else {
            comparatorLabel.setText("Sorted by: " + comparator.toString());
        }
        currentComparator = comparator;
    }

}

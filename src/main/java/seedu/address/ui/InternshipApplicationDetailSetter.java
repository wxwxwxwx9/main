package seedu.address.ui;

import static seedu.address.model.ListenerPropertyType.DISPLAYED_INTERNSHIP_DETAIL;
import static seedu.address.model.ListenerPropertyType.PREDICATE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.address.model.internship.InternshipApplication;

/**
 * A graphical interface for the statistics that is displayed at the footer of the application.
 */
public class InternshipApplicationDetailSetter implements PropertyChangeListener {

    private static final String FXML = "ComparatorDisplayFooter.fxml";

    private final StackPane internshipApplicationDetailPlaceholder;
    private InternshipApplication internshipApplication;
    private Region internshipApplicationDetailRoot;

    public InternshipApplicationDetailSetter(StackPane internshipApplicationDetailPlaceholder) {
        this.internshipApplicationDetailPlaceholder = internshipApplicationDetailPlaceholder;
    }

    /**
     * Receives the latest changes in Comparator from internship diary.
     * Updates the relevant display accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        if (propertyName.equals(DISPLAYED_INTERNSHIP_DETAIL.toString())) {
            if (Objects.equals(e.getOldValue(), internshipApplication) || e.getOldValue() == null) {
                updateInternshipDetail((InternshipApplication) e.getNewValue());
            }
        } else if (propertyName.equals(PREDICATE.toString())) {
            Predicate<InternshipApplication> predicate = (Predicate<InternshipApplication>) e.getNewValue();
            if (predicate != null && !predicate.test(this.internshipApplication)) {
                updateInternshipDetail(null);
            }
        } else {
            throw new RuntimeException("InternshipApplicationDetailSetter listening to unhandled property name");
        }
    }

    /**
     * Computes and updates the InternshipApplicationDetail.
     *
     * @param internshipApplication internshipApplication object that generates relevant display.
     */
    public void updateInternshipDetail(InternshipApplication internshipApplication) {
        if (Objects.equals(this.internshipApplication, internshipApplication)) {
            return;
        }
        this.internshipApplication = internshipApplication;

        internshipApplicationDetailPlaceholder.getChildren().remove(internshipApplicationDetailRoot);

        if (internshipApplication != null) {
            this.internshipApplicationDetailRoot = new InternshipApplicationDetail(internshipApplication).getRoot();
            internshipApplicationDetailPlaceholder.getChildren().add(internshipApplicationDetailRoot);
        }
    }

}

package seedu.diary.ui;

import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERNSHIP_DETAIL;
import static seedu.diary.model.ListenerPropertyType.PREDICATE;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.diary.model.internship.InternshipApplication;

/**
 * A setter for the internship application details on the stack pane.
 */
public class InternshipApplicationDetailSetter implements PropertyChangeListener {
    private final StackPane internshipApplicationDetailPlaceholder;
    private InternshipApplication internshipApplication;
    private Region internshipApplicationDetailRoot;

    public InternshipApplicationDetailSetter(StackPane internshipApplicationDetailPlaceholder) {
        this.internshipApplicationDetailPlaceholder = internshipApplicationDetailPlaceholder;
    }

    /**
     * Receives the latest changes in predicate or the internship application selected from internship diary.
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
            if (internshipApplication == null) {
                return;
            }

            Predicate<InternshipApplication> predicate = (Predicate<InternshipApplication>) e.getNewValue();
            if (predicate != null && !predicate.test(this.internshipApplication)) {
                updateInternshipDetail(null);
            }
        } else {
            throw new IllegalStateException("InternshipApplicationDetailSetter listening to unhandled property name");
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

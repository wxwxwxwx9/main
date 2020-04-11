package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.diary.commons.core.LogsCenter;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.predicate.ApplicationDateDuePredicate;
import seedu.diary.model.internship.predicate.InterviewDateDuePredicate;
import seedu.diary.model.internship.predicate.StatusIsInterviewPredicate;
import seedu.diary.model.internship.predicate.StatusIsWishlistPredicate;
import seedu.diary.model.status.Status;

/**
 * Panel containing the list of internship applications.
 */
public class InternshipApplicationListPanel extends UiPart<Region> implements PropertyChangeListener {
    private static final String FXML = "InternshipApplicationListPanel.fxml";
    private static final String UPCOMING_BACKGROUND_COLOR = "-fx-background-color: #0d914f";
    private static final String GHOSTED_BACKGROUND_COLOR = "-fx-background-color: CRIMSON";
    private final Logger logger = LogsCenter.getLogger(InternshipApplicationListPanel.class);

    @FXML
    private ListView<InternshipApplication> internshipApplicationListView;

    public InternshipApplicationListPanel(ObservableList<InternshipApplication> internshipApplicationList) {
        super(FXML);
        internshipApplicationListView.setItems(internshipApplicationList);
        internshipApplicationListView.setCellFactory(listView -> new InternshipApplicationListViewCell());
    }

    public ListView<InternshipApplication> getInternshipApplicationListView() {
        return internshipApplicationListView;
    }

    /**
     * Receives the latest changes in displayed internships from internship diary.
     * Updates the internship application list view accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        ObservableList<InternshipApplication> ia = (ObservableList<InternshipApplication>) e.getNewValue();
        internshipApplicationListView.setItems(ia);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InternshipApplication} using a
     * {@code InternshipApplicationCard}.
     */
    class InternshipApplicationListViewCell extends ListCell<InternshipApplication> {
        private String originalStyle = getStyle();

        @Override
        protected void updateItem(InternshipApplication internshipApplication, boolean empty) {
            super.updateItem(internshipApplication, empty);

            if (empty || internshipApplication == null) {
                setGraphic(null);
                setText(null);
                setStyle(originalStyle);
            } else {
                setGraphic(new InternshipApplicationCard(internshipApplication, getIndex() + 1).getRoot());
                if (internshipApplication.isArchived()) {
                    setStyle(originalStyle);
                } else if (isUpcoming(internshipApplication)) {
                    setStyle(UPCOMING_BACKGROUND_COLOR);
                } else if (internshipApplication.getStatus().equals(Status.GHOSTED)) {
                    setStyle(GHOSTED_BACKGROUND_COLOR);
                } else {
                    setStyle(originalStyle);
                }
            }
        }

        /**
         * Verify if the given internship application has upcoming deadline or interview.
         *
         * @param internshipApplication The internship application to check.
         * @return true if the application has a application deadline and is of status wishlist, or if the application
         * has an upcoming interview and is of status interview. False otherwise.
         */
        private boolean isUpcoming(InternshipApplication internshipApplication) {
            Predicate<InternshipApplication> applicationDateDuePredicate =
                new ApplicationDateDuePredicate().and(new StatusIsWishlistPredicate());
            Predicate<InternshipApplication> upcomingInterviewDatePredicate =
                new InterviewDateDuePredicate().and(new StatusIsInterviewPredicate());
            return applicationDateDuePredicate.or(upcomingInterviewDatePredicate).test(internshipApplication);
        }
    }

}

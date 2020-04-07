package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.diary.commons.core.archival.InternshipApplicationViewType;

/**
 * A graphical interface for the current view type that is displayed at the footer of the application.
 */
public class ViewDisplayFooter extends UiPart<Region> implements PropertyChangeListener {

    private static final String FXML = "ViewDisplayFooter.fxml";
    private static final String UNARCHIVED_LIST = "MAIN";
    private static final String ARCHIVED_LIST = "ARCHIVAL";

    @FXML
    private Label viewLabel;

    public ViewDisplayFooter() {
        super(FXML);
        updateViewDisplay(InternshipApplicationViewType.UNARCHIVED);
    }

    /**
     * Receives the latest changes in displayed internships from internship diary.
     * Updates the relevant display accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        updateViewDisplay((InternshipApplicationViewType) e.getNewValue());
    }

    /**
     * Computes and updates the viewLabel.
     *
     * @param viewType enumeration that indicates the current display.
     */
    private void updateViewDisplay(InternshipApplicationViewType viewType) {
        if (viewType.equals(InternshipApplicationViewType.UNARCHIVED)) {
            viewLabel.setText(UNARCHIVED_LIST);
        } else {
            viewLabel.setText(ARCHIVED_LIST);
        }
    }

}

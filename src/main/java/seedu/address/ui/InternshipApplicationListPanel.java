package seedu.address.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.internship.InternshipApplication;

/**
 * Panel containing the list of internship applications.
 */
public class InternshipApplicationListPanel extends UiPart<Region> implements PropertyChangeListener {
    private static final String FXML = "InternshipApplicationListPanel.fxml";
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
        @Override
        protected void updateItem(InternshipApplication internshipApplication, boolean empty) {
            super.updateItem(internshipApplication, empty);

            if (empty || internshipApplication == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InternshipApplicationCard(internshipApplication, getIndex() + 1).getRoot());
            }
        }
    }

}

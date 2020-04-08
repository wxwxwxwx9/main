package seedu.diary.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.interview.Interview;

/**
 * Panel containing the list of interviews in a specific Internship Application.
 */
public class InterviewListPanel extends UiPart<Region> {

    public static final String FXML = "InterviewListPanel.fxml";

    @FXML
    private ListView<Interview> interviewListView;

    public InterviewListPanel(ObservableList<Interview> interviews) {
        super(FXML);
        interviewListView.setItems(interviews);
        interviewListView.setCellFactory(listView -> new InternshipApplicationListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code InternshipApplication} using a
     * {@code InternshipApplicationCard}.
     */
    class InternshipApplicationListViewCell extends ListCell<Interview> {
        @Override
        protected void updateItem(Interview interview, boolean empty) {
            super.updateItem(interview, empty);

            if (empty || interview == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new InterviewCard(interview, getIndex() + 1).getRoot());
            }
        }
    }
}

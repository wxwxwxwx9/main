package seedu.diary.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.interview.Interview;


/**
 * Panel containing the list of interviews in a specific Internship Application.
 */
public class InterviewListPanel extends UiPart<Region> implements PropertyChangeListener {

    public static final String FXML = "InterviewListPanel.fxml";

    @FXML
    private ListView<Interview> interviewListView;

    public InterviewListPanel(ObservableList<Interview> interviews) {
        super(FXML);
        interviewListView.setItems(interviews);
        interviewListView.setCellFactory(listView -> new InternshipApplicationListViewCell());
    }

    /**
     * Receives the latest changes in displayed interviews from the internship application.
     * Updates the interview list view accordingly.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(PropertyChangeEvent e) {
        List<Interview> interviews = (ArrayList<Interview>) e.getNewValue();
        interviewListView.setItems(FXCollections.observableArrayList(interviews));
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Interview} using a
     * {@code InterviewCard}.
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

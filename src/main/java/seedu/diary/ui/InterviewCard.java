package seedu.diary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.interview.Interview;

/**
 * A UI component that shows information regarding an {@code Interview}.
 */
public class InterviewCard extends UiPart<Region> {

    public static final String FXML = "InterviewCard.fxml";

    public final Interview interview;

    @FXML
    private HBox cardPane;

    @FXML
    private Label title;

    @FXML
    private Label date;

    @FXML
    private Label address;

    public InterviewCard(Interview interview, int index) {
        super(FXML);
        this.interview = interview;
        String title = "Interview ";
        date.setText(interview.getDate().printDate());
        if (interview.getIsOnline()) {
            this.title.setText(index + ". ONLINE " + title);
        } else {
            this.title.setText(index + ". OFFLINE " + title);
        }
        address.setText(interview.getInterviewAddress().value);
    }
}

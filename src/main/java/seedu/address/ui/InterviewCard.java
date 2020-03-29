package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.interview.Interview;

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
        String title = "Interview " + index + ": ";
        date.setText(interview.getDate().toString());
        if (interview.isOnline) {
            this.title.setText("ONLINE " + title);
        } else {
            this.title.setText("OFFLINE " + title);
        }
        address.setText(interview.getInterviewAddress().value);
    }
}

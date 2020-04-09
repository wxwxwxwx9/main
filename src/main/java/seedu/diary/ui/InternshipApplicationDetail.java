package seedu.diary.ui;

import static seedu.diary.model.ListenerPropertyType.DISPLAYED_INTERVIEWS;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import seedu.diary.model.internship.InternshipApplication;


/**
 * Represents a panel with the details of a specific {@code InternshipApplication}.
 */
public class InternshipApplicationDetail extends UiPart<Region> {

    private static final String FXML = "InternshipApplicationDetail.fxml";

    /** Utility method to pad strings to the right until fixed length. */
    public static String fixedLengthPadRight(String string, int length) {
        return String.format("%1$-"+length+ "s", string);
    }


    private InternshipApplication internshipApplication;

    private InterviewListPanel interviewListPanel;

    @FXML
    private SplitPane splitPanePlaceHolder;

    @FXML
    private StackPane interviewListPanelPlaceHolder;

    @FXML
    private HBox detailPane;

    @FXML
    private Label company;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    @FXML
    private Label email;

    @FXML
    private Label role;

    @FXML
    private Label applicationDate;

    @FXML
    private Label priority;

    @FXML
    private Label status;

    @FXML
    private Label interviewPreamble;

    public InternshipApplicationDetail(InternshipApplication internshipApplication) {
        super(FXML);
        company.setText(internshipApplication.getCompany().fullCompany);
        phone.setText(
            fixedLengthPadRight("Phone:", 20) + internshipApplication.getPhone().value);
        address.setText(
            fixedLengthPadRight("Address:", 20) + internshipApplication.getAddress().value);
        email.setText(
            fixedLengthPadRight("Email:", 20) + internshipApplication.getEmail().value);
        role.setText(
            fixedLengthPadRight("Role:", 20) + internshipApplication.getRole().fullRole);
        priority.setText(
            fixedLengthPadRight("Priority:", 20) + internshipApplication.getPriority().fullPriority);
        applicationDate.setText(
            fixedLengthPadRight("Application Date:", 20)
                + internshipApplication.getApplicationDate().printDate());
        status.setText(
            fixedLengthPadRight("Status:", 20) + internshipApplication.getStatus().toString()
                + internshipApplication.getLastStageMessage());
        interviewPreamble.setText("Interviews: ");
        interviewListPanel = new InterviewListPanel(
            FXCollections.observableArrayList(internshipApplication.getInterviews()));
        interviewListPanelPlaceHolder.getChildren().add(interviewListPanel.getRoot());
        // remove old listeners to InternshipApplication
        internshipApplication.removeAllPropertyChangeListener();
        // initiate listener to InternshipApplication
        internshipApplication.addPropertyChangeListener(DISPLAYED_INTERVIEWS, interviewListPanel);

    }
}

package seedu.address.ui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.internship.InternshipApplication;

/**
 * Represents a panel with the details of a specific {@code InternshipApplication}.
 */
public class InternshipApplicationDetail extends UiPart<Region> {

    private static final String FXML = "InternshipApplicationDetail.fxml";
    // Whitespace utility for printing details.
    private static final String ROLE_WHITESPACE = "                        ";
    private static final String PHONE_WHITESPACE = "                     ";
    private static final String ADDRESS_WHITESPACE = "                  ";
    private static final String EMAIL_WHITESPACE = "                      ";
    private static final String DATE_WHITESPACE = "    ";
    private static final String PRIORITY_WHITESPACE = "                   ";
    private static final String STATUS_WHITESPACE = "                     ";


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
    private Label id;

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
                "Phone:" + PHONE_WHITESPACE + internshipApplication.getPhone().value);
        address.setText(
                "Address:" + ADDRESS_WHITESPACE + internshipApplication.getAddress().value);
        email.setText(
                "Email:" + EMAIL_WHITESPACE + internshipApplication.getEmail().value);
        role.setText(
                "Role:" + ROLE_WHITESPACE + internshipApplication.getRole().fullRole);
        priority.setText(
                "Priority:" + PRIORITY_WHITESPACE + internshipApplication.getPriority().fullPriority);
        applicationDate.setText(
                "Application Date:" + DATE_WHITESPACE + internshipApplication.getApplicationDate().printDate());
        status.setText(
                "Status:" + STATUS_WHITESPACE + internshipApplication.getStatus().toString()
                        + internshipApplication.getLastStageMessage());
        interviewPreamble.setText("Interviews: ");
        interviewListPanel = new InterviewListPanel(
                FXCollections.observableArrayList(internshipApplication.getInterviews()));
        interviewListPanelPlaceHolder.getChildren().add(interviewListPanel.getRoot());
    }
}

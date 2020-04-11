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
    // Whitespace utility for printing details.


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
        phone.setText(internshipApplication.getPhone().value);
        address.setText(internshipApplication.getAddress().value);
        email.setText(internshipApplication.getEmail().value);
        role.setText(internshipApplication.getRole().fullRole);
        priority.setText(internshipApplication.getPriority().fullPriority + "");
        applicationDate.setText(internshipApplication.getApplicationDate().printDate());
        status.setText(internshipApplication.getStatus().toString()
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

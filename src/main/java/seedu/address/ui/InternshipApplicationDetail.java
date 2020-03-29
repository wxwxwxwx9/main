package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.InternshipApplication;


public class InternshipApplicationDetail extends UiPart<Region> {

    private static String FXML = "InternshipApplicationDetail.fxml";

    public InternshipApplication internshipApplication;

    @FXML
    private HBox cardPane;
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
    private Label isArchive;

    public InternshipApplicationDetail(InternshipApplication internshipApplication) {
        super(FXML);
        company.setText(internshipApplication.getCompany().fullCompany);
        phone.setText(internshipApplication.getPhone().value);
        address.setText(internshipApplication.getAddress().value);
        email.setText(internshipApplication.getEmail().value);
        role.setText(internshipApplication.getRole().fullRole);
        priority.setText(Integer.toString(internshipApplication.getPriority().fullPriority));
        applicationDate.setText(internshipApplication.getApplicationDate().toString());
        status.setText(internshipApplication.getStatus().toString());
        isArchive.setText("Archived: " + internshipApplication.isArchived().toString());
    }
}

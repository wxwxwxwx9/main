package seedu.diary.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.status.Status;

/**
 * An UI component that displays information of a {@code InternshipApplication}.
 */
public class InternshipApplicationCard extends UiPart<Region> {

    private static final String FXML = "InternshipApplicationListCard.fxml";
    private static final String WISHLIST_COLOR = "-fx-background-color: #cd70ff;";
    private static final String APPLIED_COLOR = "-fx-background-color: #209cee;";
    private static final String INTERVIEW_COLOR = "-fx-background-color: #22c65b;";
    private static final String OFFERED_COLOR = "-fx-text-fill: black; -fx-background-color: #ffdd57;";
    private static final String REJECTED_COLOR = "-fx-background-color: #ff3860;";
    private static final String GHOSTED_COLOR = "-fx-text-fill: black; -fx-background-color: #cdcdcd;";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final InternshipApplication internshipApplication;

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
    private FlowPane status;

    public InternshipApplicationCard(InternshipApplication internshipApplication, int displayedIndex) {
        super(FXML);
        this.internshipApplication = internshipApplication;
        id.setText(displayedIndex + ". ");
        company.setText(internshipApplication.getCompany().fullCompany);
        role.setText(internshipApplication.getRole().fullRole);
        Label statusLabel = getStatusLabel();
        status.getChildren().add(statusLabel);
    }

    /**
     * Gets the status label colored based on the status of the internship application.
     *
     * @return the status label colored based on the status of the internship application
     */
    private Label getStatusLabel() {
        Status internshipApplicationStatus = internshipApplication.getStatus();
        Label statusLabel = new Label(internshipApplicationStatus.toString().toLowerCase());
        switch (internshipApplicationStatus) {
        case WISHLIST:
            statusLabel.setStyle(WISHLIST_COLOR);
            break;
        case APPLIED:
            statusLabel.setStyle(APPLIED_COLOR);
            break;
        case INTERVIEW:
            statusLabel.setStyle(INTERVIEW_COLOR);
            break;
        case OFFERED:
            statusLabel.setStyle(OFFERED_COLOR);
            break;
        case REJECTED:
            statusLabel.setStyle(REJECTED_COLOR);
            break;
        case GHOSTED:
            statusLabel.setStyle(GHOSTED_COLOR);
            break;
        default:
            break;
        }
        return statusLabel;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InternshipApplicationCard)) {
            return false;
        }

        // state check
        InternshipApplicationCard card = (InternshipApplicationCard) other;
        return id.getText().equals(card.id.getText())
            && internshipApplication.equals(card.internshipApplication);
    }
}

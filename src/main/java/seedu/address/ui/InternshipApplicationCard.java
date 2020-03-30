package seedu.address.ui;

import static seedu.address.model.status.Status.APPLIED;
import static seedu.address.model.status.Status.INTERVIEW;
import static seedu.address.model.status.Status.OFFERED;
import static seedu.address.model.status.Status.REJECTED;
import static seedu.address.model.status.Status.WISHLIST;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.status.Status;

/**
 * An UI component that displays information of a {@code InternshipApplication}.
 */
public class InternshipApplicationCard extends UiPart<Region> {

    private static final String FXML = "InternshipApplicationListCard.fxml";

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
            statusLabel.setStyle("-fx-background-color: #cd70ff");
            break;
        case APPLIED:
            statusLabel.setStyle("-fx-background-color: #209cee");
            break;
        case INTERVIEW:
            statusLabel.setStyle("-fx-background-color: #22c65b");
            break;
        case OFFERED:
            statusLabel.setStyle("-fx-text-fill: black; -fx-background-color: #ffdd57");
            break;
        case REJECTED:
            statusLabel.setStyle("-fx-background-color: #ff3860; ");
            break;
        case GHOSTED:
            statusLabel.setStyle("-fx-background-color: SLATEGRAY");
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

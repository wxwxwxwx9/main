package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.internship.InternshipApplication;

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
    private Label status;

    public InternshipApplicationCard(InternshipApplication IA, int displayedIndex) {
        super(FXML);
        this.internshipApplication = IA;
        id.setText(displayedIndex + ". ");
        company.setText(internshipApplication.getCompany().fullCompany);
        phone.setText(internshipApplication.getPhone().value);
        address.setText(internshipApplication.getAddress().value);
        email.setText(internshipApplication.getEmail().value);
        role.setText(internshipApplication.getRole().fullRole);
        priority.setText(Integer.toString(internshipApplication.getPriority().fullPriority));
        applicationDate.setText(internshipApplication.getApplicationDate().toString());
        status.setText(internshipApplication.getStatus().toString());
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
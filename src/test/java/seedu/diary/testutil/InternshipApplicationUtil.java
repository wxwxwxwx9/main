package seedu.diary.testutil;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import seedu.diary.logic.commands.AddCommand;
import seedu.diary.logic.commands.EditCommand;
import seedu.diary.model.internship.InternshipApplication;

/**
 * A utility class for Internship Application.
 */
public class InternshipApplicationUtil {

    /**
     * Returns an add command string for adding the {@code InternshipApplication}.
     */
    public static String getAddCommand(InternshipApplication internshipApplication) {
        return AddCommand.COMMAND_WORD + " " + getInternshipApplicationDetails(internshipApplication);
    }

    /**
     * Returns the part of command string for the given {@code InternshipApplication}'s details.
     */
    public static String getInternshipApplicationDetails(InternshipApplication internshipApplication) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM YYYY");
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_COMPANY + internshipApplication.getCompany().fullCompany + " ");
        sb.append(PREFIX_ROLE + internshipApplication.getRole().fullRole + " ");
        sb.append(PREFIX_ADDRESS + internshipApplication.getAddress().value + " ");
        sb.append(PREFIX_PHONE + internshipApplication.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + internshipApplication.getEmail().value + " ");
        sb.append(PREFIX_DATE + dateFormat
            .format(internshipApplication.getApplicationDate().fullApplicationDate) + " ");
        sb.append(PREFIX_PRIORITY + internshipApplication.getPriority().toString() + " ");
        sb.append(PREFIX_STATUS + internshipApplication.getStatus().name() + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditInternshipDescriptor}'s details.
     */
    public static String getEditInternshipApplicationDescriptorDetails(
        EditCommand.EditInternshipDescriptor descriptor) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MM YYYY");
        StringBuilder sb = new StringBuilder();
        descriptor.getCompany().ifPresent(company -> sb.append(PREFIX_COMPANY).append(company.fullCompany).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        descriptor.getRole().ifPresent(role -> sb.append(PREFIX_ROLE).append(role.fullRole).append(" "));
        descriptor.getDate().ifPresent(date -> sb.append(PREFIX_DATE)
            .append(dateFormat.format(date.fullApplicationDate)).append(" "));
        descriptor.getPriority()
            .ifPresent(priority -> sb.append(PREFIX_PRIORITY).append(priority.toString()).append(" "));
        descriptor.getStatus().ifPresent(status -> sb.append(PREFIX_STATUS).append(status.name()).append(" "));

        return sb.toString();
    }

    /**
     * Modifies an internship application to be archived.
     */
    public static InternshipApplication createArchivedInternshipApplication(
        InternshipApplication internshipApplication) {
        InternshipApplication archivedInternship = new InternshipApplication(
            internshipApplication.getCompany(),
            internshipApplication.getRole(),
            internshipApplication.getAddress(),
            internshipApplication.getPhone(),
            internshipApplication.getEmail(),
            internshipApplication.getApplicationDate(),
            internshipApplication.getPriority(),
            internshipApplication.getStatus(),
            true, new ArrayList<>()
        );
        assertTrue(archivedInternship.isArchived());
        return archivedInternship;
    }

    /**
     * Modifies an internship application to be unarchived.
     */
    public static InternshipApplication createUnarchivedInternshipApplication(
        InternshipApplication internshipApplication) {
        InternshipApplication archivedInternship = new InternshipApplication(
            internshipApplication.getCompany(),
            internshipApplication.getRole(),
            internshipApplication.getAddress(),
            internshipApplication.getPhone(),
            internshipApplication.getEmail(),
            internshipApplication.getApplicationDate(),
            internshipApplication.getPriority(),
            internshipApplication.getStatus(),
            false, new ArrayList<>()
        );
        assertTrue(archivedInternship.isArchived());
        return archivedInternship;
    }

}

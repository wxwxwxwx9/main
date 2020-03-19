package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.internship.InternshipApplication;

/**
 * A utility class for Internship Application.
 */
public class InternshipApplicationUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(InternshipApplication internshipApplication) {
        return AddCommand.COMMAND_WORD + " " + getInternshipApplicationDetails(internshipApplication);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
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
}

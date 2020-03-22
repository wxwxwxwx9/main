package seedu.address.logic.commands.interviewsubcommands;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.interview.Interview;

import java.util.Optional;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

public class InterviewEditCommand extends InterviewCommand {
    public static final String MESSAGE_USAGE = "Edits an Interview from an Internship Application "
            + "by using an index of the internship application, followed by an index of interview to be edited.\n"
            + "Parameters: INDEX(index of internship application) edit INDEX (index of interview to be edited) "
            + "[" + PREFIX_IS_ONLINE + "is it an online interview (true/false)] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_ADDRESS + "ADDRESS (optional if online interview] "
            + "Example: " + COMMAND_WORD + " 1 edit "
            + PREFIX_IS_ONLINE + "false "
            + PREFIX_ADDRESS + "123 road "
            + PREFIX_DATE + "01 02 2020 ";;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return null;
    }

    /**
     * Stores the details to edit the interview with. Each non-empty field value will replace the
     * corresponding field value of the interview.
     */
    public static class EditInterviewDescriptor {
        private boolean isOnline;
        private Address address;
        private ApplicationDate date;

        public EditInterviewDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditInterviewDescriptor(EditInterviewDescriptor toCopy) {
            setOnline(toCopy.isOnline);
            setAddress(toCopy.address);
            setDate(toCopy.date);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(address, date, isOnline);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setDate(ApplicationDate date) {
            this.date = date;
        }

        public Optional<ApplicationDate> getInterviewDate() {
            return Optional.ofNullable(date);
        }

        public void setOnline(boolean isOnline) {
            this.isOnline = isOnline;
        }

        public Optional<Boolean> getIsOnline() {
            return Optional.ofNullable(isOnline);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInterviewDescriptor)) {
                return false;
            }

            // state check
            EditInterviewDescriptor e = (EditInterviewDescriptor) other;

            return getAddress().equals(e.getAddress())
                    && getInterviewDate().equals(e.getInterviewDate())
                    && getIsOnline().equals(e.getIsOnline());
        }
    }
}

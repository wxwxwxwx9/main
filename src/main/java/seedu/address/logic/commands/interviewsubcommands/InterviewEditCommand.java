package seedu.address.logic.commands.interviewsubcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

/**
 * Edits the details of an existing interview in an Internship Application.
 */
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
            + PREFIX_DATE + "01 02 2020 ";
    public static final String MESSAGE_EDIT_INTERVIEW_SUCCESS = "Edited Interview: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INTERVIEW =
            "This interview already exists in the following internship application: %1$s.";

    private final Index internshipIndex;
    private final Index interviewIndex;
    private final EditInterviewDescriptor editInterviewDescriptor;

    /**
     * @param internshipIndex index of the internship application to modify the interviews in.
     * @param interviewIndex index of the interview under the internship application above.
     * @param editInterviewDescriptor details to edit the interview with.
     */
    public InterviewEditCommand(Index internshipIndex,
                                Index interviewIndex, EditInterviewDescriptor editInterviewDescriptor) {
        requireNonNull(internshipIndex);
        requireNonNull(interviewIndex);
        requireNonNull(editInterviewDescriptor);

        this.internshipIndex = internshipIndex;
        this.interviewIndex = interviewIndex;
        this.editInterviewDescriptor = editInterviewDescriptor;
    }
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        InternshipApplication internshipToEdit = super.getInternshipApplication(model, internshipIndex);

        List<Interview> lastShownList = internshipToEdit.getInterviews();

        if (interviewIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
        }

        Interview interviewToEdit = lastShownList.get(interviewIndex.getZeroBased());
        Interview editedInterview = createEditedInterview(interviewToEdit, editInterviewDescriptor);

        if (internshipToEdit.hasInterview(editedInterview)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_INTERVIEW, internshipToEdit));
        }

        lastShownList.set(interviewIndex.getZeroBased(), editedInterview);
        // todo: update display
        return new CommandResult(String.format(MESSAGE_EDIT_INTERVIEW_SUCCESS, editedInterview));
    }

    /**
     * Creates and returns a {@code InternshipApplication} with the details of {@code internshipToEdit}
     * edited with {@code editInternshipDescriptor}.
     */
    private static Interview createEditedInterview(Interview interviewToEdit,
                                                   EditInterviewDescriptor editInterviewDescriptor) {
        assert interviewToEdit != null;

        Address updatedAddress = editInterviewDescriptor.getAddress().orElse(interviewToEdit.getInterviewAddress());
        ApplicationDate updatedDate = editInterviewDescriptor.getInterviewDate()
                .orElse(interviewToEdit.getDate());
        boolean updatedIsOnline = editInterviewDescriptor.getIsOnline().orElse(interviewToEdit.isOnline);

        return new Interview(updatedIsOnline, updatedDate, updatedAddress);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewEditCommand)) {
            return false;
        }

        // state check
        InterviewEditCommand e = (InterviewEditCommand) other;
        return interviewIndex.equals(e.interviewIndex)
                && internshipIndex.equals(e.internshipIndex)
                && editInterviewDescriptor.equals(e.editInterviewDescriptor);
    }

    /**
     * Stores the details to edit the interview with. Each non-empty field value will replace the
     * corresponding field value of the interview.
     */
    public static class EditInterviewDescriptor {
        private Boolean isOnline;
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

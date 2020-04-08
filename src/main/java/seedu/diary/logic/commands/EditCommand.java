package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.List;
import java.util.Optional;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.index.Index;
import seedu.diary.commons.util.CollectionUtil;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.internship.interview.Interview;
import seedu.diary.model.status.Status;

/**
 * Edits the details of an existing internship application in the internship diary.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Edits the details of the internship application identified "
        + "by the index number used in the displayed internship application list. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_COMPANY + "COMPANY] "
        + "[" + PREFIX_ROLE + "ROLE] "
        + "[" + PREFIX_ADDRESS + "ADDRESS] "
        + "[" + PREFIX_PHONE + "PHONE] "
        + "[" + PREFIX_EMAIL + "EMAIL] "
        + "[" + PREFIX_DATE + "APPLICATION DATE] "
        + "[" + PREFIX_PRIORITY + "PRIORITY] "
        + "[" + PREFIX_STATUS + "STATUS] "
        + "\nExample: " + COMMAND_WORD + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_INTERNSHIP_SUCCESS = "Edited Internship Application: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_INTERNSHIP =
        "This internship application already exists in the internship diary.";

    private final Index index;
    private final EditInternshipDescriptor editInternshipDescriptor;

    /**
     * @param index of the internship application in the filtered internship application list to edit
     * @param editInternshipDescriptor details to edit the internship application with
     */
    public EditCommand(Index index, EditInternshipDescriptor editInternshipDescriptor) {
        requireNonNull(index);
        requireNonNull(editInternshipDescriptor);

        this.index = index;
        this.editInternshipDescriptor = new EditInternshipDescriptor(editInternshipDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToEdit = lastShownList.get(index.getZeroBased());
        InternshipApplication editedInternship = createEditedInternship(internshipToEdit, editInternshipDescriptor);
        if (editedInternship.getIsGhostedOrRejected() && (internshipToEdit.getStatus() != Status.GHOSTED)
            && (internshipToEdit.getStatus() != Status.REJECTED)) {
            editedInternship.setLastStage(internshipToEdit.getStatus());
        }
        if (!internshipToEdit.isSameInternshipApplication(editedInternship)
            && model.hasInternshipApplication(editedInternship)) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERNSHIP);
        }

        model.setInternshipApplication(internshipToEdit, editedInternship);
        model.displayInternshipDetail(editedInternship);
        return new CommandResult(String.format(MESSAGE_EDIT_INTERNSHIP_SUCCESS, editedInternship));
    }

    /**
     * Creates and returns a {@code InternshipApplication} with the details of {@code internshipToEdit}
     * edited with {@code editInternshipDescriptor}.
     */
    private static InternshipApplication createEditedInternship(InternshipApplication internshipToEdit,
        EditInternshipDescriptor editInternshipDescriptor) {
        assert internshipToEdit != null;

        Company updatedCompany = editInternshipDescriptor.getCompany().orElse(internshipToEdit.getCompany());
        Role updatedRole = editInternshipDescriptor.getRole().orElse(internshipToEdit.getRole());
        Address updatedAddress = editInternshipDescriptor.getAddress().orElse(internshipToEdit.getAddress());
        Phone updatedPhone = editInternshipDescriptor.getPhone().orElse(internshipToEdit.getPhone());
        Email updatedEmail = editInternshipDescriptor.getEmail().orElse(internshipToEdit.getEmail());
        ApplicationDate updatedDate = editInternshipDescriptor.getDate().orElse(internshipToEdit.getApplicationDate());
        Priority updatedPriority = editInternshipDescriptor.getPriority().orElse(internshipToEdit.getPriority());
        Optional<Status> toBeUpdatedStatus = editInternshipDescriptor.getStatus();
        Status updatedStatus = toBeUpdatedStatus.orElse(internshipToEdit.getStatus());
        Boolean isArchived = internshipToEdit.isArchived();
        List<Interview> interviews = internshipToEdit.getInterviews();

        InternshipApplication updatedInternshipApplication = new InternshipApplication(updatedCompany, updatedRole,
            updatedAddress, updatedPhone, updatedEmail, updatedDate, updatedPriority,
            updatedStatus, isArchived, interviews);
        if (toBeUpdatedStatus.isPresent()) {
            if (toBeUpdatedStatus.get() == Status.GHOSTED || toBeUpdatedStatus.get() == Status.REJECTED) {
                updatedInternshipApplication.setIsGhostedOrRejected(true);
            } else {
                updatedInternshipApplication.setIsGhostedOrRejected(false);
            }
        }
        if (internshipToEdit.getStatus() == Status.GHOSTED || internshipToEdit.getStatus() == Status.REJECTED) {
            updatedInternshipApplication = updatedInternshipApplication.setLastStage(internshipToEdit.getLastStage());
        } else {
            updatedInternshipApplication = updatedInternshipApplication.setLastStage(internshipToEdit.getStatus());
        }
        return updatedInternshipApplication;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
            && editInternshipDescriptor.equals(e.editInternshipDescriptor);
    }

    /**
     * Stores the details to edit the internship application with. Each non-empty field value will replace the
     * corresponding field value of the internship application.
     */
    public static class EditInternshipDescriptor {
        private Company company;
        private Role role;
        private Address address;
        private Phone phone;
        private Email email;
        private ApplicationDate date;
        private Priority priority;
        private Status status;
        private Boolean isGhostedOrRejected;
        private Status lastStage;

        public EditInternshipDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditInternshipDescriptor(EditInternshipDescriptor toCopy) {
            setCompany(toCopy.company);
            setRole(toCopy.role);
            setAddress(toCopy.address);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setDate(toCopy.date);
            setPriority(toCopy.priority);
            setStatus(toCopy.status);
            setIsGhostedOrRejected(toCopy.isGhostedOrRejected);
            setLastStage(toCopy.lastStage);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(
                company, role, address, phone, email, date, priority, status
            );
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setDate(ApplicationDate date) {
            this.date = date;
        }

        public Optional<ApplicationDate> getDate() {
            return Optional.ofNullable(date);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setLastStage(Status lastStage) {
            this.lastStage = lastStage;
        }

        public Optional<Status> getLastStage() {
            return Optional.ofNullable(lastStage);
        }

        public void setIsGhostedOrRejected(Boolean bool) {
            this.isGhostedOrRejected = bool;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditInternshipDescriptor)) {
                return false;
            }

            // state check
            EditInternshipDescriptor e = (EditInternshipDescriptor) other;

            return getCompany().equals(e.getCompany())
                && getRole().equals(e.getRole())
                && getAddress().equals(e.getAddress())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getDate().equals(e.getDate())
                && getPriority().equals(e.getPriority())
                && getStatus().equals(e.getStatus());
        }
    }
}

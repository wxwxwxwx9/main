package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Deletes an internship application identified using it's displayed index from the internship diary.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE_BY_INDICES = COMMAND_WORD
            + ": Deletes the internship application "
            + "identified by the index number used in the displayed internship list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example 1: " + COMMAND_WORD + " 1\n"
            + "Example 2: " + COMMAND_WORD + " 3, 1 ,5";

    public static final String MESSAGE_USAGE_BY_FIELD = "";

    public static final String MESSAGE_DELETE_INTERNSHIP_SUCCESS = "Deleted Internship Application: %1$s";
    public static final String MESSAGE_DELETE_INTERNSHIP_FAILURE = "Was not able to delete the internship: %1$s";

    private final Optional<Index> targetIndex;
    private final Optional<Set<Index>> targetIndices;
    private final Optional<Predicate<InternshipApplication>> targetPredicate;
    private final CommandExecutionType executionType;

    public DeleteCommand(Index targetIndex, CommandExecutionType executionType) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
    }

    public DeleteCommand(Set<Index> indices, CommandExecutionType executionType) {
        this.targetIndex = Optional.empty();
        this.targetIndices = Optional.of(indices);
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
    }

    public DeleteCommand(Predicate<InternshipApplication> predicate, CommandExecutionType executionType) {
        this.targetIndex = Optional.empty();
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.of(predicate);
        this.executionType = executionType;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        switch (executionType) {
        case BY_INDEX:
            return executeByIndex(model);
        case BY_INDICES:
            return executeByIndices(model);
        case BY_FIELD:
           return executeByField(model);
        default:
            // this should never happen
            assert false;
            return new CommandResult(String.format(MESSAGE_DELETE_INTERNSHIP_FAILURE));
        }
    }

    public CommandResult executeByIndex(Model model) throws CommandException {
        requireNonNull(model);

        Index index = targetIndex.get();
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        }

        InternshipApplication internshipToDelete = lastShownList.get(index.getZeroBased());
        model.deleteInternshipApplication(internshipToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipToDelete));
    }

    public CommandResult executeByIndices(Model model) throws CommandException {
        requireNonNull(model);

        Set<Index> indices = this.targetIndices.get();
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();

        // throw exception if any of the listed indices are out of range
        for (Index index : indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
            }
        }

        List<InternshipApplication> internshipsToDelete = new ArrayList<>();

        // retrieve all internships to be deleted
        for (Index index : indices) {
            InternshipApplication internshipToDelete = lastShownList.get(index.getZeroBased());
            internshipsToDelete.add(internshipToDelete);
        }

        // delete all retrieved internships
        for (InternshipApplication internshipToDelete : internshipsToDelete) {
            model.deleteInternshipApplication(internshipToDelete);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipsToDelete));
    }

    public CommandResult executeByField(Model model) {

        // filter appropriate internship applications into a new list
        List<InternshipApplication> copy = new ArrayList<>();
        for (InternshipApplication internshipApplication : model.getFilteredInternshipApplicationList()) {
            copy.add(internshipApplication);
        }
        List<InternshipApplication> internshipsToDelete =
                copy.stream().filter(targetPredicate.get()).collect(Collectors.toList());

        // delete all retrieved internships
        for (InternshipApplication internshipToDelete : internshipsToDelete) {
            model.deleteInternshipApplication(internshipToDelete);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_INTERNSHIP_SUCCESS, internshipsToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex) // state check
                && targetIndices.equals(((DeleteCommand) other).targetIndices) // state check
                && targetPredicate.equals(((DeleteCommand) other).targetPredicate)); // state check
    }
}

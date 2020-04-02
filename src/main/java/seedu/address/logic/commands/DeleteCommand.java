package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Deletes an internship application identified using its displayed indices or field from the internship diary.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE_BY_INDICES = COMMAND_WORD
        + ": Deletes the internship application "
        + "identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example 1: " + COMMAND_WORD + " 1\n"
        + "Example 2: " + COMMAND_WORD + " 3, 1 ,5";

    public static final String MESSAGE_USAGE_BY_FIELD = COMMAND_WORD
        + ": Deletes all internship applications whose fields contain all of "
        + "the specified field keywords (case-insensitive).\n"
        + "There must be only one specified field. \n"
        + "Parameters: "
        + "[KEYWORDS] "
        + "[" + PREFIX_COMPANY + "COMPANY] "
        + "[" + PREFIX_DATE + "COMPANY] "
        + "[" + PREFIX_ROLE + "ROLE] "
        + "[" + PREFIX_STATUS + "STATUS] "
        + "Example: " + COMMAND_WORD + " c/google";

    public static final String MESSAGE_DELETE_INTERNSHIP_SUCCESS = "Deleted Internship Application: %1$s";
    public static final String MESSAGE_DELETE_INTERNSHIP_FAILURE = "Was not able to delete the internship!";

    private final Optional<Index> targetIndex;
    private final Optional<List<Index>> targetIndices;
    private final Optional<Predicate<InternshipApplication>> targetPredicate;
    private final CommandExecutionType executionType;

    public DeleteCommand(Index targetIndex, CommandExecutionType executionType) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
    }

    public DeleteCommand(List<Index> indices, CommandExecutionType executionType) {
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

    /**
     * Executes the command by a single index.
     *
     * @param model model for execution of command.
     * @throws CommandException if the index is out of range.
     */
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

    /**
     * Executes the command by multiple indexes.
     * It places all the internship applications indicated by the indices in a new list
     * and then runs through the list to delete internship applications from the
     * underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     * @throws CommandException if the indices are out of range.
     */
    public CommandResult executeByIndices(Model model) throws CommandException {
        requireNonNull(model);

        List<Index> indices = this.targetIndices.get();
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

    /**
     * Executes the command by the field specified and its relevant input.
     * Makes a copy of the filtered list and then runs through that list to delete the internship applications
     * from the underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     */
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

package seedu.diary.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.diary.commons.core.Messages;
import seedu.diary.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType;
import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.exceptions.CommandException;
import seedu.diary.model.Model;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Represents a command that can execute based on the different command execution type available.
 * It acts as the connector between a removal-based command and the ability to execute on multiple items
 * or by a specific field.
 */
public class RemovalBasedCommand extends Command {

    public static final Function<String, String> MESSAGE_USAGE_BY_INDEX = (commandWord) -> commandWord.toUpperCase()
        + "S the internship application.\n"
        + "Identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + commandWord + " 1\n";

    public static final Function<String, String> MESSAGE_USAGE_BY_INDICES = (commandWord) -> commandWord.toUpperCase()
        + "S the internship application.\n"
        + "Identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX, [INDEX], [INDEX], ... (all index must be positive integer with comma as separator)\n"
        + "Note that INDEX enclosed in bracket is optional.\n"
        + "Example: " + commandWord + " 3, 1, 5";

    public static final Function<String, String> MESSAGE_USAGE_BY_FIELD = (commandWord) -> commandWord.toUpperCase()
        + "S all internship applications with the specified status.\n"
        + "Note that you can only execute based on Status field!\n"
        + "You may archive based on any number of valid status field (but there must be at least one valid status)\n"
        + "As long as there is one valid status, the command will execute.\n"
        + "Parameters: "
        + PREFIX_STATUS + "STATUS" + "\n"
        + "Example: " + commandWord + " s/rejected\n"
        + "Example: " + commandWord + " s/sss rejected";

    public static final Function<String, String> MESSAGE_COMMAND_INTERNSHIP_SUCCESS = (commandWord) ->
        commandWord.toUpperCase() + "D internship application(s) — \n\n%1$s";

    public static final String MESSAGE_COMMAND_UNEXPECTED_FAILURE = "Something went wrong!";

    private final Optional<Index> targetIndex;
    private final Optional<List<Index>> targetIndices;
    private final Optional<Predicate<InternshipApplication>> targetPredicate;
    private final RemovalBasedCommandExecutionType executionType;
    private final String commandWord;

    public RemovalBasedCommand(Index targetIndex, RemovalBasedCommandExecutionType executionType, String commandWord) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
        this.commandWord = commandWord;
    }

    public RemovalBasedCommand(List<Index> indices, RemovalBasedCommandExecutionType executionType,
        String commandWord) {
        this.targetIndex = Optional.empty();
        this.targetIndices = Optional.of(indices);
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
        this.commandWord = commandWord;
    }

    public RemovalBasedCommand(Predicate<InternshipApplication> predicate,
        RemovalBasedCommandExecutionType executionType,
        String commandWord) {
        this.targetIndex = Optional.empty();
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.of(predicate);
        this.executionType = executionType;
        this.commandWord = commandWord;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        Function<Index, Command> lazyCommand = generateLazyCommand();

        switch (executionType) {
        case BY_INDEX:
            return executeByIndex(model, lazyCommand);
        case BY_INDICES:
            return executeByIndices(model, lazyCommand);
        case BY_FIELD:
            return executeByField(model, lazyCommand);
        default:
            // this should never happen
            assert false;
            throw new IllegalStateException("unreachable");
        }

    }

    /**
     * Executes the command by a single index.
     *
     * @param model model for execution of command.
     * @param lazyCommand a function that creates the command.
     * @throws CommandException if the index is out of range.
     */
    private CommandResult executeByIndex(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        Index index = targetIndex.get();

        String commandFeedback = executeLazyCommandByIndex(model, lazyCommand, index) + "\n";
        String resultFeedback =
            String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    /**
     * Executes the command by multiple indexes.
     * It runs through the list of indices to execute on the internship application
     * retrieved from the underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     * @param lazyCommand a function that creates the command.
     * @throws CommandException if the indices are out of range.
     */
    private CommandResult executeByIndices(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        List<Index> indices = targetIndices.get();

        checkValidIndices(model, indices);

        String commandFeedback = executeLazyCommandByIndices(model, lazyCommand, indices);
        String resultFeedback = String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    /**
     * Executes the command by the field specified and its relevant input.
     * Converts the list of predicate-filtered internship applications into the corresponding indices
     * and runs through that list of indices to execute on the internship applications retrieved
     * from the underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     * @param lazyCommand a function that creates the command.
     * @throws CommandException if the indices are out of range.
     */
    private CommandResult executeByField(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        List<InternshipApplication> internshipApplicationsToExecuteOn =
            getPredicateFilteredInternshipApplications(model);

        List<Index> indices = convertInternshipApplicationsToIndices(model, internshipApplicationsToExecuteOn);

        checkValidIndices(model, indices);

        String commandFeedback = executeLazyCommandByIndices(model, lazyCommand, indices);
        String resultFeedback = String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    /**
     * Generates the appropriate lazy function to create the command based on the command word given by input.
     * The lazy command will be created and executed when this RemovalBasedCommand is executed.
     *
     * @return a function that takes in an index and creates a command.
     */
    private Function<Index, Command> generateLazyCommand() {
        switch (commandWord) {
        case DeleteCommand.COMMAND_WORD:
            return (Index index) -> new DeleteCommand(index);
        case ArchiveCommand.COMMAND_WORD:
            return (Index index) -> new ArchiveCommand(index);
        case UnarchiveCommand.COMMAND_WORD:
            return (Index index) -> new UnarchiveCommand(index);
        default:
            // this should never happen
            assert false;
            throw new IllegalStateException("unreachable");
        }
    }

    /**
     * Executes the lazy command by index.
     * Retrieves the feedback from the command for output to user.
     *
     * @param model model for execution of command.
     * @param lazyCommand a function that creates the command.
     * @param index to execute upon.
     * @return the command feedback for user.
     * @throws CommandException if the indices are out of range.
     */
    private String executeLazyCommandByIndex(Model model, Function<Index, Command> lazyCommand,
        Index index) throws CommandException {
        Command actualCommand = lazyCommand.apply(index);
        String commandFeedback = actualCommand.execute(model).getFeedbackToUser();
        return commandFeedback;
    }

    /**
     * Executes the lazy command by indices iteratively.
     * The function will also decrement the indices after the current index upon each execution of
     * a removal-based command because each execution will shift the remaining indices forward (decrement
     * index by 1).
     * Retrieves the feedback from the command for output to user.
     *
     * @param model model for execution of command.
     * @param lazyCommand a function that creates the command.
     * @param indices to execute upon.
     * @return the command feedback for user.
     * @throws CommandException if the indices are out of range.
     */
    private String executeLazyCommandByIndices(Model model, Function<Index, Command> lazyCommand,
        List<Index> indices) throws CommandException {
        String commandFeedback = "";
        for (int i = 0; i < indices.size(); i++) {
            for (int j = i + 1; j < indices.size(); j++) {
                // decrements the following indices because of the removal of the head index in the list
                Index newIndex = indices.get(j).getDecrementIndex();
                indices.set(j, newIndex);
            }
            commandFeedback += executeLazyCommandByIndex(model, lazyCommand, indices.get(i)) + "\n\n";
        }
        return commandFeedback;
    }

    /**
     * Retrieves the predicate-filtered internship applications.
     *
     * @param model model for execution of command.
     * @return the internship applications that was filtered by the predicate.
     */
    private List<InternshipApplication> getPredicateFilteredInternshipApplications(Model model) {
        // filter appropriate internship applications into a new list
        List<InternshipApplication> copy = new ArrayList<>();
        for (InternshipApplication internshipApplication : model.getFilteredInternshipApplicationList()) {
            copy.add(internshipApplication);
        }
        List<InternshipApplication> internshipsToExecuteOn = copy.stream()
            .filter(targetPredicate.get()).collect(Collectors.toList());
        return internshipsToExecuteOn;
    }

    /**
     * Converts the given internship applications to their corresponding indices in the
     * underlying internship applications list in the internship diary.
     * It will also sort the indices by order as users may not enter the indices sequentially.
     *
     * @param model model for execution of command.
     * @param predicateFilteredInternshipApplications internship applications to be converted to indices.
     * @return the appropriate indices.
     */
    private List<Index> convertInternshipApplicationsToIndices(Model model,
        List<InternshipApplication> predicateFilteredInternshipApplications) {
        List<Integer> integerIndices = new ArrayList<>();
        for (int i = 0; i < model.getFilteredInternshipApplicationList().size(); i++) {
            InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList().get(i);
            if (predicateFilteredInternshipApplications.contains(internshipApplication)) {
                integerIndices.add(i);
            }
        }
        List<Index> indices = integerIndices.stream()
            .sorted()
            .map(number -> Index.fromZeroBased(number))
            .collect(Collectors.toList());
        return indices;
    }

    /**
     * Verifies that all the given indices by user are valid and not out of bounds according to the
     * underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     * @param indices to check for validity.
     * @throws CommandException if the indices are out of range, with the specified indices that are out of range.
     */
    private void checkValidIndices(Model model, List<Index> indices) throws CommandException {
        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();
        List<Integer> invalidIndices = indices.stream()
            .filter(index -> index.getZeroBased() >= lastShownList.size())
            .map(index -> index.getOneBased())
            .collect(Collectors.toList());
        boolean hasInvalidIndices = !invalidIndices.isEmpty();
        if (hasInvalidIndices) {
            String exceptionMessage = Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX + ": " + invalidIndices;
            throw new CommandException(exceptionMessage);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RemovalBasedCommand // instanceof handles nulls
            && targetIndex.equals(((RemovalBasedCommand) other).targetIndex) // state check
            && targetIndices.equals(((RemovalBasedCommand) other).targetIndices) // state check
            && targetPredicate.equals(((RemovalBasedCommand) other).targetPredicate) // state check
            && executionType.equals(((RemovalBasedCommand) other).executionType) // state check
            && commandWord.equals(((RemovalBasedCommand) other).commandWord)); // state check
    }

}

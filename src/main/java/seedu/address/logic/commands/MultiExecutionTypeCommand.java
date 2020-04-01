package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.internship.InternshipApplication;

/**
 * Represents a command that can execute based on the different command execution type available.
 */
public class MultiExecutionTypeCommand extends Command {

    public static final Function<String, String> MESSAGE_USAGE_BY_INDEX =
        (commandWord) -> commandWord
        + "s the internship application.\n"
        + "Identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDEX (must be a positive integer)\n"
        + "Example: " + commandWord + " 1\n";

    public static final Function<String, String> MESSAGE_USAGE_BY_INDICES =
        (commandWord) -> commandWord
        + "s the internship application "
        + "identified by the index number used in the displayed internship list.\n"
        + "Parameters: INDICES (must have more than 1 positive integer with comma as separator)\n"
        + "Example: " + commandWord + " 3, 1 ,5";

    public static final Function<String, String> MESSAGE_USAGE_BY_FIELD =
        (commandWord) -> commandWord
        + "s all internship applications whose fields contain all of "
        + "the specified field keywords (case-insensitive).\n"
        + "There must be only one specified field. \n"
        + "Parameters: "
        + "[KEYWORDS] "
        + "[" + PREFIX_COMPANY + "COMPANY] "
        + "[" + PREFIX_DATE + "COMPANY] "
        + "[" + PREFIX_ROLE + "ROLE] "
        + "[" + PREFIX_STATUS + "STATUS] "
        + "Example: " + commandWord + " c/google";

    public static final String MESSAGE_COMMAND_UNEXPECTED_FAILURE = "Something went wrong!";

    public static final Function<String, String> MESSAGE_COMMAND_INTERNSHIP_SUCCESS =
        (commandWord) -> commandWord + " success: %1$s";
    public static final Function<String, String> MESSAGE_COMMAND_INTERNSHIP_FAILURE =
        (commandWord) -> commandWord + " fail: %1$s";

    private final Optional<Index> targetIndex;
    private final Optional<List<Index>> targetIndices;
    private final Optional<Predicate<InternshipApplication>> targetPredicate;
    private final CommandExecutionType executionType;
    private final String commandWord;

    public MultiExecutionTypeCommand(Index targetIndex, CommandExecutionType executionType, String commandWord) {
        this.targetIndex = Optional.of(targetIndex);
        this.targetIndices = Optional.empty();
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
        this.commandWord = commandWord;
    }

    public MultiExecutionTypeCommand(List<Index> indices, CommandExecutionType executionType, String commandWord) {
        this.targetIndex = Optional.empty();
        this.targetIndices = Optional.of(indices);
        this.targetPredicate = Optional.empty();
        this.executionType = executionType;
        this.commandWord = commandWord;
    }

    public MultiExecutionTypeCommand(Predicate<InternshipApplication> predicate, CommandExecutionType executionType,
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
            return new CommandResult(MESSAGE_COMMAND_UNEXPECTED_FAILURE);
        }

    }

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
            return (Index index) -> new DeleteCommand(index);
        }
    }

    private String executeLazyCommandByIndex(Model model, Function<Index, Command> lazyCommand,
        Index index) throws CommandException {
        Command actualCommand = lazyCommand.apply(index);
        String commandFeedback = actualCommand.execute(model).getFeedbackToUser();
        return commandFeedback;
    }

    private String executeLazyCommandByIndices(Model model, Function<Index, Command> lazyCommand,
        List<Index> indices) throws CommandException {
        String commandFeedback = "";
        for (int i = 0; i < indices.size(); i++) {
            for (int j = i + 1; j < indices.size(); j++) {
                // decrements the following indices because of the deletion of the head index in the list
                Index newIndex = indices.get(j).getDecrementIndex();
                indices.set(j, newIndex);
            }
            commandFeedback += executeLazyCommandByIndex(model, lazyCommand, indices.get(i)) + "\n";
        }
        return commandFeedback;
    }

    /**
     * Executes the command by a single index.
     *
     * @param model model for execution of command.
     * @throws seedu.address.logic.commands.exceptions.CommandException if the index is out of range.
     */
    public CommandResult executeByIndex(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        String commandFeedback = executeLazyCommandByIndex(model, lazyCommand, targetIndex.get()) + "\n";

        String resultFeedback =
            String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    /**
     * Executes the command by multiple indexes.
     * It places all the internship applications indicated by the indices in a new list
     * and then runs through the list to delete internship applications from the
     * underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     * @throws seedu.address.logic.commands.exceptions.CommandException if the indices are out of range.
     */
    public CommandResult executeByIndices(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        List<Index> indices = this.targetIndices.get();

        System.out.println(indices);

        List<InternshipApplication> lastShownList = model.getFilteredInternshipApplicationList();
        // // throw exception if any of the listed indices are out of range
        // for (Index index : indices) {
        //     if (index.getZeroBased() >= lastShownList.size()) {
        //         throw new CommandException(Messages.MESSAGE_INVALID_INTERNSHIP_DISPLAYED_INDEX);
        //     }
        // }

        // List<InternshipApplication> internshipApplicationsToDelete = indices.stream()
        //     .map(index -> lastShownList.get(index.getZeroBased()))
        //     .collect(Collectors.toList());

        String commandFeedback = executeLazyCommandByIndices(model, lazyCommand, indices);

        String resultFeedback = String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    /**
     * Executes the command by the field specified and its relevant input.
     * Makes a copy of the filtered list and then runs through that list to delete the internship applications
     * from the underlying internship applications list in the internship diary.
     *
     * @param model model for execution of command.
     */
    public CommandResult executeByField(Model model, Function<Index, Command> lazyCommand) throws CommandException {
        requireNonNull(model);

        // filter appropriate internship applications into a new list
        List<InternshipApplication> copy = new ArrayList<>();
        for (InternshipApplication internshipApplication : model.getFilteredInternshipApplicationList()) {
            copy.add(internshipApplication);
        }

        List<InternshipApplication> internshipsToDelete = copy.stream()
            .filter(targetPredicate.get()).collect(Collectors.toList());

        List<Index> indices = convertInternshipApplicationsToIndices(model, internshipsToDelete);

        String commandFeedback = executeLazyCommandByIndices(model, lazyCommand, indices);

        String resultFeedback = String.format(MESSAGE_COMMAND_INTERNSHIP_SUCCESS.apply(commandWord), commandFeedback);

        return new CommandResult(resultFeedback);
    }

    private List<Index> convertInternshipApplicationsToIndices(Model model,
        List<InternshipApplication> predicateFilteredInternshipApplications) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < model.getFilteredInternshipApplicationList().size(); i++) {
            InternshipApplication internshipApplication = model.getFilteredInternshipApplicationList().get(i);
            if (predicateFilteredInternshipApplications.contains(internshipApplication)) {
                indices.add(i);
            }
        }
        indices.sort(null);
        return indices.stream().map(number -> Index.fromZeroBased(number)).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof MultiExecutionTypeCommand // instanceof handles nulls
            && targetIndex.equals(((MultiExecutionTypeCommand) other).targetIndex) // state check
            && targetIndices.equals(((MultiExecutionTypeCommand) other).targetIndices) // state check
            && targetPredicate.equals(((MultiExecutionTypeCommand) other).targetPredicate)); // state check
    }
}

package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.diary.logic.commands.AddCommand;
import seedu.diary.logic.commands.ArchivalCommand;
import seedu.diary.logic.commands.ArchiveCommand;
import seedu.diary.logic.commands.Command;
import seedu.diary.logic.commands.DeleteCommand;
import seedu.diary.logic.commands.EditCommand;
import seedu.diary.logic.commands.ExitCommand;
import seedu.diary.logic.commands.FindCommand;
import seedu.diary.logic.commands.HelpCommand;
import seedu.diary.logic.commands.InitClearCommand;
import seedu.diary.logic.commands.InterviewCommand;
import seedu.diary.logic.commands.ListCommand;
import seedu.diary.logic.commands.ReminderCommand;
import seedu.diary.logic.commands.SelectCommand;
import seedu.diary.logic.commands.SortCommand;
import seedu.diary.logic.commands.StatisticsCommand;
import seedu.diary.logic.commands.UnarchiveCommand;
import seedu.diary.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class InternshipDiaryParser {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase();
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new RemovalBasedCommandExecutionTypeParser(DeleteCommand.COMMAND_WORD).parse(arguments);

        case InitClearCommand.COMMAND_WORD:
            return new InitClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case SortCommand.COMMAND_WORD:
            return new SortCommandParser().parse(arguments);

        case InterviewCommand.COMMAND_WORD:
            return new InterviewCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case StatisticsCommand.COMMAND_WORD:
            return new StatisticsCommand();

        case ArchiveCommand.COMMAND_WORD:
            return new RemovalBasedCommandExecutionTypeParser(ArchiveCommand.COMMAND_WORD).parse(arguments);

        case UnarchiveCommand.COMMAND_WORD:
            return new RemovalBasedCommandExecutionTypeParser(UnarchiveCommand.COMMAND_WORD).parse(arguments);

        case ArchivalCommand.COMMAND_WORD:
            return new ArchivalCommand();

        case ReminderCommand.COMMAND_WORD:
            return new ReminderCommand();

        case SelectCommand.COMMAND_WORD:
            return new SelectCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}

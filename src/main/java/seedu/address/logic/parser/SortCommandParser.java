package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Comparator;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparator.DateComparator;
import seedu.address.logic.comparator.PriorityComparator;
import seedu.address.logic.comparator.StatusComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.comparator.CompanyComparator;
import seedu.address.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return new SortCommand(prefixToComparator(trimmedArgs));
    }

    private Comparator<InternshipApplication> prefixToComparator(String prefix) throws ParseException {
        if (prefix.equals(PREFIX_COMPANY.getPrefix())) {
            return new CompanyComparator();
        } else if (prefix.equals(PREFIX_DATE.getPrefix())) {
            return new DateComparator();
        } else if (prefix.equals(PREFIX_PRIORITY.getPrefix())) {
            return new PriorityComparator();
        } else if (prefix.equals(PREFIX_STATUS.getPrefix())) {
            return new StatusComparator();
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

}

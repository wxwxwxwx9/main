package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Comparator;
import java.util.Map;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparator.ApplicationDateComparator;
import seedu.address.logic.comparator.CompanyComparator;
import seedu.address.logic.comparator.PriorityComparator;
import seedu.address.logic.comparator.StatusComparator;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Map<Prefix, Comparator<InternshipApplication>> comparatorMap;
    private static final Prefix[] acceptedPrefixes;

    static {
        comparatorMap = Map.of(
                PREFIX_COMPANY, new CompanyComparator(),
                PREFIX_DATE, new ApplicationDateComparator(),
                PREFIX_PRIORITY, new PriorityComparator(),
                PREFIX_STATUS, new StatusComparator()
        );
        acceptedPrefixes = comparatorMap.keySet().toArray(new Prefix[0]);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortCommand parse(String args) throws ParseException {
        Prefix[] prefixes = acceptedPrefixes;
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        Comparator<InternshipApplication> comparator = argMultimapToComparator(argMultimap);
        if (argMultimap.getPreamble().equals(SortCommand.REVERSE_KEYWORD)) {
            comparator = comparator.reversed();
        }
        return new SortCommand(comparator);
    }

    /**
     * Returns the comparator corresponding to the argMultimap given.
     *
     * @throws ParseException if the argMultimap does not contain exactly one accepted prefix.
     */
    private Comparator<InternshipApplication> argMultimapToComparator(ArgumentMultimap argMultimap)
            throws ParseException {
        Comparator<InternshipApplication> comparator = null;
        for (Prefix prefix : acceptedPrefixes) {
            if (argMultimap.getValue(prefix).isPresent()) {
                if (comparator != null) {
                    comparator = null;
                    break;
                }
                comparator = comparatorMap.get(prefix);
            }
        }
        if (comparator == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return comparator;
    }

}

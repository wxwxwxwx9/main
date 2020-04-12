package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.diary.logic.commands.SortCommand;
import seedu.diary.logic.comparator.ApplicationDateComparator;
import seedu.diary.logic.comparator.CompanyComparator;
import seedu.diary.logic.comparator.PriorityComparator;
import seedu.diary.logic.comparator.RoleComparator;
import seedu.diary.logic.comparator.StatusComparator;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {
    private static final Map<Prefix, Comparator<InternshipApplication>> comparatorMap;
    private static final Prefix[] acceptedPrefixes;

    static {
        comparatorMap = Map.of(
            PREFIX_COMPANY, new CompanyComparator(),
            PREFIX_ROLE, new RoleComparator(),
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        Comparator<InternshipApplication> comparator = argMultimapToComparator(argMultimap);
        if (argMultimap.getPreamble().equals(SortCommand.REVERSE_KEYWORD)) {
            comparator = comparator.reversed();
        } else if (!argMultimap.getPreamble().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
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
        List<Prefix> prefixFound = Arrays.stream(acceptedPrefixes)
            .filter(p -> argMultimap.getValue(p).isPresent())
            .collect(Collectors.toList());

        if (prefixFound.size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        Prefix prefix = prefixFound.get(0);
        if (argMultimap.getAllValues(prefix).size() != 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        // isPresent check already done above.
        if (!argMultimap.getValue(prefix).get().equals("")) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
        return comparatorMap.get(prefix);
    }

}

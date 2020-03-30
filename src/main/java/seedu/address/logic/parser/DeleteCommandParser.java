package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.PrefixUtil.areAnyPrefixesPresent;

import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String INDICES_DELIMITER = ",";
    private static final Prefix[] acceptedPrefixes = { PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS };
    private static final Map<Prefix, PredicateFunction> predicateMap = Map.of(
            PREFIX_COMPANY, CompanyContainsKeywordsPredicate::new,
            PREFIX_ROLE, RoleContainsKeywordsPredicate::new,
            PREFIX_STATUS, StatusContainsKeywordsPredicate::new
    );

    @FunctionalInterface
    private interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        CommandExecutionType executionType = areAnyPrefixesPresent(argMultimap, acceptedPrefixes)
                ? CommandExecutionType.BY_FIELD
                : args.contains(INDICES_DELIMITER)
                    ? CommandExecutionType.BY_INDICES
                    : CommandExecutionType.BY_INDEX;

        switch (executionType) {
        case BY_INDEX:
            return deleteByIndex(args);
        case BY_INDICES:
            return deleteByIndices(args);
        case BY_FIELD:
            return deleteByField(argMultimap);
        default:
            // this should never happen
            assert false;
            return deleteByIndex(args);
        }

    }

    public DeleteCommand deleteByIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index, CommandExecutionType.BY_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES), pe);
        }
    }

    public DeleteCommand deleteByIndices(String args) throws ParseException {
        try {
            Set<Index> indicesList = ParserUtil.parseIndices(args, INDICES_DELIMITER);
            return new DeleteCommand(indicesList, CommandExecutionType.BY_INDICES);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES), pe);
        }
    }

    public DeleteCommand deleteByField(ArgumentMultimap argMultimap) throws ParseException {
        boolean onlyOneField = (argMultimap.getSize() - 1) == 1;
        if (!onlyOneField) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteCommand.MESSAGE_USAGE_BY_FIELD));
        }
        Predicate<InternshipApplication> predicate = getFieldPredicate(argMultimap);

        return new DeleteCommand(predicate, CommandExecutionType.BY_FIELD);
    }

    public Predicate<InternshipApplication> getFieldPredicate(ArgumentMultimap argMultimap) throws ParseException {
        Predicate<InternshipApplication> predicate = null;
        for (Prefix prefix : acceptedPrefixes) {
            if (argMultimap.getValue(prefix).isEmpty()) {
                continue;
            } else {
                String[] keywords = argMultimap.getValue(prefix).get().split("\\s+");
                predicate = predicateMap.get(prefix).apply(Arrays.asList(keywords));
                break;
            }
        }
        return predicate;
    }

}

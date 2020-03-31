package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.PrefixUtil.areAnyPrefixesPresent;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String INDICES_DELIMITER = ",";

    /**
     * To execute a predicate lazily.
     */
    @FunctionalInterface
    private interface PredicateFunction {
        Predicate<InternshipApplication> apply(List<String> t) throws ParseException;
    }

    /**
     * Prefixes that are accepted as executed as fields in DeleteCommand.
     */
    private static final Prefix[] acceptedPrefixes = { PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS };

    private static final Prefix[] allPrefixes = {
        PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS, PREFIX_ADDRESS, PREFIX_DATE, PREFIX_EMAIL,
        PREFIX_PHONE, PREFIX_PRIORITY, PREFIX_IS_ONLINE
    };

    /**
     * Prefixes and their mapping to its appropriate predicates.
     */
    private static final Map<Prefix, PredicateFunction> predicateMap = Map.of(
        PREFIX_COMPANY, CompanyContainsKeywordsPredicate::new,
        PREFIX_ROLE, RoleContainsKeywordsPredicate::new,
        PREFIX_STATUS, StatusContainsKeywordsPredicate::new
    );

    /**
     * Tokenizes the given {@code String} of arguments in the context of the DeleteCommand
     * and retrieves the appropriate execution type based on the content of the string.
     *
     * @return the appropriate DeleteCommand based the the execution type.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, allPrefixes);
        CommandExecutionType executionType = getExecutionType(args, argMultimap);

        switch (executionType) {
        case BY_INDEX:
            return deleteByIndex(args);
        case BY_INDICES:
            return deleteByIndices(args);
        case BY_FIELD:
            return deleteByField(args, argMultimap);
        default:
            // this should never happen
            assert false;
            return deleteByIndex(args);
        }

    }

    /**
     * Retrieves the appropriate command execution type based on the argument.
     *
     * @return the appropriate command execution type.
     */
    public CommandExecutionType getExecutionType(String args, ArgumentMultimap argMultimap) {
        return areAnyPrefixesPresent(argMultimap, allPrefixes)
            ? CommandExecutionType.BY_FIELD
            : args.contains(INDICES_DELIMITER)
                ? CommandExecutionType.BY_INDICES
                : CommandExecutionType.BY_INDEX;
    }

    /**
     * Parses the given {@code String} of arguments in the context of a DeleteCommand
     * that is to be executed by index and returns a DeleteCommand object for execution.
     *
     * @param args the argument to be parsed into an Index object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand deleteByIndex(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index, CommandExecutionType.BY_INDEX);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * that is to be executed by multiple indexes and returns a DeleteCommand object for execution.
     *
     * @param args the argument to be parsed into an a set of Index object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand deleteByIndices(String args) throws ParseException {
        try {
            List<Index> indicesList = ParserUtil.parseIndices(args, INDICES_DELIMITER);
            return new DeleteCommand(indicesList, CommandExecutionType.BY_INDICES);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE_BY_INDICES), pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * that is to be executed by accepted fields/prefixes and returns a DeleteCommand object for execution.
     *
     * @param argMultimap the argument multimap to retrieve the appropriate field.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand deleteByField(String args, ArgumentMultimap argMultimap) throws ParseException {
        argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        boolean hasOnlyOneField = (argMultimap.getSize() - 1) == 1;
        boolean hasAcceptedPrefixesPresent = areAnyPrefixesPresent(argMultimap, acceptedPrefixes);
        if (!hasOnlyOneField || !hasAcceptedPrefixesPresent) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE_BY_FIELD));
        }
        Predicate<InternshipApplication> predicate = getFieldPredicate(argMultimap);

        return new DeleteCommand(predicate, CommandExecutionType.BY_FIELD);
    }

    /**
     * Retrieves the value of the prefix from argument multimap
     * and packages it into a predicate for internship application.
     *
     * @param argMultimap argument multimap to extract the prefix for predicate creation.
     * @returns predicate to filter internship application list.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public Predicate<InternshipApplication> getFieldPredicate(ArgumentMultimap argMultimap) throws ParseException {
        Predicate<InternshipApplication> predicate = null;
        for (Prefix prefix : acceptedPrefixes) {
            if (argMultimap.getValue(prefix).isPresent()) {
                String input = argMultimap.getValue(prefix).get();
                String[] keywords = input.split("\\s+");
                predicate = predicateMap.get(prefix).apply(Arrays.asList(keywords));
                break;
            }
        }
        return predicate;
    }

}

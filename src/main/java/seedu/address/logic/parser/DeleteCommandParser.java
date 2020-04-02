package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.util.PrefixPredicateUtil.getFieldPredicate;
import static seedu.address.logic.util.PrefixUtil.areAnyPrefixesPresent;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.commandexecutiontype.CommandExecutionType;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private static final String INDICES_DELIMITER = ",";

    /** Prefixes that are accepted for execution as fields in DeleteCommand. */
    private static final Prefix[] acceptedPrefixes = { PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS };

    /**
     * Tokenizes the given {@code String} of arguments in the context of the DeleteCommand
     * and retrieves the appropriate execution type based on the content of the string.
     *
     * @return the appropriate DeleteCommand based the the execution type.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        CommandExecutionType executionType = getExecutionType(args, argMultimap);

        switch (executionType) {
        case BY_INDEX:
            return deleteByIndex(args);
        case BY_INDICES:
            return deleteByIndices(args);
        case BY_FIELD:
            return deleteByField(args);
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
        boolean hasPrefixes = areAnyPrefixesPresent(argMultimap, ALL_PREFIXES);
        boolean hasDelimiter = args.contains(INDICES_DELIMITER);
        return hasPrefixes
            ? CommandExecutionType.BY_FIELD
            : hasDelimiter
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
     * @throws ParseException if the user input does not conform to the expected format.
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
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public DeleteCommand deleteByField(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        boolean hasOnlyOneField = (argMultimap.getSize() - 1) == 1;
        boolean hasAcceptedPrefixesPresent = areAnyPrefixesPresent(argMultimap, acceptedPrefixes);
        boolean isValidField = hasOnlyOneField & hasAcceptedPrefixesPresent;
        if (!isValidField) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCommand.MESSAGE_USAGE_BY_FIELD));
        }
        Predicate<InternshipApplication> predicate = getFieldPredicate(argMultimap, acceptedPrefixes);

        return new DeleteCommand(predicate, CommandExecutionType.BY_FIELD);
    }

}

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
import seedu.address.logic.commands.MultiExecutionTypeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class MultiExecutionTypeCommandParser {

    /** Prefixes that are accepted for execution as fields in DeleteCommand. */
    private static final Prefix[] acceptedPrefixes = { PREFIX_COMPANY, PREFIX_ROLE, PREFIX_STATUS };

    /**
     * Tokenizes the given {@code String} of arguments in the context of the DeleteCommand
     * and retrieves the appropriate execution type based on the content of the string.
     *
     * @return the appropriate DeleteCommand based the the execution type.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MultiExecutionTypeCommand parse(String args, String commandWord) throws ParseException {

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        CommandExecutionType executionType = CommandExecutionType.getExecutionType(args, argMultimap);

        switch (executionType) {
        case BY_INDEX:
            return commandByIndex(args, commandWord);
        case BY_INDICES:
            return commandByIndices(args, commandWord);
        case BY_FIELD:
            return commandByField(args, commandWord);
        default:
            // this should never happen
            assert false;
            return commandByIndex(args, commandWord);
        }

    }

    /**
     * Parses the given {@code String} of arguments in the context of a DeleteCommand
     * that is to be executed by index and returns a DeleteCommand object for execution.
     *
     * @param args the argument to be parsed into an Index object.
     * @throws ParseException if the user input does not conform the expected format.
     */
    public MultiExecutionTypeCommand commandByIndex(String args, String commandWord) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new MultiExecutionTypeCommand(index, CommandExecutionType.BY_INDEX, commandWord);
        } catch (ParseException pe) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MultiExecutionTypeCommand.MESSAGE_USAGE_BY_INDEX.apply(commandWord));
            throw new ParseException(exceptionMessage, pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * that is to be executed by multiple indexes and returns a DeleteCommand object for execution.
     *
     * @param args the argument to be parsed into an a set of Index object.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public MultiExecutionTypeCommand commandByIndices(String args, String commandWord) throws ParseException {
        try {
            List<Index> indicesList = ParserUtil.parseIndices(args, CommandExecutionType.INDICES_DELIMITER);
            return new MultiExecutionTypeCommand(indicesList, CommandExecutionType.BY_INDICES, commandWord);
        } catch (ParseException pe) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MultiExecutionTypeCommand.MESSAGE_USAGE_BY_INDICES.apply(commandWord));
            throw new ParseException(exceptionMessage, pe);
        }
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * that is to be executed by accepted fields/prefixes and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public MultiExecutionTypeCommand commandByField(String args, String commandWord) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, acceptedPrefixes);
        boolean hasOnlyOneField = (argMultimap.getSize() - 1) == 1;
        boolean hasAcceptedPrefixesPresent = areAnyPrefixesPresent(argMultimap, acceptedPrefixes);
        boolean isValidField = hasOnlyOneField & hasAcceptedPrefixesPresent;
        if (!isValidField) {
            String exceptionMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MultiExecutionTypeCommand.MESSAGE_USAGE_BY_FIELD.apply(commandWord));
            throw new ParseException(exceptionMessage);
        }
        Predicate<InternshipApplication> predicate = getFieldPredicate(argMultimap, acceptedPrefixes);

        return new MultiExecutionTypeCommand(predicate, CommandExecutionType.BY_FIELD, commandWord);
    }

}

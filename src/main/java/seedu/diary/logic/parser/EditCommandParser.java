package seedu.diary.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.diary.commons.core.index.Index;
import seedu.diary.logic.commands.EditCommand;
import seedu.diary.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditCommand.EditInternshipDescriptor editInternshipDescriptor = new EditCommand.EditInternshipDescriptor();
        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            editInternshipDescriptor.setCompany(ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get()));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            editInternshipDescriptor.setRole(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editInternshipDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editInternshipDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editInternshipDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editInternshipDescriptor.setDate(ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            editInternshipDescriptor.setPriority(ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editInternshipDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }

        if (!editInternshipDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editInternshipDescriptor);
    }

}

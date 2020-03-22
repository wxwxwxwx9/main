package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.interviewcode.InterviewCode;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.interviewsubcommands.InterviewAddCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.ApplicationDate;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;

public class InterviewCommandParser implements Parser<InterviewCommand> {


    @Override
    public InterviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ADDRESS, PREFIX_IS_ONLINE);

        Index index;
        InterviewCode interviewCode;
        String[] indexAndCode;
        try {
            indexAndCode = ParserUtil.parseInterviewPreamble(argumentMultimap.getPreamble());
            index = ParserUtil.parseIndex(indexAndCode[0]);
            interviewCode = ParserUtil.parseInterviewCode(indexAndCode[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE), pe);
        }

        switch (interviewCode) {
        case ADD:
            return parseAdd(index, argumentMultimap);
        case DELETE:
            return parseDelete(index, indexAndCode[2]);
        case EDIT:
            return parseEdit(index, indexAndCode[3], argumentMultimap);
        case LIST:
            return new InterviewListCommand(index);
        default:
            throw new ParseException("invalid");
        }
    }

    private InterviewCommand parseAdd(Index index, ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_IS_ONLINE, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
        }

        boolean isOnline = Boolean.parseBoolean(argMultimap.getValue(PREFIX_IS_ONLINE).get());
        ApplicationDate date = ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get());
        Interview interview = new Interview(isOnline, date);
        // if not an online interview but address prefix is missing.
        if (!isOnline && !arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE));
        }
        if (!isOnline) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            interview = new Interview(false, date, address);
        }

        return new InterviewAddCommand(index, interview);
    }

    private InterviewCommand parseDelete(Index internshipIndex, String interviewIndex) {

        return null;
    }

    private InterviewCommand parseEdit(Index internshipIndex,
                                       String interviewIndex, ArgumentMultimap argMultimap) {

        return null;
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

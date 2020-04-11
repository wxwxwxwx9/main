package seedu.diary.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.diary.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

import java.util.stream.Stream;

import seedu.diary.commons.core.index.Index;
import seedu.diary.commons.core.interviewcode.InterviewCode;
import seedu.diary.commons.util.BooleanUtil;
import seedu.diary.logic.commands.InterviewCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewAddCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewDeleteCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.diary.logic.commands.interviewsubcommands.InterviewListCommand;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.interview.Interview;

/**
 * Parses input arguments and creates a new Interview Command object.
 */
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
            if (indexAndCode.length != 3) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE));
            }
            return parseDelete(index, indexAndCode[2]);
        case EDIT:
            if (indexAndCode.length != 3) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewEditCommand.MESSAGE_USAGE));
            }
            return parseEdit(index, indexAndCode[2], argumentMultimap);
        case LIST:
            return new InterviewListCommand(index);
        default:
            throw new ParseException("invalid");
        }
    }

    /**
     * Parses the add version of interview command. Returns an InterviewAddCommand object.
     */
    private InterviewCommand parseAdd(Index index, ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_IS_ONLINE, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewAddCommand.MESSAGE_USAGE));
        }

        boolean isOnline = Boolean.parseBoolean(argMultimap.getValue(PREFIX_IS_ONLINE).get());
        // check if parseBoolean mistakenly parsed an invalid value as false
        if (!isOnline && !argMultimap.getValue(PREFIX_IS_ONLINE).get().equalsIgnoreCase("FALSE")) {
            throw new ParseException(BooleanUtil.INVALID_BOOLEAN);
        }

        ApplicationDate date = ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get());
        Interview interview = Interview.createOnlineInterview(date);
        // if not an online interview but address prefix is missing.
        if (!isOnline && !arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                InterviewAddCommand.MESSAGE_OFFLINE_INTERVIEW_ADDRESS));
        }
        if (!isOnline) {
            Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
            interview = Interview.createInterview(false, date, address);
        }

        return new InterviewAddCommand(index, interview);
    }

    /**
     * Parses the delete version of interview command. Returns an InterviewDeleteCommand object.
     */
    private InterviewCommand parseDelete(Index internshipIndex, String interviewIndex) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(interviewIndex);
            return new InterviewDeleteCommand(internshipIndex, index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewDeleteCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Parses the edit version of interview command. Returns an InterviewEditCommand object.
     */
    private InterviewCommand parseEdit(Index internshipIndex,
        String interviewIndex, ArgumentMultimap argMultimap) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(interviewIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewEditCommand.MESSAGE_USAGE), pe);
        }
        InterviewEditCommand.EditInterviewDescriptor editInterviewDescriptor =
            new InterviewEditCommand.EditInterviewDescriptor();
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editInterviewDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editInterviewDescriptor.setDate(ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_IS_ONLINE).isPresent()) {
            if (!BooleanUtil.isValidBoolean(argMultimap.getValue(PREFIX_IS_ONLINE).get())) {
                throw new ParseException(BooleanUtil.INVALID_BOOLEAN);
            }
            editInterviewDescriptor.setOnline(Boolean.parseBoolean(argMultimap.getValue(PREFIX_IS_ONLINE).get()));
        }

        if (!editInterviewDescriptor.isAnyFieldEdited()) {
            throw new ParseException(InterviewEditCommand.MESSAGE_NOT_EDITED);
        }

        return new InterviewEditCommand(internshipIndex, index, editInterviewDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

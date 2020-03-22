package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.interviewcode.InterviewCode;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IS_ONLINE;

public class InterviewCommandParser implements Parser<InterviewCommand> {


    @Override
    public InterviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_ADDRESS, PREFIX_IS_ONLINE);

        Index index;
        InterviewCode interviewCode;

        try {
            String[] indexAndCode = ParserUtil.parseInterviewPreamble(argumentMultimap.getPreamble());
            index = ParserUtil.parseIndex(indexAndCode[0]);
            interviewCode = ParserUtil.parseInterviewCode(indexAndCode[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, InterviewCommand.MESSAGE_USAGE), pe);
        }



        return null;
    }

}

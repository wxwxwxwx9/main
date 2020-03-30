package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.comparator.ApplicationDateComparator;
import seedu.address.logic.comparator.CompanyComparator;
import seedu.address.logic.comparator.PriorityComparator;
import seedu.address.logic.comparator.StatusComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // includes more than a single Prefix
        assertParseFailure(parser, " " + PREFIX_COMPANY.toString() + " " + PREFIX_DATE.toString(),
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsCorrectSortCommand() {
        SortCommand expectedSortCommand = new SortCommand(new CompanyComparator());
        assertParseSuccess(parser, " " + PREFIX_COMPANY.toString(), expectedSortCommand);

        expectedSortCommand = new SortCommand(new ApplicationDateComparator());
        assertParseSuccess(parser, " " + PREFIX_DATE.toString(), expectedSortCommand);

        expectedSortCommand = new SortCommand(new PriorityComparator());
        assertParseSuccess(parser, " " + PREFIX_PRIORITY.toString(), expectedSortCommand);

        expectedSortCommand = new SortCommand(new StatusComparator());
        assertParseSuccess(parser, " " + PREFIX_STATUS.toString(), expectedSortCommand);

        expectedSortCommand = new SortCommand(new CompanyComparator().reversed());
        assertParseSuccess(parser, SortCommand.REVERSE_KEYWORD + " "
            + PREFIX_COMPANY.toString(), expectedSortCommand);
    }

}

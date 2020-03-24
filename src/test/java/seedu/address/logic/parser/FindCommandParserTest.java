package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.internship.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.ApplicationDateIsDatePredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.StatusContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(List.of(new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Facebook")),
                        new RoleContainsKeywordsPredicate(Arrays.asList("Software", "Engineer")),
                        new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Street")),
                        new PhoneContainsNumbersPredicate(Arrays.asList("12345")),
                        new EmailContainsKeywordsPredicate(Arrays.asList("Alice")),
                        new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01)),
                        new PriorityContainsNumbersPredicate(Arrays.asList("5")),
                        new StatusContainsKeywordsPredicate(Arrays.asList("Active"))),
                        false);
        assertParseSuccess(parser, " c/Google Facebook r/Software Engineer a/Main Street p/12345 e/Alice "
                + "d/01 02 2020 w/5 s/Active", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/Google Facebook \n \t\n r/Software Engineer     "
                        + "a/Main Street     \t      \n    p/12345  \t  e/Alice d/01 02 2020 \t w/5 \n s/Active",
                expectedFindCommand);
    }

}

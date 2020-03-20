package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.internship.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;

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
                new FindCommand(new CompanyContainsKeywordsPredicate(Arrays.asList("Google", "Facebook")),
                        new RoleContainsKeywordsPredicate(Arrays.asList("Software", "Engineer")),
                        new AddressContainsKeywordsPredicate(Arrays.asList("Main", "Street")),
                        new PhoneContainsNumbersPredicate(Arrays.asList("12345")),
                        new EmailContainsKeywordsPredicate(Arrays.asList("Alice")));
        assertParseSuccess(parser, " c/Google Facebook r/Software Engineer a/Main Street p/12345 e/Alice",
                expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/Google Facebook \n \t\n r/Software Engineer     "
                        + "a/Main Street     \t      \n    p/12345  \t  e/Alice",
                expectedFindCommand);
    }

}

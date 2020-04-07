package seedu.diary.logic.parser;

import static seedu.diary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.diary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.diary.logic.commands.FindCommand;
import seedu.diary.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.ApplicationDateIsDatePredicate;
import seedu.diary.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.diary.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.diary.model.internship.predicate.StatusContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces on non preamble find
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

        // multiple whitespaces between keywords on non preamble find
        assertParseSuccess(parser, " c/Google Facebook \n \t\n r/Software Engineer     "
                + "a/Main Street     \t      \n    p/12345  \t  e/Alice d/01 02 2020 \t w/5 \n s/Active",
            expectedFindCommand);

        // no leading and trailing whitespaces on preamble find
        expectedFindCommand =
            new FindCommand(List.of(new CompanyContainsKeywordsPredicate(Arrays.asList("Google")),
                new RoleContainsKeywordsPredicate(Arrays.asList("Google")),
                new AddressContainsKeywordsPredicate(Arrays.asList("Google")),
                new PhoneContainsNumbersPredicate(Arrays.asList("Google")),
                new EmailContainsKeywordsPredicate(Arrays.asList("Google")),
                new PriorityContainsNumbersPredicate(Arrays.asList("Google")),
                new StatusContainsKeywordsPredicate(Arrays.asList("Google"))),
                true);
        assertParseSuccess(parser, "Google c/Google Facebook r/Software Engineer a/Main Street p/12345 e/Alice "
            + "d/01 02 2020 w/5 s/Active", expectedFindCommand);

        assertParseSuccess(parser, "Google", expectedFindCommand);

        // multiple whitespaces between keywords on preamble find
        assertParseSuccess(parser, "Google c/Google Facebook \n \t\n r/Software Engineer     "
                + "a/Main Street     \t      \n    p/12345  \t  e/Alice d/01 02 2020 \t w/5 \n s/Active",
            expectedFindCommand);
        assertParseSuccess(parser, "\t  Google \n",
            expectedFindCommand);
    }

}

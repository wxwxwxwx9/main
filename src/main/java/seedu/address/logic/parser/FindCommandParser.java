package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.ApplicationDateIsDatePredicate;
import seedu.address.model.internship.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.StatusContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS);

        if (!areAnyPrefixesPresent(argMultimap, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS,
                PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS)
                && argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        CompanyContainsKeywordsPredicate cPredicate = new CompanyContainsKeywordsPredicate(null);
        RoleContainsKeywordsPredicate rPredicate = new RoleContainsKeywordsPredicate(null);
        AddressContainsKeywordsPredicate aPredicate = new AddressContainsKeywordsPredicate(null);
        PhoneContainsNumbersPredicate pPredicate = new PhoneContainsNumbersPredicate(null);
        EmailContainsKeywordsPredicate ePredicate = new EmailContainsKeywordsPredicate(null);
        ApplicationDateIsDatePredicate dPredicate = new ApplicationDateIsDatePredicate(null);
        PriorityContainsNumbersPredicate wPredicate = new PriorityContainsNumbersPredicate(null);
        StatusContainsKeywordsPredicate sPredicate = new StatusContainsKeywordsPredicate(null);

        if (!argMultimap.getPreamble().isEmpty()) {
            String[] preamble = argMultimap.getPreamble().split("\\s+");
            cPredicate = new CompanyContainsKeywordsPredicate(Arrays.asList(preamble));
            rPredicate = new RoleContainsKeywordsPredicate(Arrays.asList(preamble));
            aPredicate = new AddressContainsKeywordsPredicate(Arrays.asList(preamble));
            pPredicate = new PhoneContainsNumbersPredicate(Arrays.asList(preamble));
            ePredicate = new EmailContainsKeywordsPredicate(Arrays.asList(preamble));
            wPredicate = new PriorityContainsNumbersPredicate(Arrays.asList(preamble));
            sPredicate = new StatusContainsKeywordsPredicate(Arrays.asList(preamble));
            return new FindCommand(cPredicate, rPredicate, aPredicate, pPredicate, ePredicate, dPredicate, wPredicate,
                    sPredicate, true);
        }

        if (argMultimap.getValue(PREFIX_COMPANY).isPresent()) {
            String[] companyKeywords = argMultimap.getValue(PREFIX_COMPANY).get().split("\\s+");
            cPredicate = new CompanyContainsKeywordsPredicate(Arrays.asList(companyKeywords));
        }
        if (argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            String[] roleKeywords = argMultimap.getValue(PREFIX_ROLE).get().split("\\s+");
            rPredicate = new RoleContainsKeywordsPredicate(Arrays.asList(roleKeywords));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            String[] addressKeywords = argMultimap.getValue(PREFIX_ADDRESS).get().split("\\s+");
            aPredicate = new AddressContainsKeywordsPredicate(Arrays.asList(addressKeywords));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            String[] phoneNumbers = argMultimap.getValue(PREFIX_PHONE).get().split("\\s+");
            pPredicate = new PhoneContainsNumbersPredicate(Arrays.asList(phoneNumbers));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            String[] emailKeywords = argMultimap.getValue(PREFIX_EMAIL).get().split("\\s+");
            ePredicate = new EmailContainsKeywordsPredicate(Arrays.asList(emailKeywords));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String date = argMultimap.getValue(PREFIX_DATE).get();
            dPredicate = new ApplicationDateIsDatePredicate(ParserUtil.parseApplicationDate(date).fullApplicationDate);
        }
        if (argMultimap.getValue(PREFIX_PRIORITY).isPresent()) {
            String[] priorityNumbers = argMultimap.getValue(PREFIX_PRIORITY).get().split("\\s+");
            wPredicate = new PriorityContainsNumbersPredicate(Arrays.asList(priorityNumbers));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            String[] statusKeywords = argMultimap.getValue(PREFIX_STATUS).get().split("\\s+");
            sPredicate = new StatusContainsKeywordsPredicate(Arrays.asList(statusKeywords));
        }
        return new FindCommand(cPredicate, rPredicate, aPredicate, pPredicate, ePredicate, dPredicate, wPredicate,
                sPredicate, false);
    }

    /**
     * Returns true if not all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

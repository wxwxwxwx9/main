package seedu.address.logic.parser;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.*;
import seedu.address.model.status.Status;

import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_ADDRESS,
                        PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE, PREFIX_PRIORITY, PREFIX_STATUS);

        if (!arePrefixesPresent(argMultimap, PREFIX_COMPANY, PREFIX_ROLE, PREFIX_DATE, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Company company = ParserUtil.parseCompany(argMultimap.getValue(PREFIX_COMPANY).get());
        Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).orElse(""));
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).orElse(""));
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).orElse(""));
        ApplicationDate date = ParserUtil.parseApplicationDate(argMultimap.getValue(PREFIX_DATE).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(PREFIX_PRIORITY).orElse("5"));
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

        InternshipApplication internshipApplication =
                new InternshipApplication(company, role, address, phone, email, date, priority, status);

        return new AddCommand(internshipApplication);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

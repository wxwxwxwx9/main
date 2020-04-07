package seedu.diary.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.diary.commons.core.index.Index;
import seedu.diary.commons.core.interviewcode.InterviewCode;
import seedu.diary.commons.util.StringUtil;
import seedu.diary.logic.parser.exceptions.ParseException;
import seedu.diary.model.internship.Address;
import seedu.diary.model.internship.ApplicationDate;
import seedu.diary.model.internship.Company;
import seedu.diary.model.internship.Email;
import seedu.diary.model.internship.Phone;
import seedu.diary.model.internship.Priority;
import seedu.diary.model.internship.Role;
import seedu.diary.model.status.Status;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_PREAMBLE =
        "Index followed by Command Code of add, edit, or delete is expected";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code args} into a list of {@code Index} and returns it.
     * Leading and trailing whitespaces for each index will be trimmed.
     * The list of index will also be sorted and not contain duplicates.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndices(String args, String delimiter) throws ParseException {
        String[] indices = args.split(delimiter);
        List<Integer> integers = new ArrayList<>();
        for (String oneBasedIndex : indices) {
            oneBasedIndex = oneBasedIndex.trim();
            if (!StringUtil.isNonZeroUnsignedInteger(oneBasedIndex)) {
                throw new ParseException(MESSAGE_INVALID_INDEX);
            }
            integers.add(Integer.parseInt(oneBasedIndex));
        }
        List<Index> indicesList = integers.stream()
            .distinct()
            .sorted()
            .map(oneBasedIndex -> Index.fromOneBased(oneBasedIndex))
            .collect(Collectors.toList());
        return indicesList;
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String company} into an {@code Company}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code company} is invalid.
     */
    public static Company parseCompany(String company) throws ParseException {
        requireNonNull(company);
        String trimmedCompany = company.trim();
        if (!Company.isValidCompany(trimmedCompany)) {
            throw new ParseException(Company.MESSAGE_CONSTRAINTS);
        }
        return new Company(trimmedCompany);
    }

    /**
     * Parses a {@code String role} into an {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        if (!Role.isValidRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses a {@code String date} into an {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate, DateTimeFormatter.ofPattern("dd MM yyyy"));
        } catch (DateTimeParseException e) {
            throw new ParseException("Date should be in the form: DD MM YYYY");
        }
    }

    /**
     * Parses a {@code String applicationDate} into an {@code ApplicationDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static ApplicationDate parseApplicationDate(String applicationDate) throws ParseException {
        requireNonNull(applicationDate);
        String trimmedDate = applicationDate.trim();
        if (!ApplicationDate.isValidApplicationDate(trimmedDate)) {
            throw new ParseException(ApplicationDate.MESSAGE_CONSTRAINTS);
        }

        return new ApplicationDate(trimmedDate);
    }

    /**
     * Parses a {@code String priority} into an {@code Priority}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code priority} is invalid.
     */
    public static Priority parsePriority(String priority) throws ParseException {
        requireNonNull(priority);
        String trimmedPriority = priority.trim();
        if (!Priority.isValidPriority(trimmedPriority)) {
            throw new ParseException(Priority.MESSAGE_CONSTRAINTS);
        }
        return new Priority(trimmedPriority);
    }

    /**
     * Parses a {@code String status} into an {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        trimmedStatus = trimmedStatus.replaceAll("\\s", "_");
        List<Status> possibleStatus = Status.possibleStatus(trimmedStatus);
        if (possibleStatus.size() != 1) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return possibleStatus.get(0);
    }

    /**
     * Parses the preamble of an interview command.
     * Leading and trailing whitespaces will be trimmed.
     * The preamble should consist of either 2 or 3 strings separated by a single whitespace.
     *
     * @throws ParseException if the given {@code preamble}
     * has more than or less than 2/3 strings separated by a single whitespace.
     */
    public static String[] parseInterviewPreamble(String preamble) throws ParseException {
        requireNonNull(preamble);
        String[] indexAndCode = preamble.trim().split(" ");
        if (indexAndCode.length != 2 && indexAndCode.length != 3) {
            throw new ParseException(MESSAGE_INVALID_PREAMBLE);
        }
        return indexAndCode;
    }

    /**
     * Parses a {@code code} into an {@code InterviewCode}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code code} is invalid.
     */
    public static InterviewCode parseInterviewCode(String code) throws ParseException {
        requireNonNull(code);
        String trimmedCode = code.trim();
        if (!InterviewCode.isValidCode(trimmedCode)) {
            throw new ParseException(InterviewCode.MESSAGE_CONSTRAINTS);
        }
        return InterviewCode.valueOf(trimmedCode.toUpperCase());
    }
}

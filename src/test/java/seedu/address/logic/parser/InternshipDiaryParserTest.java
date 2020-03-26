package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERVIEW;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ArchivalCommand;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.InitClearCommand;
import seedu.address.logic.commands.InterviewCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.StatisticsCommand;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewAddCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewDeleteCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewEditCommand;
import seedu.address.logic.commands.interviewsubcommands.InterviewListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.internship.InternshipApplication;
import seedu.address.model.internship.interview.Interview;
import seedu.address.model.internship.predicate.AddressContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.ApplicationDateIsDatePredicate;
import seedu.address.model.internship.predicate.CompanyContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.EmailContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.PhoneContainsNumbersPredicate;
import seedu.address.model.internship.predicate.PriorityContainsNumbersPredicate;
import seedu.address.model.internship.predicate.RoleContainsKeywordsPredicate;
import seedu.address.model.internship.predicate.StatusContainsKeywordsPredicate;
import seedu.address.testutil.EditInternshipDescriptorBuilder;
import seedu.address.testutil.EditInterviewDescriptorBuilder;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.InternshipApplicationUtil;
import seedu.address.testutil.InterviewBuilder;
import seedu.address.testutil.InterviewUtil;

public class InternshipDiaryParserTest {

    private final InternshipDiaryParser parser = new InternshipDiaryParser();

    @Test
    public void parseCommand_add() throws Exception {
        InternshipApplication internshipApplication = new InternshipApplicationBuilder().build();
        AddCommand command = (AddCommand) parser
                .parseCommand(InternshipApplicationUtil.getAddCommand(internshipApplication));
        assertEquals(new AddCommand(internshipApplication), command);
    }

    @Test
    public void parseCommand_initClear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof InitClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof InitClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        InternshipApplication internshipApplication = new InternshipApplicationBuilder().build();
        EditCommand.EditInternshipDescriptor descriptor =
                new EditInternshipDescriptorBuilder(internshipApplication).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " "
                + InternshipApplicationUtil.getEditInternshipApplicationDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("c/google", "r/engineer", "a/main", "p/12345", "e/alice", "d/01 02 2020",
                "w/5", "s/Active");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(List.of(new CompanyContainsKeywordsPredicate(Arrays.asList("google")),
                new RoleContainsKeywordsPredicate(Arrays.asList("engineer")),
                new AddressContainsKeywordsPredicate(Arrays.asList("main")),
                new PhoneContainsNumbersPredicate(Arrays.asList("12345")),
                new EmailContainsKeywordsPredicate(Arrays.asList("alice")),
                new ApplicationDateIsDatePredicate(LocalDate.of(2020, 02, 01)),
                new PriorityContainsNumbersPredicate(Arrays.asList("5")),
                new StatusContainsKeywordsPredicate(Arrays.asList("Active"))),
                false), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_interview_list() throws Exception {
        InterviewListCommand command = (InterviewListCommand) parser.parseCommand(InterviewCommand.COMMAND_WORD
                + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " list");
        assertEquals(new InterviewListCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), command);
    }

    @Test
    public void parseCommand_interview_add() throws Exception {
        Interview interview = new InterviewBuilder().build();
        InterviewAddCommand command = (InterviewAddCommand)
                parser.parseCommand(InterviewUtil.getAddCommand(interview));
        assertEquals(new InterviewAddCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, interview), command);
    }

    @Test
    public void parseCommand_interview_delete() throws Exception {
        InterviewDeleteCommand command = (InterviewDeleteCommand) parser.parseCommand(
                InterviewCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased()
                        + " delete " + INDEX_FIRST_INTERVIEW.getOneBased());
        assertEquals(new InterviewDeleteCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_FIRST_INTERVIEW), command);
    }

    @Test
    public void parseCommand_interview_edit() throws Exception {
        Interview interview = new InterviewBuilder().build();
        InterviewEditCommand.EditInterviewDescriptor descriptor =
                new EditInterviewDescriptorBuilder(interview).build();
        InterviewEditCommand command = (InterviewEditCommand) parser.parseCommand(
                InterviewCommand.COMMAND_WORD + " "
                        + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased() + " edit "
                        + INDEX_FIRST_INTERVIEW.getOneBased()
                        + " " + InterviewUtil.getEditInterviewApplicationDescriptorDetails(descriptor));
        assertEquals(new InterviewEditCommand(INDEX_FIRST_INTERNSHIP_APPLICATION,
                INDEX_FIRST_INTERVIEW, descriptor), command);
    }

    @Test
    public void parseCommand_stats() throws Exception {
        assertTrue(parser.parseCommand(StatisticsCommand.COMMAND_WORD) instanceof StatisticsCommand);
        assertTrue(parser.parseCommand(StatisticsCommand.COMMAND_WORD + " 3") instanceof StatisticsCommand);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        assertEquals(new ArchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), command);
    }

    @Test
    public void parseCommand_unarchive() throws Exception {
        UnarchiveCommand command = (UnarchiveCommand) parser.parseCommand(
                UnarchiveCommand.COMMAND_WORD + " " + INDEX_FIRST_INTERNSHIP_APPLICATION.getOneBased());
        assertEquals(new UnarchiveCommand(INDEX_FIRST_INTERNSHIP_APPLICATION), command);
    }

    @Test
    public void parseCommand_archival() throws Exception {
        assertTrue(parser.parseCommand(ArchivalCommand.COMMAND_WORD) instanceof ArchivalCommand);
        assertTrue(parser.parseCommand(ArchivalCommand.COMMAND_WORD + " 3") instanceof ArchivalCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

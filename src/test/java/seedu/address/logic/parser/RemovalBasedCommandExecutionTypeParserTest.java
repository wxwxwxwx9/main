// package seedu.address.logic.parser;
//
// import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
// import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
// import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
// import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INTERNSHIP_APPLICATION;
// import static seedu.address.testutil.TypicalIndexes.INDEX_LIST_FIRST_INTERNSHIP_APPLICATION;
//
// import org.junit.jupiter.api.Test;
//
// import seedu.address.commons.core.commandexecutiontype.RemovalBasedCommandExecutionType;
// import seedu.address.logic.commands.DeleteCommand;
// import seedu.address.logic.commands.RemovalBasedCommand;
// import seedu.address.testutil.PredicateUtil;
//
// /**
//  * As we are only doing white-box testing, our test cases do not cover path variations
//  * outside of the RemovalBasedCommand code. For example, inputs "1" and "1 abc" take the
//  * same path through the RemovalBasedCommand, and therefore we test only one of them.
//  * The path variation for those two cases occur inside the ParserUtil, and
//  * therefore should be covered by the ParserUtilTest.
//  */
// public class RemovalBasedCommandExecutionTypeParserTest {
//
//     private static final String VALID_FIELD_COMPANY = "c/";
//     private static final String INVALID_FIELD_DATE = "d/";
//
//     private RemovalBasedCommandExecutionTypeParser parser = new RemovalBasedCommandExecutionTypeParser();
//
//     @Test
//     public void parse_byIndexValidIndexDeleteCommandWord_returnsRemovalBasedCommand() {
//         String validIndexInput = "1";
//         assertParseSuccess(parser, validIndexInput,
//             new RemovalBasedCommand(INDEX_FIRST_INTERNSHIP_APPLICATION, RemovalBasedCommandExecutionType.BY_INDEX,
//                 DeleteCommand.COMMAND_WORD)
//         );
//     }
//
//     @Test
//     public void parse_byIndexInvalidIndex_throwsParseException() {
//         String invalidIndexInput = "a";
//         assertParseFailure(parser, invalidIndexInput,
//             String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovalBasedCommand.MESSAGE_USAGE_BY_INDICES));
//     }
//
//     @Test
//     public void parse_byIndicesValidIndicesDeleteCommandWord_returnsRemovalBasedCommand() {
//         String validIndicesInput = "1, 2";
//         assertParseSuccess(parser, validIndicesInput,
//             new RemovalBasedCommand(INDEX_LIST_FIRST_INTERNSHIP_APPLICATION,
//                 RemovalBasedCommandExecutionType.BY_INDICES,
//                 DeleteCommand.COMMAND_WORD)
//         );
//     }
//
//     @Test
//     public void parse_byIndicesInvalidIndices_throwsParseException() {
//         String invalidIndicesInput = "1, a, 3";
//         assertParseFailure(parser, invalidIndicesInput,
//             String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovalBasedCommand.MESSAGE_USAGE_BY_INDICES));
//     }
//
//     @Test
//     public void parse_byFieldValidFieldDeleteCommandWord_returnsRemovalBasedCommand() {
//         String company = "google";
//         String validCommand = String.format("%s %s%s", DeleteCommand.COMMAND_WORD, VALID_FIELD_COMPANY, company);
//         assertParseSuccess(parser, validCommand,
//             new RemovalBasedCommand(PredicateUtil.prepareCompanyPredicate(company),
//                 RemovalBasedCommandExecutionType.BY_FIELD,
//                 DeleteCommand.COMMAND_WORD)
//         );
//     }
//
//     @Test
//     public void parse_byFieldInvalidField_throwsParseException() {
//         String date = "20 12 2020";
//         String invalidCommand = String.format("%s %s%s", DeleteCommand.COMMAND_WORD, INVALID_FIELD_DATE, date);
//         assertParseFailure(parser, invalidCommand,
//             String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovalBasedCommand.MESSAGE_USAGE_BY_FIELD));
//     }
//
// }

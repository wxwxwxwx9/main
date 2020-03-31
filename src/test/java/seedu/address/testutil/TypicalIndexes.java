package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;

/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalIndexes {
    public static final Index INDEX_FIRST_INTERNSHIP_APPLICATION = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_INTERNSHIP_APPLICATION = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_INTERNSHIP_APPLICATION = Index.fromOneBased(3);

    public static final Set<Index> INDEX_SET_FIRST_INTERNSHIP_APPLICATION =
        new HashSet<>(
            new ArrayList<Index>(Arrays.asList(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_SECOND_INTERNSHIP_APPLICATION))
        );
    public static final Set<Index> INDEX_SET_SECOND_INTERNSHIP_APPLICATION =
        new HashSet<>(
            new ArrayList<Index>(Arrays.asList(INDEX_FIRST_INTERNSHIP_APPLICATION, INDEX_THIRD_INTERNSHIP_APPLICATION))
        );

    public static final Index INDEX_FIRST_INTERVIEW = Index.fromOneBased(1);
    public static final Index INDEX_SECOND_INTERVIEW = Index.fromOneBased(2);
    public static final Index INDEX_THIRD_INTERVIEW = Index.fromOneBased(3);

}

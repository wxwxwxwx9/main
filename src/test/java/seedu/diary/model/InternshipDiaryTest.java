package seedu.diary.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.diary.testutil.Assert.assertThrows;
import static seedu.diary.testutil.TypicalInternshipApplications.GOOGLE;
import static seedu.diary.testutil.TypicalInternshipApplications.getTypicalInternshipDiary;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.diary.model.internship.InternshipApplication;
import seedu.diary.model.internship.exceptions.DuplicateInternshipApplicationException;
import seedu.diary.model.status.Status;
import seedu.diary.testutil.InternshipApplicationBuilder;


public class InternshipDiaryTest {

    private final InternshipDiary internshipDiary = new InternshipDiary();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), internshipDiary.getDisplayedInternshipList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> internshipDiary.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyInternshipDiary_replacesData() {
        InternshipDiary newData = getTypicalInternshipDiary();
        internshipDiary.resetData(newData);
        assertEquals(newData, internshipDiary);
    }

    @Test
    public void resetData_withDuplicateInternship_throwsDuplicateInternshipException() {
        // Two internship applications with the same identity fields
        InternshipApplication editedGoogle = new InternshipApplicationBuilder(GOOGLE)
            .withAddress("1600 Amphitheatre Parkway")
            .build();
        List<InternshipApplication> newInternshipApplications = Arrays.asList(GOOGLE, editedGoogle);
        InternshipDiaryStub newData = new InternshipDiaryStub(newInternshipApplications);

        assertThrows(DuplicateInternshipApplicationException.class, () -> internshipDiary.resetData(newData));
    }

    @Test
    public void hasInternship_nullInternship_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> internshipDiary.hasInternship(null));
    }

    @Test
    public void hasInternship_internshipNotInInternshipDiary_returnsFalse() {
        assertFalse(internshipDiary.hasInternship(GOOGLE));
    }

    @Test
    public void hasInternship_internshipInInternshipDiary_returnsTrue() {
        internshipDiary.addInternshipApplication(GOOGLE);
        assertTrue(internshipDiary.hasInternship(GOOGLE));
    }

    @Test
    public void hasInternship_internshipApplicationWithSameIdentityFieldsInInternshipDiary_returnsTrue() {
        internshipDiary.addInternshipApplication(GOOGLE);
        InternshipApplication editedGoogle = new InternshipApplicationBuilder(GOOGLE)
            .withPriority(1)
            .withStatus(Status.APPLIED)
            .build();
        assertTrue(internshipDiary.hasInternship(editedGoogle));
    }

    @Test
    public void getInternshipList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> internshipDiary.getDisplayedInternshipList().remove(0));
    }

    /**
     * A stub ReadOnlyInternshipDiary whose internship list can violate interface constraints.
     */
    private static class InternshipDiaryStub implements ReadOnlyInternshipDiary {
        private final ObservableList<InternshipApplication> internshipApplications =
            FXCollections.observableArrayList();

        private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

        InternshipDiaryStub(Collection<InternshipApplication> internshipApplications) {
            this.internshipApplications.setAll(internshipApplications);
        }

        @Override
        public ObservableList<InternshipApplication> getDisplayedInternshipList() {
            return internshipApplications;
        }

        @Override
        public ObservableList<InternshipApplication> getAllInternshipList() {
            return internshipApplications;
        }

        @Override
        public void addPropertyChangeListener(ListenerPropertyType propertyType, PropertyChangeListener l) {
            changes.addPropertyChangeListener(l);
        }
    }

}

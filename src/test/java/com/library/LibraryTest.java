
package com.library;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryTest {

    private static final String ISBN = "978-0-00-000001-1";
    private static final LocalDate DAY =
            LocalDate.of(2026, 10, 1);

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library();

        library.addBook(
            new Book(ISBN, "Software Engineering", 2016)
        );

        library.addCopy(ISBN, "C-001");

        library.registerMember(
            new Member("M-01", "First member")
        );
    }

    @Test
    void lendingMakesTheCopyUnavailable() {

        Loan loan = library.lend("M-01", "C-001", DAY);

        assertFalse(library.findCopy("C-001").isAvailable());

        assertEquals(DAY.plusDays(14), loan.getDueDate());
    }

    @Test
    void cannotLendUnavailableCopy() {

        library.lend("M-01", "C-001", DAY);

        assertThrows(IllegalStateException.class, () ->
            library.lend("M-01", "C-001", DAY)
        );
    }

    @Test
    void unknownMemberThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
            library.lend("M-99", "C-001", DAY)
        );
    }

    @Test
    void unknownCopyThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
            library.lend("M-01", "C-999", DAY)
        );
    }

    @Test
    void cannotExceedFiveOpenLoans() {

        library.addCopy(ISBN, "C-002");
        library.addCopy(ISBN, "C-003");
        library.addCopy(ISBN, "C-004");
        library.addCopy(ISBN, "C-005");
        library.addCopy(ISBN, "C-006");

        library.lend("M-01", "C-001", DAY);
        library.lend("M-01", "C-002", DAY);
        library.lend("M-01", "C-003", DAY);
        library.lend("M-01", "C-004", DAY);
        library.lend("M-01", "C-005", DAY);

        library.addCopy(ISBN, "C-007");

        assertThrows(IllegalStateException.class, () ->
            library.lend("M-01", "C-007", DAY)
        );
    }

    @Test
    void returnMakesCopyAvailable() {

        library.lend("M-01", "C-001", DAY);

        library.returnCopy("C-001", DAY.plusDays(5));

        assertTrue(library.findCopy("C-001").isAvailable());
    }

    @Test
    void cannotReturnAvailableCopy() {

        assertThrows(IllegalStateException.class, () ->
            library.returnCopy("C-001", DAY)
        );
    }

    @Test
    void returningUnknownCopyThrowsException() {

        assertThrows(IllegalArgumentException.class, () ->
            library.returnCopy("C-999", DAY)
        );
    }
}


# UC-07 – Register a return

## 1. Name
UC-07 Register a return

## 2. Goal
Register the return of a borrowed copy, close its loan, and handle late returns and reservations.

## 3. Actors
- **Primary actor:** Librarian.
- **Supporting actor:** Member.
- **External supporting actor:** Mail server (for sending a notification).

## 4. Stakeholders and interests
- **Member returning the book:** Wants the return to be recorded correctly.
- **Member who reserved the book:** Wants to be notified when the reserved copy becomes available.
- **Library:** Wants returned copies and loans to be correctly recorded and late returns handled.

## 5. Level and scope
- **Level:** User goal.
- **Scope:** Library system.

## 6. Preconditions
- The librarian is authenticated.

## 7. Trigger
A member brings back a borrowed copy to the library.

## 8. Main success scenario
1. The member presents the borrowed copy to the librarian.
2. The librarian scans the copy's barcode.
3. The system identifies the copy and retrieves the corresponding loan.
4. The system closes the loan and records the return.
5. The system checks whether the return is late.
6. If the return is late, the extension point **late return** activates the use case **Suspend the member**.
7. The system checks whether the copy is reserved for another member.
8. If the copy is reserved, the system keeps it aside and notifies the member who made the reservation.
9. The system prints the receipt.

## 9. Alternative flows

- **1a. Barcode cannot be read:** The librarian manually enters the inventory number. The system identifies the copy and continues at step 3.

- **2a. Copy is not currently on loan:** The system displays an error message and the use case ends.

- **8a. Copy is not reserved:** The system makes the returned copy available in the library. The process continues at step 9.

## 10. Extensions
- **Late return:** If the copy is returned late, the use case **Suspend the member** is activated at the extension point **late return**.

## 11. Exceptions
- No additional exceptions are specified in the exercise statement.

## 12. Postconditions
- **Success:** The loan is closed and the return is recorded. If the copy is reserved, it is kept aside and the member who reserved it is notified. A receipt is printed.
- **Minimal guarantee:** A loan must not be considered closed unless the return has been recorded.

## 13. Special requirements
- The return and loan information must be recorded correctly.
- The reservation notification must be sent to the member who made the reservation.
- A receipt must be printed after a successful return.

## 14. Open issues
- What happens if the notification cannot be sent?
- How long should a reserved copy remain aside for the member who reserved it?
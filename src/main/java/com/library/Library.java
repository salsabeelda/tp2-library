package com.library;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> books = new ArrayList<>();
    private List<Copy> copies = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    // Add a book
    public void addBook(Book book) {
        books.add(book);
    }

    // Add a copy
    public void addCopy(Copy copy) {
        copies.add(copy);
    }

    // Add a copy using ISBN and inventory number
    public void addCopy(String isbn, String inventoryNumber) {
        Book book = null;

        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                book = b;
                break;
            }
        }

        if (book == null) {
            throw new IllegalArgumentException("Book not found");
        }

        copies.add(new Copy(inventoryNumber, book));
    }

    // Register a member
    public void registerMember(Member member) {
        members.add(member);
    }

    // Find a copy
    public Copy findCopy(String inventoryNumber) {
        for (Copy copy : copies) {
            if (copy.getInventoryNumber().equals(inventoryNumber)) {
                return copy;
            }
        }

        throw new IllegalArgumentException("Copy not found");
    }

    // Find a member
    private Member findMember(String memberNumber) {
        for (Member member : members) {
            if (member.getNumber().equals(memberNumber)) {
                return member;
            }
        }

        throw new IllegalArgumentException("Member not found");
    }

    // Lend a copy
    public Loan lend(String memberNumber,
                     String inventoryNumber,
                     LocalDate today) {

        Member member = findMember(memberNumber);
        Copy copy = findCopy(inventoryNumber);

        if (!copy.isAvailable()) {
            throw new IllegalStateException("Copy is not available");
        }

        int count = 0;

        for (Loan loan : loans) {
            if (loan.isOpen()
                    && loan.getMember().getNumber().equals(memberNumber)) {
                count++;
            }
        }

        if (count >= 5) {
            throw new IllegalStateException(
                "Member cannot have more than 5 open loans");
        }

        Loan loan = new Loan(member, copy, today);

        loans.add(loan);
        copy.setAvailable(false);

        return loan;
    }

    // Return a copy
    public void returnCopy(String inventoryNumber, LocalDate today) {

        Copy copy = findCopy(inventoryNumber);

        if (copy.isAvailable()) {
            throw new IllegalStateException("Copy is not borrowed");
        }

        for (Loan loan : loans) {
            if (loan.getCopy().getInventoryNumber()
                    .equals(inventoryNumber) && loan.isOpen()) {

                loan.setReturnDate(today);
                copy.setAvailable(true);
                return;
            }
        }

        throw new IllegalStateException("No open loan found");
    }

    // Getters
    public List<Book> getBooks() {
        return books;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Loan> getLoans() {
        return loans;
    }
}
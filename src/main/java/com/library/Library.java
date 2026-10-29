package com.library;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Copy> copies;
    private List<Member> members;
    private List<Loan> loans;

    public Library() {
        this.books = new ArrayList<>();
        this.copies = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void addCopy(Copy copy) {
        copies.add(copy);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

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
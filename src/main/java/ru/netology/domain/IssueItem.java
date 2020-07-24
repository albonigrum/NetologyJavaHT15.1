package ru.netology.domain;

import lombok.Data;

import java.util.*;

@Data
public class IssueItem {
    private static long minNewId = 1;

    private long id = minNewId++;
    private boolean isOpen = true;
    private String author = "";
    private Set<String> labels = new HashSet<>();
    private Set<String> assignees = new HashSet<>();

    private long countComment = 0;
    private Calendar dateOfCreation = new GregorianCalendar();
    private Calendar dateOfLastUpdate = new GregorianCalendar();

    private String title = "";
    private String description = "";

    //Запись в labels и assignees - Collection<? extends String> хоть String и final
    //для того чтобы можно было заменить класс String на что-нибудь другое без потери валидности кода.

    public IssueItem(boolean isOpen, String author,
                     Collection<? extends String> labels,
                     Collection<? extends String> assignees
    ) {
        this.isOpen = isOpen;
        this.author = author;
        this.labels = new HashSet<>(labels);
        this.assignees = new HashSet<>(assignees);
    }

    public IssueItem(boolean isOpen, String author,
                     Collection<? extends String> labels,
                     Collection<? extends String> assignees,
                     long countComment
    ) {
        this.isOpen = isOpen;
        this.author = author;
        this.labels = new HashSet<>(labels);
        this.assignees = new HashSet<>(assignees);
        this.countComment = countComment;
    }
    public IssueItem(boolean isOpen, String author,
                     Collection<? extends String> labels,
                     Collection<? extends String> assignees,
                     long countComment,
                     Calendar dateOfCreation,
                     Calendar dateOfLastUpdate
    ) {
        this.isOpen = isOpen;
        this.author = author;
        this.labels = new HashSet<>(labels);
        this.assignees = new HashSet<>(assignees);
        this.countComment = countComment;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public IssueItem(boolean isOpen, String author,
                     Collection<? extends String> labels,
                     Collection<? extends String> assignees,
                     long countComment,
                     Calendar dateOfCreation,
                     Calendar dateOfLastUpdate,
                     String title,
                     String description
    ) {
        this.isOpen = isOpen;
        this.author = author;
        this.labels = new HashSet<>(labels);
        this.assignees = new HashSet<>(assignees);
        this.countComment = countComment;
        this.dateOfCreation = dateOfCreation;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.title = title;
        this.description = description;
    }

    public void changeStatus() {
        isOpen = !isOpen;
    }

    public String toString() {
        return "IssueItem(id=" + this.getId() + ", isOpen=" + this.isOpen() + ", title=" + this.getTitle() + ", description=" + this.getDescription() + ")";
    }
}

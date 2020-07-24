package ru.netology.manager;

import ru.netology.domain.IssueItem;
import ru.netology.domain.IssueItemComparators.*;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

public class IssueManager {
    private final IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        if (repository == null)
            throw new IllegalArgumentException("repository not should be null");
        this.repository = repository;
    }

    private List<IssueItem> filterBy(Predicate<IssueItem> predicate) {
        List<IssueItem> result = new ArrayList<>();
        for (IssueItem item : repository.findAll()) {
            if (predicate.test(item))
                result.add(item);
        }
        return result;
    }

    private List<IssueItem> sortBy(Comparator<IssueItem> comparator) {
        List<IssueItem> result = repository.findAll();
        result.sort(comparator);
        return result;
    }

    public void add(IssueItem item) {
        repository.save(item);
    }

    public List<IssueItem> showOpened() {
        return filterBy(IssueItem::isOpen);
    }
    public List<IssueItem> showClosed() {
        return filterBy((item) -> !item.isOpen());
    }

    public List<IssueItem> filterByAuthor(String author) {
        return filterBy((item) -> item.getAuthor().equals(author));
    }
    public List<IssueItem> filterByLabels(Set<? extends String> labels) {
        return filterBy((item) -> item.getLabels().containsAll(labels));
    }
    public List<IssueItem> filterByAssignee(String assignee) {
        return filterBy((item) -> item.getAssignees().contains(assignee));
    }

    public List<IssueItem> sortByCountCommentAsc() {
        return sortBy(new CountCommentAscComparator());
    }
    public List<IssueItem> sortByCountCommentDesc() {
        return sortBy(new CountCommentDescComparator());
    }
    public List<IssueItem> sortByDateOfCreationAsc() {
        return sortBy(new DateOfCreationAscComparator());
    }
    public List<IssueItem> sortByDateOfCreationDesc() {
        return sortBy(new DateOfCreationDescComparator());
    }
    public List<IssueItem> sortByDateOfLastUpdateAsc() {
        return sortBy(new DateOfLastUpdateAscComparator());
    }
    public List<IssueItem> sortByDateOfLastUpdateDesc() {
        return sortBy(new DateOfLastUpdateDescComparator());
    }

    public void changeStatusIssue(long id) {
        repository.findById(id).changeStatus();
    }
    public void setStatusIssue(long id, boolean isOpen) {
        repository.findById(id).setOpen(isOpen);
    }
}

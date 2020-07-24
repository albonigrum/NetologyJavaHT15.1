package ru.netology.repository;

import ru.netology.domain.IssueItem;

import java.util.ArrayList;
import java.util.List;

public class IssueRepository {
    private final List<IssueItem> items = new ArrayList<>();

    public List<IssueItem> findAll() {
        return items;
    }

    public void save(IssueItem item) {
        items.add(item);
    }

    public IssueItem findById(long id) {
        for (IssueItem item : items) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public void removeById(long id) {
        items.removeIf((item) -> item.getId() == id);
    }

    public void removeAll() {
        items.clear();
    }
}

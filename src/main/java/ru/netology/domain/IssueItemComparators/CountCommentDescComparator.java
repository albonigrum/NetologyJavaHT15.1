package ru.netology.domain.IssueItemComparators;

import ru.netology.domain.IssueItem;

import java.util.Comparator;

public class CountCommentDescComparator implements Comparator<IssueItem> {
    @Override
    public int compare(IssueItem o1, IssueItem o2) {
        long dif = o1.getCountComment() - o2.getCountComment();
        return (dif < 0 ? 1 : (dif == 0 ? 0 : -1));
    }
}
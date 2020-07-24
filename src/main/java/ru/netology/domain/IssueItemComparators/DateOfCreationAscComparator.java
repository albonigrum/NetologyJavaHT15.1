package ru.netology.domain.IssueItemComparators;

import ru.netology.domain.IssueItem;

import java.util.Comparator;
import java.util.Date;

public class DateOfCreationAscComparator implements Comparator<IssueItem> {
    @Override
    public int compare(IssueItem o1, IssueItem o2) {
        Date date1 = o1.getDateOfCreation().getTime();
        Date date2 = o2.getDateOfCreation().getTime();
        return (date1.before(date2) ? -1 : (date1.equals(date2) ? 0 : 1));
    }
}

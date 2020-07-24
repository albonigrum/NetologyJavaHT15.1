package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.domain.IssueItem;
import ru.netology.repository.IssueRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class IssueManagerTest {
    @Mock
    IssueRepository repository;
    @InjectMocks
    IssueManager manager;

    private final IssueItem item1 = new IssueItem(true, "author1",
            Collections.emptyList(),
            Collections.emptyList(),
            0L,
            new GregorianCalendar(2019, Calendar.JANUARY, 1),
            new GregorianCalendar(2019, Calendar.JANUARY, 1,13, 13)
    );
    private final IssueItem item2 = new IssueItem(true, "author2",
            Arrays.asList("label1", "label2", "label3"),
            Arrays.asList("assignee1", "assignee2", "assignee3"),
            1L,
            new GregorianCalendar(2019, Calendar.FEBRUARY, 1),
            new GregorianCalendar(2019, Calendar.FEBRUARY, 1,13, 13)
    );
    private final IssueItem item3 = new IssueItem(true, "author3",
            Arrays.asList("label1", "label2"),
            Arrays.asList("assignee2", "assignee3"),
            2L,
            new GregorianCalendar(2019, Calendar.APRIL, 1),
            new GregorianCalendar(2019, Calendar.APRIL, 1,13, 13)
    );
    private final IssueItem item4 = new IssueItem(false, "author1",
            Collections.singletonList("label1"),
            Collections.singletonList("assignee3"),
            3L,
            new GregorianCalendar(2019, Calendar.MARCH, 1),
            new GregorianCalendar(2019, Calendar.MARCH, 1,13, 13)
    );
    private final IssueItem item5 = new IssueItem(false, "author2",
            Arrays.asList("label4", "label5", "label6"),
            Arrays.asList("assignee4", "assignee5", "assignee6"),
            4L,
            new GregorianCalendar(2020, Calendar.JANUARY, 1),
            new GregorianCalendar(2020, Calendar.JANUARY, 1,13, 13)
    );
    private final IssueItem item6 = new IssueItem(false, "author2",
            Arrays.asList("label4", "label5", "label6"),
            Arrays.asList("assignee4", "assignee5", "assignee6"),
            5L,
            new GregorianCalendar(2020, Calendar.JANUARY, 2),
            new GregorianCalendar(2020, Calendar.JANUARY, 2,13, 13)
    );
    private final IssueItem item7 = new IssueItem(false, "author2",
            Arrays.asList("label4", "label5", "label6"),
            Arrays.asList("assignee4", "assignee5", "assignee6"),
            6L,
            new GregorianCalendar(2020, Calendar.MAY, 1),
            new GregorianCalendar(2020, Calendar.MAY, 1,13, 13)
    );
    private final IssueItem item8 = new IssueItem(false, "author2",
            Arrays.asList("label4", "label5", "label6"),
            Arrays.asList("assignee4", "assignee5", "assignee6"),
            7L
    );



    @BeforeEach
    void setUp() {
        doNothing().when(repository).save(item1);
        doNothing().when(repository).save(item2);
        doNothing().when(repository).save(item3);
        doNothing().when(repository).save(item4);
        doNothing().when(repository).save(item5);
        doNothing().when(repository).save(item6);
        doNothing().when(repository).save(item7);
        doNothing().when(repository).save(item8);

        manager.add(item1);
        manager.add(item2);
        manager.add(item3);
        manager.add(item4);
        manager.add(item5);
        manager.add(item6);
        manager.add(item7);
        manager.add(item8);
    }

    @Test
    void shouldFailConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new IssueManager(null));
    }

    @Test
    void shouldShowOpened() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Arrays.asList(item1, item2, item3));
        Set<IssueItem> actual = new HashSet<>(manager.showOpened());

        assertEquals(expected, actual);
    }
    @Test
    void shouldShowClosed() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Arrays.asList(item4, item5, item6, item7, item8));
        Set<IssueItem> actual = new HashSet<>(manager.showClosed());

        assertEquals(expected, actual);
    }

    @Test
    void shouldFilterByAuthorIfAuthorExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Arrays.asList(item1, item4));
        Set<IssueItem> actual = new HashSet<>(manager.filterByAuthor("author1"));

        assertEquals(expected, actual);

        expected = new HashSet<>(Collections.singletonList(item3));
        actual = new HashSet<>(manager.filterByAuthor("author3"));

        assertEquals(expected, actual);
    }
    @Test
    void shouldFilterByAuthorIfAuthorNotExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Collections.emptyList());
        Set<IssueItem> actual = new HashSet<>(manager.filterByAuthor("author4"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldFilterByLabelsIfAllLabelsExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        //labels = {label1, label2}
        Set<String> labels = new HashSet<>(Arrays.asList("label1", "label2"));

        Set<IssueItem> expected = new HashSet<>(Arrays.asList(item2, item3));
        Set<IssueItem> actual = new HashSet<>(manager.filterByLabels(labels));

        assertEquals(expected, actual);

        //labels = {label1}
        labels.remove("label2");

        expected.add(item4);
        actual = new HashSet<>(manager.filterByLabels(labels));

        assertEquals(expected, actual);

        //labels = {label1, label4}
        labels.add("label4");
        expected.clear();
        actual = new HashSet<>(manager.filterByLabels(labels));

        assertEquals(expected, actual);

        //labels = {}
        labels.remove("label4");
        labels.remove("label1");

        expected.add(item1);
        expected.add(item2);
        expected.add(item3);
        expected.add(item4);
        expected.add(item5);
        expected.add(item6);
        expected.add(item7);
        expected.add(item8);
        actual = new HashSet<>(manager.filterByLabels(labels));

        assertEquals(expected, actual);

    }
    @Test
    void shouldFilterByLabelsIfNotAllLabelsExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<String> labels = new HashSet<>(Arrays.asList("label1", "label7"));

        Set<IssueItem> expected = Collections.emptySet();
        Set<IssueItem> actual = new HashSet<>(manager.filterByLabels(labels));

        assertEquals(expected, actual);
    }

    @Test
    void shouldFilterByAssigneeIfAssigneeExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Arrays.asList(item2, item3, item4));
        Set<IssueItem> actual = new HashSet<>(manager.filterByAssignee("assignee3"));

        assertEquals(expected, actual);

        expected = new HashSet<>(Collections.singletonList(item2));
        actual = new HashSet<>(manager.filterByAssignee("assignee1"));

        assertEquals(expected, actual);
    }
    @Test
    void shouldFilterByAssigneeIfAssigneeNotExists() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        Set<IssueItem> expected = new HashSet<>(Collections.emptyList());
        Set<IssueItem> actual = new HashSet<>(manager.filterByAssignee("assignee7"));

        assertEquals(expected, actual);
    }

    @Test
    void shouldChangeAndSetStatusIssue() {
        long idItem = item1.getId();

        doReturn(item1).when(repository).findById(idItem);
        manager.changeStatusIssue(idItem);

        assertFalse(item1.isOpen());

        manager.setStatusIssue(idItem, true);

        assertTrue(item1.isOpen());
    }

    @Test
    void shouldSortByCountCommentAsc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8);
        List<IssueItem> actual = manager.sortByCountCommentAsc();

        assertEquals(expected, actual);
    }
    @Test
    void shouldSortByCountCommentDesc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item8, item7, item6, item5, item4, item3, item2, item1);
        List<IssueItem> actual = manager.sortByCountCommentDesc();

        assertEquals(expected, actual);
    }
    @Test
    void shouldSortByDateOfCreationAsc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item1, item2, item4, item3, item5, item6, item7, item8);
        List<IssueItem> actual = manager.sortByDateOfCreationAsc();

        assertEquals(expected, actual);
    }
    @Test
    void shouldSortByDateOfCreationDesc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item8, item7, item6, item5, item3, item4, item2, item1);
        List<IssueItem> actual = manager.sortByDateOfCreationDesc();

        assertEquals(expected, actual);
    }
    @Test
    void shouldSortByDateOfLastUpdateAsc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item1, item2, item4, item3, item5, item6, item7, item8);
        List<IssueItem> actual = manager.sortByDateOfLastUpdateAsc();

        assertEquals(expected, actual);
    }
    @Test
    void shouldSortByDateOfLastUpdateDesc() {
        doReturn(Arrays.asList(item1, item2, item3, item4, item5, item6, item7, item8)).when(repository).findAll();

        List<IssueItem> expected = Arrays.asList(item8, item7, item6, item5, item3, item4, item2, item1);
        List<IssueItem> actual = manager.sortByDateOfLastUpdateDesc();

        assertEquals(expected, actual);
    }
}
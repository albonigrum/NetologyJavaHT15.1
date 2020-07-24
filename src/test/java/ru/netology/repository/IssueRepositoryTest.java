package ru.netology.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.IssueItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssueRepositoryTest {
    private final IssueRepository repository = new IssueRepository();

    private final IssueItem item1 = new IssueItem(true, "author1",
            Collections.emptyList(),
            Collections.emptyList()
    );
    private final IssueItem item2 = new IssueItem(true, "author2",
            Arrays.asList("label1", "label2", "label3"),
            Arrays.asList("assignee1", "assignee2", "assignee3")
    );
    private final IssueItem item3 = new IssueItem(true, "author3",
            Arrays.asList("label1", "label2"),
            Arrays.asList("assignee2", "assignee3")
    );
    private final IssueItem item4 = new IssueItem(false, "author1",
            Collections.singletonList("label1"),
            Collections.singletonList("assignee3")
    );
    private final IssueItem item5 = new IssueItem(false, "author2",
            Arrays.asList("label4", "label5", "label6"),
            Arrays.asList("assignee4", "assignee5", "assignee6")
    );


    @BeforeEach
    void setUp() {
        repository.save(item1);
        repository.save(item2);
        repository.save(item3);
        repository.save(item4);
        repository.save(item5);
    }

    @Test
    void shouldFindAll() {
        List<IssueItem> actual = repository.findAll();
        List<IssueItem> expected = Arrays.asList(item1, item2, item3, item4, item5);

        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByIdIfInRepository() {
        long idToFind = item1.getId();

        IssueItem actual = repository.findById(idToFind);

        assertEquals(item1, actual);
    }

    @Test
    void shouldFindByIdIfNoInRepository() {
        long idToFind = item1.getId() - 1;

        IssueItem actual = repository.findById(idToFind);

        assertNull(actual);
    }


    @Test
    void shouldRemoveByIdIfInRepository() {
        long idToRemove = item2.getId();

        repository.removeById(idToRemove);

        List<IssueItem> actual = repository.findAll();
        List<IssueItem> expected = Arrays.asList(item1, item3, item4, item5);

        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveByIdIfNoInRepository() {
        long idToRemove = item1.getId() - 1;

        repository.removeById(idToRemove);

        List<IssueItem> actual = repository.findAll();
        List<IssueItem> expected = Arrays.asList(item1, item2, item3, item4, item5);

        assertEquals(expected, actual);
    }

    @Test
    void shouldRemoveAll() {
        repository.removeAll();

        List<IssueItem> actual = repository.findAll();
        List<Object> expected = Collections.emptyList();

        assertEquals(expected, actual);
    }
}
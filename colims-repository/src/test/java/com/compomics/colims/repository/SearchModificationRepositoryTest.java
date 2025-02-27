package com.compomics.colims.repository;

import com.compomics.colims.model.SearchModification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:colims-repository-context.xml", "classpath:colims-repository-test-context.xml"})
@Transactional
public class SearchModificationRepositoryTest {

    @Autowired
    private SearchModificationRepository searchModificationRepository;

    @Test
    public void testFindByName() {
        //try to find a non existing search modification
        SearchModification searchModification = searchModificationRepository.findByName("nonexisting");

        Assert.assertNull(searchModification);

        //find an existing search modification
        searchModification = searchModificationRepository.findByName("monohydroxylated residue");

        Assert.assertNotNull(searchModification);
        //check the ID
        Assert.assertNotNull(searchModification.getId());
    }

    @Test
    public void testFindByAccession() {
        //try to find a non existing search modification
        SearchModification searchModification = searchModificationRepository.findByAccession("nonexisting");

        Assert.assertNull(searchModification);

        //find an existing search modification
        searchModification = searchModificationRepository.findByAccession("MOD:00425");

        Assert.assertNotNull(searchModification);
        //check the ID
        Assert.assertNotNull(searchModification.getId());
    }

    @Test
    public void testFindByUtilitiesPtmName() {
        //try to find a non existing search modification
        SearchModification searchModification = searchModificationRepository.findByUtilitiesPtmName("nonexisting");

        Assert.assertNull(searchModification);

        //find an existing modification
        searchModification = searchModificationRepository.findByUtilitiesPtmName("test_utilities_name");

        Assert.assertNotNull(searchModification);
        //check the ID
        Assert.assertNotNull(searchModification.getId());
    }

    @Test
    public void GetConstraintLessSearchModificationIdsForRuns() {
        List<Long> searchParametersIds = new ArrayList<>();
        searchParametersIds.add(1L);

        List<Long> searchModificationIds = searchModificationRepository.getConstraintLessSearchModIdsForSearchParams(searchParametersIds);

        //2 search modifications linked to 3 search parameters entries, 1 search modification is linked to a single search parameters entry.
        Assert.assertEquals(1, searchModificationIds.size());
        Assert.assertEquals(2L, searchModificationIds.get(0).longValue());
    }
}

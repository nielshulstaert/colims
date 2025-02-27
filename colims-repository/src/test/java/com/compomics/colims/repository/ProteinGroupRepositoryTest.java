package com.compomics.colims.repository;

import com.compomics.colims.model.ProteinGroup;
import com.compomics.colims.model.ProteinGroupHasProtein;
import com.compomics.colims.repository.hibernate.ProteinGroupDTO;
import com.compomics.colims.repository.hibernate.SortDirection;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Niels Hulstaert
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:colims-repository-context.xml", "classpath:colims-repository-test-context.xml"})
@Transactional
@Rollback
public class ProteinGroupRepositoryTest {

    @Autowired
    private ProteinGroupRepository proteinGroupRepository;

    @Test
    public void testGetPagedProteinsForRunTest() {
        List<Long> analyticalRunIds = new ArrayList<>();
        analyticalRunIds.add(1L);

        //test normal usage
        List<ProteinGroupDTO> proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "");
        Assert.assertEquals(2, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(1L), proteinGroupDTOs.get(0).getId());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(1).getId());

        //test descending sort order
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.DESCENDING, "");
        Assert.assertEquals(2, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(0).getId());
        Assert.assertEquals(Long.valueOf(1L), proteinGroupDTOs.get(1).getId());

        //test paging 1
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 1, "id", SortDirection.ASCENDING, "");
        Assert.assertEquals(1, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(1L), proteinGroupDTOs.get(0).getId());

        //test paging 2
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 1, 20, "id", SortDirection.ASCENDING, "");
        Assert.assertEquals(1, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(0).getId());

        //test filter by accession 1
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "ACC_2");
        Assert.assertEquals(1, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(0).getId());

        //test filter by accession 2, same but lower case
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "acc_2");
        Assert.assertEquals(1, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(0).getId());

        //test filter by accession 4, filter by non main protein accession, should return nothing
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "ACC_3");
        Assert.assertTrue(proteinGroupDTOs.isEmpty());

        //test filter by accession 4, non existing accession, should return nothing
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "AC_3");
        Assert.assertTrue(proteinGroupDTOs.isEmpty());

        //test filter by sequence 1
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "LENNART");
        Assert.assertEquals(2, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(1L), proteinGroupDTOs.get(0).getId());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(1).getId());

        //test filter by sequence 2, filter by non main protein sequence, should return nothing
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "AMA");
        Assert.assertEquals(1, proteinGroupDTOs.size());
        Assert.assertEquals(Long.valueOf(2L), proteinGroupDTOs.get(0).getId());

        //test filter by sequence 3, non existing accession, should return nothing
        proteinGroupDTOs = proteinGroupRepository.getPagedProteinGroups(analyticalRunIds, 0, 20, "id", SortDirection.ASCENDING, "NOTAPROTEINSEQUENCE");
        Assert.assertTrue(proteinGroupDTOs.isEmpty());
    }

    @Test
    public void testGetProteinGroupCountForRun() {
        List<Long> analyticalRunIds = new ArrayList<>();
        analyticalRunIds.add(1L);

        long proteinGroupCountForRun = proteinGroupRepository.getProteinGroupCount(analyticalRunIds, "");
        assertThat(proteinGroupCountForRun, is(2L));

        proteinGroupCountForRun = proteinGroupRepository.getProteinGroupCount(analyticalRunIds, "ACC_11");
        assertThat(proteinGroupCountForRun, is(1L));

        proteinGroupCountForRun = proteinGroupRepository.getProteinGroupCount(analyticalRunIds, "LENNART");
        assertThat(proteinGroupCountForRun, is(2L));

        proteinGroupCountForRun = proteinGroupRepository.getProteinGroupCount(analyticalRunIds, "AC_11");
        assertThat(proteinGroupCountForRun, is(0L));

        proteinGroupCountForRun = proteinGroupRepository.getProteinGroupCount(analyticalRunIds, "NOTAPROTEIN");
        assertThat(proteinGroupCountForRun, is(0L));
    }

    @Test
    public void testGetProteinGroupsForRuns() {
        List<Long> analyticalRunIds = new ArrayList<>();
        analyticalRunIds.add(1L);

        List<ProteinGroup> proteinGroupsForRuns = proteinGroupRepository.getProteinGroups(analyticalRunIds);

        Assert.assertEquals(2, proteinGroupsForRuns.size());
    }

    @Test
    public void testGetMainProteinGroupHasProtein() {
        ProteinGroupHasProtein proteinGroupHasProtein = proteinGroupRepository.getMainProteinGroupHasProtein(2L);

        Assert.assertNotNull(proteinGroupHasProtein);
        Assert.assertTrue(proteinGroupHasProtein.getIsMainGroupProtein());
        Assert.assertNotNull(proteinGroupHasProtein.getProtein());
    }

    @Test
    public void testGetConstraintLessProteinGroupIdsForRunsTest() {
        List<Long> runIds = new ArrayList<>();
        runIds.add(1L);

        List<Long> proteinGroupIds = proteinGroupRepository.getConstraintLessProteinGroupIdsForRuns(runIds);

        //3 ProteinGroup entries, 2 linked to run 1, one linked to run 2 and 3.
        //2 ProteinGroup entries linked only to run 1.
        Assert.assertEquals(2, proteinGroupIds.size());

        runIds.add(2L);
        proteinGroupIds = proteinGroupRepository.getConstraintLessProteinGroupIdsForRuns(runIds);
        //2 ProteinGroup entries linked only to run 1 and 2.
        Assert.assertEquals(2, proteinGroupIds.size());

        runIds.add(3L);
        proteinGroupIds = proteinGroupRepository.getConstraintLessProteinGroupIdsForRuns(runIds);
        //All the 3 entries are linked to run 1, 2 or 3.
        Assert.assertEquals(3, proteinGroupIds.size());

        runIds.remove(0);
        //Only ProteinGroup entry is linked only to run 2 or 3.
        proteinGroupIds = proteinGroupRepository.getConstraintLessProteinGroupIdsForRuns(runIds);
        Assert.assertEquals(1, proteinGroupIds.size());

        runIds.remove(0);
        //No ProteinGroup entry is only linked to run 3.
        proteinGroupIds = proteinGroupRepository.getConstraintLessProteinGroupIdsForRuns(runIds);
        Assert.assertEquals(0, proteinGroupIds.size());
    }

//    @Test
//    public void testGetProteinGroupsProjections(){
//        AnalyticalRun analyticalRun = analyticalRunRepository.findById(1L);
//
//        Object[] proteinGroupsProjections = proteinGroupRepository.getProteinGroupsProjections(analyticalRun);
//    }
//    @Test
//    public void testHibernateSearchFindBySequence() {
//        //(re)build the lucene indexes
//        proteinGroupRepository.rebuildIndex();
//
//        Protein foundProtein = proteinGroupRepository.hibernateSearchFindBySequence("MGDERPHYYGKHGTPQKYDPTFKG");
//        Assert.assertNotNull(foundProtein);
//        Assert.assertEquals("MGDERPHYYGKHGTPQKYDPTFKG", foundProtein.getSequence());
//    }
}

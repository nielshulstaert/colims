package com.compomics.colims.repository.impl;

import com.compomics.colims.model.Peptide;
import com.compomics.colims.model.PeptideHasModification;
import com.compomics.colims.model.PeptideHasProteinGroup;
import com.compomics.colims.repository.PeptideRepository;
import com.compomics.colims.repository.hibernate.PeptideDTO;
import com.compomics.colims.repository.hibernate.PeptideMzTabDTO;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * This interface provides repository methods for the Peptide class.
 *
 * @author Iain
 */
@Repository("peptideRepository")
@Transactional
public class PeptideHibernateRepository extends GenericHibernateRepository<Peptide, Long> implements PeptideRepository {

    @Override
    public List<PeptideDTO> getPeptideDTOs(Long proteinGroupId, List<Long> analyticalRunIds) {
        Query query = getCurrentSession().getNamedQuery("Peptide.getPeptideDTOsByProteinGroupId");
        query.setLong("proteinGroupId", proteinGroupId);
        query.setParameterList("analyticalRunIds", analyticalRunIds);

        List list = query.list();

        List<PeptideDTO> peptideDTOs = new ArrayList<>(list.size());
        //keep track of peptide IDs to only keep the distinct ones
        Set<Long> peptideIds = new HashSet<>();
        for (Object object : list) {
            Object[] objectArray = (Object[]) object;
            Peptide peptide = (Peptide) objectArray[0];
            if (!peptideIds.contains(peptide.getId())) {
                peptideIds.add(peptide.getId());

                PeptideDTO peptideDTO = new PeptideDTO();
                peptideDTO.setPeptide(peptide);
                peptideDTO.setPeptideProbability((Double) objectArray[1]);
                peptideDTO.setPeptidePostErrorProbability((Double) objectArray[2]);

                //get the number of protein groups associated with the given peptide
                //and set it in the PeptideDTO instance
                Criteria criteria = getCurrentSession().createCriteria(PeptideHasProteinGroup.class, "peptideHasProteinGroup");
                criteria.add(Restrictions.eq("peptideHasProteinGroup.peptide.id", peptideDTO.getPeptide().getId()));
                ProjectionList projectionList = Projections.projectionList();

                projectionList.add(Projections.countDistinct("peptideHasProteinGroup.proteinGroup.id").as("proteinGroupCount"));
                criteria.setProjection(projectionList);
                long proteinGroupCount = (long) criteria.uniqueResult();
                peptideDTO.setProteinGroupCount(proteinGroupCount);

                peptideDTOs.add(peptideDTO);
            }
        }

        return peptideDTOs;
    }

    @Override
    public List<PeptideHasModification> fetchPeptideHasModifications(Long peptideId) {
        Criteria criteria = getCurrentSession().createCriteria(PeptideHasModification.class);

        criteria.add(Restrictions.eq("peptide.id", peptideId));

        return criteria.list();
    }

    @Override
    public List<String> getDistinctPeptideSequences(Long proteinGroupId, List<Long> analyticalRunIds) {
        Query query = getCurrentSession().getNamedQuery("Peptide.getDistinctPeptideSequencesByProteinGroupIdAndRunIds");

        query.setLong("proteinGroupId", proteinGroupId);
        query.setParameterList("analyticalRunIds", analyticalRunIds);

        return query.list();
    }

    @Override
    public List<Peptide> getUniquePeptides(Long proteinGroupId, List<Long> analyticalRunIds) {
        List<Peptide> peptides = new ArrayList<>();
        List<PeptideDTO> peptideDTOs = getPeptideDTOs(proteinGroupId, analyticalRunIds);
        peptideDTOs.forEach(peptideDto -> {
            Criteria criteria = getCurrentSession().createCriteria(PeptideHasProteinGroup.class);
            criteria.add(Restrictions.eq("peptide.id", peptideDto.getPeptide().getId()));
            if (criteria.list().size() == 1) {
                peptides.add(peptideDto.getPeptide());
            }
        });

        return peptides;
    }

    @Override
    public List<PeptideMzTabDTO> getPeptideMzTabDTOs(List<Long> analyticalRunIds) {
        Query query = getCurrentSession().getNamedQuery("Peptide.getPeptideMzTabDTOsByRunIds");
        query.setParameterList("analyticalRunIds", analyticalRunIds);

        List list = query.list();

        List<PeptideMzTabDTO> peptideMzTabDTOs = new ArrayList();
        for (Object object : list) {
            Object[] objectArray = (Object[]) object;

            PeptideMzTabDTO peptideMzTabDTO = new PeptideMzTabDTO((Peptide) objectArray[0], (Long) objectArray[2], (Long) objectArray[1]);
            peptideMzTabDTOs.add(peptideMzTabDTO);
        }

        return peptideMzTabDTOs;
    }

}

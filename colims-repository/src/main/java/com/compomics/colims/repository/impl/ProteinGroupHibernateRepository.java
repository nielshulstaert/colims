package com.compomics.colims.repository.impl;

import com.compomics.colims.model.Protein;
import com.compomics.colims.model.ProteinGroup;
import com.compomics.colims.model.ProteinGroupHasProtein;
import com.compomics.colims.repository.ProteinGroupRepository;
import com.compomics.colims.repository.ProteinRepository;
import com.compomics.colims.repository.hibernate.ProteinGroupDTO;
import com.compomics.colims.repository.hibernate.SortDirection;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Niels Hulstaert
 */
@Repository("proteinGroupRepository")
public class ProteinGroupHibernateRepository extends GenericHibernateRepository<ProteinGroup, Long> implements ProteinGroupRepository {
    
    @Autowired
    private ProteinRepository proteinRepository;
    
    @Override
    public List<ProteinGroupDTO> getPagedProteinGroups(List<Long> analyticalRunIds, int start, int length, String orderBy, SortDirection sortDirection, String filter) {
        
        Criteria criteria = getCurrentSession().createCriteria(ProteinGroup.class, "proteinGroup");

        //joins
        criteria.createAlias("peptide.spectrum", "spectrum");
        criteria.createAlias("peptideHasProteinGroup.peptide", "peptide");
        criteria.createAlias("proteinGroup.peptideHasProteinGroups", "peptideHasProteinGroup");
        criteria.createAlias("proteinGroup.proteinGroupHasProteins", "proteinGroupHasProtein");
        if (!filter.isEmpty()){
            criteria.createAlias("proteinGroupHasProtein.protein", "protein");
        }

        //restrictions
        criteria.add(Restrictions.in("spectrum.analyticalRun.id", analyticalRunIds));
        criteria.add(Restrictions.eq("proteinGroupHasProtein.isMainGroupProtein", true));

        //projections
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("id").as("id"));
        projectionList.add(Projections.count("spectrum.id").as("spectrumCount"));
        projectionList.add(Projections.countDistinct("peptide.sequence").as("distinctPeptideSequenceCount"));
        projectionList.add(Projections.property("proteinGroup.proteinProbability").as("proteinProbability"));
        projectionList.add(Projections.property("proteinGroup.proteinPostErrorProbability").as("proteinPostErrorProbability"));
        projectionList.add(Projections.property("proteinGroupHasProtein.proteinAccession").as("mainAccession"));
        projectionList.add(Projections.property("proteinGroupHasProtein.protein.id").as("proteinId"));
        if (!filter.isEmpty()) {
            projectionList.add(Projections.property("protein.sequence").as("mainSequence"));
        }

        criteria.setProjection(projectionList);

        //paging
        criteria.setFirstResult(start);
        criteria.setMaxResults(length);

        //transform results into ProteinGroupForRun instances
        criteria.setResultTransformer(Transformers.aliasToBean(ProteinGroupDTO.class));

        //order
        if (!orderBy.isEmpty() && !orderBy.equals("mainSequence")) {
            if (sortDirection.equals(SortDirection.ASCENDING)) {
                criteria.addOrder(Order.asc(orderBy));
            } else {
                criteria.addOrder(Order.desc(orderBy));
            }
        }

        //filter by only protein accession

        if (!filter.isEmpty()) {
            //filter restrictions
            filter = "%" + filter + "%";
            criteria.add(Restrictions.or(Restrictions.ilike("protein.sequence", filter), Restrictions.ilike("proteinGroupHasProtein.proteinAccession", filter)));
        }

        List<ProteinGroupDTO> proteinGroupDTOs =  criteria.list();
        if(filter.isEmpty()){
            proteinGroupDTOs.forEach(proteinGroupDTO ->{
                proteinGroupDTO.setMainSequence(proteinRepository.findById(proteinGroupDTO.getProteinId()).getSequence());
            });
        }
        
        return proteinGroupDTOs;
    }
    
    @Override
    public long getProteinGroupCount(List<Long> analyticalRunIds, String filter) {
        Criteria criteria = getCurrentSession().createCriteria(ProteinGroup.class, "proteinGroup");

        //joins
        criteria.createAlias("proteinGroup.peptideHasProteinGroups", "peptideHasProteinGroup");
        criteria.createAlias("peptideHasProteinGroup.peptide", "peptide");
        criteria.createAlias("peptide.spectrum", "spectrum");

        //restrictions
        criteria.add(Restrictions.in("spectrum.analyticalRun.id", analyticalRunIds));

        //projections
        criteria.setProjection(Projections.countDistinct("id"));

        //filter
        if (!filter.isEmpty()) {
            //joins
            criteria.createAlias("proteinGroup.proteinGroupHasProteins", "proteinGroupHasProtein");
            criteria.createAlias("proteinGroupHasProtein.protein", "protein");

            //filter restrictions
            filter = "%" + filter + "%";
            criteria.add(Restrictions.or(Restrictions.ilike("protein.sequence", filter), Restrictions.ilike("proteinGroupHasProtein.proteinAccession", filter)));
        }

        return (long) criteria.uniqueResult();
    }

    @Override
    public void saveOrUpdate(ProteinGroup proteinGroup) {
        getCurrentSession().saveOrUpdate(proteinGroup);
    }

    @Override
    public List<ProteinGroupDTO> getProteinGroupDTOs(List<Long> analyticalRunIds) {
        Criteria criteria = getCurrentSession().createCriteria(ProteinGroup.class, "proteinGroup");

        //joins
        criteria.createAlias("peptide.spectrum", "spectrum");
        criteria.createAlias("peptideHasProteinGroup.peptide", "peptide");
        criteria.createAlias("proteinGroup.peptideHasProteinGroups", "peptideHasProteinGroup");
        criteria.createAlias("proteinGroup.proteinGroupHasProteins", "proteinGroupHasProtein");
        criteria.createAlias("proteinGroupHasProtein.protein", "protein");

        //restrictions
        criteria.add(Restrictions.in("spectrum.analyticalRun.id", analyticalRunIds));
        criteria.add(Restrictions.eq("proteinGroupHasProtein.isMainGroupProtein", true));

        //projections
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.groupProperty("id").as("id"));
        projectionList.add(Projections.count("spectrum.id").as("spectrumCount"));
        projectionList.add(Projections.countDistinct("peptide.sequence").as("distinctPeptideSequenceCount"));
        projectionList.add(Projections.property("proteinGroup.proteinProbability").as("proteinProbability"));
        projectionList.add(Projections.property("proteinGroup.proteinPostErrorProbability").as("proteinPostErrorProbability"));
        projectionList.add(Projections.property("proteinGroupHasProtein.proteinAccession").as("mainAccession"));
        projectionList.add(Projections.property("protein.sequence").as("mainSequence"));
        projectionList.add(Projections.property("protein.id").as("mainId"));
        criteria.setProjection(projectionList);

        //transform results into ProteinGroupDTO instances
        criteria.setResultTransformer(Transformers.aliasToBean(ProteinGroupDTO.class));

        return criteria.list();
    }

    @Override
    public List<ProteinGroup> getProteinGroups(List<Long> analyticalRunIds) {
        Query query = getCurrentSession().getNamedQuery("ProteinGroup.getProteinGroupsByRunIds");
        query.setParameterList("analyticalRunIds", analyticalRunIds);

        return query.list();
    }

    @Override
    public List<ProteinGroupHasProtein> getAmbiguityMembers(Long proteinGroupId) {
        Criteria criteria = getCurrentSession().createCriteria(ProteinGroupHasProtein.class);

        criteria.add(Restrictions.eq("proteinGroup.id", proteinGroupId));
        criteria.add(Restrictions.eq("isMainGroupProtein", false));

        return criteria.list();
    }

    @Override
    public ProteinGroupHasProtein getMainProteinGroupHasProtein(Long proteinGroupId) {
        ProteinGroupHasProtein proteinGroupHasProtein = null;

        Criteria criteria = getCurrentSession().createCriteria(ProteinGroupHasProtein.class);

        //join to fetch the protein group
        criteria.createAlias("protein", "protein");

        criteria.add(Restrictions.eq("proteinGroup.id", proteinGroupId));
        criteria.add(Restrictions.eq("isMainGroupProtein", true));

        List<ProteinGroupHasProtein> results = criteria.list();
        if (!results.isEmpty()) {
            proteinGroupHasProtein = results.get(0);
        }

        return proteinGroupHasProtein;
    }

    @Override
    public List<Long> getConstraintLessProteinGroupIdsForRuns(List<Long> analyticalRunIds) {
        SQLQuery sqlQuery = (SQLQuery) getCurrentSession().getNamedQuery("ProteinGroup.getConstraintLessProteinGroupIdsForRuns");
        sqlQuery.setParameterList("runIds", analyticalRunIds);
        sqlQuery.addScalar("protein_group.id", LongType.INSTANCE);

        return sqlQuery.list();
    }
}

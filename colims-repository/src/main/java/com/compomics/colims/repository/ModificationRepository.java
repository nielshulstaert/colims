package com.compomics.colims.repository;

import com.compomics.colims.model.Modification;

import java.util.List;

/**
 * This interface provides repository methods for the Modification class.
 *
 * @author Niels Hulstaert
 */
public interface ModificationRepository extends GenericRepository<Modification, Long> {

    /**
     * Find a modification by the modification name. Returns the first modification found, null if none were found.
     *
     * @param name the modification name
     * @return the found modification
     */
    Modification findByName(String name);

    /**
     * Find a modification by the modification accession. Returns null if nothing was found.
     *
     * @param accession the modification accession
     * @return the found modification
     */
    Modification findByAccession(String accession);

    /**
     * Find a modification by the Utilities PTM name. Returns the first modification found, null if none were found.
     *
     * @param utilitiesPtmName the utilities PTM name
     * @return the found modification
     */
    Modification findByUtilitiesPtmName(String utilitiesPtmName);

    /**
     * Get the IDs of the modifications that are only related to the given runs.
     *
     * @param analyticalRunIds the list of analytical run IDs
     * @return the list of protein IDs
     */
    List<Long> getConstraintLessModificationIdsForRuns(List<Long> analyticalRunIds);
    
}

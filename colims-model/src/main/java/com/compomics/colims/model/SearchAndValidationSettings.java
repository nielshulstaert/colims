package com.compomics.colims.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Niels Hulstaert
 */
@Table(name = "search_and_validation_settings")
@Entity
public class SearchAndValidationSettings extends AuditableDatabaseEntity {

    private static final long serialVersionUID = 3229983473906664007L;

    /**
     * The analytical run onto which the searches were performed.
     */
    @JoinColumn(name = "l_analytical_run_id", referencedColumnName = "id")
    @OneToOne
    private AnalyticalRun analyticalRun;
    /**
     * The search engine used for the searches.
     */
    @JoinColumn(name = "l_search_engine_id", referencedColumnName = "id")
    @ManyToOne(cascade = CascadeType.MERGE)
    private SearchEngine searchEngine;
    /**
     * The search parameters.
     */
    @JoinColumn(name = "l_search_parameters_id", referencedColumnName = "id")
    @ManyToOne
    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private SearchParameters searchParameters;
    /**
     * The SearchSettingsHasFastaDb instances from the join table between the search and validation settings and FASTA
     * databases.
     */
    @OneToMany(mappedBy = "searchAndValidationSettings", cascade = CascadeType.ALL)
    private List<SearchSettingsHasFastaDb> searchSettingsHasFastaDbs = new ArrayList<>();

    public AnalyticalRun getAnalyticalRun() {
        return analyticalRun;
    }

    public void setAnalyticalRun(AnalyticalRun analyticalRun) {
        this.analyticalRun = analyticalRun;
    }

    public SearchEngine getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(SearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    public SearchParameters getSearchParameters() {
        return searchParameters;
    }

    public void setSearchParameters(SearchParameters searchParameters) {
        this.searchParameters = searchParameters;
    }

    public List<SearchSettingsHasFastaDb> getSearchSettingsHasFastaDbs() {
        return searchSettingsHasFastaDbs;
    }

    public void setSearchSettingsHasFastaDbs(List<SearchSettingsHasFastaDb> searchSettingsHasFastaDbs) {
        this.searchSettingsHasFastaDbs = searchSettingsHasFastaDbs;
    }

}

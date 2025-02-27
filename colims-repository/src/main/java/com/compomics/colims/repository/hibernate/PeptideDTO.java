package com.compomics.colims.repository.hibernate;

import com.compomics.colims.model.Peptide;

/**
 * This class represents a peptide data transfer object that holds some
 * additional information about a peptide.
 * <p/>
 * Created by Niels Hulstaert on 14/10/15.
 */
public class PeptideDTO {

    /**
     * The peptide probability score.
     */
    private Double peptideProbability;
    /**
     * The peptide posterior error probability score.
     */
    private Double peptidePostErrorProbability;
    /**
     * The Peptide entity instance.
     */
    private Peptide peptide;
    /**
     * The number of protein groups associated with this peptide.
     */
    private long proteinGroupCount;

    /**
     * No-arg constructor.
     */
    public PeptideDTO() {
    }

    public Double getPeptideProbability() {
        return peptideProbability;
    }

    public void setPeptideProbability(Double peptideProbability) {
        this.peptideProbability = peptideProbability;
    }

    public Double getPeptidePostErrorProbability() {
        return peptidePostErrorProbability;
    }

    public void setPeptidePostErrorProbability(Double peptidePostErrorProbability) {
        this.peptidePostErrorProbability = peptidePostErrorProbability;
    }

    public Peptide getPeptide() {
        return peptide;
    }

    public void setPeptide(Peptide peptide) {
        this.peptide = peptide;
    }

    public long getProteinGroupCount() {
        return proteinGroupCount;
    }

    public void setProteinGroupCount(long proteinGroupCount) {
        this.proteinGroupCount = proteinGroupCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.peptide.getTheoreticalMass().hashCode();
        hash = 67 * hash + (int) (this.proteinGroupCount ^ (this.proteinGroupCount >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PeptideDTO other = (PeptideDTO) obj;
        if (this.proteinGroupCount != other.proteinGroupCount) {
            return false;
        }
        return peptide.representsSamePeptide(other.getPeptide());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        PeptideDTO that = (PeptideDTO) o;
//        if (proteinGroupCount != that.proteinGroupCount) {
//            return false;
//        }
//        if (peptideProbability != null ? !CompareUtils.equals(peptideProbability, that.peptideProbability) : that.peptideProbability != null) {
//            return false;
//        }
//        if (peptidePostErrorProbability != null ? !CompareUtils.equals(peptidePostErrorProbability, that.peptidePostErrorProbability) : that.peptidePostErrorProbability != null) {
//            return false;
//        }
//
//        return peptide.representsSamePeptide(that.getPeptide());
//    }
//
//    @Override
//    public int hashCode() {
//        int result = peptideProbability != null ? peptideProbability.hashCode() : 0;
//        result = 31 * result + (peptidePostErrorProbability != null ? peptidePostErrorProbability.hashCode() : 0);
//        result = 31 * result + (int) (proteinGroupCount ^ (proteinGroupCount >>> 32));
//        return result;
//    }

}

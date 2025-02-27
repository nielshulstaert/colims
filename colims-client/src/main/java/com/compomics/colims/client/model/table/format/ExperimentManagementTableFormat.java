package com.compomics.colims.client.model.table.format;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.gui.AdvancedTableFormat;
import com.compomics.colims.model.Experiment;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 *
 * @author Niels Hulstaert
 */
public class ExperimentManagementTableFormat implements AdvancedTableFormat<Experiment> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final String[] COLUMN_NAMES = {"Id", "Title", "Number", "Created", "# samples"};
    private static final String NOT_APPLICABLE = "N/A";
    public static final int EXPERIMENT_ID = 0;
    public static final int TITLE = 1;
    public static final int NUMBER = 2;
    public static final int CREATED = 3;
    public static final int NUMBER_OF_SAMPLES = 4;

    @Override
    public Class getColumnClass(final int column) {
        switch (column) {
            case EXPERIMENT_ID:
                return Long.class;
            case TITLE:
                return String.class;
            case NUMBER:
                return Long.class;
            case CREATED:
                return String.class;    
            case NUMBER_OF_SAMPLES:
                return Integer.class;
            default:
                throw new IllegalArgumentException("Unexpected column number " + column);
        }
    }

    @Override
    public Comparator getColumnComparator(final int column) {
        return GlazedLists.comparableComparator();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public String getColumnName(final int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getColumnValue(final Experiment experiment, final int column) {
        switch (column) {
            case EXPERIMENT_ID:
                return experiment.getId();
            case TITLE:
                return experiment.getTitle();
            case NUMBER:
                return experiment.getNumber();
            case CREATED:
                return DATE_FORMAT.format(experiment.getCreationDate());    
            case NUMBER_OF_SAMPLES:
                return experiment.getSamples().size();
            default:
                throw new IllegalArgumentException("Unexpected column number " + column);
        }
    }
}
package com.compomics.colims.core.io.mztab.enums;

/**
 * The mzTab mode enum.
 * <p/>
 * Created by niels on 6/03/15.
 */
public enum MzTabMode {

    /**
     * Used to report  final results (e.g. quantification data at the level of study variables).
     */
    SUMMARY("Summary"),
    /**
     * Used if all quantification data is provided (e.g. quantification on the assay level and on the study variable
     * level).
     */
    COMPLETE("Complete");

    /**
     * How the enum will be shown in the GUI and in the mzTab file.
     */
    private final String mzTabName;

    /**
     * Constructor.
     *
     * @param mzTabName the enum name as shown in the GUI and in the mzTab file.
     */
    MzTabMode(String mzTabName) {
        this.mzTabName = mzTabName;
    }

    public String mzTabName() {
        return mzTabName;
    }

    /**
     * Get the MzTabMode enum by its mzTab name. Return null if no
     * enum value could be matched.
     *
     * @param mzTabName the mzTab name value
     * @return the MzTabType enum value
     */
    public static MzTabMode getByMzTabName(final String mzTabName) {
        MzTabMode foundMzTabMode = null;

        //iterate over enum values
        for (MzTabMode mzTabMode : values()) {
            if (mzTabMode.mzTabName().equals(mzTabName)) {
                foundMzTabMode = mzTabMode;
            }
        }

        return foundMzTabMode;
    }
}

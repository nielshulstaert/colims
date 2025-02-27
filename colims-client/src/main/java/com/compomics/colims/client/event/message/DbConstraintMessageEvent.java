package com.compomics.colims.client.event.message;

import javax.swing.JOptionPane;

/**
 *
 * @author Niels Hulstaert
 */
public class DbConstraintMessageEvent extends MessageEvent {

    private static final String CONSTRAINT_MESSAGE = "The entry '%s' can't be deleted because of a database constraint;"
            + System.lineSeparator() + "it is being used in a relation between the '%s' database table and another one."
            + System.lineSeparator() + "Remove any existing relations between this entry and other entries and try again.";

    /**
     * Constructor.
     *
     * @param className the entity class name
     * @param entityName the entity name
     */
    public DbConstraintMessageEvent(final String className, final String entityName) {
        super("database constraint violation", String.format(CONSTRAINT_MESSAGE, entityName, className), JOptionPane.WARNING_MESSAGE);
    }
}

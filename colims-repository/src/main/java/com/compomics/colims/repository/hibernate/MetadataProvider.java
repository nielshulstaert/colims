package com.compomics.colims.repository.hibernate;

import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.spi.SessionFactoryBuilderFactory;
import org.hibernate.boot.spi.SessionFactoryBuilderImplementor;

/**
 * Created by Niels Hulstaert on 11/05/16.
 */
public class MetadataProvider implements SessionFactoryBuilderFactory {

    private static MetadataImplementor metadata;

    @Override
    public SessionFactoryBuilder getSessionFactoryBuilder(MetadataImplementor metadata, SessionFactoryBuilderImplementor defaultBuilder) {
        MetadataProvider.metadata = metadata;
        return defaultBuilder;
    }

    public static MetadataImplementor getMetadata() {
        return metadata;
    }

}

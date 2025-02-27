package com.compomics.colims.distributed.io.unimod;

import com.compomics.colims.model.AbstractModification;
import com.compomics.colims.model.SearchModification;
import org.slf4j.Logger;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class is used to marshall and access the UNIMOD modifications from the unimod.xml file.
 * <p/>
 * Created by niels on 5/02/15.
 */
@Component("unimodMarshaller")
public class UnimodMarshaller {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UnimodMarshaller.class);

    private static final Namespace NAMESPACE = Namespace.getNamespace("http://www.unimod.org/xmlns/schema/unimod_2");
    private static final String UNIMOD_ACCESSION = "UNIMOD:%d";

    /**
     * This maps holds the modifications from the parsed UNIMOD .xml file. This map can contains instances of {@link
     * com.compomics.colims.model.Modification} and {@link com.compomics.colims.model.SearchModification}. The key is
     * UNIMOD accession.
     */
    private final java.util.Map<String, AbstractModification> modifications = new HashMap<>();

    public Map<String, AbstractModification> getModifications() {
        return modifications;
    }

    /**
     * Get the modification by accession. Returns null if nothing was found.
     *
     * @param clazz     the AbstractModification subclass (Modification or SearchModification)
     * @param accession the modification accession
     * @param <T>       AbstractModification subclass
     * @return the found search modification, null if nothing was found
     */
    public <T extends AbstractModification> T getModificationByAccession(Class<T> clazz, String accession) {
        T modification = null;

        if (modifications.containsKey(accession)) {
            AbstractModification foundModification = modifications.get(accession);
            if (clazz.isInstance(foundModification)) {
                modification = (T) foundModification;
            } else {
                modification = copyModification(clazz, foundModification);
            }
            // set ID to null to avoid database issues
            modification.setId(null);
        }

        return modification;
    }

    /**
     * Get the modification by name. Returns null if nothing was found.
     *
     * @param clazz the AbstractModification subclass (Modification or SearchModification)
     * @param name  the modification name
     * @param <T>   AbstractModification subclass
     * @return the found search modification, null if nothing was found
     */
    public <T extends AbstractModification> T getModificationByName(Class<T> clazz, String name) {
        T modification = null;

        Optional<AbstractModification> foundModification = modifications
                .values()
                .stream()
                .filter(abstractModification -> abstractModification.getName().equals(name))
                .findFirst();

        if (foundModification.isPresent()) {
            AbstractModification abstractModification = foundModification.get();
            if (clazz.isInstance(abstractModification)) {
                modification = (T) abstractModification;
            } else {
                modification = copyModification(clazz, abstractModification);
            }
            // set ID to null to avoid database issues
            modification.setId(null);
        }

        return modification;
    }

    /**
     * This method marshals the UNIMOD file and puts all modifications in the map for later usage.
     *
     * @throws JDOMException top level exception that can be thrown in case of a problem in the JDOM classes.
     * @throws IOException   in case of a problem with the unimod.xml recource
     */
    @PostConstruct
    private void marshal() throws JDOMException, IOException {
        Resource unimodResource = new ClassPathResource("unimod/unimod.xml");
        SAXBuilder builder = new SAXBuilder();

        Document document;
        document = builder.build(unimodResource.getInputStream());

        Element root = document.getRootElement();
        //get the modifications element
        Element modificationsElement = root.getChild("modifications", NAMESPACE);

        for (Element modificationElement : modificationsElement.getChildren("mod", NAMESPACE)) {
            //get the mod name and record ID
            Attribute title = modificationElement.getAttribute("title");
            Attribute record = modificationElement.getAttribute("record_id");

            SearchModification searchModification = new SearchModification(title.getValue());
            //set the accession
            searchModification.setAccession(String.format(UNIMOD_ACCESSION, record.getIntValue()));

            //get the mono isotopic and average mass shift
            Element delta = modificationElement.getChild("delta", NAMESPACE);
            Attribute monoIsotopicMassShift = delta.getAttribute("mono_mass");
            Attribute averageMassShift = delta.getAttribute("avge_mass");

            searchModification.setMonoIsotopicMassShift(monoIsotopicMassShift.getDoubleValue());
            searchModification.setAverageMassShift(averageMassShift.getDoubleValue());

            modifications.put(searchModification.getAccession(), searchModification);
        }

    }

    /**
     * Copy (the instance fields of) the modification from one subclass of AbstractModification to another.
     *
     * @param clazz     the subclass of AbstractModification
     * @param modToCopy the modification to copy
     * @param <T>       the AbstractModification subclass
     * @return the copied modification
     */
    private <T extends AbstractModification> T copyModification(Class<T> clazz, AbstractModification modToCopy) {
        T modification = null;

        try {
            modification = clazz.newInstance();
            modification.setAccession(modToCopy.getAccession());
            modification.setName(modToCopy.getName());
            if (modToCopy.getMonoIsotopicMassShift() != null) {
                modification.setMonoIsotopicMassShift(modToCopy.getMonoIsotopicMassShift());
            }
            if (modToCopy.getAverageMassShift() != null) {
                modification.setAverageMassShift(modToCopy.getAverageMassShift());
            }
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return modification;
    }
}

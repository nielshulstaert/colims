/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.colims.core.io.colims_to_utilities;

import com.compomics.colims.model.Spectrum;
import com.compomics.colims.model.SpectrumFile;
import com.compomics.colims.model.enums.FragmentationType;
import com.compomics.util.experiment.massspectrometry.Charge;
import com.compomics.util.experiment.massspectrometry.MSnSpectrum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.nio.file.Files;

/**
 * @author Kenneth Verheggen
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:colims-core-context.xml", "classpath:colims-core-test-context.xml"})
public class ColimsSpectrumMapperTest {

    @Autowired
    private ColimsSpectrumMapper colimsSpectrumMapper;

    /**
     * Test of map method, of class ColimsSpectrumMapper.
     *
     * @throws java.lang.Exception in case of an exception
     */
    @Test
    public void testMap() throws Exception {
        Spectrum spectrum = new Spectrum();

        spectrum.setAccession("fake spectrum 1");
        spectrum.setFragmentationType(FragmentationType.CID);
        spectrum.setIntensity(1233123.32);
        spectrum.setRetentionTime(2123.1);
        spectrum.setMzRatio(123.12);
        spectrum.setCharge(3);
        spectrum.setScanNumber(12L);
        spectrum.setScanTime(11231.1);

        File inputSpectra = new ClassPathResource("data/input_spectra_Z.mgf.gz").getFile();
        byte[] data = Files.readAllBytes(inputSpectra.toPath());
        SpectrumFile spectrumFile = new SpectrumFile();
        spectrumFile.setContent(data);
        spectrumFile.setSpectrum(spectrum);
        spectrum.getSpectrumFiles().add(spectrumFile);

        MSnSpectrum targetSpectrum = colimsSpectrumMapper.map(spectrum);
        Assert.assertEquals(677, targetSpectrum.getPeakList().size());
        Assert.assertEquals("12", targetSpectrum.getScanNumber());
        Assert.assertEquals(1233123.32, targetSpectrum.getPrecursor().getIntensity(), 0.01);
        Assert.assertEquals(123.12, targetSpectrum.getPrecursor().getMz(), 0.01);
        Assert.assertEquals(new Charge(1, 3), targetSpectrum.getPrecursor().getPossibleCharges().get(0));
    }
}

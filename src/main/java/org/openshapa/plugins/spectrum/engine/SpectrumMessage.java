package org.openshapa.plugins.spectrum.engine;

import static org.openshapa.plugins.spectrum.SpectrumUtils.findIndices;

import org.gstreamer.Bus;
import org.gstreamer.Message;
import org.gstreamer.Structure;
import org.gstreamer.ValueList;

import org.gstreamer.Bus.MESSAGE;

import org.openshapa.plugins.spectrum.SpectrumConstants;
import org.openshapa.plugins.spectrum.swing.SpectrumView;


/**
 * Listens to messages generated by the gstreamer spectrum plugin.
 */
public class SpectrumMessage implements MESSAGE {

    /** Spectrum viewer. */
    private final SpectrumView spectrumView;

    /** Index values to choose from the spectrum message. */
    private int[] indices;

    /** Calculated frequency values. */
    private double[] freqVals;

    public SpectrumMessage(final SpectrumView sv) {
        spectrumView = sv;
    }

    @Override public void busMessage(final Bus bus, final Message message) {
        Structure msgStruct = message.getStructure();
        String name = msgStruct.getName();

        if ("spectrum".equals(name)) {
            System.out.println("Blah");

            ValueList mags = msgStruct.getValueList("magnitude");

            if (indices == null) {
                indices = findIndices(SpectrumConstants.FFT_BANDS,
                        SpectrumConstants.SPECTRUM_BANDS);
            }

            if (freqVals == null) {

                freqVals = new double[SpectrumConstants.SPECTRUM_BANDS];

                for (int i = 0; i < SpectrumConstants.SPECTRUM_BANDS; i++) {
                    int idx = indices[i];

                    freqVals[i] = (((SpectrumConstants.SAMPLE_RATE / 2) * idx)
                            + (SpectrumConstants.SAMPLE_RATE / 4D))
                        / SpectrumConstants.FFT_BANDS;
                }

                spectrumView.setFreqVals(freqVals);
            }

            final double[] result =
                new double[SpectrumConstants.SPECTRUM_BANDS];

            for (int i = 0; i < SpectrumConstants.SPECTRUM_BANDS; i++) {
                int idx = indices[i];
                result[i] = mags.getFloat(idx);
            }

            spectrumView.setMagnitudelVals(result);

        }
    }

}

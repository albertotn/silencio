package pl.szczepanik.silencio.processors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.StringContains.containsString;

import java.io.StringWriter;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.core.Configuration;
import pl.szczepanik.silencio.core.Execution;
import pl.szczepanik.silencio.core.ProcessorException;
import pl.szczepanik.silencio.decisions.PositiveDecision;
import pl.szczepanik.silencio.mocks.WriterCrashOnWrite;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class XMLProcessorTest extends GenericTest {

    @Test
    public void shouldReturnPassedFormat() {

        // given
        XMLProcessor processor = new XMLProcessor();

        // when
        Format format = processor.getFormat();

        // then
        assertThat(format).isEqualTo(Format.XML);
    }

    @Test
    public void shouldLoadXMLFileOnRealLoad() {

        // given
        XMLProcessor processor = new XMLProcessor();
        input = ResourceLoader.loadXmlAsReader("suv.xml");
        String refInput = ResourceLoader.loadXmlAsString("suv_tranformed.xml");
        output = new StringWriter();

        // when
        processor.load(input);

        // then
        processor.realWrite(output);
        assertThat(refInput).isEqualTo(output.toString());
    }

    @Test
    public void shouldFailWhenLoadingInvalidJSONFile() {

        // given
        Processor processor = new XMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        input = ResourceLoader.loadXmlAsReader("corrupted.xml");

        // when
        processor.setConfiguration(new Configuration(execution));

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(containsString("XML document structures must start and end within the same entity"));
        processor.load(input);
    }

    @Test
    public void shouldFailWhenWrittingToInvalidWriter() {

        final String errorMessage = "Don't write into this writter!";

        // given
        XMLProcessor processor = new XMLProcessor();
        Execution execution = new Execution(new PositiveDecision(), Builder.BLANK);
        processor.setConfiguration(new Configuration(execution));
        input = ResourceLoader.loadXmlAsReader("suv.xml");
        output = new WriterCrashOnWrite(errorMessage);

        // when
        processor.load(input);
        processor.realProcess();

        // then
        thrown.expect(ProcessorException.class);
        thrown.expectMessage(errorMessage);
        processor.realWrite(output);
    }
}
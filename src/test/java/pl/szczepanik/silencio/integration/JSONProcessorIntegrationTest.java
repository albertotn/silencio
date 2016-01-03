package pl.szczepanik.silencio.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;

import org.junit.Test;

import pl.szczepanik.silencio.GenericTest;
import pl.szczepanik.silencio.api.Format;
import pl.szczepanik.silencio.api.Processor;
import pl.szczepanik.silencio.core.Builder;
import pl.szczepanik.silencio.diagnostics.ProcessorSmokeChecker;
import pl.szczepanik.silencio.processors.JSONProcessor;
import pl.szczepanik.silencio.utils.ResourceLoader;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class JSONProcessorIntegrationTest extends GenericTest {

    @Test
    public void shouldProcessJSONFile() {

        // given
        Processor processor = new Builder(Format.JSON).with(Builder.NUMBER_SEQUENCE).build();
        input = ResourceLoader.loadJsonAsReader("suv.json");
        output = new StringWriter();

        // when
        processor.load(input);
        processor.process();

        // then
        processor.write(output);
        String reference = ResourceLoader.loadJsonAsString("suv_Positive_NumberSequence.json");
        assertThat(output.toString()).isEqualTo(reference);
    }

    @Test
    public void shouldNotCrashOnDiagnosticTests() {

        // given
        String content = ResourceLoader.loadJsonAsString("suv.json");
        ProcessorSmokeChecker checker = new ProcessorSmokeChecker(new JSONProcessor());

        // when
        checker.validateWithAllCombinations(content);

        // then
        // no crash
    }
}
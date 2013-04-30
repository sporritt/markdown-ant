package net.sf.markdownant;

import org.jmock.MockObjectTestCase;
import org.apache.tools.ant.BuildException;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

public class FileConverterTest extends MockObjectTestCase {
    private FileConverter fileConverter;
    private static final String BUILD_DIR = "build";

    public void setUp() {
        fileConverter = new FileConverter();
    }

    public void testConvert() throws IOException {
        String input = "Test\n====\n\nHello\n";

        File inputFile = File.createTempFile("convertertest",".markdown");
        FileWriter writer = new FileWriter(inputFile);
        writer.write(input);
        writer.flush();
        writer.close();

        File outputDir = new File(BUILD_DIR);
        fileConverter.convert(inputFile, outputDir);
        String newFileName = inputFile.getName().replaceAll(".markdown", ".html");
        assertTrue(new File(BUILD_DIR, newFileName).exists());
    }

    public void testAcceptOnlyDotMarkdown() throws IOException {
        File inputFile = File.createTempFile("convertertest",".not_markdown");

        try {
            fileConverter.convert(inputFile, new File(BUILD_DIR));
            fail("Should only accept markdown files");
        } catch(BuildException e) {
            //All is good
        }

    }


    public void testHandlesNullFiles() {
        try {
            fileConverter.convert(null, new File(""));
            fail("Should not accept null files");
        } catch (Exception e) {
            // All is good
        }
    }


    public void testHandlesNullOutputDir() {
        try {
            fileConverter.convert(new File(""), null);
            fail("Should not accept null outputDirs");
        } catch (Exception e) {
            // All is good
        }
    }
}

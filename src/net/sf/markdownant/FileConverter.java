package net.sf.markdownant;

import com.petebevin.markdown.MarkdownProcessor;
import org.apache.tools.ant.BuildException;

import java.io.*;

/**
 * Instances of this class is responsible for converting single Markdown files to HTML files. The actual conversion
 * is handled by Pete Bevins MarkdownProcessor.
 */
public class FileConverter {

    private MarkdownProcessor markdownProcessor;
    private String outputExtension;

    public FileConverter(String outputExtension) {
        this.outputExtension = outputExtension;
        markdownProcessor = new MarkdownProcessor();
    }

    /**
     * Converts the input Markdown file to an HTML file with the same name in the output directory.
     * @param inputFile
     * @param outputDir
     */
    public void convert(File inputFile, File outputDir) {        
        handleNulls(inputFile, outputDir);
        checkOutputDir(outputDir);        
        writeContentToFile(convertToString(inputFile), new File(outputDir, getOutputFileName(inputFile)));
    }

    private String convertToString(File inputFile) {
        return markdownProcessor.markdown(readFile(inputFile));
    }

    private void writeContentToFile(String convertedContent, File outputFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
            fileWriter.write(convertedContent);
            fileWriter.close();

        } catch (IOException e) {
            throwWriteException(outputFile, e);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throwWriteException(outputFile, e);
            }
        }
    }

    private void throwWriteException(File outputFile, IOException e) {
        throw new BuildException("Error writing converted content to " + outputFile.getName() + ": " + e);
    }

    private String getOutputFileName(File inputFile) {
        int idx = inputFile.getName().lastIndexOf(".");
        return inputFile.getName().substring(0, idx + 1) + outputExtension; 
    }

    private void checkOutputDir(File outputDir) {
        if (!outputDir.exists()) {
            outputDir.mkdir();
        }
    }

    private String readFile(File inputFile) {
        StringBuffer sb = new StringBuffer();
        String aLine = null;
        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(inputFile));
            while ((aLine = fileReader.readLine()) != null) {
                sb.append(aLine).append("\n");
            }
            fileReader.close();
        } catch (IOException e) {
            throwReadingException(e);
        } finally {
            try {
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                throwReadingException(e);
            }
        }
        return sb.toString();


    }

    private String throwReadingException(IOException e) {
        throw new BuildException("Error reading markdown file: " + e);
    }

    private void handleNulls(File inputFile, File outputDir) {
        if(inputFile == null) {
            throw new BuildException("input file is null");
        }

        if(outputDir == null) {
            throw new BuildException("outputDir is null");
        }
    }

}

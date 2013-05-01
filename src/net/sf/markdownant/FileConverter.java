package net.sf.markdownant;

import com.petebevin.markdown.MarkdownProcessor;
import org.apache.tools.ant.BuildException;

import java.io.*;

/**
 * Instances of this class is responsible for converting single Markdown files to HTML files. The actual conversion
 * is handled by Pete Bevins MarkdownProcessor.
 *
 * This class was originally in this project:
 *
 * http://sourceforge.net/p/markdown-ant
 *
 * written by Marcus Ahnve  (http://sourceforge.net/users/ahnve)
 */
public class FileConverter {

    private MarkdownProcessor markdownProcessor;
    private String outputExtension;
    private String template;
    private boolean convertGollumLinks = true;

    public FileConverter(String outputExtension) {
        this(outputExtension, null);
    }

    public FileConverter(String outputExtension, String template) {
        this.outputExtension = outputExtension;
        this.template = template;
        markdownProcessor = new MarkdownProcessor();
    }

    public void setConvertGollumLinks(boolean c) {
        convertGollumLinks = c;
    }

    /**
     * Converts the input Markdown file to an HTML file with the same name in the output directory.
     * @param inputFile
     * @param outputDir
     */
    public void convert(File inputFile, File outputDir) {        
        handleNulls(inputFile, outputDir);
        checkOutputDir(outputDir);        
        String[] outputFileName = getOutputFileName(inputFile);
        writeContentToFile(convertToString(inputFile), outputDir, outputFileName);
    }

    private String convertToString(File inputFile) {
        String s = readFile(inputFile);
        if (convertGollumLinks)
            s = s.replaceAll("\\[\\[(.*)\\|(.*)\\]\\]", "[$1]($2)");
        return markdownProcessor.markdown(s);
    }

    private void writeContentToFile(String convertedContent, File outputDir, String[] outputFileName) {
        FileWriter fileWriter = null;
        try {
            File outputFile = new File(outputDir, outputFileName[1]);
            fileWriter = new FileWriter(outputFile);
            convertedContent = applyTemplate(convertedContent, outputFileName[0]);             
            fileWriter.write(convertedContent);
            fileWriter.close();

        } catch (IOException e) {
            throwWriteException(outputFileName[1], e);
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                throwWriteException(outputFileName[1], e);
            }
        }
    }

    /**
    *   Applies the template to the given content, if there was one set.
    */
    private String applyTemplate(String content, String fileName) {
        if (template != null) {
            String tmpl = readFile(new File(template));
            content = tmpl.replace("${body}", content);
            content = content.replace("${title}", fileName);
        }
        return content;
    }

    private void throwWriteException(String outputFileName, IOException e) {
        throw new BuildException("Error writing converted content to " + outputFileName + ": " + e);
    }

    private String[] getOutputFileName(File inputFile) {
        int idx = inputFile.getName().lastIndexOf(".");
        String prefix = inputFile.getName().substring(0, idx);
        return new String[] { prefix, prefix  + "." + outputExtension }; 
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

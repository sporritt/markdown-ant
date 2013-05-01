package net.sf.markdownant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MarkdownTask extends Task {    

    private List filesets = new ArrayList();
    private String outputDir;
    private String outputExtension = "html";
    private String template = null;
    private FileSetsManager fileSetsManager;
    private FileConverter fileConverter;
    private File outputDirFile;

    public void execute() throws BuildException {
        fileSetsManager = new FileSetsManager(getProject());
        outputDirFile = new File(outputDir);
        fileConverter = new FileConverter(outputExtension, template);
        addFileSets();
        convertFiles();
    }

    private void convertFiles() {
        for (int i = 0; i < fileSetsManager.getFiles().size(); i++) {
            File file = (File) fileSetsManager.getFiles().get(i);
            fileConverter.convert(file, outputDirFile);
        }
    }

    private void addFileSets() {
        for (Iterator iterator = filesets.iterator(); iterator.hasNext();) {
            FileSet fileSet = (FileSet) iterator.next();
            fileSetsManager.add(fileSet);
        }
    }


    /** @noinspection InstanceMethodNamingConvention,NonBooleanMethodNameMayNotStartWithQuestion*/
    public void add(FileSet fileSet) {
        filesets.add(fileSet);
    }

    public void setOutputDir(String dir) {
        this.outputDir = dir;
    }

    public void setOutputExtension(String ext) {
        outputExtension = ext;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String toString() {
        return "MarkdownTask{" +
               "filesets=" + filesets +
               ", outputDir='" + outputDir + '\'' +
               ", outputExtension='" + outputExtension + '\'' +
               '}';
    }
}

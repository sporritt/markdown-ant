package net.sf.markdownant;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.types.FileSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FileSetsManager {

    private List fileSets = new ArrayList();
    private Project project;

    public FileSetsManager(Project project) {
        this.project = project;
    }

    public FileSetsManager(FileSet fileSet, Project project) {
        this(project);
        this.fileSets.add(fileSet);
    }

    public List getFiles() {

        List files = new ArrayList();
        for (int i = 0; i < fileSets.size(); i++) {
            FileSet fileSet = (FileSet) fileSets.get(i);
            DirectoryScanner directoryScanner = fileSet.getDirectoryScanner(project);
            File basedir = directoryScanner.getBasedir();
            String[] includedFiles = directoryScanner.getIncludedFiles();
            for (int j = 0; j < includedFiles.length; j++) {
                files.add(new File(basedir, includedFiles[j]));
            }
        }
        return files;
    }

    public void add(FileSet fileSet) {
        fileSets.add(fileSet);
    }

    List getFileSets() {
        return Collections.unmodifiableList(fileSets);
    }
}

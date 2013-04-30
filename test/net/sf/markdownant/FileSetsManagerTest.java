package net.sf.markdownant;

import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.DirectoryScanner;
import org.jmock.cglib.MockObjectTestCase;
import org.jmock.cglib.Mock;

import java.io.File;

public class FileSetsManagerTest extends MockObjectTestCase {
    private FileSetsManager fileSetsManager;


    public void testGetFiles() {

        Mock mockFileSet = new Mock(FileSet.class);
        FileSet fileSet = (FileSet) mockFileSet.proxy();

        Mock mockDirectoryScanner = new Mock(DirectoryScanner.class);
        DirectoryScanner directoryScanner = (DirectoryScanner) mockDirectoryScanner.proxy();


        String[] fileList = {"file1", "dir2/file2"};

        fileSetsManager.add(fileSet);

        mockFileSet.expects(atLeastOnce()).method("getDirectoryScanner").will(returnValue(directoryScanner));
        File baseDir = new File("build/tmp");

        mockDirectoryScanner.expects(atLeastOnce()).method("getBasedir").will(returnValue(baseDir));
        mockDirectoryScanner.expects(atLeastOnce()).method("getIncludedFiles").will(returnValue(fileList));

        assertEquals(new File("build/tmp/file1"), fileSetsManager.getFiles().get(0));
        assertEquals(new File("build/tmp/dir2/file2"), fileSetsManager.getFiles().get(1));

        mockFileSet.verify();
        mockDirectoryScanner.verify();
    }

    protected void setUp() {
        fileSetsManager = new FileSetsManager(null);
    }


    public void testAddFileSet() {
        FileSet fileSet = new FileSet();
        fileSetsManager.add(fileSet);
        assertTrue(fileSetsManager.getFileSets().contains(fileSet));
    }
}

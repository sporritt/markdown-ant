package net.sf.markdownant;

import junit.framework.TestCase;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.Project;

import java.io.File;

public class MarkdownTaskTest extends TestCase {
    private MarkdownTask markdownTask;


    protected void setUp() throws Exception {
        markdownTask = new MarkdownTask();
        markdownTask.setProject(new Project());
    }

    public void testShouldAddFileSets() {
        FileSet fileSet = new FileSet();
        fileSet.setDir(new File("testdata"));
        fileSet.setIncludes("**/*.markdown");
        markdownTask.add(fileSet);
        markdownTask.setOutputDir("build");
        markdownTask.execute();
        assertTrue(new File("build", "test1.html").exists());
    }


}

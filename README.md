markdown-ant
============

Markdown to HTML converter task for Ant.  Written by Marcus Ahnve; I just found it and changed one or two minor things then pulled it into GitHub.

Original project is on [Sourceforge](http://sourceforge.net/projects/markdown-ant/)

### Usage

You use this as you would any custom Ant task - first declare it with a `taskdef`:

	<path id="markdown.classpath">
        <fileset dir="./lib">
            <include name="*.jar"/>
        </fileset>
    </path>

    <taskdef name="md2html" classname="net.sf.markdownant.MarkdownTask">
    	<classpath>
    		<path refid="markdown.classpath"/>
    	</classpath>
    </taskdef>

... and then call it:

	<md2html outputDir="FOO">
		<fileset dir="BAR>
			<include name="*.md"/>
		</fileset>
	</md2html>

#### Dependencies
In this example, we've setup the classpath to be all the jars in the `lib` directory.  Your configuration may vary.  You need to copy two jars into your project:

	build/markdown-ant.jar
	lib/markdownj-1.0.2b1-0.2.0.jar

#### Output File Extension
By default, the output file extension is `.html`.  You can change this:

	<md2html outputDir="FOO" outputExtension=".htm">
		<fileset dir="BAR>
			<include name="*.md"/>
		</fileset>
	</md2html>

### Tests

There were tests in the original project, and they are in this project, but they have a few dependencies and I am in a hurry etc, so they are disabled right now. For shame!
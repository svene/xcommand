package org.xcommand

import org.gradle.api.*
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*

class JavaCCTask extends DefaultTask {
	@InputFile
	final Property<File> inputFile = project.objects.property(File)

	@OutputDirectory
	final Property<File> genDir = project.objects.property(File)

	@TaskAction
	void generate() {
		println "generate jcc source"
		File input = inputFile.get()
		File output = genDir.get()

		ant.mkdir(dir: output)
		ant.javacc(target: input, outputdirectory: output, javacchome: 'build/_artifacts')
	}

}


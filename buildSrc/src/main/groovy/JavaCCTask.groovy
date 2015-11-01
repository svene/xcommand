import org.gradle.api.*
import org.gradle.api.tasks.*

class JavaCCTask extends DefaultTask {
//	@InputFile
	def inputFile = 'xxx.jj'

//	@OutputDirectory
	def genDir = 'build/org/collage/jcc'

	@TaskAction
	void doit() {
		println "generate jcc source"
		ant.mkdir(dir: genDir)
		ant.javacc(target: inputFile, outputdirectory: genDir, javacchome: 'build/_artifacts')

//		project.sourceSets.main.java.srcDir(genDir)
	}

}


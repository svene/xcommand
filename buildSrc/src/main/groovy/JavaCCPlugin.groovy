import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class JavaCCPlugin implements Plugin<Project>  {
	def void apply(Project project) {

		project.task ('copyToLib', dependsOn: project.configurations.compile, type: Copy) {
	        into('build/_artifacts')
    	    from project.configurations.default
    	    from project.configurations.testCompile
    	    from project.configurations.default.allArtifacts*.file
    	    from project.configurations.testCompile.allArtifacts*.file
		}

		project.task('renameJCC', type: Copy, dependsOn: 'copyToLib') {
			from('build/_artifacts/javacc-4.0.jar')
			into('build/_artifacts')
			rename('javacc-4.0.jar', 'javacc.jar')
		}

		project.task('jcc', dependsOn: 'renameJCC') {
			def inputFile = 'templateparser.jj' 
			def genDir = 'build/org/collage/jcc'

			inputs.file inputFile
			outputs.dir genDir

			doLast {
				println "generate jcc source"
				ant.mkdir(dir: 'build/_artifacts')
				ant.mkdir(dir: 'build/org/collage/jcc')
				ant.javacc(target: inputFile, outputdirectory: genDir, javacchome: 'build/_artifacts')
			}

			project.sourceSets.main.java.srcDir(genDir)
		}

		project.compileJava.dependsOn(project.jcc)

	}
}



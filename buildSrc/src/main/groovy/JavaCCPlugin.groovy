import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

class JavaCCPlugin implements Plugin<Project>  {
	def void apply(Project project) {

		project.convention.plugins.javaccg = new JavaCCPluginConvention()

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

		project.task('javacc', dependsOn: 'renameJCC') {

//			inputs.file project.convention.plugins.javaccg.inputFile
//			outputs.dir project.convention.plugins.javaccg.genDir

			doLast {
				println "generate javacc source to: $project.convention.plugins.javaccg.genDir"
				ant.mkdir(dir: 'build/_artifacts')
				ant.mkdir(dir: 'build/org/collage/jcc')
				ant.javacc(target: project.convention.plugins.javaccg.inputFile, outputdirectory: project.convention.plugins.javaccg.genDir, javacchome: 'build/_artifacts')
				project.sourceSets.main.java.srcDir(project.convention.plugins.javaccg.genDir)
			}
			// println "***: genDir= ${project.convention.plugins.javaccg.genDir}" // --> default-gendir
		}

		project.compileJava.dependsOn(project.javacc)

	}

	class JavaCCPluginConvention {
//		def inputFile = 'xxx.jj'
//		def genDir = 'default-gendir'

		def javaccg(Closure closure) {
			closure.delegate = this
			closure()
		}

		def doit() {
			println "in doit"
		}

	}


}



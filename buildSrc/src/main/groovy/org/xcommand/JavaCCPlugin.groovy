package org.xcommand

import org.gradle.api.*
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Copy

class JavaCCPluginExtension {
	final Property<File> inputFile
	final Property<File> genDir

	JavaCCPluginExtension(Project project) {
		inputFile = project.objects.property(File)
		genDir = project.objects.property(File)
	}
}

class JavaCCPlugin implements Plugin<Project>  {
	void apply(Project project) {
		def ext = project.extensions.create("javacc", JavaCCPluginExtension, project)
		def versionJavacc = project.findProperty('VERSION_JAVACC') ?: 'UNKNOWN'

		project.tasks.register('copyToLib', Copy) {
			into("${project.buildDir}/_artifacts")
			from project.configurations.compileClasspath
			from project.configurations.default.allArtifacts*.file
			from project.configurations.compileClasspath.allArtifacts*.file
		}

		project.tasks.register('renameJCC', Copy) {
			dependsOn project.tasks.named('copyToLib')
			from("${project.buildDir}/_artifacts/javacc-${versionJavacc}.jar")
			into("${project.buildDir}/_artifacts")
			rename("javacc-${versionJavacc}.jar", "javacc.jar")
		}

		// jcc task
		project.tasks.register('jcc', JavaCCTask) { JavaCCTask t ->
			dependsOn(project.tasks.named('renameJCC'))

			// must be provided by the caller; fail if missing
			t.inputFile.set(ext.inputFile)
			t.genDir.set(ext.genDir)

			// Add generated directory to main source set lazily
			//project.sourceSets.main.java.srcDirs += t.genDir
		}

	}

}



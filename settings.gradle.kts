include(":rPi-music-box-remote")
include(":libraries:rxnearby")
include(":libraries:androidcommons")
include(":libraries:commons")

setBuildFilesName(rootProject)

// Ensure that each module's Gradle build file name is formatted as <module-name>.gradle.kts
fun setBuildFilesName(project: ProjectDescriptor) {
    if (project.children.isNotEmpty()) {
        project.children.forEach { subProject -> setBuildFilesName(subProject) }
    } else {
        project.buildFileName = "${project.name}.gradle.kts"
    }
}

include(":rPi-music-box-remote")

include(":core:design")

include(":features:discovery")
include(":features:instrumentplayer")

include(":libraries:rxnearby")
include(":libraries:androidcommons")
include(":libraries:permissions")
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

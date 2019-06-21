include(":rPi-music-box-remote")
include(":rxnearby")
include(":androidcommons")
include(":commons")

// Ensure that each module's Gradle build file name is formatted as <module-name>.gradle.kts
rootProject.children.forEach { module ->
    module.buildFileName = "${module.name}.gradle.kts"
}

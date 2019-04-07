package com.christiankula.rpimusicbox.commons.randomanimal

import kotlin.random.Random

/**
 * Return a randomly generated 'animal name' composed of an adjective and an animal
 *
 * Examples:
 * - Enumerable Tarantula
 * - Absolute Zebra
 * - Modest Horse
 */
fun generateRandomAnimalName(): String {
    val adj = adjectives[Random.nextInt(0, adjectives.size)]
    val animal = animals[Random.nextInt(0, animals.size)]

    return "$adj $animal"
}

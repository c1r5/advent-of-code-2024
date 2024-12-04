package dev.eric.java

import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val right = mutableListOf<Int>()
    val left = mutableListOf<Int>()
    val distancesBetween = mutableListOf<Int>()

    File("src/main/resources/day1.txt")
        .readLines()
        .forEach { line ->
            val (r, l) = line.split(Regex("\\s+"))

            right.add(r.toInt())
            left.add(l.toInt())
        }

    right.sort()
    left.sort()

    repeat(right.size) {
        var distance = left[it] - right[it]
        if (distance < 0) {
            distance *= -1
        }
        distancesBetween.add(distance)
    }

    println(distancesBetween.sum())

}
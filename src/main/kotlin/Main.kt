package dev.eric.java

import java.io.File
import kotlin.math.abs


enum class Directions (val dx: Int, val dy: Int) {
    START(0, 0),
    RIGHT(1, 0),
    LEFT(-1, 0),
    UP(0, -1),
    DOWN(0, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(1, -1),
    LEFT_UP(-1, 1),
    RIGHT_UP(1, 1),
}

fun readInput(filename: String): File = File("src/main/resources/$filename")

fun main() {
    day1(readInput("day1.txt").readLines())
    day2(readInput("day2.txt").readLines())
    day3(readInput("day3.txt").readText())
    day4(readInput("day4.txt").readText())
}

fun day4(input: String) {
    val grid = input.lines()

    val part1 = grid.flatMapIndexed { line, s ->
        s.mapIndexed { index, c ->
            index to c
        }.filter { it.second == 'X' }.map { line to it.first }
    }.sumOf {p ->
        Directions.entries.count { direction ->
            "XMAS".indices.mapNotNull {
                grid.getOrNull(p.first + it * direction.dx)?.getOrNull(p.second + it * direction.dy)
            }.joinToString("") == "XMAS"
        }
    }

    println("Day4-1: $part1")
}

fun day3(input: String) {
    val sumMultipliers = {text: String ->
        val matchMultiplier = "mul\\(\\d+,\\d+\\)".toRegex().findAll(text)
        matchMultiplier.sumOf {

            val n1 = it.value
                .substringAfter("mul(")
                .substringBefore(',')
                .trim()
                .toInt()

            val n2 = it.value
                .substringAfterLast(',')
                .substringBefore(')')
                .trim()
                .toInt()
            n1 * n2
        }
    }


    val part1 = sumMultipliers(input)

    println("Day3-1: $part1")

    val explode = input.split("do()")

    val part2 = explode.sumOf {line ->
        val multipliers = line.split("don't()").map(String::trim)[0]
        sumMultipliers(multipliers)
    }

    println("Day3-2: $part2")

}

fun day2(input: List<String>) {
    fun isSafe(report: List<Int>): Boolean {
        val isIncreasing = report.zipWithNext().all { (a, b) -> b > a }
        val isDecreasing = report.zipWithNext().all {(a, b) -> b < a}
        val differencesOk = report.zipWithNext().all { (a, b) -> abs(b - a) in 1..3 }
        return (isIncreasing || isDecreasing) && differencesOk
    }
    val reports = input.map { line -> line.split(" ").map(String::toInt) }

    val part1 = reports.count { report -> isSafe(report) }

    println("Day2-1: $part1")

    val part2 = reports.count { report ->
        if (isSafe(report)) return@count true

        for (i in report.indices) {
            val newReport = report.toMutableList().apply { removeAt(i) }
            if (isSafe(newReport)) return@count true
        }

        return@count false
    }

    println("Day2-2: $part2")
}

fun day1(input: List<String>) {
    val (left, right) = input.map { line ->
        val v1 = line.substringBefore(" ").trim()
        val v2 = line.substringAfterLast(" ").trim()
        v1.toLong() to v2.toLong()
    }.unzip()

    val frequencies = right.groupingBy { it }.eachCount()

    val result = left.sumOf {
         val frequency = frequencies.getOrDefault(it, 0)
        it * frequency
    }

    println("Day1-1: $result")

    val result2 = left
        .sorted()
        .zip(
            right.sorted()
        ).sumOf { (a, b) ->
            abs(a - b)
        }

    println("Day1-2: $result2")
}

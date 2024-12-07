package dev.eric.java

import java.io.File
import kotlin.math.abs


fun readInput(filename: String): File = File("src/main/resources/$filename")

fun main() {
    day1(readInput("day1.txt").readLines())
    day2(readInput("day2.txt").readLines())
    day3(readInput("day3.txt").readText())
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

    println("Day3-1 Result: $part1")

    val explode = input.split("do()")

    val part2 = explode.sumOf {line ->
        val multipliers = line.split("don't()").map(String::trim)[0]
        sumMultipliers(multipliers)
    }

    println("Day3-2 Result: $part2")

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

    println("Day2-1 Result: $part1")

    val part2 = reports.count { report ->
        if (isSafe(report)) return@count true

        for (i in report.indices) {
            val newReport = report.toMutableList().apply { removeAt(i) }
            if (isSafe(newReport)) return@count true
        }

        return@count false
    }

    println("Day2-2 Result: $part2")
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

    println("Day1-1 Result: $result")

    val result2 = left
        .sorted()
        .zip(
            right.sorted()
        ).sumOf { (a, b) ->
            abs(a - b)
        }

    println("Day1-2 Result: $result2")
}

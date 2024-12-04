package dev.eric.java

import java.io.File
import kotlin.math.abs

val splitRgx = Regex("\\s+")
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val lines = File("src/main/resources/day1.txt")
        .readLines()

    val day11 = day11(lines)

    println(day11)
}

fun day11(lines: List<String>): Long {
    val (left, right) = lines.map { line ->
        val v1 = line.substringBefore(" ").trim()
        val v2 = line.substringAfterLast(" ").trim()
        v1.toLong() to v2.toLong()
    }.unzip()

    val frequencies = right.groupingBy { it }.eachCount()
    var score = 0L

    repeat(left.size) {
        val item = left[it]
        val frequency = frequencies.getOrDefault(item, 0)

        score += (item * frequency )
    }

    return score

}

fun day2(reports: List<String>): Int {
    return reports.filter {line ->
        val report = line.split(splitRgx).map(String::toInt)
        val diffBetween = report.zipWithNext { a, b ->
            abs(a - b)
        }

        if (!diffBetween.all { it in 1..3 }) return@filter false

        val isIncreaseOnly = report.zipWithNext { a, b ->
            b > a
        }.all { it }

        val isDecreaseOnly = report.zipWithNext { a, b ->
            a > b
        }.all { it }

        isDecreaseOnly || isIncreaseOnly
    }.size
}

fun day1(lines: List<String>): Int { // 2970687
    val right = mutableListOf<Int>()
    val left = mutableListOf<Int>()
    var distancesBetween = 0
    lines.forEach { line ->
        val (r, l) = line.split(splitRgx)

        right.add(r.toInt())
        left.add(l.toInt())
    }
    right.sort()
    left.sort()
    repeat(right.size) {
        val distance = abs(left[it] - right[it])
        distancesBetween += distance
    }

    return distancesBetween
}
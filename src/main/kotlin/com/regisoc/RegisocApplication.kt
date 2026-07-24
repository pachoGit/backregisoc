package com.regisoc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RegisocApplication

fun main(args: Array<String>) {
    runApplication<RegisocApplication>(*args)
}

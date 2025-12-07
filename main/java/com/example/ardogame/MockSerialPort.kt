package com.example.ardogame

class MockSerialPort {
    fun read(): String {
        // simulate Arduino sending random data
        val values = listOf("Sensor: 123", "Sensor: 456", "Sensor: 789")
        return values.random()
    }
}

package com.smartcampus.crm.domain.models.managers

interface HardwareManager {
    fun getDeviceUuid(): String
    object Default : HardwareManager {
        override fun getDeviceUuid(): String {
            val process = ProcessBuilder(
                "reg", "query",
                "HKLM\\SOFTWARE\\Microsoft\\Cryptography",
                "/v", "MachineGuid"
            )
                .redirectErrorStream(true)
                .start()

            val output = process.inputStream.bufferedReader().readText()
            return output.substringAfter("REG_SZ").trim()
        }
    }
}
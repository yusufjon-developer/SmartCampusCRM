package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.core.AppConfig
import com.smartcampus.crm.domain.models.Device
import oshi.SystemInfo
import oshi.hardware.ComputerSystem
import java.nio.file.Files.createDirectories
import java.nio.file.Files.exists
import java.nio.file.Files.readAllBytes
import java.nio.file.Files.write
import java.nio.file.Paths
import java.util.UUID

interface DeviceManager {
    operator fun invoke(): Device

    class Base : DeviceManager {

        override fun invoke(): Device {
            val machineGuid = getSystemUUID()
            val motherboardId = getMotherboardUUID()
            val finalUuid = machineGuid ?: motherboardId ?: generateAndStoreAppSpecificId()

            val deviceType = getWindowsDeviceType()

            return Device(uuid = finalUuid, type = deviceType)
        }

        private fun generateAndStoreAppSpecificId(): String {
            val idManager = object {
                private val idFilePath =
                    Paths.get(System.getenv("LOCALAPPDATA"), AppConfig.APP_NAME, "device.id")

                fun getOrCreateAppSpecificId(): String {
                    try {
                        if (exists(idFilePath)) {
                            return String(readAllBytes(idFilePath)).trim()
                        } else {
                            val newId = UUID.randomUUID().toString()
                            createDirectories(idFilePath.parent)
                            write(idFilePath, newId.toByteArray())
                            return newId
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        return UUID.randomUUID().toString()
                    }
                }
            }
            return idManager.getOrCreateAppSpecificId()
        }

        fun getSystemUUID(): String? {
            return try {
                val si = SystemInfo()
                val computerSystem: ComputerSystem = si.hardware.computerSystem
                computerSystem.hardwareUUID
            } catch (e: Exception) {
                // Логирование ошибки
                println("Error getting system UUID with Oshi: ${e.message}")
                null
            }
        }

        private fun getMotherboardUUID(): String? {
            return try {
                val si = SystemInfo()
                val computerSystem: ComputerSystem = si.hardware.computerSystem
                computerSystem.baseboard.serialNumber
            } catch (e: Exception) {
                println("Error getting motherboard serial with Oshi: ${e.message}")
                null
            }
        }

        private fun getWindowsDeviceType(): String {
            return "Desktop"
        }

    }

}
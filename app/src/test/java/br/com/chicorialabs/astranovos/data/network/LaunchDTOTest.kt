package br.com.chicorialabs.astranovos.data.network

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class LaunchDTOTest {

    private val launchDTO = LaunchDTO(
        id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
        provider = "Launch Library 2"
    )

    @Test
    fun `deve converter em entidade de modelo`() {
        val launch = launchDTO.toModel()
        assertTrue(launch.id == "0d779392-1a36-4c1e-b0b8-ec11e3031ee6")
        assertTrue(launch.provider == "Launch Library 2")
    }

}
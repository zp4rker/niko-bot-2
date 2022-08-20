package com.zp4rker.nikobot.ii

import com.charleskorn.kaml.Yaml
import com.zp4rker.log4kt.Log4KtLoggerFactory
import com.zp4rker.nikobot.ii.events.GuildMessageReaction
import com.zp4rker.nikobot.ii.events.PrivateMessage
import com.zp4rker.nikobot.ii.events.PrivateMessageReaction
import kotlinx.serialization.SerializationException
import net.dv8tion.jda.api.JDABuilder
import java.io.File

val LOGGER = Log4KtLoggerFactory().getLogger("bot")

fun main() {
    LOGGER.info("Checking for config.yml...")
    if (!config()) return

    LOGGER.info("Initialising bot...")
    val jda = JDABuilder.createDefault(TOKEN).build()

    jda.addEventListener(PrivateMessage, PrivateMessageReaction, GuildMessageReaction)
    jda.awaitReady()
    LOGGER.info("Bot initialised!")
}

private fun config(): Boolean {
    val file = File("config.yml")
    if (file.exists()) {
        return try {
            val config = Yaml.default.decodeFromString(BotConfig.serializer(), file.readText())
            TOKEN = config.token
            VERIFICATION_CHANNEL = config.verificationChannel
            VERIFICATION_ROLE = config.verificationRole
            LOGGER.info("Successfully read config.yml!")
            true
        } catch (e: SerializationException) {
            LOGGER.error("Unable to read config.yml correctly, please check the file then start the bot again")
            false
        }
    } else {
        LOGGER.warn("No config.yml found! Creating one now...")
        file.createNewFile()
        file.writeText(
            """token: inserttokenhere
              |verificationChannel: insertidhere
              |verificationRole: insertidhere
            """.trimMargin()
        )
        LOGGER.info("Example config.yml created, please fill it in then start the bot again")
        return false
    }
}
package com.zp4rker.nikobot.ii

import kotlinx.serialization.Serializable

lateinit var TOKEN: String
lateinit var VERIFICATION_CHANNEL: String

@Serializable
data class BotConfig (
    val token: String,
    val verificationChannel: String
)
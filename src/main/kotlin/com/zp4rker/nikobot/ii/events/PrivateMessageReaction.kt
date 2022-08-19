package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.LOGGER
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object PrivateMessageReaction : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromType(ChannelType.PRIVATE)) return
        if (event.member!!.user.isBot) return

        LOGGER.info("Received a private message reaction!")
    }
}
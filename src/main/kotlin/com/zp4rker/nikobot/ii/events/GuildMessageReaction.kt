package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.LOGGER
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object GuildMessageReaction : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromGuild) return
        if (event.member!!.user.isBot) return

        LOGGER.info("Received a guild message reaction!")
    }
}
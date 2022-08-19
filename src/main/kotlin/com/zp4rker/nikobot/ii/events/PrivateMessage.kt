package com.zp4rker.nikobot.ii.events

import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object PrivateMessage : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.isFromType(ChannelType.PRIVATE)) return
        if (event.author == event.jda.selfUser) return

        // Only check for images sent
        if (event.message.attachments.isEmpty()) return
        if (!event.message.attachments.first().isImage) return

        // Add confirm/cancel reactions
        event.message.apply {
            addReaction(Emoji.fromFormatted("✔️")).queue()
            addReaction(Emoji.fromFormatted("❌")).queue()
        }

        event.channel.sendMessage("Please confirm or cancel your image being posted to the verification channel.").queue()
    }
}
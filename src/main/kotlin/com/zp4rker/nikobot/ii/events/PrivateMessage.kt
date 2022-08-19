package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.VERIFICATION_CHANNEL
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object PrivateMessage : ListenerAdapter() {
    override fun onMessageReceived(event: MessageReceivedEvent) {
        if (!event.isFromType(ChannelType.PRIVATE)) return
        if (event.author.isBot) return

        // Only check for images sent
        if (event.message.attachments.isEmpty()) return
        if (!event.message.attachments.first().isImage) return

        val image = event.message.attachments.first()

        val msg = event.jda.getTextChannelById(VERIFICATION_CHANNEL)?.sendMessage(image.proxyUrl)?.complete()
        msg?.let {
            it.addReaction(Emoji.fromFormatted("✔️")).queue()
            it.addReaction(Emoji.fromFormatted("❌")).queue()
        }
    }
}
package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.VERIFICATION_CHANNEL
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object PrivateMessageReaction : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromType(ChannelType.PRIVATE)) return
        if (event.user!!.isBot) return

        if (event.emoji == Emoji.fromFormatted("✔️")) { // Confirm
            val image = event.channel.retrieveMessageById(event.messageId).complete().attachments.first()

            val msg = event.jda.getTextChannelById(VERIFICATION_CHANNEL)?.sendMessage(image.proxyUrl)?.complete()
            msg?.let {
                it.addReaction(Emoji.fromFormatted("✔️")).queue()
                it.addReaction(Emoji.fromFormatted("❌")).queue()
            }
        } else if (event.emoji == Emoji.fromFormatted("❌")) { // Cancel
            event.channel.sendMessage("Your image was not sent to the verification channel.")
        }
    }
}
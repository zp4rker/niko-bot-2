package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.APPROVE_EMOJI
import com.zp4rker.nikobot.ii.DENY_EMOJI
import com.zp4rker.nikobot.ii.VERIFICATION_CHANNEL
import net.dv8tion.jda.api.entities.ChannelType
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object PrivateMessageReaction : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromType(ChannelType.PRIVATE)) return
        if (event.user == event.jda.selfUser) return

        // Prevent them reacting multiple times
        if (!event.reaction.retrieveUsers().contains(event.jda.selfUser)) return

        val imageMsg = event.channel.retrieveMessageById(event.messageId).complete()

        if (event.emoji == Emoji.fromFormatted(APPROVE_EMOJI)) { // Confirm
            val image = imageMsg.attachments.first()

            val msg = event.jda.getTextChannelById(VERIFICATION_CHANNEL)
                ?.sendMessage("${event.user!!.asMention} ${image.proxyUrl}")?.complete()
            msg?.apply {
                addReaction(Emoji.fromFormatted(APPROVE_EMOJI)).queue()
                addReaction(Emoji.fromFormatted(DENY_EMOJI)).queue()
            }

            event.channel.sendMessage("Your image was sent to the verification channel.").queue()
        } else if (event.emoji == Emoji.fromFormatted(DENY_EMOJI)) { // Cancel
            event.channel.sendMessage("Your image was not sent to the verification channel.").queue()
        }

        imageMsg.apply {
            removeReaction(Emoji.fromFormatted(APPROVE_EMOJI), event.jda.selfUser).queue()
            removeReaction(Emoji.fromFormatted(DENY_EMOJI), event.jda.selfUser).queue()
        }
    }
}
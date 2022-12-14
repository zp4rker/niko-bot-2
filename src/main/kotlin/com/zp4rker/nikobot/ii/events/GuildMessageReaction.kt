package com.zp4rker.nikobot.ii.events

import com.zp4rker.nikobot.ii.APPROVE_EMOJI
import com.zp4rker.nikobot.ii.DENY_EMOJI
import com.zp4rker.nikobot.ii.VERIFICATION_ROLE
import net.dv8tion.jda.api.entities.emoji.Emoji
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

object GuildMessageReaction : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromGuild) return
        if (event.user == event.jda.selfUser) return

        // Prevent them reacting multiple times + multiple users reacting
        val reactions = event.reaction.retrieveUsers().complete()
        if (!reactions.contains(event.jda.selfUser) || reactions.size > 2) return

        val imageMsg = event.channel.retrieveMessageById(event.messageId).complete()

        if (event.emoji == Emoji.fromFormatted(APPROVE_EMOJI)) { // Confirm
            val candidate = event.guild.getMember(imageMsg.mentions.users.first()) ?: run {
                event.channel.sendMessage("Unable to verify which user this is for.").queue()
                return
            }
            val role = event.guild.getRoleById(VERIFICATION_ROLE) ?: run {
                event.channel.sendMessage("Unable to find verification role! Please check the config.").queue()
                return
            }
            candidate.guild.addRoleToMember(candidate, role).queue()

            event.channel.sendMessage("The user has been approved.").queue()
        } else if (event.emoji == Emoji.fromFormatted(DENY_EMOJI)) { // Cancel
            event.channel.sendMessage("The user has been denied.").queue()
        }

        imageMsg.apply {
            removeReaction(Emoji.fromFormatted(APPROVE_EMOJI), event.jda.selfUser).queue()
            removeReaction(Emoji.fromFormatted(DENY_EMOJI), event.jda.selfUser).queue()
        }
    }
}
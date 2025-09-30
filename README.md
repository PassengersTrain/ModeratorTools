# About this plugin!
A Minecraft plugin for version 1.21.5 aimed to make server moderation easier.

# Commands
The plugin currently uses a subcommand system. The commands currently supported are listed below:

 ``` /moderatortools freeze <player> <reason>```

  ``` /moderatortools unfreeze <player> <reason>```

 # Permission
The permission(s) required for a player to run certain commands or access specific features are listed below:

```moderatortools.moderator.command ``` - Required to be able to execute the certain command(s).

```moderatortools.moderator.freezecommand``` - Required to be able to execute the freeze player command.

```moderatortools.moderator.unfreezecommand``` - Required to be able to execute the un-freeze player command.

```moderatortools.moderator.frozenbroadcastmessage``` - Required to be able to receive notifications upon a player being frozen.

```moderatortools.moderator.unfrozenbroadcastmessage``` - Required to be able to receive notifications upon a player being un-frozen.

# Placeholders
This plugin currently **supports only** internal placeholders, which are:

```{frozen_suspected_player}``` - Replaces the frozen player’s name with the internal placeholder, making language file configuration much easier.

```{frozen_punishment_reason}``` - Internal placeholder used to display the punishment reason.

```{unfrozen_punishment_reason}``` - Internal placeholder used to display the removal of punishment reason.

# Credits
I would like to thank these awesome developers!

@KodySimpson – Creating a guide on developing the subcommand system.
 

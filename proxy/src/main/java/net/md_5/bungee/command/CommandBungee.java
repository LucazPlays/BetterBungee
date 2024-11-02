package net.md_5.bungee.command;

import de.luca.betterbungee.BetterBungeeAPI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CommandBungee extends Command
{

    public CommandBungee()
    {
        super( "bungee" );
    }

    @Override
    public void execute(CommandSender sender, String[] args)
    {
        TextComponent text1 = new TextComponent();
        text1.addExtra("§eThis server is running §f§lHoly§6§lBungee§e version §f" + BetterBungeeAPI.getHolyBungeeVersion());
        text1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/teamholy-network/HolyBungee"));
        text1.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Link to Github")));
        sender.sendMessage(text1);

        TextComponent text2 = new TextComponent();
        text2.addExtra("§7BetterBungee Version §f" + BetterBungeeAPI.getBetterBungeeVersion());
        text2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/LucazPlays/BetterBungee"));
        text2.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Link to Github")));
        sender.sendMessage(text2);

        TextComponent text3 = new TextComponent();
        text3.addExtra("§7BungeeCord Version §f#" + BetterBungeeAPI.getBungeecordversion().substring(0, 7));
        text3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/SpigotMC/BungeeCord/tree/"+BetterBungeeAPI.getBungeecordversion()));
        text3.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("Link to Github")));
        sender.sendMessage(text3);


    }
}
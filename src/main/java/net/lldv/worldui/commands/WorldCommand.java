package net.lldv.worldui.commands;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.utils.ConfigSection;
import net.lldv.worldui.WorldUI;
import net.lldv.worldui.components.forms.simple.SimpleForm;
import net.lldv.worldui.components.language.Language;

/**
 * @author LlamaDevelopment
 * @project WorldUI
 * @website http://llamadevelopment.net/
 */
public class WorldCommand extends PluginCommand<WorldUI> {

    public WorldCommand(final WorldUI plugin, final ConfigSection section) {
        super(section.getString("Name"), plugin);
        this.setDescription(section.getString("Description"));
        this.setAliases(section.getStringList("Aliases").toArray(new String[]{}));
        if (this.getPlugin().isUsingPermission()) this.setPermission(section.getString("Permission"));
        this.setUsage(section.getString("Usage"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (this.getPlugin().isUsingPermission() && !sender.hasPermission(this.getPermission())) {
            sender.sendMessage(Language.get("no.permission"));
            return false;
        }
        if (sender.isPlayer()) {
            final Player player = (Player) sender;

            final SimpleForm.Builder form = new SimpleForm.Builder(Language.getNP("form.title"), Language.getNP("form.content"));

            this.getPlugin().getServer().getLevels().values().forEach((level) -> {
                if (!this.getPlugin().getHiddenWorlds().contains(level.getName())) {
                    form.addButton(new ElementButton(Language.getNP("form.button", level.getName())), (p) -> {
                        p.teleport(level.getSpawnLocation());
                    });
                }
            });

            form.build().send(player);
        }
        return false;
    }
}

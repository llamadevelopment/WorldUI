package net.lldv.worldui;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import lombok.Getter;
import net.lldv.worldui.commands.WorldCommand;
import net.lldv.worldui.components.forms.FormListener;
import net.lldv.worldui.components.language.Language;

import java.util.HashSet;
import java.util.Set;

/**
 * @author LlamaDevelopment
 * @project WorldUI
 * @website http://llamadevelopment.net/
 */
public class WorldUI extends PluginBase {

    @Getter
    private boolean usingPermission;

    @Getter
    private final Set<String> hiddenWorlds = new HashSet<>();

    @Override
    public void onEnable() {
        Language.init(this);
        this.saveDefaultConfig();
        final Config config = this.getConfig();
        this.getServer().getPluginManager().registerEvents(new FormListener(), this);
        this.hiddenWorlds.addAll(config.getStringList("HiddenWorlds"));
        this.usingPermission = config.getBoolean("UsePermission");
        this.getServer().getCommandMap().register("world", new WorldCommand(this, config.getSection("Commands.World")));
        config.getStringList("LoadWorlds").forEach((e) -> {
            this.getServer().loadLevel(e);
        });
    }

}

package com.minepalm.party.palmparty;

import com.minepalm.party.palmparty.data.PartyData;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PalmParty extends JavaPlugin {
    @Override
    public void onEnable() {
        PartyData partyData = new PartyData();
        Objects.requireNonNull(getCommand("파티")).setExecutor(new PartyCommand(partyData));
        getServer().getPluginManager().registerEvents(new PartyEvent(partyData),this);
    }

    @Override
    public void onDisable() {

    }
}

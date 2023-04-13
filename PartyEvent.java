package com.minepalm.party.palmparty;

import com.minepalm.party.palmparty.api.PartyImpl;
import com.minepalm.party.palmparty.data.PartyData;
import com.minepalm.party.palmparty.gui.PartyGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class PartyEvent implements Listener {
    PartyData partyData;
    public PartyEvent(PartyData partyData){
        this.partyData = partyData;
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        ItemMeta meta = clickedItem.getItemMeta();
        if (!meta.hasDisplayName()) return;
        String title = meta.getDisplayName();

        for (Map.Entry<Integer, PartyImpl> entry : partyData.getParty().entrySet()) {
            PartyImpl party = entry.getValue();
            if (title.equals(party.getTitle())) {
                if (party.getPartyMembers().contains(player)) {
                    player.sendMessage("이미 해당 파티에 속해 있습니다.");
                    player.closeInventory();
                    return;
                }
                party.addMember(player);
                player.sendMessage("파티 " + party.getTitle() + "에 가입하셨습니다.");
                player.closeInventory();
                return;
            }
        }
    }
}

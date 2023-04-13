package com.minepalm.party.palmparty.gui;

import com.minepalm.party.palmparty.api.PartyImpl;
import com.minepalm.party.palmparty.data.PartyData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartyGUI {
    private Inventory gui;
    private Player player;
    PartyData partyData;

    public PartyGUI(Player player,PartyData partyData) {
        this.partyData = partyData;
        this.player = player;
        this.gui = Bukkit.createInventory(null, 45, "파티 목록");
        refresh();
    }

    public void open() {
        player.openInventory(gui);
    }

    public void refresh() {
        gui.clear();
        Map<Integer, PartyImpl> parties = partyData.getParty();
        List<PartyImpl> partyList = new ArrayList<>(parties.values());
        int page = 1;

        // 한 페이지당 9개의 파티
        for (int i = 0; i < partyList.size(); i += 9) {
            List<PartyImpl> subList = partyList.subList(i, Math.min(i + 9, partyList.size()));

            // 페이지 번호 추가
            ItemStack pageItem = new ItemStack(Material.PAPER);
            ItemMeta pageMeta = pageItem.getItemMeta();
            pageMeta.setDisplayName("페이지 " + page++);
            pageItem.setItemMeta(pageMeta);
            gui.addItem(pageItem);

            for (PartyImpl party : subList) {
                ItemStack item = new ItemStack(Material.OAK_SIGN);
                ItemMeta meta = item.getItemMeta();

                // 아이템 이름: 파티 제목
                meta.setDisplayName(party.getTitle());

                // 로어 추가
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add("No : " + party.getPartyID());
                lore.add("Context : " + party.getContext());
                lore.add("");

                // 멤버 추가
                lore.add("Member");

                // 파티장 추가
                lore.add(" - " + party.getLeader().getName() + " [파티장]");

                // 파티원 추가
                for (Player member : party.getMembers()) {
                    if (!member.equals(party.getLeader())) {
                        lore.add(" - " + member.getName() + " [파티원]");
                    }
                }

                meta.setLore(lore);
                item.setItemMeta(meta);
                gui.addItem(item);
            }
        }
    }
}

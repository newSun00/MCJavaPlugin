package com.minepalm.party.palmparty.gui;

import com.minepalm.party.palmparty.api.PartyImpl;
import com.minepalm.party.palmparty.data.PartyData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartyGUI {
    private final Inventory gui;
    private final Player player;
    PartyData partyData;

    public PartyGUI(Player player,PartyData partyData) {
        this.partyData = partyData;
        this.player = player;
        this.gui = Bukkit.createInventory(null, 54, "파티 목록");
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

        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta empty_meta = empty.getItemMeta();
        empty_meta.setDisplayName(" ");
        empty.setItemMeta(empty_meta);

        ItemStack pageItem = new ItemStack(Material.PAPER);
        ItemMeta pageMeta = pageItem.getItemMeta();
        pageMeta.setDisplayName("페이지 " + page);
        pageItem.setItemMeta(pageMeta);

        ItemStack info = new ItemStack(Material.CHORUS_FLOWER);
        ItemMeta info_meta = info.getItemMeta();
        info_meta.setDisplayName(" 내 정보 ");
        info.setItemMeta(info_meta);


        // 페이지에 관계 없이 파티를 출력함.
        for ( int i = 0 ; i < 54; i++){
            if ( i < 4 || (i>4&& i<9) || (i>35 && i<45) ) {
                gui.setItem(i,empty);
            }
            if ( i == 4 ) gui.setItem(i,info);
            if ( i == 53) gui.setItem(i,pageItem);
        }
        for (PartyImpl party : partyList) {
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
            lore.add(ChatColor.WHITE+" - " + party.getLeader().getName() + " [파티장]");

            // 파티원 추가
            for (Player member : party.getMembers()) {
                if (!member.equals(party.getLeader())) {
                    lore.add(ChatColor.GRAY+" - " + member.getName() + " [파티원]");
                }
            }

            meta.setLore(lore);
            item.setItemMeta(meta);
            gui.addItem(item);
        }
    }
}

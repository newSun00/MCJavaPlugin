package com.minepalm.party.palmparty;


import com.minepalm.party.palmparty.api.PartyImpl;
import com.minepalm.party.palmparty.data.PartyData;
import com.minepalm.party.palmparty.gui.PartyGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class PartyFunction {
    private final PartyData partyData;

    public PartyFunction(PartyData partyData) {
        this.partyData = partyData;
    }

    public void createParty(String title, String context, Player leader) {
        if (isInParty(leader)) {
            leader.sendMessage("이미 다른 파티에 가입되어 있습니다.");
            return;
        }
        int partyID = generatePartyID();
        String formattedTitle = title.replace("_", " ");
        String formattedContext = context.replace("_", " ");
        PartyImpl party = new PartyImpl(partyID, formattedTitle, formattedContext, leader, new ArrayList<>());
        Map<Integer, PartyImpl> partyMap = new HashMap<>();
        partyMap.put(partyID, party);
        partyData.addParty(partyMap);
        leader.sendMessage(" 파티가 생성되었습니다. : "+partyID);
    }

    private boolean isInParty(Player player) {
        for (PartyImpl party : partyData.getParty().values()) {
            if (party.getMembers().contains(player) || party.getLeader().equals(player)) {
                return true;
            }
        }
        return false;
    }
    public void removeParty(Player player) {
        Map<Integer, PartyImpl> parties = partyData.getParty();
        for (PartyImpl party : parties.values()) {
            if (party.getLeader().equals(player)) {
                if (party.getMembers().size() >= 1) {
                    Player newLeader = party.getMembers().get(0);
                    party.setLeader(newLeader);
                    party.removeMember(newLeader);
                    Objects.requireNonNull(newLeader).sendMessage("파티장이 되셨습니다.");
                    for (Player member : party.getMembers()) {
                        member.sendMessage(newLeader.getName() + "님이 새로운 파티장이 되었습니다.");
                    }
                    player.sendMessage(newLeader.getName() + "님이 새로운 파티장이 되었습니다.");
                } else {
                    parties.remove(party.getPartyID());
                    for (Player member : party.getMembers()) {
                        member.sendMessage("파티가 삭제되었습니다.");
                    }
                    player.sendMessage("파티가 삭제되었습니다.");
                }
                break;
            }
        }
    }

    public void kickPlayer(Player leader, String targetName) {
        if (Bukkit.getPlayer(targetName) == null)
            return;
        Player target = Bukkit.getPlayer(targetName);

        Map<Integer, PartyImpl> parties = partyData.getParty();
        for (PartyImpl party : parties.values()) {
            if (party.getLeader().equals(leader) && party.getMembers().contains(target)) {
                party.removeMember(target);
                Objects.requireNonNull(target).sendMessage("파티에서 강제로 추방되었습니다.");
                for (Player member : party.getMembers()) {
                    member.sendMessage(target.getName() + "님이 파티에서 추방되었습니다.");
                }
                party.getLeader().sendMessage(target.getName() + "님이 파티에서 추방되었습니다.");
                parties.put(party.getPartyID(), party);
                return;
            }
        }
    }

    public void partyList(Player player) {
        PartyGUI partyGUI = new PartyGUI(player,partyData);
        partyGUI.open();
    }
    private int generatePartyID() {
        int partyID;
        Random random = new Random();
        do {
            partyID = random.nextInt(9000) + 1000;
            if ( partyData.getParty() == null ) return partyID;
        } while (partyData.getParty().containsKey(partyID));
        return partyID;
    }
}
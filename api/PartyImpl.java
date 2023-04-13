package com.minepalm.party.palmparty.api;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PartyImpl implements Party {
    private final int partyID;
    private String title;
    private String context;
    private Player leader;
    private List<Player> members;
    public PartyImpl(int partyID, String title, String context, Player leader, List<Player> members) {
        this.partyID = partyID;
        this.title = title;
        this.context = context;
        this.leader = leader;
        this.members = members;
    }
    @Override
    public List<Player> getPartyMembers() {
        return new ArrayList<>(members);
    }
    @Override
    public void joinParty(Player player, int partyID) {

    }

    @Override
    public void leaveParty(Player player) {

    }

    @Override
    public void findParty(int partyID) {

    }

    @Override
    public void delegateLeader(Player player, Player newLeader) {

    }

    @Override
    public void kickMember(Player player, Player member) {

    }

    @Override
    public void invitePlayer(Player player, Player invitedPlayer, int partyID) {

    }

    @Override
    public int getPartyID() {
        return partyID;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContext() {
        return context;
    }

    @Override
    public Player getLeader() {
        return leader;
    }

    @Override
    public List<Player> getMembers() {
        return members;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setContext(String context) {

    }

    @Override
    public void addMember(Player player) {
        if (members.size() >= 4) return;

        if (!members.contains(player)) {
            members.add(player);
            player.sendMessage("파티 " + title + " 에 가입되었습니다.");

            for (Player member : members) {
                if (!member.equals(player)) {
                    member.sendMessage(player.getName() + " 님이 파티에 참가하셨습니다.");
                }
            }
        }
    }

    @Override
    public void removeMember(Player player) {

    }

    @Override
    public void setLeader(Player player) {

    }
}
package com.minepalm.party.palmparty.api;


import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public interface Party {
        List<Player> getPartyMembers();

        // 파티에 가입할 경우 파티원이 됨
        void joinParty(Player player, int partyID);

        // 파티에서 퇴장함
        void leaveParty(Player player);

        // 파티를 찾기 위해 고유번호로 검색 가능
        void findParty(int partyID);

        // 파티장이 자신의 파티원 중 1명에게 파티장을 위임함
        void delegateLeader(Player player, Player newLeader);

        // 파티장이 파티원 중 1명을 강제 퇴장시킴
        void kickMember(Player player, Player member);

        // 파티에 가입하지 않은 플레이어를 초대함
        void invitePlayer(Player player, Player invitedPlayer, int partyID);

        int getPartyID();

        // 파티 제목 반환
        String getTitle();

        // 파티 내용 반환
        String getContext();

        // 파티장 반환
        Player getLeader();

        // 파티원 목록 반환
        List<Player> getMembers();

        void setTitle(String title);

        void setContext(String context);

        void addMember(Player player);

        void removeMember(Player player);

        void setLeader(Player player);
}

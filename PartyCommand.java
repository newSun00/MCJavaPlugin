package com.minepalm.party.palmparty;

import com.minepalm.party.palmparty.data.PartyData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PartyCommand implements CommandExecutor{

    PartyFunction partyFunction;
    public PartyCommand (PartyData partyData){
        partyFunction = new PartyFunction(partyData);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if ( sender instanceof Player ) {
            Player p = (Player) sender;
            if ( args.length == 0) showDefaultMsg(p);
            if ( args.length == 1) {
                if ( args[0].equals("삭제")) partyFunction.deleteParty(p);
                else if ( args[0].equals("탈퇴")) partyFunction.removeParty(p);
                else if ( args[0].equals("목록")) partyFunction.partyList(p);
            }
            if ( args.length == 2) {
                if ( args[0].equals("강퇴") ) partyFunction.kickPlayer(p,args[1]);
            }
            if ( args.length == 3){
                if ( args[0].equals("생성")) partyFunction.createParty(args[1],args[2],p);
            }
        }
        return false;
    }

    private void showDefaultMsg(Player player) {
        player.sendMessage(" ");
        player.sendMessage(" Party [ Beta 0.0.1 ] ");
        player.sendMessage(" /파티 생성 <Title> <Content> ");
        player.sendMessage(" /파티 삭제");
        player.sendMessage(" /파티 탈퇴");
        player.sendMessage(" /파티 강퇴 <Player> ");
        player.sendMessage(" /파티 목록 - 파티 가입은 목록에서 해주세요.");
        if ( player.isOp()) player.sendMessage(" /파티 초기화 ");
        player.sendMessage(" ");
    }
}

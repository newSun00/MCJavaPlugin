package com.minepalm.party.palmparty.data;

import com.minepalm.party.palmparty.api.PartyImpl;

import java.util.HashMap;
import java.util.Map;

public class PartyData {
    Map<Integer, PartyImpl> party = new HashMap<>();
    public void addParty(Map<Integer,PartyImpl> party){
        this.party.putAll(party);
    }
    public void removeParty(int partyID){
        this.party.remove(partyID);
    }
    public Map<Integer, PartyImpl> getParty() {
        return party;
    }
}

package io.github.moochnet.moochnet;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user-f on 5/3/15.
 */
public class GenDeets {
    String rMAC = null;
    String rEmail = null;
    String rZip = null;
    private ArrayList<String> nameslist = new ArrayList<String>();
    private ArrayList<String> domainslist = new ArrayList<String>();
    public GenDeets(){
        nameslist.add("XxSexyxx");
        nameslist.add("Coolboxer");
        nameslist.add("IOWN");
        nameslist.add("XXX_XXX");
        nameslist.add("IPOD");
        nameslist.add("Malekite");
        nameslist.add("Pokemon");
        nameslist.add("Holgar");
        nameslist.add("ICOOL");
        nameslist.add("LLCoolDave");
        nameslist.add("Gamedope");
        nameslist.add("0_0");
        nameslist.add("Mecool");
        nameslist.add("Raigan");
        nameslist.add("Lightfire300");
        nameslist.add("Oneiromancer");
        nameslist.add("Cutemachine");
        nameslist.add("Tktktk");
        nameslist.add("Miso");
        nameslist.add("Rini");
        nameslist.add("ROOT");
        nameslist.add("Mushi56");
        nameslist.add("Royuthar");
        nameslist.add("princess");
        nameslist.add("Emma");
        nameslist.add("Natalie");
        nameslist.add("Ashley");
        nameslist.add("Elizabeth");
        nameslist.add("Isabella");
        nameslist.add("Sarah");
        nameslist.add("Emily");
        nameslist.add("Samantha");
        nameslist.add("Ava");
        nameslist.add("Sophia");
        nameslist.add("Madison");
        nameslist.add("Catherine");
        nameslist.add("Grace");
        nameslist.add("Olivia");
        nameslist.add("Hannah");
        nameslist.add("James");
        nameslist.add("Michael");
        nameslist.add("David");
        nameslist.add("Johnathan");
        nameslist.add("Charles");
        nameslist.add("William");
        nameslist.add("Nicholas");
        nameslist.add("Stephen");
        nameslist.add("Christopher");
        nameslist.add("Cameron");
        nameslist.add("Alexander");
        nameslist.add("Gabriel");
        nameslist.add("Walter");
        nameslist.add("Daniel");
        nameslist.add("Brian");
        nameslist.add("Jesus");
        nameslist.add("Muhammed");
        nameslist.add("Moses");
        domainslist.add("@yahoo.com");
        domainslist.add("@gmail.com");
        domainslist.add("@hotmail.com");
        domainslist.add("@outlook.com");
        domainslist.add("@mail.com");
        domainslist.add("@inbox.com");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
        //domainslist.add("");
    }
    public String genMacAddress(){
        String macAddressOui = "28:37:37"; // Apple Computers
        StringBuilder s = new StringBuilder(17);
        s.append(macAddressOui);
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            s.append(":");
            int j = r.nextInt(255);
            s.append(String.format("%02x", j));
        }
        rMAC = s.toString();
        return rMAC;
    }
    public String genEmailAddress(){
        Random r = new Random();
        String name = nameslist.get(r.nextInt(nameslist.size()));
        String domain = domainslist.get(r.nextInt(domainslist.size()));
        rEmail = name + "@" + domain;
        return rEmail;
    }
    public String genZipCode(){
        Random r = new Random();
        int a = r.nextInt(10);
        int b = r.nextInt(10);
        int c = r.nextInt(10);
        int d = r.nextInt(10);
        int e = r.nextInt(10);
        StringBuilder s = new StringBuilder(6);
        s.append(a);
        s.append(b);
        s.append(c);
        s.append(d);
        s.append(e);
        rZip = s.toString();
        return rZip;
    }
    public String getrMAC(){
        return rMAC;
    }
    public String getrEmail(){
        return rEmail;
    }
    public String getrZip(){
        return rZip;
    }
}

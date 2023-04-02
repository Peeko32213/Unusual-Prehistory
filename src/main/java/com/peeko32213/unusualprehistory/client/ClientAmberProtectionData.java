package com.peeko32213.unusualprehistory.client;

public class ClientAmberProtectionData {
    private static int amberProtection;

    public static void set(int amber_protection){

        ClientAmberProtectionData.amberProtection = amber_protection;
    }


    public static int getAmberProtection(){
        return amberProtection;
    }
}

package it.laskaridis.userservice;

public final class UUID {

    private UUID() { }

    public static String newUUID() {
        return java.util.UUID.randomUUID().toString().replace("-", "");
    }

}

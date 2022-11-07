package com.okexchain.env;

public class EnvInstance {

    static EnvBase env = new EnvBase();

    public static void setEnv(String type) {
    }

    public static void setEnv(EnvBase env) {
        EnvInstance.env = env;
    }

    public static EnvBase getEnv() {
        return env;
    }

    public static EnvBase getEnvMainNet() {
        return env.configEnvMainNet();
    }

    public static EnvBase getEnvTestNet() {
        return env.configEnvTestNet();
    }

    public static EnvBase getEnvLocalNet(){ return env.configEnvLocalNet();}
}

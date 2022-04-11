package com.okexchain.env;

public class EnvInstance {

    static EnvBase env = new EnvBase();

    static public void setEnv(String type) {
    }

    static public void setEnv(EnvBase env) {
        EnvInstance.env = env;
    }

    static public EnvBase getEnv() {
        return env;
    }

    static public EnvBase getEnvMainNet() { return env.configEnvMainNet();}

    static public EnvBase getEnvTestNet() {
        return env.configEnvTestNet();
    }

}

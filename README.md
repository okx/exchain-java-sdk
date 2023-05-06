## OKBChain Java SDK

The OKBChain Java SDK is a lightweight Java library to interact with OKBChain through rest. 

### 1.Components

- src - the functions which could interact with OKBChain are contained in class `OKBChainRPCClientImpl` of package `com.okbchain.client.impl`. 
- test - it seems like a set of examples to show how to use the functions mentioned above. The developers could design their code refering to the test functions of class `OKBChainRPCClientImplTest` in package `com.okbchain.client` in detail.

There are some function modules in path `src` as well :

- env - set environment for Java SDK to interact with OKBChain
- msg - define the message types according to module in OKBChain, and the example is the main function in class Msgxxx not end with "value" or in sample package.
- common - network communications by RPC；
- utils.crypto - related with the account info and address info of OKBChain；
- utils.encoding - the implementation of go-amino in Java;

### 2.Installation

```
git clone https://github.com/okx/okbchain-java-sdk.git
cd okbchain-java-sdk
mvn install -Dmaven.test.skip=true
```

### 3.API

Find it on the [sdk documents](https://okexchain-docs.readthedocs.io/en/latest/api/sdk/java-sdk.html)

### 4.Tesing

All changes and addition of codes will be pushed with unit tests strictly. Right now you can find existing test cases : github.com/okex/okexchain-java-sdk/src/test

### 5.Contributing

No doubt that it's admirable to make contributions to okexchain Java SDK. You can provide your code as long as you have tested it with a local client, and your unit test showed its validity.

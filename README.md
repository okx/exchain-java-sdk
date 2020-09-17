## OKEXChain Java SDK

The OKEXChain Java SDK is a lightweight Java library to interact with OKEXChain. 

### 1.Components

- src - the functions which could be invoked to interact with OKEXChain are contained in class `OKEXChainRPCClientImpl` of package `com.okexchain.client.impl`. 
- test - it seems like a set of examples to show how to use the functions mentioned above. The developers could design their code refering to the test functions of class `OKEXChainRPCClientImplTest` in package `com.okexchain.client` in detail.

There are some function modules in path `src` as well :

- common - network communications by RPC；

- crypto - related with the account info and address info of OKEXChain；
- encoding - the implementation of go-amino in Java;
- proto - the base of amino with protobuf3;
- transaction - how to build an available transaction for OKEXChain;
- types -  all the message types used in Java SDK;

### 2.Installation

```
git clone https://github.com/okex/okexchain-java-sdk.git
```

### 3.API

Find it on the [sdk documents](https://okexchain-docs.readthedocs.io/zh_CN/latest/api/sdk/java-sdk.html)

### 4.Tesing

All changes and addition of codes will be pushed with unit tests strictly. Right now you can find existing test cases : github.com/okex/okexchain-java-sdk/src/test

### 5.Contributing

No doubt that it's admirable to make contributions to okexchain Java SDK. You can provide your code as long as you have tested it with a local client and your unit test showed its validity.  

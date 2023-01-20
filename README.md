# FallingAccident
**Compose + MVI + Clean Architecture**

### **1. Compose + MVI**

This architecture is compatible with ***Compose***.

Inside UiScope, Views follow the ***Compose*** **lifecycle** when **emitting Intents** or **updating**.

![MVI](https://user-images.githubusercontent.com/11436005/213765164-9876d570-dde4-4cd3-b596-a9a35e2796dd.png)



### **2. Processor**

**Action** guarantees sequential I/O, whereas **Effect** is asynchronous I/O.

![Processor](https://user-images.githubusercontent.com/11436005/213766067-f8837ecb-6af8-4121-aceb-41158744db99.png)

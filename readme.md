##Summary
This project implemented a simple messager as required by the test.pdf.  
Bonus tasks 1, 2, 3 are implemented.  
Task 4 is not implemented due to time constraint. Thoughts are:
1. Seperate client and server to different modules. Use a common jar to define common domain.
They can communicate using HTTP, RPC frameworks like Netty, or simply Java IO Socket. 
2. Server now needs to maintain connection with client, ie, keep the session info. 
Internal state of server - logged in user and their messages need to be accessed in thread safe manner. 
For example, use a ConcurrentHashMap instead of HashMap. 

##How to use?
This is a java 8 application, built using maven. Use ```mvn clean install``` to build jar from source code.
To run application in java8, use `java -jar messager-1.0-SNAPSHOT.jar`.

###Supported Commands
1. `login <username>`. Login a new/existing user
2. `send <username> <"message">`. Send a message to a logged user in system.
3. `broadcast <"message">`. Broadcast to all other users in system.
4. `reply <"message">`. Reply a message to sender of last read message.
5. `forward <username>`. Forward last read message to another user in system.
6. `read <no>`.
    * If no is not specified, display the overall message count.  
    * If no=0, read out all messages.  
    * If no > 0, read the specified message.
7. `exit`. Exit program.


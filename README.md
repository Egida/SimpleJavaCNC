# Java CNC / Botnet
It's a simple CNC / Botnet made in Java, it isn't really functional, but you can learn something from my code (I hope)


### Server
The Server receives commands from the clients, it will send them to the bots to be executed

### Client
The Client is really simple, it connects to the server, sends the device hostname, and then you can input some commands.

### Bot
I wrote the command system like most Minecraft Clients, you have a `Command` class, you create a new class that extends the `Command` class, 
it has a simple `super()` function / initiator and a `onCommand` function which is where the code for commands is.

# ceserver-pcileech

Ceserver-pcileech allows using Cheat Engine against a remote machine, without the need to install ANY software on that
remote machine. It was developed independently from the Cheat Engine software by DarkByte and PCILeech by Ulf Frisk, and
is not affiliated with either.

All Cheat Engine functions may not be available. Currently implemented is the ability to:

* Connect to a Process
* Read Memory
* Write Memory
* Search Memory
* Browse Memory
* View Module Listing

Other functions may or may not work (likely the latter).

# Terminology

* "Source": The machine running Cheat Engine and PCILeech.
* "Target": The machine running the process to be inspected/altered.

# Prerequisites

* Two machines running Windows
* [MemProcFS](https://github.com/ufrisk/MemProcFS) running on the source machine (part of the PCILeech ecosystem by Ulf Frisk)
* Additional requirements, including possibly the purchase of a hardware FPGA card if you choose to go that route. See
  the [PCILeech documentation](https://github.com/ufrisk/pcileech/blob/master/readme.md) for your particular use case.

# Installation Option #1 - Downloading the Binary

It is *recommended* that you build ceserver-pcileech yourself from its source code:

1. Download and install [Temurin 17](https://adoptium.net/?variant=openjdk17) (Any other Java 17+ is fine too)
2. Download [the latest ceserver-pcileech.jar](https://github.com/isabellaflores/ceserver-pcileech/releases) from Github
3. Copy the downloaded ceserver-pcileech.jar file to your Desktop
4. Continue with "Running the Server" section below

# Installation Option #2 - Building from Source

It is *recommended* that you build ceserver-pcileech yourself from its source code:

1. Download and install [Temurin 17](https://adoptium.net/?variant=openjdk17) (Any other Java 17+ is fine too)
2. Download and install [Apache Maven](https://www.youtube.com/watch?v=--Iv5vBIHjI)
3. Download [the latest source code](https://github.com/isabellaflores/ceserver-pcileech/releases) from Github
4. Unzip the source code to any desired location
5. Build it using Maven by typing "mvn package" in the source code directory
6. Copy the ceserver-pcileech.jar file from the 'target' directory to your Desktop
7. Continue with "Running the Server" section below

# Running the Server

1. Open a command prompt (Win+R then type "cmd")
2. Type "cd Desktop"
3. Type "java -jar ceserver-pcileech.jar"
4. Configure the server in the window that appears
5. Press the "Start Server" button
6. The server will now be listening on the default port, 52736.

# Connecting to the server

1. Open Cheat Engine
2. File -> Open Process
3. Click 'Network'
4. Type 'localhost' in the 'Host' field
5. Click 'Connect' and select a process to open

# Contributing to ceserver-pcileech

Thank you for your interest in contributing to ceserver-pcileech!

To submit your changes to me, please [create a pull request](https://github.com/isabellaflores/ceserver-pcileech/pulls),
and I will personally review your submission. If it is accepted, you will receive credit for your submission. If you'd
like your submission to be anonymous or pseudonymous, please let me know.
/*
 * This file is part of ceserver-pcileech by Isabella Flores
 *
 * Copyright © 2021 Isabella Flores
 *
 * It is licensed to you under the terms of the
 * GNU Affero General Public License, Version 3.0.
 * Please see the file LICENSE for more information.
 */

package iflores.ceserver.pcileech;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerMain {

    public static final int DEFAULT_PORT_NUMBER = 52736;

    public static void main(String[] args) {
        try {
            if (args.length != 2) {
                System.err.println("ERROR: Expected 2 command line arguments");
                System.exit(-1);
            }
            // start thread that ends the process on any input from parent process
            new Thread(() -> {
                try {
                    //noinspection ResultOfMethodCallIgnored
                    System.in.read(); // wait for any data
                } catch (Throwable t) {
                    t.printStackTrace();
                } finally {
                    System.exit(0);
                }
            }).start();
            File jnaLibraryPath = new File(args[0]);
            if (!jnaLibraryPath.exists()) {
                throw new FileNotFoundException("JNA Library Path does not exist: '" + args[0] + "'");
            }
            try {
                List<String> pciLeechArgs = new ArrayList<>();
                pciLeechArgs.add("");
                pciLeechArgs.addAll(Arrays.asList(args[1].split(" +")));
                System.setProperty("jna.library.path", jnaLibraryPath.getParent());
                int numTries = 0;
                while (true) {
                    System.out.println("Initializing PCILeech...");
                    boolean result = PciLeech.initialize(pciLeechArgs.toArray(String[]::new));
                    if (result) {
                        break;
                    }
                    if (++numTries >= 10) {
                        throw new PciLeechException("Unable to initialize PCILeech -- Giving up.");
                    } else {
                        System.out.println("Failed to initialize PCILeech -- Trying again...");
                    }
                }
                System.out.println("PCILeech Initialization Complete.");
                runServer();
            } catch (UnsatisfiedLinkError ex) {
                throw new PciLeechException("Unable to load PCILeech's VMM DLL.\nCheck MemProcFS location.", ex);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            System.exit(-1);
        }
    }

    private static void runServer() throws IOException {
        ServerSocketChannel ss = ServerSocketChannel.open();
        int port = DEFAULT_PORT_NUMBER;
        try {
            ss.bind(new InetSocketAddress(port));
        } catch (BindException ex) {
            throw new IOException("Unable to listen on port " + port, ex);
        }
        System.err.println("Server running on port " + port + "...");
        //noinspection InfiniteLoopStatement
        while (true) {
            SocketChannel socketChannel = ss.accept();
            ClientHandler clientHandler = new ClientHandler(socketChannel);
            clientHandler.start();
        }
    }

}

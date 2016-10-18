/*
 * Copyright 2016 Stephan Knitelius {@literal <stephan@knitelius.com>}.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package science.raketen.apollosocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServer {

    private static final String HTTP_RESPONSE_HEADER = "HTTP/1.1 200 OK\r\n\r\n";
    
    private final ServerSocket serverSocket;
    private boolean run = true;

    public ApolloServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        while (run) {
            try (Socket socket = serverSocket.accept()) {
                logRequest(socket);
                String httpResponse = HTTP_RESPONSE_HEADER + "Hallo World!";
                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ApolloServer.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
    }

    private void logRequest(Socket socket) throws IOException {
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        StringBuilder request = new StringBuilder();
        
        String line = reader.readLine(); 
        while(line != null && !line.isEmpty()) {
            request.append(line).append(System.lineSeparator());
            line = reader.readLine();
        }

        Logger.getLogger(ApolloServer.class.getName()).log(Level.INFO, request.toString());
    }

    public void stop() {
        this.run = false;
    }

    public int getPort() {
        return serverSocket.getLocalPort();
    }

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args.length > 1) {
            port = Integer.parseInt(args[0]);
        }
        ApolloServer apolloServer = new ApolloServer(port);
        apolloServer.start();
    }
}

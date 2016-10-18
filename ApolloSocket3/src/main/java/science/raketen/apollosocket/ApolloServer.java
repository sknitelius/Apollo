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

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServer {

    private final ExecutorService executorService;
    private final ServerSocket serverSocket;
    private boolean run = true;

    public ApolloServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.executorService = Executors.newFixedThreadPool(8);
    }

    public void start() {
        while (run) {
            try {
                executorService.execute(new RequestHandler(serverSocket.accept()));
            } catch (IOException ex) {
                Logger.getLogger(ApolloServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void stop() {
        this.run = false;
    }

    public static void main(String[] args) throws IOException {
        ApolloServer apolloServer = new ApolloServer(8080);
        apolloServer.start();
    }
}

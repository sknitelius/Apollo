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
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class RequestHandler implements Runnable {

    private static final String HTTP_RESPONSE_HEADER = "HTTP/1.1 200 OK\r\n\r\n";
    private final Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            Request request = parseRequest(socket);

            String name = request.getQueryParam("name");
            String httpResponse = String.format("%s Hallo %s!", HTTP_RESPONSE_HEADER, name);

            socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ApolloServer.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    private Request parseRequest(Socket socket) throws IOException {
        InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);

        String requestLine = reader.readLine();
        String[] http = requestLine.split(" ");
        return new Request((http[0]), (http[1]), (http[2]));
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package science.raketen.apollosocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServerTest {

    private ApolloServer apolloServer;

    @Before
    public void before() throws IOException {
        
//        apolloServer = new ApolloServer(10112);
//        
//        Thread thread = new Thread(apolloServer::start);
//        thread.start();
    }

    @After
    public void tearDown() {
//        apolloServer.stop();
    }

    @Test
    public void hello() throws IOException {
        Socket socket = new Socket("localhost", 8080);
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        os.writeUTF("hallo!");
        os.flush();

        DataInputStream is = new DataInputStream(socket.getInputStream());
        
        assertEquals("hallo!",is.readUTF());
        socket.close();
    }
}

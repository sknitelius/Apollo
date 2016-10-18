/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package science.raketen.apollosocket;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Stephan Knitelius {@literal <stephan@knitelius.com>}
 */
public class ApolloServerTest {

    private static ApolloServer apolloServer;

    @BeforeClass
    public static void setUp() throws IOException, InterruptedException {
        apolloServer = new ApolloServer(0);

        Thread thread = new Thread(apolloServer::start);
        thread.start();
    }

    @AfterClass
    public static void tearDown() {
        apolloServer.stop();
    }

    @Test
    public void hello() throws IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ApolloServerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        Socket socket = new Socket("localhost", apolloServer.getPort());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        os.write("hallo!\r\nasdfasdf\r\nasdfasfdasfd\r\n".getBytes("UTF-8"));
        os.flush();
        final InputStream inputStream = socket.getInputStream();
        while (inputStream.available() < 10) {
            try {
                Thread.sleep(2000);
                System.out.println("byte: " + inputStream.available());
            } catch (InterruptedException ex) {
                Logger.getLogger(ApolloServerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader is = new BufferedReader(inputStreamReader);
        assertEquals("hallo!", is.readLine());
        socket.close();
    }
}

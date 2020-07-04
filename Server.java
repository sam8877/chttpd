package cdbb.httpd;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author cdbb
 */
public class Server {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(80);
		System.out.println("Server started at " + new Date() + "\n");

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 5, 200, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(5), Executors.defaultThreadFactory(),
				new ThreadPoolExecutor.AbortPolicy());
		try {
			while (true) {
				// 通信socket的获得
				Socket socket = serverSocket.accept();
				threadPoolExecutor.execute(new Handler(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
			serverSocket.close();
		}

	}
}

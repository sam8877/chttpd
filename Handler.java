package cdbb.httpd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 
 * @author cdbb
 *
 */

public class Handler implements Runnable {
	public Socket socket;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			Request req = new Request(in);
			System.out.println(req);

			if ("/".equals(req.path)) {
				req.path = "index.html";
			}
			if ("/favicon.ico".equals(req.path)) {
				req.path = "favicon.ico";
			}
			File file = new File(req.path);
			InputStream fin = new FileInputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];

			OutputStream out = socket.getOutputStream();
			Response res = new Response(out, "HTTP/1.1", "200", "OK");
			res.writeHeader();
			while ((len = fin.read(buf)) > 0) {
				res.writeBytes(buf, 0, len);
			}
			socket.close();
			fin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

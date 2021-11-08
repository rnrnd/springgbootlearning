package com.example.demo.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {

	private final static Logger log = LoggerFactory.getLogger(NioSelectorServer.class);
	public static void main(String[] args) {

		try {
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.socket().bind(new InetSocketAddress(9000));
			serverSocketChannel.configureBlocking(false);
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			log.info("服务启动成功！");
			while (true) {
				selector.select();
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectionKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey key = iterator.next();
					if (key.isAcceptable()) {
						ServerSocketChannel channel = (ServerSocketChannel) key.channel();
						SocketChannel socketChannel = channel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						System.out.println("客户端连接成功！");
					} else if (key.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) key.channel();
						ByteBuffer byteBuffer = ByteBuffer.allocate(128);
						int len = socketChannel.read(byteBuffer);
						if (len > 0) {
							System.out.println("接收到消息： " + new String(byteBuffer.array()));
						} else if (len == -1) {
							System.out.println("客户端断开连接！");
							socketChannel.close();
						}
					}
					iterator.remove();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

package com.xiaozhi.nio;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;

/**
 * 类描述
 *
 * @author zhangzy
 * @date 2021/4/7-19:00
 * @since v1.0
 */
public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Server().createServer();
    }
    public void createServer() throws IOException, InterruptedException {
//        ServerSocketChannel serverSocket = ServerSocketChannel.open();
//        serverSocket.socket().bind(new InetSocketAddress(9000));
//        serverSocket.configureBlocking(false);

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch){
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                });
        ChannelFuture future = serverBootstrap.bind(9000).sync();
        future.channel().closeFuture().sync();
    }
}

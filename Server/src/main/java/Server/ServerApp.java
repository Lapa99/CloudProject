package Server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ConcurrentHashMap;

public class ServerApp {
    private ConcurrentHashMap<ChannelHandlerContext, String> clients;
    private static final int PORT = 8189;
    public ServerApp() {
        clients = new ConcurrentHashMap<>();
        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new StringDecoder(),
                                    new StringEncoder(),
                                    new AuthHandler(ServerApp.this)
                            );
                        }
                    });
            ChannelFuture future = bootstrap.bind(PORT).sync();
            System.out.println("Server started on port " + PORT);
            future.channel().closeFuture().sync(); //block
        } catch (InterruptedException e) {
            System.out.println("Server was broken");
        } finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public ConcurrentHashMap<ChannelHandlerContext, String> getClients() {
        return clients;
    }

    public static void main(String[] args) {
        new ServerApp();
    }
}

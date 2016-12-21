package com.github.liuzhengyang.simplerpc.core;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;

/**
 * Description:
 *
 * @author liuzhengyang
 * @version 1.0
 * @since 2016-12-16
 */
@ChannelHandler.Sharable
public class RpcClientHandler extends SimpleChannelInboundHandler<Response>{
	private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientHandler.class);

	protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
		LOGGER.info("Receive {}", msg);
		BlockingQueue<Response> blockingQueue = RpcClient.responseMap.get(msg.getRequestId());
		blockingQueue.put(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		LOGGER.error("Exception caught on {}, ", ctx.channel(), cause);
		ctx.channel().close();
	}
}

/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.network.mock

import java.net.*
import java.nio.*
import java.nio.channels.*

open class SocketChannelMock: SocketChannel(SelectorProviderMock()) {
    override fun implCloseSelectableChannel() {}
    override fun implConfigureBlocking(block: Boolean) {}

    override fun socket(): Socket {
        TODO("Not yet implemented")
    }

    override fun connect(remote: SocketAddress?): Boolean {
        TODO("Not yet implemented")
    }

    override fun finishConnect(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isConnected(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isConnectionPending(): Boolean {
        TODO("Not yet implemented")
    }

    override fun read(dst: ByteBuffer?): Int {
        TODO("Not yet implemented")
    }

    override fun read(dsts: Array<out ByteBuffer>?, offset: Int, length: Int): Long {
        TODO("Not yet implemented")
    }

    override fun write(src: ByteBuffer?): Int {
        TODO("Not yet implemented")
    }

    override fun write(srcs: Array<out ByteBuffer>?, offset: Int, length: Int): Long {
        TODO("Not yet implemented")
    }

    override fun bind(local: SocketAddress?): SocketChannel {
        TODO("Not yet implemented")
    }

    override fun getLocalAddress(): SocketAddress {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> setOption(name: SocketOption<T>?, value: T): SocketChannel {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getOption(name: SocketOption<T>?): T {
        TODO("Not yet implemented")
    }

    override fun supportedOptions(): MutableSet<SocketOption<*>> {
        TODO("Not yet implemented")
    }

    override fun shutdownInput(): SocketChannel {
        TODO("Not yet implemented")
    }

    override fun shutdownOutput(): SocketChannel {
        TODO("Not yet implemented")
    }

    override fun getRemoteAddress(): SocketAddress {
        TODO("Not yet implemented")
    }
}

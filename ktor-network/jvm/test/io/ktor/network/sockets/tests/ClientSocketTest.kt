/*
 * Copyright 2014-2019 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.network.sockets.tests

import io.ktor.network.mock.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.network.sockets.Socket
import io.ktor.network.sockets.SocketImpl
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.debug.junit4.*
import org.junit.*
import org.junit.rules.*
import sun.nio.ch.*
import java.net.*
import java.net.ServerSocket
import java.nio.*
import java.nio.channels.*
import java.nio.channels.spi.*
import java.util.concurrent.*
import kotlin.concurrent.*
import kotlin.coroutines.*
import kotlin.test.*
import kotlin.test.Test

class ClientSocketTest {
    private val exec = Executors.newCachedThreadPool()
    private val selector = ActorSelectorManager(exec.asCoroutineDispatcher())
    private var server: Pair<ServerSocket, Thread>? = null

    @get:Rule
    val timeout = CoroutinesTimeout.seconds(600)

    @get:Rule
    val errors = ErrorCollector()

    @AfterTest
    fun tearDown() {
        server?.let { (server, thread) ->
            server.close()
            thread.interrupt()
        }
        selector.close()
        exec.shutdown()
    }

    @Test
    fun testConnect() {
        server { it.close() }

        client {
        }
    }

    @Test
    fun testRead() {
        server { client ->
            client.getOutputStream().use { o ->
                o.write("123".toByteArray())
                o.flush()
            }
        }

        client { socket ->
            val bb = ByteBuffer.allocate(3)
            val channel = socket.openReadChannel()
            channel.readFully(bb)
            assertEquals("123", String(bb.array()))
        }
    }

    @Test
    fun testWrite() {
        server { client ->
            assertEquals("123", client.getInputStream().reader().readText())
        }

        client { socket ->
            val channel = socket.openWriteChannel(true)
            channel.writeStringUtf8("123")
        }
    }

    @Test
    fun testReadParts() {
        server { client ->
            client.getOutputStream().use { o ->
                o.write("0123456789".toByteArray())
                o.flush()
            }
        }

        client { socket ->
            assertEquals("0123456789", socket.openReadChannel().readUTF8Line())
        }
    }

    @Test
    fun testSelfConnect() {
        val channel = object : SocketChannelMock() {
            override fun socket(): java.net.Socket {
                return SocketMock(
                    localAddress = InetSocketAddress("client", 1),
                    remoteAddress = InetSocketAddress("client", 1),
                    sourceChannel = this
                )
            }

            override fun connect(remote: SocketAddress?): Boolean {
                return false
            }

            override fun finishConnect(): Boolean {
                if (!isOpen) throw ClosedChannelException()
                return true
            }
        }

        channel.configureBlocking(false)

        runBlocking {
            assertFailsWith<ClosedChannelException>(
                "Channel should be closed if local and remote addresses of client socket match"
            ) {
                SocketImpl(channel, channel.socket(), SelectorManagerMock()).connect(
                    InetSocketAddress("server", 2)
                )
            }
        }
    }

    private fun client(block: suspend (Socket) -> Unit) {
        runBlocking {
            aSocket(selector).tcp().connect(server!!.first.localSocketAddress).use {
                block(it)
            }
        }
    }

    private fun server(block: (java.net.Socket) -> Unit) {
        val server = ServerSocket(0)
        val thread = thread(start = false) {
            try {
                while (true) {
                    val client = try {
                        server.accept()
                    } catch (t: Throwable) {
                        break
                    }

                    client.use(block)
                }
            } catch (t: Throwable) {
                errors.addError(t)
            }
        }

        this.server = Pair(server, thread)
        thread.start()
    }
}

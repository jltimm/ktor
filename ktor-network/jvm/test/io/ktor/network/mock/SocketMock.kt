/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.network.mock

import java.net.*
import java.nio.channels.*

class SocketMock(
    private val localAddress: SocketAddress,
    private val remoteAddress: SocketAddress,
    private val sourceChannel: SocketChannel? = null
): java.net.Socket() {
    override fun getRemoteSocketAddress() = remoteAddress
    override fun getLocalSocketAddress() = localAddress

    override fun close() {
        sourceChannel?.close()
    }
}

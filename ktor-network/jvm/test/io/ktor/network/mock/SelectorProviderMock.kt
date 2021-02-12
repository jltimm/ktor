/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.network.mock

import java.net.*
import java.nio.channels.*
import java.nio.channels.spi.*

class SelectorProviderMock: SelectorProvider() {
    override fun openDatagramChannel(): DatagramChannel {
        TODO("Not yet implemented")
    }

    override fun openDatagramChannel(family: ProtocolFamily?): DatagramChannel {
        TODO("Not yet implemented")
    }

    override fun openPipe(): Pipe {
        TODO("Not yet implemented")
    }

    override fun openSelector(): AbstractSelector {
        TODO("Not yet implemented")
    }

    override fun openServerSocketChannel(): ServerSocketChannel {
        TODO("Not yet implemented")
    }

    override fun openSocketChannel(): SocketChannel {
        TODO("Not yet implemented")
    }
}

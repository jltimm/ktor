/*
 * Copyright 2014-2021 JetBrains s.r.o and contributors. Use of this source code is governed by the Apache 2.0 license.
 */

package io.ktor.network.mock

import io.ktor.network.selector.*
import java.nio.channels.spi.*
import kotlin.coroutines.*

class SelectorManagerMock: SelectorManager {
    override suspend fun select(selectable: Selectable, interest: SelectInterest) {}

    override val provider: SelectorProvider
        get() = TODO("Not yet implemented")

    override fun notifyClosed(s: Selectable) {
        TODO("Not yet implemented")
    }

    override val coroutineContext: CoroutineContext
        get() = TODO("Not yet implemented")

    override fun close() {
        TODO("Not yet implemented")
    }
}

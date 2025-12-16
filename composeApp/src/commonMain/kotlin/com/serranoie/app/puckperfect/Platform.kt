package com.serranoie.app.puckperfect

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
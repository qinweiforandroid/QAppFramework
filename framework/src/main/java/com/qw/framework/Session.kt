package com.qw.framework

/**
 * Created by qinwei on 2/3/21 9:40 AM
 * email: qinwei_it@163.com
 */
object Session {
    private var session: ISession? = null
    val token: String
        get() = session?.getToken() ?: ""
    val tokenType: String
        get() = session?.getTokenType() ?: ""
    val expiresIn: Long
        get() = session?.getExpiresIn() ?: 0

    val isLogin: Boolean
        get() = session != null

    fun inject(iSession: ISession?) {
        session = iSession
    }

    fun getAuthorization(): String {
        return "$tokenType $token"
    }

    fun logout() {
        session?.logout()
        session = null
    }
}

interface ISession {
    fun getToken(): String
    fun getTokenType(): String
    fun getExpiresIn(): Long
    fun logout()
}
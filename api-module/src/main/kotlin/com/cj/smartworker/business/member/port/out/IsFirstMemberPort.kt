package com.cj.smartworker.business.member.port.out

fun interface IsFirstMemberPort {

    /**
     * ADMIN 권한을 가진 사용자가 한명도 없으면 true, 있다면 false
     */
    fun isFirstMember(): Boolean
}

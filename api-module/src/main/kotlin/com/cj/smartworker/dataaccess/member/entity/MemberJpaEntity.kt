package com.cj.smartworker.dataaccess.member.entity

import com.cj.smartworker.domain.member.valueobject.Deleted
import com.cj.smartworker.domain.member.valueobject.Gender
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "member",
    indexes = [
        Index(name = "member_login_id_index", columnList = "login_id"),
        Index(name = "member_phone_index", columnList = "phone"),
        Index(name = "employee_name_index", columnList = "employee_name"),
    ],
)
class MemberJpaEntity(
    @Id
    @Column(name = "member_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "login_id", nullable = false, unique = true)
    val loginId: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Column(name = "phone", nullable = false)
    val phone: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    val gender: Gender,

    @Column(name = "email")
    val email: String?,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "member_authority",
        joinColumns = [JoinColumn(name = "member_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")],
    )
    val authorities: Set<AuthorityJpaEntity>,

    @Enumerated(EnumType.STRING)
    @Column(name = "deleted", nullable = false)
    val deleted: Deleted,

    @Column(name = "employee_name", nullable = false)
    val employeeName: String,

    @Column(name = "year", nullable = false)
    val year: Int,

    @Column(name = "month", nullable = false)
    val month: Int,

    @Column(name = "day", nullable = false)
    val day: Int,

    @Column(name = "heart_rate_threshold", nullable = false)
    val heartRateThreshold: Int,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MemberJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

package com.cj.smartworker.dataaccess.fcm.entity

import com.cj.smartworker.dataaccess.member.entity.MemberJpaEntity
import com.cj.smartworker.domain.fcm.valueobject.Emergency
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(
    name = "fcm_history",
    indexes = [
//        Index(name = "idx_rounded_xy_small", columnList = "rounded_x_small, rounded_y_small"),
//        Index(name = "idx_rounded_xy_large", columnList = "rounded_x_large, rounded_y_large"),
        Index(name = "idx_created_at", columnList = "created_at"),
    ]
)
@Entity
class FcmHistoryJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_history_id", nullable = false, unique = true)
    val id: Long?,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "fcm_history_admin",
        joinColumns = [JoinColumn(name = "fcm_history_id")],
        inverseJoinColumns = [JoinColumn(name = "member_id")],
    )
    val admins: Set<MemberJpaEntity>, // 알람을 받은 관리자들

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    val reporter: MemberJpaEntity, // 알람을 보낸 사용자

    @Column(name = "x", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val x: Float,

    @Column(name = "y", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val y: Float,

    @Column(name = "rounded_x_small", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val roundedXSmall: Float,

    @Column(name = "rounded_y_small", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val roundedYSmall: Float,

    @Column(name = "rounded_x_large", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val roundedXLarge: Float,

    @Column(name = "rounded_y_large", nullable = false, columnDefinition = "DECIMAL(9,6)")
    val roundedYLarge: Float,

    @Column(name = "emergency", nullable = false)
    @Enumerated(EnumType.STRING)
    val emergency: Emergency,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FcmHistoryJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

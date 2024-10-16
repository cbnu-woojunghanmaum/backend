package com.cj.smartworker.dataaccess.day_report.entity

import com.cj.smartworker.dataaccess.member.entity.MemberJpaEntity
import jakarta.persistence.*
import org.hibernate.annotations.ColumnDefault
import java.time.LocalDateTime

@Entity
@Table(name = "day_report")
class DayReportJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    @JoinColumn(name = "member_id")
    val memberJpaEntity: MemberJpaEntity,

    @Column(name = "move_work")
    val moveWork: Int, // 걸음 수

    @Column(name = "heart_rate")
    var heartRate: String, // 심박수

    @Column(name = "km")
    val km: Double, // 이동거리 km 단위

    @Column(name = "created_at")
    val createdAt: LocalDateTime,

    @Column(name = "is_over_heart_rate")
    var isOver: Boolean, // 심박수 초과 여부, 초과의 경우 true
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DayReportJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

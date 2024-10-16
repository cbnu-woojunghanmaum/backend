package com.cj.smartworker.dataaccess.member.entity

import com.cj.smartworker.domain.member.valueobject.Authority
import jakarta.persistence.*

@Entity
@Table(
    name = "authority",
    indexes = [
        Index(name = "authority_index", columnList = "authority"),
    ],
)
class AuthorityJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "authority_id",
        nullable = false,
        unique = true,
    )
    val id: Long?,

    @Enumerated(EnumType.STRING)
    @Column(
        name = "authority",
        nullable = false,
        unique = true,
    )
    val authority: Authority,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AuthorityJpaEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

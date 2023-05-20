package lxqtpr.ecommerce.linda.auth.userDetails

import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserDetailsImpl (val user: UserEntity) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> =
        user.roles.map { SimpleGrantedAuthority(it.role.name) }
    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
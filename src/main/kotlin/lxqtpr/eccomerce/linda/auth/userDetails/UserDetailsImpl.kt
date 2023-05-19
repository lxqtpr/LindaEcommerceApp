package lxqtpr.eccomerce.linda.auth.userDetails

import lxqtpr.eccomerce.linda.models.UserEntity.models.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserDetailsImpl (val user: UserEntity) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities = HashSet<GrantedAuthority>(user.roles.size)
        for (userRole in user.roles) authorities.add(SimpleGrantedAuthority("ROLE_${userRole.role}"))
        return authorities
    }
    override fun getPassword(): String = user.password
    override fun getUsername(): String = user.email
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}
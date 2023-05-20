package lxqtpr.ecommerce.linda.auth.userDetails

import lxqtpr.ecommerce.linda.models.UserEntity.UserRepository
import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class DetailsService(private val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user: UserEntity = userRepository.findByEmail(email)?: throw UsernameNotFoundException("user not found")
        return UserDetailsImpl(user)
    }
}
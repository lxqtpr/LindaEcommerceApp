package lxqtpr.ecommerce.linda.auth.service

import lxqtpr.ecommerce.linda.auth.jwt.JwtRepository
import lxqtpr.ecommerce.linda.auth.jwt.JwtService
import lxqtpr.ecommerce.linda.auth.jwt.TokenTypeEnum
import lxqtpr.ecommerce.linda.auth.jwt.models.JwtResponse
import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEntity
import lxqtpr.ecommerce.linda.models.RoleEntity.RoleEnum
import lxqtpr.ecommerce.linda.models.UserEntity.UserRepository
import lxqtpr.ecommerce.linda.models.UserEntity.models.CreateUserDto
import lxqtpr.ecommerce.linda.models.UserEntity.models.ResponseUserEntity
import lxqtpr.ecommerce.linda.models.UserEntity.models.UserEntity
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthServiceImpl(
    val authenticationManager: AuthenticationManager,
    val bCrypt: BCryptPasswordEncoder,
                      val userRepository: UserRepository,
                      val jwtRepository: JwtRepository,
                      val jwtService: JwtService

) : AuthService {
    override fun register(createUserDto: CreateUserDto): ResponseUserEntity {
        userRepository.findByEmail(createUserDto.email)?.let { throw Exception("User already exist") }
        val user = userRepository.save(toUserEntity(createUserDto))
        val tokenPair = jwtService.generateTokenPair(user)
        val accessCookie = jwtService.generateTokenCookie(TokenTypeEnum.ACCESS, tokenPair.accessToken)
        val refreshCookie = jwtService.generateTokenCookie(TokenTypeEnum.REFRESH, tokenPair.refreshToken)
        jwtRepository.setRefreshToken(user.email, tokenPair.refreshToken)
        return ResponseUserEntity(user, accessCookie, refreshCookie)
    }


    override fun login(createUserDto: CreateUserDto): JwtResponse {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                createUserDto.email,
                createUserDto.password
            )
        )
        SecurityContextHolder.getContext().authentication = authentication
        val user = userRepository.findByEmail(createUserDto.email) ?: throw  NotFoundException()
        if (bCrypt.matches(createUserDto.password, user.password)) {
            val accessToken: String = jwtService.generateAccessToken(user)
            val refreshToken: String = jwtService.generateRefreshToken(user)
            jwtRepository.setRefreshToken(user.email, refreshToken)
            return JwtResponse(accessToken,refreshToken)
        }
        else{
            throw Exception("Password doesn`t match")
        }
    }

    override fun getAccessToken(refreshToken: String): JwtResponse {
        TODO("Not yet implemented")
    }

    override fun refresh(refreshToken: String): JwtResponse {
        TODO("Not yet implemented")
    }
    private fun toUserEntity(createUserDto: CreateUserDto): UserEntity =
        UserEntity(
            id = 0,
            username = createUserDto.username,
            email = createUserDto.email,
            password = bCrypt.encode(createUserDto.password),
            roles = mutableListOf(RoleEntity(0,RoleEnum.USER))
        )

}
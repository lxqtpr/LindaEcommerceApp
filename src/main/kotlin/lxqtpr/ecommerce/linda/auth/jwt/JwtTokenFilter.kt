package lxqtpr.ecommerce.linda.auth.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lxqtpr.ecommerce.linda.auth.jwt.models.TokenTypeEnum
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtTokenFilter(
    val jwtTokenService: JwtService,
    val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            parseJwt(request, TokenTypeEnum.ACCESS.type)?.let {
                if (jwtTokenService.validateAccessToken(it)) {
                    val userEmail = jwtTokenService.getUserEmailFromClaims(it, TokenTypeEnum.ACCESS)
                    val userDetails = userDetailsService.loadUserByUsername(userEmail)
                    val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authentication
                }
            }
        } catch (e: Exception) {
            logger.error("Cannot set user authentication: {}", e);
        }
        filterChain.doFilter(request, response);
    }

    private fun parseJwt(request: HttpServletRequest, cookieName: String): String? {
        return jwtTokenService.getJwtFromCookies(request, cookieName)
    }
}
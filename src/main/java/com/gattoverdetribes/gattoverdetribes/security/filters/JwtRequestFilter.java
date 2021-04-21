package com.gattoverdetribes.gattoverdetribes.security.filters;

import com.gattoverdetribes.gattoverdetribes.security.exceptions.JwtTokenMissingException;
import com.gattoverdetribes.gattoverdetribes.security.service.PlayerDetailsService;
import com.gattoverdetribes.gattoverdetribes.security.utilities.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  private final PlayerDetailsService playerDetailsService;
  private final JwtUtil jwtUtil;

  @Autowired
  public JwtRequestFilter(PlayerDetailsService playerDetailsService, JwtUtil jwtUtil) {
    this.playerDetailsService = playerDetailsService;
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getRequestURI().startsWith("/register")
        || request.getRequestURI().startsWith("/login")) {
      filterChain.doFilter(request, response);
      return;
    }
    String jwt = parseJwt(request);
    if (jwt != null) {
      String username = jwtUtil.extractUsername(jwt);
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = playerDetailsService.loadUserByUsername(username);
        if (jwtUtil.validateToken(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken auth =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }
    } else {
      throw new JwtTokenMissingException();
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader("X-tribes-token");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      return authorizationHeader.substring(7, authorizationHeader.length());
    }
    return null;
  }
}

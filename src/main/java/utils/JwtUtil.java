package utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtUtil - Utilidad PROPIA para manejo de tokens JWT
 * PROPÓSITO: Crear y validar tokens de autenticación personalizados
 */
public class JwtUtil {
    
    // Clave secreta propia - En producción debe estar en variables de entorno
    private static final String SECRET_KEY = "MiProyectoAcademicoWebJWTSecretKey2025UDC";
    private static final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    
    // Tiempo de expiración (24 horas)
    private static final long EXPIRATION_TIME = 24 * 60 * 60 * 1000; // 24 horas en milisegundos
    
    /**
     * Generar token JWT propio con datos del usuario
     */
    public static String generateToken(String email, String nombres) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("nombres", nombres);
        claims.put("role", "STUDENT"); // Rol por defecto
        
        return createToken(claims, email);
    }
    
    /**
     * Crear token con claims personalizados
     */
    private static String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }
    
    /**
     * Validar token JWT
     */
    public static boolean validateToken(String token, String email) {
        try {
            final String tokenEmail = extractEmail(token);
            return tokenEmail.equals(email) && !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Extraer email del token
     */
    public static String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    
    /**
     * Extraer nombres del token
     */
    public static String extractNombres(String token) {
        return extractClaim(token, claims -> claims.get("nombres", String.class));
    }
    
    /**
     * Extraer fecha de expiración
     */
    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    
    /**
     * Extraer claim específico
     */
    public static <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    
    /**
     * Extraer todos los claims del token
     */
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
    
    /**
     * Verificar si el token ha expirado
     */
    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    /**
     * Obtener información del usuario desde el token
     */
    public static Map<String, Object> getUserInfoFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("email", claims.getSubject());
            userInfo.put("nombres", claims.get("nombres"));
            userInfo.put("role", claims.get("role"));
            userInfo.put("issuedAt", claims.getIssuedAt());
            userInfo.put("expiresAt", claims.getExpiration());
            return userInfo;
        } catch (Exception e) {
            return null;
        }
    }
}

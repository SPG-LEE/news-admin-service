package sq.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

import sq.news.admin.constants.SysConstants;

public class JwtsUtils {
	/**
	 * 创建 jwt
	 * 
	 * @param subject
	 * @author mrlij
	 * @return
	 * @throws Exception
	 */
	public static String createToken(String subject) throws Exception {
		return createJWT(SysConstants.PUBLIC_TOKEN_ID, SysConstants.TOKEN_KEY,
				subject, 0);
	}

	/**
	 * 创建 jwt
	 * 
	 * @param subject
	 * @param ttlMillis
	 * @author mrlij
	 * @return
	 * @throws Exception
	 */
	public static String createToken(String subject, long ttlMillis)
			throws Exception {
		return createJWT(SysConstants.PUBLIC_TOKEN_ID, SysConstants.TOKEN_KEY,
				subject, ttlMillis);
	}

	/**
	 * 创建 jwt
	 * 
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @author mrlij
	 * @return
	 * @throws Exception
	 */
	public static String createToken(String id, String subject, long ttlMillis)
			throws Exception {
		return createJWT(id, SysConstants.TOKEN_KEY, subject, ttlMillis);
	}

	/**
	 * 创建 jwt
	 * 
	 * @param id
	 * @param subject
	 * @param ttlMillis
	 * @author mrlij
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String createJWT(String id, String key, String subject,
			long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now)
				.setSubject(subject).signWith(signatureAlgorithm, key);
		if (ttlMillis > 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	/**
	 * 解密 jwt
	 * 
	 * @param token
	 * @author mrlij
	 * @return
	 * @throws Exception
	 */
	public static String parseJWT(String token) throws Exception {
		Claims claims = Jwts.parser().setSigningKey(SysConstants.TOKEN_KEY)
				.parseClaimsJws(token).getBody();
		try {
			return claims.getSubject();
		} catch (ExpiredJwtException e) {
			System.out.println("token:" + token + " expired");
			return null;
		}
	}

//	public static void main(String[] args) {
//		try {
//			String token1 = JwtsUtils.createToken(SysConstants.SUPERADMIN, 3000);
//			System.out.println(token1);
//			System.out.println(parseJWT(token1));
//			Thread.sleep(2000);
//			System.out.println(parseJWT(token1));
//		} catch (ExpiredJwtException e) {
//			System.out.println("token expired");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}

package com.goldcap.security;


import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Role;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.goldcap.util.Constants.*;

@Component
public class TokenProvider {

    //generate the token
    public String generateToken(Authentication authentication){

        GoldcapUser user = (GoldcapUser) authentication.getPrincipal();
        Date startDate = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(startDate.getTime() + TOKEN_EXPIRATION_TIME);

        System.out.println("Setting expiration date is ms.....");
        System.out.println(expiryDate);
        boolean isAdmin = false;

        String goldcapUserId = Long.toString(user.getId());
        for (Role role : user.getRoles()){
            if (role.getName().equals(ROLE_SUPER_ADMIN) ||
                role.getName().equals(ROLE_ADMIN)) {
                isAdmin = true;
                break;
            }
        }

        //information that we put in token
        //maybe put roles in token
        Map<String , Object> tokenClaims = new HashMap<>();
        tokenClaims.put("id" , Long.toString(user.getId()));
        tokenClaims.put("username" , user.getUsername());
        tokenClaims.put("isAdmin" , isAdmin);

       return Jwts.builder()
               .setSubject(goldcapUserId)
               .setClaims(tokenClaims)
               .setIssuedAt(startDate)
               .setExpiration(expiryDate)
               .signWith(SignatureAlgorithm.HS512 , SECRET)
               .compact();
    }
    //validate the token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            System.out.println("Invalid JWT signature");
        }catch (MalformedJwtException e ) {
            System.out.println("Invalid JWT");
        }catch (ExpiredJwtException e){
            System.out.println("Token has Expired");
        }catch (UnsupportedJwtException e){
            System.out.println("Token not supported");
        }catch (IllegalArgumentException e){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }
    //Get user Id from token
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();

        String id = (String)claims.get("id");

        return Long.parseLong(id);
    }
}

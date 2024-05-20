/**
 * Created by Rohan
 */

package com.Genesis.SwiftSend.Registration.Token;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author rohan
 *
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	VerificationToken findByToken(String token); // aop
}

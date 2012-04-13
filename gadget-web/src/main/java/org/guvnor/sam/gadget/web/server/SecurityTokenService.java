package org.guvnor.sam.gadget.web.server;

import org.apache.shindig.auth.SecurityToken;
import org.apache.shindig.auth.SecurityTokenException;

/**
 * @author: Jeff Yu
 * @date: 12/04/12
 */
public interface SecurityTokenService {

    SecurityToken getSecurityToken(String appUrl, String moduleId, String userId) throws SecurityTokenException;

    String getEncryptedSecurityToken(String appUrl, String moduleId, String userId) throws SecurityTokenException;

    SecurityToken decryptSecurityToken(String encryptedSecurityToken) throws SecurityTokenException;

    String refreshEncryptedSecurityToken(String encryptedSecurityToken) throws SecurityTokenException;
}

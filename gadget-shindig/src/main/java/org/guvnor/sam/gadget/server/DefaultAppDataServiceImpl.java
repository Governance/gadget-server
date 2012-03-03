/*
 * JBoss, Home of Professional Open Source
 * Copyright 2008-12, Red Hat Middleware LLC, and others contributors as indicated
 * by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.guvnor.sam.gadget.server;

import org.apache.shindig.auth.SecurityToken;
import org.apache.shindig.protocol.DataCollection;
import org.apache.shindig.protocol.ProtocolException;
import org.apache.shindig.social.opensocial.spi.AppDataService;
import org.apache.shindig.social.opensocial.spi.GroupId;
import org.apache.shindig.social.opensocial.spi.UserId;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

/**
 * @author: Jeff Yu
 * @date: 18/01/12
 */
public class DefaultAppDataServiceImpl implements AppDataService {

    public Future<DataCollection> getPersonData(Set<UserId> userIds, GroupId groupId, String appId, Set<String> fields, SecurityToken token) throws ProtocolException {
        return null;
    }

    public Future<Void> deletePersonData(UserId userId, GroupId groupId, String appId, Set<String> fields, SecurityToken token) throws ProtocolException {
        return null;
    }

    public Future<Void> updatePersonData(UserId userId, GroupId groupId, String appId, Set<String> fields, Map<String, String> values, SecurityToken token) throws ProtocolException {
        return null;
    }
}

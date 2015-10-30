/*
 * Copyright (c) 2015 Christian Chevalley
 * This file is part of Project Ethercis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ethercis.dao.access.interfaces;

import com.ethercis.dao.access.jooq.EhrAccess;

import java.util.Map;
import java.util.UUID;

import static com.ethercis.dao.jooq.Tables.STATUS;

/**
 * Ehr access layer<br>
 * This interface deals with the main Ehr table as well as Status. Status provides the information
 * related to the actual Ehr owner (eg. patient or Named Subject generally).
 * Created by Christian Chevalley on 4/21/2015.
 */
public interface I_EhrAccess extends I_SimpleCRUD<I_EhrAccess, UUID > {

    /**
     * get a new Ehr access layer instance
     * @param domain SQL access
     * @param partyId owner UUID (patient)
     * @param systemId system on which the Ehr is initiated (UUID)
     * @param directoryId optional directory structure Id
     * @param accessId optional access strategy Id
     * @return I_EhrAccess
     * @throws Exception
     */
    public static I_EhrAccess getInstance(I_DomainAccess domain, UUID partyId, UUID systemId, UUID directoryId, UUID accessId) throws Exception {
        return new EhrAccess(domain.getContext(), partyId, systemId, directoryId, accessId);
    }

    void setModifiable(Boolean modifiable);

    void setQueryable(Boolean queryable);

    /**
     * retrieve an Ehr for a named subject (patient)<br>
     * NB. for security reason, most deployment will not provide an explicit subject name, this method is provided
     * for small deployment or test purpose.
     * @param domainAccess SQL access
     * @param subjectname the name of the patient
     * @return UUID of corresponding Ehr or null
     */
    public static UUID retrieveInstanceByNamedSubject(I_DomainAccess domainAccess, String subjectname) {
        return EhrAccess.retrieveInstanceByNamedSubject(domainAccess, subjectname);
    }

    /**
     * retrieve an Ehr for a subject identification<br>
     * a subject identification consists of the issuer identification (ex. NHS) and an identification code
     * @param domainAccess SQL access
     * @param subjectId the subject code or number
     * @param issuerSpace the issuer identifier
     * @return UUID of corresponding Ehr or null
     */
    public static UUID retrieveInstanceBySubject(I_DomainAccess domainAccess, String subjectId, String issuerSpace) {
        return EhrAccess.retrieveInstanceBySubject(domainAccess, subjectId, issuerSpace);
    }

    /**
     * retrieve an Ehr for a known status entry
     * @param domainAccess SQL access
     * @param status status UUID
     * @return UUID of corresponding Ehr or null
     */
    public static I_EhrAccess retrieveInstanceByStatus(I_DomainAccess domainAccess, UUID status) {
        return EhrAccess.retrieveInstanceByStatus(domainAccess, status);
    }

    public static boolean checkExist(I_DomainAccess domainAccess, UUID partyId){
        return domainAccess.getContext().fetchExists(STATUS, STATUS.PARTY.eq(partyId));
    }

    /**
     * retrieve the Ehr entry from its id
     * @param domainAccess SQL access
     * @param ehrId the Ehr UUID
     * @return UUID of corresponding Ehr or null
     */
    public static I_EhrAccess retrieveInstance(I_DomainAccess domainAccess, UUID ehrId){
        return EhrAccess.retrieveInstance(domainAccess, ehrId);
    }

    /**
     * retrieve the list of identifiers for a subject owning an Ehr<br>
     * the identifiers are formatted as: "CODE:ISSUER"
     * @param domainAccess SQL access
     * @param ehrId the Ehr Id to search the subject from
     * @return a list of identifiers
     */
    public static Map<String, String> fetchSubjectIdentifiers(I_DomainAccess domainAccess, UUID ehrId) {
        return EhrAccess.fetchSubjectIdentifiers(domainAccess, ehrId);
    }

    public static Map<String, Map<String,String>> getCompositionList(I_DomainAccess domainAccess, UUID ehrId) {
        return EhrAccess.getCompositionList(domainAccess, ehrId);
    }


    /**
     * set access id
     * @param access UUID
     */
    void setAccess(UUID access);

    /**
     * set directory id
     * @param directory UUID
     */
    void setDirectory(UUID directory);

    /**
     * set system Id
     * @param system UUID
     */
    void setSystem(UUID system);

    UUID reload();

    /**
     * check if Ehr is newly created (uncommitted)
     * @return true if new, false otherwise
     */
    public boolean isNew();

    UUID getParty();

    void setParty(UUID partyId);

    UUID getId();

    Boolean isModifiable();

    Boolean isQueryable();

    UUID getSystemId();

    UUID getStatusId();

    UUID getDirectoryId();

    UUID getAccessId();
}